package com.ling.aop;

import com.ling.mapper.OperateLogMapper;
import com.ling.pojo.OperateLog;
import com.ling.utils.CurrentHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

// 操作日志切面
@Slf4j
@Aspect
@Component
public class OperateLogAspect {

    @Autowired
    private OperateLogMapper operateLogMapper;
    //private final ObjectMapper objectMapper; // 用于JSON序列化

    @Around("@annotation(com.ling.anno.Log)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        // 1. 记录开始时间
        long begin = System.currentTimeMillis();
        
        // 2. 执行原始方法
        Object result = null;
        try {
            result = joinPoint.proceed(); // 执行被拦截的方法
        } catch (Throwable throwable) {
            // 记录异常信息到日志（可以选择抛出去让全局异常处理器处理）
            throw throwable;
        }

        // 3. 记录结束时间
        long end = System.currentTimeMillis();
        // 4. 计算耗时
        long costTime = end - begin;
        
        // 5. 获取方法信息
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        
        // 6. 获取方法参数
        Object[] args = joinPoint.getArgs();
        String methodParams = Arrays.toString(args);
        
        // 7. 创建OperateLog对象并填充信息
        OperateLog olog = new OperateLog();
        olog.setOperateEmpId(getCurrentEmpId()); // 获取当前操作员工ID
        olog.setOperateTime(LocalDateTime.now()); // 当前时间
        olog.setClassName(className);
        olog.setMethodName(methodName);
        olog.setMethodParams(methodParams);
        olog.setReturnValue(result != null ? result.toString() : "void");
        olog.setCostTime(costTime);
        
        // 8. 保存日志
        operateLogMapper.insert(olog);
        log.info("保存操作日志: {}", olog);

        // 9. 返回原方法的结果
        return result;
    }
    
    /**
     * 获取当前操作的员工ID
     * 这里需要根据实际情况实现，可能需要从ThreadLocal、SecurityContext等中获取
     */
    private Integer getCurrentEmpId() {
        return CurrentHolder.getCurrent();
    }
}