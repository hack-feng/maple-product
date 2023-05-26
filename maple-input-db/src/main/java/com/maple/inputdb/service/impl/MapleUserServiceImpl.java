package com.maple.inputdb.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.inputdb.config.InitConfigProperties;
import com.maple.inputdb.entity.MapleUser;
import com.maple.inputdb.mapper.MapleUserMapper;
import com.maple.inputdb.service.IMapleUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author 笑小枫
 * @since 2023-05-25
 */
@Service
@Slf4j
@AllArgsConstructor
public class MapleUserServiceImpl extends ServiceImpl<MapleUserMapper, MapleUser> implements IMapleUserService {

    private final MapleUserMapper userMapper;

    private final InitConfigProperties initConfigProperties;

    @Override
    public MapleUser getUser(String userName) {
        int count = userMapper.selectCount(Wrappers.lambdaQuery(MapleUser.class).eq(MapleUser::getUserName, userName));
        if (count == 0) {
            initUser(userName);
        }
        return null;
    }


    public MapleUser initUser(String userNameInput) {
        File userFile = new File(initConfigProperties.getInitFilePath() + initConfigProperties.getInitUserName());
        if (userFile.exists()) {
            try (InputStream rs = new FileInputStream(userFile)) {
                Properties properties = new Properties();
                properties.load(rs);
                String userName = properties.getProperty("userName");
                String password = properties.getProperty("password");
                MapleUser systemUser = new MapleUser();
                if (userName.equals(userNameInput)) {
                    systemUser.setUserName(userName);
                    systemUser.setPassword(password);
                    userMapper.insert(systemUser);
                    log.info("初始化管理员用户成功," + userName);
                }
                return systemUser;
            } catch (Exception e) {
                log.info("初始化用户信息失败", e);
            }
        }
        return null;
    }
}
