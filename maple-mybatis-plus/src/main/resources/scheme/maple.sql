CREATE TABLE `usc_user`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `account`     VARCHAR(30) NULL DEFAULT NULL COMMENT '用户账号',
    `user_name`   VARCHAR(30) NULL DEFAULT NULL COMMENT '用户姓名',
    `nick_name`   VARCHAR(30) NULL DEFAULT NULL COMMENT '用户昵称',
    `email`       VARCHAR(50) NULL DEFAULT '' COMMENT '用户邮箱',
    `phone`       VARCHAR(11) NULL DEFAULT '' COMMENT '手机号码',
    `sex`         CHAR(1) NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
    `avatar`      VARCHAR(100) NULL DEFAULT '' COMMENT '头像地址',
    `salt`        VARCHAR(32) NULL DEFAULT NULL COMMENT '用户加密盐值',
    `password`    VARCHAR(100) NULL DEFAULT '' COMMENT '密码',
    `status`      CHAR(1) NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
    `create_id`   BIGINT(20) NULL DEFAULT NULL COMMENT '创建人id',
    `create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
    `update_id`   BIGINT(20) NULL DEFAULT NULL COMMENT '更新人id',
    `update_time` DATETIME NULL DEFAULT NULL COMMENT '更新时间',
    `delete_flag` TINYINT(1) NULL DEFAULT '0' COMMENT '删除标志',
    `remark`      VARCHAR(500) NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) COMMENT='用户中心-用户信息表' COLLATE='utf8_general_ci' ENGINE=InnoDB;
