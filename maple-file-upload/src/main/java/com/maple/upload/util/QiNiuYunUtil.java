package com.maple.upload.util;

import com.maple.upload.properties.QiNiuProperties;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;

/**
 * 
 * 七牛云 对象存储工具类
 * 七牛云官方sdk：https://developer.qiniu.com/kodo/1239/java
 * 七牛云存储区域表链接：https://developer.qiniu.com/kodo/1671/region-endpoint-fq
 * 
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2022/3/24
 */
@Slf4j
@Component
@AllArgsConstructor
public class QiNiuYunUtil {

    private final QiNiuProperties qiNiuProperties;

    public String uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (StringUtils.isBlank(fileName)) {
            throw new RuntimeException("获取文件信息失败");
        }
        // 组建上传的文件名称，命名规则可自定义更改
        String objectKey = FileCommonUtil.setFilePath("xiaoxiaofeng") + FileCommonUtil.setFileName("xxf", fileName.substring(fileName.lastIndexOf(".")));

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = qiNiuConfig(qiNiuProperties.getRegionId());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        try (InputStream inputStream = file.getInputStream()) {
            Auth auth = Auth.create(qiNiuProperties.getAccessKey(), qiNiuProperties.getSecretKey());
            String upToken = auth.uploadToken(qiNiuProperties.getBucket());
            log.info(String.format("七牛云上传开始，原文件名：%s，上传后的文件名：%s", fileName, objectKey));
            Response response = uploadManager.put(inputStream, objectKey, upToken, null, null);
            log.info(String.format("七牛云上传结束，文件名：%s，返回结果：%s", objectKey, response.toString()));
            return qiNiuProperties.getShowUrl() + objectKey;
        } catch (Exception e) {
            log.error("调用七牛云失败", e);
            throw new RuntimeException("调用七牛云失败");
        }
    }

    private static Configuration qiNiuConfig(String zone) {
        Region region = null;
        if (Objects.equals(zone, "z1")) {
            region = Region.huabei();
        } else if (Objects.equals(zone, "z0")) {
            region = Region.huadong();
        } else if (Objects.equals(zone, "z2")) {
            region = Region.huanan();
        } else if (Objects.equals(zone, "na0")) {
            region = Region.beimei();
        } else if (Objects.equals(zone, "as0")) {
            region = Region.xinjiapo();
        }
        return new Configuration(region);
    }
}
