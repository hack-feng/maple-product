package com.maple.upload.util;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2024/1/10
 */
public class FileCommonUtil {

    /**
     * 设置文件上传路径
     * pre传：xiaoxiaofeng  样式如下：
     * /xiaoxiaofeng/24/01/10/
     * pre不传  样式如下：
     * /24/01/10/
     *
     * @param pre 包目录，建议按模块区分，如果无可不传
     * @return 见描述
     */
    public static String setFilePath(String pre) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd/");
        if (StringUtils.isNotBlank(pre)) {
            return "/" + pre.replace("/", "") + "/" + sdf;
        }
        return "/" + sdf;
    }

    /** 
     * 设置文件上传名称
     * pre + yyMMddHHmmss + 4位随机数 + fileType
     * 
     * @param pre      名称前缀 例如：xxf-
     * @param fileType 文件类型 例如：.png,.jpg,.doc
     * @return pre + yyMMddHHmmss + 4位随机数 + fileType
     */
    public static String setFileName(String pre, String fileType) {
        if(StringUtils.isBlank(fileType)){
            throw new RuntimeException("The fileType cannot be empty.");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        return pre + sdf.format(new Date()) + RandomUtils.nextInt(1000, 9999) + fileType;
    }
}
