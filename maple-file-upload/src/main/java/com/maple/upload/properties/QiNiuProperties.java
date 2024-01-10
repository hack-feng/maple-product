package com.maple.upload.properties;

import com.maple.upload.config.QiNiuZoneEnum;
import lombok.Data;

/**
 * 七牛云配置
 *
 * @author zhangfuzeng
 * @date 2024/1/10
 */
@Data
public class QiNiuProperties {

    private String accessKey;

    private String secretKey;

    private String bucket;

    private QiNiuZoneEnum zone;

}
