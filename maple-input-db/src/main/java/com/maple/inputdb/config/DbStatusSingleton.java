package com.maple.inputdb.config;

/**
 * 数据库是否链接全部变量
 *
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2023/3/23
 */
public class DbStatusSingleton {
    
    /**
     * false：未连接数据库  true：已连接数据库
     */
    private boolean dbStatus = false;

    private static final DbStatusSingleton DB_STATUS_SINGLETON = new DbStatusSingleton();

    private DbStatusSingleton() {
    }

    public static DbStatusSingleton getInstance() {
        return DB_STATUS_SINGLETON;
    }

    public boolean getDbStatus() {
        return dbStatus;
    }

    public void setDbStatus(boolean dbStatus) {
        this.dbStatus = dbStatus;
    }

}
