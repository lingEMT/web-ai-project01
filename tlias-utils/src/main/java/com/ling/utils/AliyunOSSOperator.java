package com.ling.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 阿里云OSS文件操作工具类
 */
@Component
public class AliyunOSSOperator {

    @Autowired
    AliyunOSSProperties aliyunOSSProperties;

    /**
     * 上传文件到阿里云OSS
     * @param fileBytes 文件字节数组
     * @param fileName 文件名
     * @return 文件在OSS中的访问URL
     */
    public String uploadFile(byte[] fileBytes, String fileName) {
        // 从配置中获取OSS服务端点、存储空间名称和URL前缀
        String endpoint = aliyunOSSProperties.getEndpoint();
        String bucketName = aliyunOSSProperties.getBucketName();
        String urlPrefix = aliyunOSSProperties.getUrlPrefix();

        // 验证参数
        if (fileBytes == null || fileBytes.length == 0) {
            throw new IllegalArgumentException("文件字节数组不能为空");
        }
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("文件名不能为空");
        }

        // 验证类属性是否已设置
        if (endpoint == null || endpoint.trim().isEmpty()) {
            throw new IllegalStateException("OSS服务端点未设置，请先调用setEndpoint方法");
        }
        if (bucketName == null || bucketName.trim().isEmpty()) {
            throw new IllegalStateException("存储空间名称未设置，请先调用setBucketName方法");
        }

        // 从环境变量获取OSS密钥
        String accessKeyId = System.getenv("OSS_ACCESS_KEY_ID");
        String accessKeySecret = System.getenv("OSS_ACCESS_KEY_SECRET");

        // 验证密钥是否已设置
        if (accessKeyId == null || accessKeySecret == null) {
            throw new IllegalStateException("阿里云OSS密钥环境变量未正确配置");
        }

        // 如果URL前缀未设置，则使用默认格式
        if (urlPrefix == null) {
            urlPrefix = "https://" + bucketName + "." + endpoint.split("//")[1] + "/";
        }

        // 构建文件路径（按日期组织）
        String datePath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        String filePath = datePath + "/" + fileName;

        // 创建OSS客户端并上传文件
        OSS ossClient = null;
        try {
            // 使用环境变量中的密钥和类属性创建OSS客户端
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 使用字节数组创建输入流
            ByteArrayInputStream inputStream = new ByteArrayInputStream(fileBytes);

            // 创建PutObjectRequest对象
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, inputStream);

            // 上传文件
            ossClient.putObject(putObjectRequest);

            // 返回文件访问URL
            return urlPrefix + filePath;
        } catch (Exception e) {
            throw new RuntimeException("文件上传到OSS失败: " + e.getMessage(), e);
        } finally {
            // 关闭OSS客户端
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}