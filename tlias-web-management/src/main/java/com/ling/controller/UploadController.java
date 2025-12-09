package com.ling.controller;

import com.ling.pojo.Result;
import com.ling.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    // 上传文件到阿里云OSS
    @PostMapping
    public Result upload(MultipartFile file) throws IOException {
        log.info("文件上传, 文件名称: {}", file.getOriginalFilename());
        String url = aliyunOSSOperator.uploadFile(file.getBytes(), file.getOriginalFilename());
        log.info("文件上传成功, 文件URL: {}", url);
        return Result.success(url);
    }
}
