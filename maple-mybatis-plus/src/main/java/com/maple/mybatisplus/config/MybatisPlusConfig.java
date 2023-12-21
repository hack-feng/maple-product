package com.maple.mybatisplus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Date;

/**
 * Mybatis plus配置
 *
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2022/7/10
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.maple.mybatisplus.mapper")
public class MybatisPlusConfig implements MetaObjectHandler {

    /**
     * 新增时,自动填充数据
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("isDeleted", false, metaObject);
        this.setFieldValByName("createId", 1L, metaObject);
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateId", 1L, metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }

    /**
     * 修改时，自动填充数据
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateId", 1L, metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
