package com.maple.mybatisplus.controller;

import com.maple.mybatisplus.entity.User;
import com.maple.mybatisplus.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户中心-用户信息表 前端控制器
 * </p>
 *
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @since 2023-12-21
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/insert")
    public User insert(@RequestBody User user) {
        userService.save(user);
        return user;
    }

    @GetMapping("/selectById/{id}")
    public User selectById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

}
