package com.maple.upload.util;

import com.alibaba.fastjson.JSON;
import com.maple.upload.bean.HwyObsModel;
import com.maple.upload.properties.HwyObsProperties;
import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.PostSignatureRequest;
import com.obs.services.model.PostSignatureResponse;
import com.obs.services.model.PutObjectResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 华为云OBS 对象存储工具类
 * 华为云OBS官方sdk使用文档：https://support.huaweicloud.com/sdk-java-devg-obs/obs_21_0101.html
 * 华为云OBS操作指南：https://support.huaweicloud.com/ugobs-obs/obs_41_0002.html
 * 华为云各服务应用区域和各服务的终端节点：https://developer.huaweicloud.com/endpoint?OBS
 *
 * @author 笑小枫
 * @date 2022/7/22
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
@Slf4j
@Component
@AllArgsConstructor
public class HwyObsUtil {

    private final HwyObsProperties fileProperties;

    /**
     * 上传华为云obs文件存储
     *
     * @param file 文件
     * @return 文件访问路径， 如果配置CDN，这里直接返回CDN+文件名（objectKey）
     */
    public String uploadFileToObs(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (StringUtils.isBlank(fileName)) {
            throw new RuntimeException("获取文件信息失败");
        }
        // 文件类型
        String fileType = fileName.substring(file.getOriginalFilename().lastIndexOf("."));
        // 组建上传的文件名称，命名规则可自定义更改
        String objectKey = FileCommonUtil.setFilePath("") + FileCommonUtil.setFileName(null, fileType);
        PutObjectResult putObjectResult;
        try (InputStream inputStream = file.getInputStream();
             ObsClient obsClient = new ObsClient(fileProperties.getAccessKey(), fileProperties.getSecretKey(), fileProperties.getEndPoint())) {
            log.info(String.format("华为云obs上传开始，原文件名：%s，上传后的文件名：%s", fileName, objectKey));
            putObjectResult = obsClient.putObject(fileProperties.getBucketName(), objectKey, inputStream);
            log.info(String.format("华为云obs上传结束，文件名：%s，返回结果：%s", objectKey, JSON.toJSONString(putObjectResult)));
        } catch (ObsException | IOException e) {
            log.error("华为云obs上传文件失败", e);
            throw new RuntimeException("华为云obs上传文件失败，请重试");
        }
        if (putObjectResult.getStatusCode() == 200) {
            return putObjectResult.getObjectUrl();
        } else {
            throw new RuntimeException("华为云obs上传文件失败，请重试");
        }
    }

    /**
     * 获取华为云上传token，将token返回给前端，然后由前端上传，这样文件不占服务器端带宽
     *
     * @param fileName 文件名称
     * @return
     */
    public HwyObsModel getObsConfig(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            throw new RuntimeException("The fileName cannot be empty.");
        }
        String obsToken;
        String objectKey = null;
        try (ObsClient obsClient = new ObsClient(fileProperties.getAccessKey(), fileProperties.getSecretKey(), fileProperties.getEndPoint())) {

            String fileType = fileName.substring(fileName.lastIndexOf("."));
            // 组建上传的文件名称，命名规则可自定义更改
            objectKey = FileCommonUtil.setFilePath("") + FileCommonUtil.setFileName("", fileType);

            PostSignatureRequest request = new PostSignatureRequest();
            // 设置表单上传请求有效期，单位：秒
            request.setExpires(3600);
            request.setBucketName(fileProperties.getBucketName());
            if (StringUtils.isNotBlank(objectKey)) {
                request.setObjectKey(objectKey);
            }
            PostSignatureResponse response = obsClient.createPostSignature(request);
            obsToken = response.getToken();
        } catch (ObsException | IOException e) {
            log.error("华为云obs上传文件失败", e);
            throw new RuntimeException("华为云obs上传文件失败，请重试");
        }
        HwyObsModel obsModel = new HwyObsModel();
        obsModel.setBucketName(fileProperties.getBucketName());
        obsModel.setEndPoint(fileProperties.getEndPoint());
        obsModel.setToken(obsToken);
        obsModel.setObjectKey(objectKey);
        obsModel.setShowUrl(fileProperties.getShowUrl());
        return obsModel;
    }
}
