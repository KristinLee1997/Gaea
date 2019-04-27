package com.aries.user.gaea.server.utils;

import java.util.UUID;

public class UUIDUtils {
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    public static String getCookie() {
        return UUID.randomUUID().toString();
    }
}
