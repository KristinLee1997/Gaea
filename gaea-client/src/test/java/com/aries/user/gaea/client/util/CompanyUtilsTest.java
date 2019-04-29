package com.aries.user.gaea.client.util;

import com.aries.user.gaea.client.model.GaeaResponse;
import com.aries.user.gaea.client.utils.CompanyUtils;
import com.aries.user.gaea.contact.model.CompanyDTO;
import org.apache.thrift.TException;
import org.junit.Test;

public class CompanyUtilsTest {
    /**
     * 公司注册接口
     * 必填参数：companyName,password
     *
     * @throws TException
     */
    @Test
    public void registerTest() throws TException {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setName("aries");
        companyDTO.setPassword("123123");
        GaeaResponse response = CompanyUtils.register(companyDTO);
        if (response != null) {
            System.out.println(response.getCode());
            System.out.println(response.getMessage());
        }
    }

    /**
     * 获取公司注册码
     * 必填参数：companyName,password
     *
     * @throws TException
     */
    @Test
    public void getRegisterNO() throws TException {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setName("eagle");
        companyDTO.setPassword("abcabc");
        GaeaResponse response = CompanyUtils.getRegisterNO(companyDTO);
        if (response != null) {
            System.out.println(response.getCode());
            System.out.println(response.getMessage());
            System.out.println(response.getData());
        }
    }
}
