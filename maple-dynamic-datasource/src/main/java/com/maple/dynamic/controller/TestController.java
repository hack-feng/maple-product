package com.maple.dynamic.controller;

import com.alibaba.fastjson2.JSON;
import com.maple.dynamic.test.entity.User;
import com.maple.dynamic.test.service.IUserService;
import com.maple.dynamic.test2.entity.Product;
import com.maple.dynamic.test2.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 笑小枫 <https://xiaoxiaofeng.com/>
 * @date 2023/8/21
 */
@RestController
@AllArgsConstructor
public class TestController {

    private final IUserService userService;

    private final IProductService productService;

    @GetMapping("/test")
    public void test() {
        User user = userService.getById(1);
        Product product = productService.getById(1);
        System.out.println("访问test数据库获取到数据：" + JSON.toJSONString(user));
        System.out.println("访问test2数据库获取到数据：" + JSON.toJSONString(product));
    }


}
