package com.maple.upload.util;

import com.maple.upload.properties.UpyOssProperties;
import com.upyun.RestManager;
import com.upyun.UpException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * 又拍云 对象存储工具类
 * 又拍云客户端配置：https://help.upyun.com/knowledge-base/quick_start/
 * 又拍云官方sdk：https://github.com/upyun/java-sdk
 *
 * @author 笑小枫
 * @date 2022/7/22
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
@Slf4j
@Component
@AllArgsConstructor
public class UpyOssUtil {

    private final UpyOssProperties fileProperties;

    /**
     * 根据url上传文件到七牛云
     */
    public String uploadUpy(String url) {
        // 组建上传的文件名称，命名规则可自定义更改
        String fileName = FileCommonUtil.setFilePath("xiaoxiaofeng") + FileCommonUtil.setFileName("xxf", url.substring(url.lastIndexOf(".")));
        RestManager restManager = new RestManager(fileProperties.getBucketName(), fileProperties.getUserName(), fileProperties.getPassword());

        URI u = URI.create(url);
        try (InputStream inputStream = u.toURL().openStream()) {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            Response response = restManager.writeFile(fileName, bytes, null);
            if (response.isSuccessful()) {
                return fileProperties.getShowUrl() + fileName;
            }
        } catch (IOException | UpException e) {
            log.error("又拍云oss上传文件失败", e);
        }
        throw new RuntimeException("又拍云oss上传文件失败，请重试");
    }

    /**
     * MultipartFile上传文件到又拍云
     */
    public String uploadUpy(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (StringUtils.isBlank(fileName)) {
            throw new RuntimeException("获取文件信息失败");
        }
        // 组建上传的文件名称，命名规则可自定义更改
        String objectKey = FileCommonUtil.setFilePath("xiaoxiaofeng") + FileCommonUtil.setFileName("xxf", fileName.substring(fileName.lastIndexOf(".")));
        RestManager restManager = new RestManager(fileProperties.getBucketName(), fileProperties.getUserName(), fileProperties.getPassword());

        try (InputStream inputStream = file.getInputStream()) {
            log.info(String.format("又拍云上传开始，原文件名：%s，上传后的文件名：%s", fileName, objectKey));
            Response response = restManager.writeFile(objectKey, inputStream, null);
            log.info(String.format("又拍云上传结束，文件名：%s，返回结果：%s", objectKey, response.isSuccessful()));
            if (response.isSuccessful()) {
                return fileProperties.getShowUrl() + objectKey;
            }
        } catch (IOException | UpException e) {
            log.error("又拍云oss上传文件失败", e);
        }
        throw new RuntimeException("又拍云oss上传文件失败，请重试");
    }
}