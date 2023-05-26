package com.maple.inputdb.config;

import com.maple.inputdb.bean.InitModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author 笑小枫 <https://xiaoxiaofeng.com/>
 * @date 2023/3/10
 */
@Slf4j
@Component
@AllArgsConstructor
public class InitDataConfig {

    private final DynamicDatasourceConfig dynamicDatasourceConfig;

    private final InitConfigProperties initConfigProperties;

    public void initData(InitModel initModel) {

        if (DbStatusSingleton.getInstance().getDbStatus()
                || Boolean.TRUE.equals(dynamicDatasourceConfig.checkDataSource())) {
            throw new RuntimeException("数据已完成初始化，请勿重复操作");
        }

        // 检查数据库连接是否正确
        checkConnection(initModel);

        if (!new File(initConfigProperties.getInitFilePath() + initConfigProperties.getInitUserName()).exists()) {
            File file = createFile(initConfigProperties.getInitFilePath(), initConfigProperties.getInitUserName());
            try (FileWriter out = new FileWriter(file);
                 BufferedWriter bw = new BufferedWriter(out)) {
                bw.write("userName=" + initModel.getSysUserName());
                bw.newLine();
                bw.write("password=" + initModel.getSysPassword());
                bw.flush();
            } catch (IOException e) {
                log.info("写入管理员信息文件失败", e);
                throw new RuntimeException("写入管理员信息文件失败，请重试");
            }
        }

        if (!new File(initConfigProperties.getInitFilePath() + initConfigProperties.getInitDbName()).exists()) {
            File file = createFile(initConfigProperties.getInitFilePath(), initConfigProperties.getInitDbName());
            try (FileWriter out = new FileWriter(file);
                 BufferedWriter bw = new BufferedWriter(out)) {

                bw.write(String.format("jdbcUrl=jdbc:mysql://%s:%s/%s?autoReconnect=true&autoReconnectForPools=true&useUnicode=true&characterEncoding=UTF-8",
                        initModel.getDatabaseHost(), initModel.getDatabasePort(), initModel.getDatabaseName()));
                bw.newLine();
                bw.write("username=" + initModel.getUser());
                bw.newLine();
                bw.write("password=" + initModel.getPassword());
                bw.newLine();
                bw.write("driverClassName=com.mysql.cj.jdbc.Driver");
                bw.flush();

            } catch (IOException e) {
                log.info("写入数据库文件失败", e);
                throw new RuntimeException("写入数据库文件失败，请重试");
            }
        }

        boolean isOk = dynamicDatasourceConfig.checkDataSource();
        if (!isOk) {
            throw new RuntimeException("初始化数据库信息失败，请检查配置是否正确");
        }
    }

    /**
     * 检查数据库连接是否正确
     *
     * @param initModel 连接信息
     */
    private void checkConnection(InitModel initModel) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s",
                    initModel.getDatabaseHost(), initModel.getDatabasePort(), initModel.getDatabaseName()), initModel.getUser(), initModel.getPassword());
            log.info("校验数据库连接成功，开始进行数据库配置" + conn.getCatalog());
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            log.info("校验数据库连接失败", e);
            throw new RuntimeException("初始化数据库信息失败，请检查配置是否正确：" + e.getMessage());
        }
    }

    private File createFile(String path, String fileName) {
        File pathFile = new File(path);
        if (pathFile.mkdirs()) {
            log.info(path + " is not exist, this is auto created.");
        }
        File file = new File(path + File.separator + fileName);
        try {
            if (!file.createNewFile()) {
                throw new RuntimeException(String.format("创建%s文件失败，请重试", fileName));
            }
        } catch (IOException e) {
            log.error(String.format("创建%s文件失败", fileName), e);
            throw new RuntimeException(String.format("创建%s文件失败，请重试", fileName));
        }
        return file;
    }

    public void deleteFile() {
        File sqlFile = new File(initConfigProperties.getInitFilePath() + initConfigProperties.getInitDbName());
        if (sqlFile.exists()) {
            log.info(sqlFile.getName() + " --- delete sql file result:" + sqlFile.delete());
        }

        File userFile = new File(initConfigProperties.getInitFilePath() + initConfigProperties.getInitUserName());
        if (userFile.exists()) {
            log.info(userFile.getName() + " --- delete user file result:" + userFile.delete());
        }

        dynamicDatasourceConfig.stopDataSource();

        // 数据初始化状态设为false
        DbStatusSingleton.getInstance().setDbStatus(false);
        log.info("初始化数据重置完成");
    }
}