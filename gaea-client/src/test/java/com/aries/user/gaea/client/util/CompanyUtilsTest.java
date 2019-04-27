package com.aries.user.gaea.client.util;

import com.aries.user.gaea.client.model.GaeaResponse;
import com.aries.user.gaea.client.utils.CompanyUtils;
import com.aries.user.gaea.contact.model.CompanyRegisterDTO;
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
        CompanyRegisterDTO companyRegisterDTO = new CompanyRegisterDTO();
        companyRegisterDTO.setName("eagle");
        companyRegisterDTO.setPassword("abcabc");
        GaeaResponse response = CompanyUtils.register(companyRegisterDTO);
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
        CompanyRegisterDTO companyRegisterDTO = new CompanyRegisterDTO();
        companyRegisterDTO.setName("eagle");
        companyRegisterDTO.setPassword("abcabc");
        GaeaResponse response = CompanyUtils.getRegisterNO(companyRegisterDTO);
        if (response != null) {
            System.out.println(response.getCode());
            System.out.println(response.getMessage());
            System.out.println(response.getData());
        }
    }
}
