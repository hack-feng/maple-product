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
     * @param initModel
     */
    @PostMapping("/initData")
    public void initData(@RequestBody InitModel initModel) {
        initDataConfig.initData(initModel);
    }

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


    @PostMapping("/getUser")
    public MapleUser getUser(String userName) {
        return userService.getUser(userName);
    }
}
