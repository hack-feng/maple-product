package com.maple.upload.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 华为云上传配置
 *
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2024/1/10
 */
@Data
@Configuration
public class HwyObsProperties {

    @Value("${file.obs.bucketName}")
    private String bucketName;

    @Value("${file.obs.accessKey}")
    private String accessKey;

    @Value("${file.obs.secretKey}")
    private String secretKey;

    @Value("${file.obs.endPoint}")
    private String endPoint;

    /**
     * 加速域名，CDN地址
     */
    @Value("${file.obs.showUrl}")
    private String showUrl;
}
