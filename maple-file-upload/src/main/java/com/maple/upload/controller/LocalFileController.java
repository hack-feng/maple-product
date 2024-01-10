package com.maple.upload.controller;

import com.maple.upload.util.LocalFileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 文件相关操作接口
 *
 * @author 笑小枫
 * @date 2024/1/10
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/example")
public class LocalFileController {

    private final LocalFileUtil fileUtil;

    /**
     * 图片上传
     */
    @PostMapping("/uploadImage")
    public String uploadImage(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("图片内容为空，上传失败!");
        }
        return fileUtil.uploadImage(file);
    }

    /**
     * 文件批量上传
     */
    @PostMapping("/uploadFiles")
    public List<Map<String, Object>> uploadFiles(@RequestParam(value = "file") MultipartFile[] files) {
        return fileUtil.uploadFiles(files);
    }

    /**
     * 批量删除文件
     */
    @PostMapping("/deleteFiles")
    public void deleteFiles(@RequestParam(value = "files") String[] files) {
        fileUtil.deleteFile(files);
    }

    /**
     * 文件下载功能
     */
    @GetMapping(value = "/download/{fileName:.*}")
    public void download(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        try {
            fileUtil.downLoadFile(response, fileName);
        } catch (Exception e) {
            log.error("文件下载失败", e);
        }
    }
}
