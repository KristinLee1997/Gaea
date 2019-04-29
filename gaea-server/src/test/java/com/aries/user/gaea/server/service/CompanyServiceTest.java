package com.aries.user.gaea.server.service;

import com.aries.user.gaea.server.model.po.Company;
import com.aries.user.gaea.server.service.impl.CompanyServiceImpl;
import com.aries.user.gaea.server.utils.UUIDUtils;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;


public class CompanyServiceTest {
    @Test
    public void registerTest() {
        Company company = new Company() {{
            setName("aries");
            setPassword("123123");
        }};
        final String registerno = new CompanyServiceImpl().register(company);
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

    @Test
    public void test() {
        System.out.println(UUIDUtils.getUUID());
    }
}
