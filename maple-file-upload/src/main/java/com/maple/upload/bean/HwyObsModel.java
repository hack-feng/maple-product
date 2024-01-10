package com.maple.upload.bean;

import lombok.Data;

/**
 * @author zhangfuzeng
 * @date 2023/6/15
 */
@Data
public class HwyObsModel {
    private String endPoint;

    private String bucketName;

    private String showUrl;

    private String token;

    private String fileName;

    private String objectKey;
}
