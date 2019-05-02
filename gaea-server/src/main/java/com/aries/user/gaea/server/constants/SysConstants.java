package com.aries.user.gaea.server.constants;

public class SysConstants {
    // 配置文件路径
    public static final String CONF_PROPERTIES = "gaea-conf.properties";

    // 数据库名称
    public static final String DATABASE_USERCENTER = "usercenter";

    // 正则匹配
    public static final String ACCOUNT_REGEX = "^[A-Za-z][A-Za-z1-9_-]+$";
    public static final String PHONENUMBER_REGEX = "^[1-9]\\d{4,11}$";
    public static final String EMAIL_REGEX = "^[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}$ ";

    // user登录方式
    public static final int LOGINID_LOGIN_TYPE = 0;
    public static final int ACCOUNT_LOGIN_TYPE = 1;
    public static final int PHONENUMBER_LOGIN_TYPE = 2;
    public static final int EMAIL_LOGIN_TYPE = 3;
    public static final int WECHAT_LOGIN_TYPE = 4;
    public static final int QQ_LOGIN_TYPE = 5;

    // 登录名前缀
    public static final String WEIXIN_PREFIX = "weixin_";
    public static final String QQ_PREFIX = "qq_";
}
