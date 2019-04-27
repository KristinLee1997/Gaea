package com.aries.user.gaea.server.thrift;

import com.aries.user.gaea.server.factory.MySqlSessionFactory;
import com.aries.user.gaea.contact.model.CompanyRegisterDTO;
import com.aries.user.gaea.contact.model.CompanyResponse;
import com.aries.user.gaea.server.mapper.CompanyMapper;
import com.aries.user.gaea.server.model.po.Company;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.thrift.TException;
import org.junit.Test;

public class CompanyBaseServiceTest {
    /**
     * 测试:公司注册接口
     * 必填参数：companyName,password
     */
    @Test
    public void companyRegisterTest() {
        CompanyRegisterDTO companyRegisterDTO = new CompanyRegisterDTO();
        companyRegisterDTO.setName("hw");
        companyRegisterDTO.setPassword("123123");
        CompanyBaseServiceImpl companyBaseService = new CompanyBaseServiceImpl();
        try {
            CompanyResponse response = companyBaseService.companyRegister(companyRegisterDTO);
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
        CompanyRegisterDTO companyRegisterDTO = new CompanyRegisterDTO();
        companyRegisterDTO.setName("aries");
        companyRegisterDTO.setPassword("123123");
        CompanyBaseServiceImpl companyBaseService = new CompanyBaseServiceImpl();
        try {
            CompanyResponse response = companyBaseService.getRegisterNO(companyRegisterDTO);
            if (response != null) {
                System.out.println(response);
                System.out.println(response.getData());
            }
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}
