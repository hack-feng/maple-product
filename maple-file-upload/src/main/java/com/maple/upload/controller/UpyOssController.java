package com.maple.upload.controller;

import com.maple.upload.util.UpyOssUtil;
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
@RequestMapping("/upyOss")
public class UpyOssController {

    private final UpyOssUtil upyOssUtil;

    /**
     * 上传文件，通过url上传
     *
     * @param url 文件地址
     * @return 访问路径
     */
    @PostMapping("/uploadUrl")
    public String uploadUrl(String url) {
        return upyOssUtil.uploadUpy(url);
    }

    /**
     * 上传文件，通过file上传
     *
     * @param file 文件
     * @return 访问路径
     */
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return upyOssUtil.uploadUpy(file);
    }

}
