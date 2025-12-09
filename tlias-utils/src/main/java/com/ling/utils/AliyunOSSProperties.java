package com.ling.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// 阿里云OSS配置属性类
// 用于从application.properties或application.yml文件中读取阿里云OSS相关配置
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOSSProperties {
    private String endpoint;
    private String bucketName;
    private String urlPrefix;
}
