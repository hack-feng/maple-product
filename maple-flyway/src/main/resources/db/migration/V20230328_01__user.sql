CREATE TABLE `maple_user`
(
    `id`        BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_name` VARCHAR(64) NOT NULL COMMENT '登录账号',
    `password`  VARCHAR(64) NOT NULL COMMENT '登录密码',
    PRIMARY KEY (`id`) USING BTREE
) COMMENT='用户信息' COLLATE='utf8_general_ci' ENGINE=InnoDB;
