package com.maple.inputdb.bean;

import lombok.Data;

/**
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2023/3/10
 */
@Data
public class InitModel {
    /**
     * 数据库相关字段
     */
    private String databaseHost;
    private String databasePort;
    private String databaseName;
    private String user;
    private String password;


    /**
     * 初始化用户
     */
    private String sysUserName;
    private String sysPassword;

}
