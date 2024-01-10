package com.maple.upload.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 又拍云上传配置
 *
 * @author zhangfuzeng
 * @date 2024/1/10
 */
@Data
@Configuration
public class UpyOssProperties {

    @Value("${file.upy.bucketName}")
    private String bucketName;

    @Value("${file.upy.userName}")
    private String userName;

    @Value("${file.upy.password}")
    private String password;

    /**
     * 加速域名
     */
    @Value("${file.upy.showUrl}")
    private String showUrl;

}
