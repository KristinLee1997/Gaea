package com.aries.user.gaea.server.thrift;

import com.aries.user.gaea.contact.model.CompanyDTO;
import com.aries.user.gaea.contact.model.ThriftResponse;
import org.apache.thrift.TException;
import org.junit.Test;

public class CompanyBaseServiceTest {
    /**
     * 测试:公司注册接口
     * 必填参数：companyName,password
     */
    @Test
    public void companyRegisterTest() {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setName("aries");
        companyDTO.setPassword("123123");
        CompanyBaseServiceImpl companyBaseService = new CompanyBaseServiceImpl();
        try {
            ThriftResponse response = companyBaseService.companyRegister(companyDTO);
            if (response != null) {
                System.out.println(response.getData());
            }
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过公司名和密码获取注册码
     * 必填参数：companyName,password
     */
    @Test
    public void getRegisterNOTest() {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setName("aries");
        companyDTO.setPassword("123123");
        CompanyBaseServiceImpl companyBaseService = new CompanyBaseServiceImpl();
        try {
            ThriftResponse response = companyBaseService.getRegisterNO(companyDTO);
            if (response != null) {
                System.out.println(response);
                System.out.println(response.getData());
            }
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}
