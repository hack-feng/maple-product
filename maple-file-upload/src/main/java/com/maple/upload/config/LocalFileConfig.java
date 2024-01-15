package com.maple.upload.config;

import com.maple.upload.properties.LocalFileProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2024/1/10
 */
@Configuration
@AllArgsConstructor
public class LocalFileConfig implements WebMvcConfigurer {

    private final LocalFileProperties localFileProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //重写方法
        //修改tomcat 虚拟映射
        //定义图片存放路径 这里加/example是为了测试避开系统的token校验，实际访问地址根据自己需求来
        registry.addResourceHandler("/example/images/**").
                addResourceLocations("file:" + localFileProperties.getImageFilePath());
        //定义文档存放路径
        registry.addResourceHandler("/example/doc/**").
                addResourceLocations("file:" + localFileProperties.getDocFilePath());
    }
    
}
