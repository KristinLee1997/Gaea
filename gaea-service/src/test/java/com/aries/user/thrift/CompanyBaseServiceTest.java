package com.aries.user.thrift;

import com.aries.user.gaea.model.dto.CompanyRegisterDTO;
import com.aries.user.gaea.model.dto.CompanyResponse;
import com.aries.user.gaea.thrift.impl.CompanyBaseServiceImpl;
import org.apache.thrift.TException;
import org.junit.Test;

public class CompanyBaseServiceTest {
    @Test
    public void companyRegisterTest() {
        CompanyRegisterDTO companyRegisterDTO = new CompanyRegisterDTO();
        companyRegisterDTO.setName("aries");
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
