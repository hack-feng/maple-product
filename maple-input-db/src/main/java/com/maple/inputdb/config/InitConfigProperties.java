package com.maple.inputdb.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author 笑小枫 <https://xiaoxiaofeng.com/>
 * @date 2023/3/10
 */
@Data
@Configuration
public class InitConfigProperties {

    @Value("${init.config.filePath}")
    private String initFilePath;

    @Value("${init.config.dbFileName}")
    private String initDbName;

    @Value("${init.config.userFileName}")
    private String initUserName;

}
