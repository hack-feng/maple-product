package com.maple.inputdb.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2023/3/9
 */
@Slf4j
@Component
public class DynamicDatasourceConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private InitConfigProperties initConfigProperties;

    public Boolean checkDataSource() {
        try {
            Connection connection = dataSource.getConnection();
            connection.close();
            DbStatusSingleton.getInstance().setDbStatus(true);
            return true;
        } catch (Exception e) {
            log.info("获取数据库连接失败，即将重新连接数据库...");
            return addDataSource();
        }
    }

    public Boolean addDataSource() {
        File file = new File(initConfigProperties.getInitFilePath() + initConfigProperties.getInitDbName());
        if (!file.exists()) {
            log.error("连接数据库失败：没有找到db.properties文件");
            return false;
        }
        try (InputStream rs = new FileInputStream(file)) {
            Properties properties = new Properties();
            properties.load(rs);
            HikariConfig config = new HikariConfig(properties);
            config.setPassword(config.getPassword());
            DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
            ds.addDataSource("master", new HikariDataSource(config));
            ds.setPrimary("master");
            DbStatusSingleton.getInstance().setDbStatus(true);
            log.info("连接数据库成功");
            return true;
        } catch (Exception e) {
            log.error("连接数据库失败：" + e);
            return false;
        }
    }

    /**
     * 关闭数据库连接
     */
    public void stopDataSource() {
        try {
            Connection connection = dataSource.getConnection();
            connection.close();
        } catch (Exception e) {
            log.info("数据库连接已关闭，无需重复关闭...");
            return;
        }
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        HikariDataSource hds = (HikariDataSource) ds.getDataSource("master");
        try {
            if (hds.isRunning()) {
                hds.close();
                log.info("数据库连接已关闭");
            }
            ds.setPrimary("null");
            ds.removeDataSource("master");
        } catch (Exception e) {
            log.error("关闭数据库连接失败:", e);
            e.printStackTrace();
        }
    }
}
