package com.maple.upload.controller;

import com.maple.upload.bean.HwyObsModel;
import com.maple.upload.util.HwyObsUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2024/1/11
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/hwyObs")
public class HwyObsController {

    private final HwyObsUtil hwyObsUtil;

    /**
     * 上传华为云obs文件存储
     */
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return hwyObsUtil.uploadFileToObs(file);
    }

    /**
     * 获取华为云上传token，将token返回给前端，然后由前端上传，这样文件不占服务器端带宽
     *
     * @param fileName 文件名称，主要取类型
     */
    @PostMapping("/getObsConfig")
    public HwyObsModel getObsConfig(String fileName) {
        return hwyObsUtil.getObsConfig(fileName);
    }
}
