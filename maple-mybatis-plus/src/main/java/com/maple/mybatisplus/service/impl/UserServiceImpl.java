package com.maple.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.mybatisplus.entity.User;
import com.maple.mybatisplus.mapper.UserMapper;
import com.maple.mybatisplus.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户中心-用户信息表 服务实现类
 * </p>
 *
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @since 2023-12-21
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final UserMapper userMapper;

    private void selectTest(User user) {
        // List查询
        List<User> userList = userMapper.selectList(Wrappers.lambdaQuery(User.class)
                // 精确查询
                .eq(User::getAccount, user.getAccount())
                // 存在即查询，不存在即不查询
                .eq(Objects.nonNull(user.getSex()), User::getSex, user.getSex())
                // 模糊查询
                .like(User::getNickName, user.getNickName())
                // 不在数组中
                .notIn(User::getId, 1, 2, 3)
                // 排序
                .orderByDesc(User::getCreateTime)
        );

        // update user对象中，如果为null不更新，如果需要强制更新为null，用set
        int updateNum = userMapper.update(user, Wrappers.lambdaUpdate(User.class)
                .set(User::getPhone, null)
                .eq(User::getId, user.getId()));

        // 删除这里是逻辑删除，修改的deleteFlag=true，如果不配置逻辑删除，则是物理删除
        // 如果配置了逻辑删除，使用mybatis plus提供的查询，查询不出删除的数据；如果直接在xml里面，可以查询出删除的数据
        int deleteNum = userMapper.deleteById(user.getId());
    }
}
