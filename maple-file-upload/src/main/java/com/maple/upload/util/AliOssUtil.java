package com.maple.upload.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.maple.upload.properties.AliOssProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 阿里云OSS 对象存储工具类
 * 阿里云OSS官方sdk使用文档：https://help.aliyun.com/zh/oss/developer-reference/java
 * 阿里云OSS操作指南：https://help.aliyun.com/zh/oss/user-guide
 * 公共云下OSS Region和Endpoint对照表：https://help.aliyun.com/zh/oss/user-guide/regions-and-endpoints
 * 
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2024/1/15
 */
@Slf4j
@Component
@AllArgsConstructor
public class AliOssUtil {

    private final AliOssProperties aliOssProperties;

    public String uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (StringUtils.isBlank(fileName)) {
            throw new RuntimeException("获取文件信息失败");
        }
        // 组建上传的文件名称，命名规则可自定义更改
        String objectKey = FileCommonUtil.setFilePath("xiaoxiaofeng") + FileCommonUtil.setFileName("xxf", fileName.substring(fileName.lastIndexOf(".")));

        //构造一个OSS对象的配置类
        OSS ossClient = new OSSClientBuilder().build(aliOssProperties.getEndpoint(), aliOssProperties.getAccessKeyId(), aliOssProperties.getSecretAccessKey());
        try (InputStream inputStream = file.getInputStream()) {
            log.info(String.format("阿里云OSS上传开始，原文件名：%s，上传后的文件名：%s", fileName, objectKey));
            PutObjectResult result = ossClient.putObject(aliOssProperties.getBucketName(), objectKey, inputStream);
            log.info(String.format("阿里云OSS上传结束，文件名：%s，返回结果：%s", objectKey, result.toString()));
            return aliOssProperties.getShowUrl() + objectKey;
        } catch (Exception e) {
            log.error("调用阿里云OSS失败", e);
            throw new RuntimeException("调用阿里云OSS失败");
        }
    }
}
