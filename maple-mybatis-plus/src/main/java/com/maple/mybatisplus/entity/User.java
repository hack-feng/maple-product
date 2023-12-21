package com.maple.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.maple.mybatisplus.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户中心-用户信息表
 * </p>
 *
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @since 2023-12-21
 */
@Getter
@Setter
@TableName("usc_user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 用户加密盐值
     */
    private String salt;

    /**
     * 密码
     */
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志
     */
    @TableLogic
    private Boolean deleteFlag;

    /**
     * 备注
     */
    private String remark;


}
