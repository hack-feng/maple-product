package com.maple.inputdb.controller;

import com.maple.inputdb.bean.InitModel;
import com.maple.inputdb.config.InitDataConfig;
import com.maple.inputdb.entity.MapleUser;
import com.maple.inputdb.service.IMapleUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 笑小枫 <https://xiaoxiaofeng.com/>
 * @date 2023/3/9
 */
@RestController
@RequestMapping("/init")
@AllArgsConstructor
public class InitController {

    private final InitDataConfig initDataConfig;

    private final IMapleUserService userService;

    /**
     * 初始化数据库
     *
     * @param initModel 数据库配置
     */
    @PostMapping("/initData")
    public void initData(@RequestBody InitModel initModel) {
        initDataConfig.initData(initModel);
    }

    /**
     * 校验数据库是否链接
     *
     * @return 配置完成
     */
    @PostMapping("/check")
    public String check() {
        return "系统配置完成";
    }

    /**
     * 重置连接数据
     */
    @PostMapping("/resetData")
    public void resetData() {
        initDataConfig.deleteFile();
    }

    /**
     * 获取用户信息，如果不存在，初始化
     *
     * @param userName 用户账号
     * @return 用户信息
     */
    @PostMapping("/getUser")
    public MapleUser getUser(String userName) {
        return userService.getUser(userName);
    }
}
