package com.aries.user;

import com.aries.user.gaea.service.impl.CompanyServiceImpl;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;


public class CompanyTest {
    @Test
    public void registerTest() {
        final String registerno = new CompanyServiceImpl().register("aries", "123123");
        System.out.println(registerno);
    }

    @Test
    public void encodeTest() {
        String str = "aries1qa2ws";
        String before = new String(Base64.encodeBase64(str.getBytes(), true));
        System.out.println("加密：" + before);
        String after = new String(Base64.decodeBase64(before));
        System.out.println("解密：" + after);
    }
}
