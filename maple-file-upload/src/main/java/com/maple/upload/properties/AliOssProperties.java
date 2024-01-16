/*
 * Copyright (c) 2018-2999 上海合齐软件科技科技有限公司 All rights reserved.
 *
 *
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.maple.upload.properties;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云OSS配置
 *
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2024/1/10
 */
@Data
@Configuration
public class AliOssProperties {

    @Value("${file.oss.bucketName}")
    private String bucketName;

    @Value("${file.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${file.oss.secretAccessKey}")
    private String secretAccessKey;

    @Value("${file.oss.endpoint}")
    private String endpoint;

    @Value("${file.oss.showUrl}")
    private String showUrl;
}
