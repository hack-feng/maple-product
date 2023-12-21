package com.maple.knife4j.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2023/12/21
 */
@Configuration
@EnableSwagger2WebMvc
@AllArgsConstructor
public class Knife4jConfiguration {

    /**
     * 引入Knife4j提供的扩展类
     */
    private final OpenApiExtensionResolver openApiExtensionResolver;

    /**
     * 接口分类：配置模块一的接口
     * 如果只有一个模块，删掉模块二即可
     * 如果有多个，可以继续配置
     */
    @Bean(value = "exampleOne")
    public Docket exampleOne() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("笑小枫演示接口1")
                        .description("笑小枫演示接口1")
                        .termsOfServiceUrl("http://127.0.0.1:8080")
                        .contact(new Contact("笑小枫", "https://www.xiaoxiaofeng.com", "zfzjava@163.com"))
                        .version("1.0")
                        //赋予插件体系
                        .build()
                )
                //分组名称
                .groupName("笑小枫演示接口1")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.maple.knife4j.controller.one"))
                .paths(PathSelectors.any())
                .build()
                //赋予插件体系
                .extensions(openApiExtensionResolver.buildExtensions("笑小枫演示接口1"));
    }

    /**
     * 接口分类：配置模块二的接口
     */
    @Bean(value = "exampleTwo")
    public Docket exampleTwo() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("笑小枫演示接口2")
                        .description("笑小枫演示接口2")
                        .termsOfServiceUrl("http://127.0.0.1:8080")
                        .contact(new Contact("笑小枫", "https://www.xiaoxiaofeng.com", "zfzjava@163.com"))
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("笑小枫演示接口2")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.maple.rest.controller.two"))
                .paths(PathSelectors.any())
                .build();
    }
}
