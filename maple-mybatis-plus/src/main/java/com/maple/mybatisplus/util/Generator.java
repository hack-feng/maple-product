package com.maple.mybatisplus.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.maple.mybatisplus.model.BaseEntity;


/**
 * MyBatis-Plus代码生成工具
 *
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2019/4/25
 */
public class Generator {

    public static void main(String[] args) {

        // 设置作者
        String auth = "笑小枫 <https://www.xiaoxiaofeng.com/>";
        // 设置父包名
        String packageName = "com.maple.mybatisplus";
        // 设置父包模块名
        String moduleName = "";
        // 指定输出目录
        String path = "D:";
        String url = "jdbc:mysql://127.0.0.1:3306/maple?useUnicode=true&useSSL=false&characterEncoding=utf8";
        String username = "root";
        String password = "Zhang123";
        // 设置需要生成的表名,多个
        String table = "usc_user";
        // 设置过滤表前缀
        String[] tablePrefix = {"usc_"};
        generateTest(auth, packageName, path, moduleName, url, username, password, table, tablePrefix);
    }

    private static void generateTest(String auth, String packageName, String path, String moduleName,
                                     String url, String username, String password, String table, String[] tablePrefix) {
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> builder.author(auth)
                        // 开启 swagger 模式
//                        .enableSwagger()
                        .outputDir(path))
                .packageConfig(builder -> builder.parent(packageName)
                                .moduleName(moduleName)
                        // 设置mapperXml生成路径
//                        .pathInfo(Collections.singletonMap(OutputFile.xml, path))
                )
                .strategyConfig(builder -> builder.addInclude(table)
                        .addTablePrefix(tablePrefix)
                        .entityBuilder()
                        // entity父类，这里把系系统字段抽取出来了，不需要删掉即可
                        .superClass(BaseEntity.class)
                        // 是否开启Lombok
                        .enableLombok()
                        // 设置逻辑删除标志
                        .logicDeleteColumnName("delete_flag")
                        .controllerBuilder()
                        .enableRestStyle()
                )
                .execute();
    }
}