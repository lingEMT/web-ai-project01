package com.ling.exception;

import com.ling.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * 全局异常处理类
 * 处理所有异常，返回自定义的错误信息
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 捕获静态资源不存在异常
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Void> handleNoResourceFound(NoResourceFoundException e) {
        String requestPath = e.getResourcePath();
        // 匹配 Chrome DevTools 相关的无效请求路径
        if (requestPath != null && requestPath.contains(".well-known/appspecific/com.chrome.devtools.json")) {
            // 静默返回 404，不打印错误日志
            return ResponseEntity.notFound().build();
        }
        // 其他静态资源不存在的情况，按原有逻辑处理
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //处理重复键异常
    @ExceptionHandler
    public Result handlerDuplicateKeyException(DuplicateKeyException e){
        log.error("程序出错：", e);
        String errMsg = e.getMessage();
        int index = errMsg.indexOf("Duplicate entry");
        if(index != -1){
            String[] arr = errMsg.split(" ");
            return Result.error(arr[2] + "已存在");
        }
        return Result.error("出错，返回");
    }
}
