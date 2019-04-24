package com.aries.user.gaea.utils;

public class DateSourceUrlUtil {
    public static String getDataSourceUrl(String ip, String port, String databaseName) {
        return "jdbc:mysql://" + ip + ":" + port + "/" + databaseName
                + "?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true";
    }
}
