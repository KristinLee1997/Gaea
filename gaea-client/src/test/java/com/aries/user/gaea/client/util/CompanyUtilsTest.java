package com.aries.user.gaea.client.util;

import com.aries.user.gaea.client.model.GaeaResponse;
import com.aries.user.gaea.client.utils.CompanyUtils;
import com.aries.user.gaea.contact.model.CompanyRegisterDTO;
import org.apache.thrift.TException;
import org.junit.Test;

public class CompanyUtilsTest {
    @Test
    public void registerTest() throws TException {
        CompanyRegisterDTO companyRegisterDTO = new CompanyRegisterDTO();
        companyRegisterDTO.setName("eagle");
        companyRegisterDTO.setPassword("abcabc");
        GaeaResponse register = CompanyUtils.register(companyRegisterDTO);
        if (register != null) {
            System.out.println(register.getCode());
            System.out.println(register.getMessage());
        }
    }

    @Test
    public void getRegisterNO() {

    }
}
