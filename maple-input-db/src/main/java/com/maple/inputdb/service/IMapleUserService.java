package com.maple.inputdb.service;

import com.maple.inputdb.entity.MapleUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author 笑小枫
 * @since 2023-05-25
 */
public interface IMapleUserService extends IService<MapleUser> {

    MapleUser getUser(String userName);
    
}
