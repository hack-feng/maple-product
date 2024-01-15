package com.maple.upload.bean;

import lombok.Data;

/**
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2023/6/15
 */
@Data
public class HwyObsModel {
    
    private String endPoint;

    private String bucketName;

    private String showUrl;

    private String token;

    private String objectKey;
}
