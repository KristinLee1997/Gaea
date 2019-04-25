package com.aries.user.thrift;

import com.aries.user.gaea.model.dto.UserRegisterDTO;
import com.aries.user.gaea.model.dto.UserResponse;
import com.aries.user.gaea.thrift.impl.UserBaseServiceImpl;
import org.apache.thrift.TException;
import org.junit.Test;

public class UserBaseServiceTest {
    @Test
    public void userRegisterTest() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setCompanyName("aries");
        userRegisterDTO.setAccount("long");
        userRegisterDTO.setPassword("abcdefg");
        try {
            UserResponse response = new UserBaseServiceImpl().userRegister(userRegisterDTO);
            System.out.println(response.getCode());
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}
