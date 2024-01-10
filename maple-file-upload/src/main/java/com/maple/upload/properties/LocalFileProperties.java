package com.maple.upload.properties;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 上传本地配置
 *
 * @author 笑小枫
 * @date 2022/7/22
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
@Data
@Configuration
public class LocalFileProperties {

    // ---------------本地文件配置 start------------------
    /**
     * 图片存储路径
     */
    @Value("${file.local.imageFilePath}")
    private String imageFilePath;

    /**
     * 文档存储路径
     */
    @Value("${file.local.docFilePath}")
    private String docFilePath;

    /**
     * 文件限制大小
     */
    @Value("${file.local.maxFileSize}")
    private long maxFileSize;
    // --------------本地文件配置 end-------------------

}
