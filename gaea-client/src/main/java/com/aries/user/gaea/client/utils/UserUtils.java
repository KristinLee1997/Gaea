package com.aries.user.gaea.client.utils;

import com.aries.user.gaea.client.factory.GaeaClientFactory;
import com.aries.user.gaea.client.model.GaeaResponse;
import com.aries.user.gaea.contact.model.UserRegisterDTO;
import com.aries.user.gaea.contact.model.UserResponse;
import com.aries.user.gaea.contact.service.UserBaseService;
import org.apache.thrift.TException;


public class UserUtils {
    public static GaeaResponse register(UserRegisterDTO userRegisterDTO) throws TException {
        UserBaseService.Client client = GaeaClientFactory.getUserUtilsSingleClient();
        UserResponse userResponse = client.userRegister(userRegisterDTO);
        GaeaResponse response = new GaeaResponse();
        response.setCode(userResponse.getCode());
        response.setMessage(userResponse.getMessage());
        return response;
    }

    public static GaeaResponse login() {
return new GaeaResponse();
    }

    public static GaeaResponse logout() {
        return new GaeaResponse();

    }

    public static GaeaResponse checkLoginType() {
        return new GaeaResponse();

    }
}

