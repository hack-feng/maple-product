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
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云OSS配置
 *
 * @author zhangfuzeng
 * @date 2024/1/10
 */
@Data
@Configuration
public class AliOssProperties {

    private String bucketName;

    private String accessKeyId;

    private String accessKeySecret;

    private String endpoint;

}
