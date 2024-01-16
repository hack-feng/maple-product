package com.maple.upload.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 七牛云配置
 *
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2024/1/10
 */
@Data
@Configuration
public class QiNiuProperties {

    @Value("${file.qiniuyun.accessKey}")
    private String accessKey;

    @Value("${file.qiniuyun.secretKey}")
    private String secretKey;

    @Value("${file.qiniuyun.bucket}")
    private String bucket;
    
    @Value("${file.qiniuyun.regionId}")
    private String regionId;

    @Value("${file.qiniuyun.showUrl}")
    private String showUrl;

}
