create table company
(
    id         bigint auto_increment
        primary key,
    name       varchar(100) null comment '公司名',
    add_time   timestamp    null comment '注册时间',
    password   varchar(50)  null comment '密码',
    registerno varchar(50)  null comment '注册码',
    db_name    varchar(50)  null comment '数据库名'
);

create table user
(
    id           bigint auto_increment
        primary key,
    nickname     varchar(50)                         null comment '昵称',
    account      varchar(50)                         null comment '账号',
    phone_number varchar(20)                         null comment '手机号',
    email        varchar(50)                         null comment '邮箱',
    password     varchar(50)                         null comment '密码',
    salt         varchar(50)                         null comment '盐',
    wechat       varchar(50)                         null comment '微信号',
    qq           varchar(50)                         null comment 'QQ号',
    image        longblob                            null comment '头像图片',
    biz_type     int       default 0                 not null comment '账号属性 0：业务方，1：员工',
    add_time     timestamp default CURRENT_TIMESTAMP not null comment '用户注册时间'
);

create table login_cookie
(
    id         bigint auto_increment
        primary key,
    login_id   varchar(50) null comment '登录号',
    cookie     varchar(50) null comment 'cookie',
    add_time   timestamp   null comment '最近一次登录时间',
    login_type int         null comment '登录方式'
);
create table active
(
    id        bigint    not null primary key,
    site_date timestamp null comment '活跃量日期',
    activenum bigint    null comment '活跃量'
);
