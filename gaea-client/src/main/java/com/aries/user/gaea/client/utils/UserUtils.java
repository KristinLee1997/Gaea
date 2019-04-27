package com.aries.user.gaea.client.utils;

import com.aries.user.gaea.client.factory.GaeaClientFactory;
import com.aries.user.gaea.client.model.GaeaResponse;
import com.aries.user.gaea.contact.model.UserLoginDTO;
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

    public static GaeaResponse login(UserLoginDTO loginDTO) throws TException {
        UserBaseService.Client client = GaeaClientFactory.getUserUtilsSingleClient();
        UserResponse userResponse = client.userLogin(loginDTO);
        GaeaResponse response = new GaeaResponse();
        response.setCode(userResponse.getCode());
        response.setMessage(userResponse.getMessage());
        return response;
    }

    public static GaeaResponse logout(String companyName, String loginId) throws TException {
        UserBaseService.Client client = GaeaClientFactory.getUserUtilsSingleClient();
        UserResponse userResponse = client.userLogout(companyName, loginId);
        GaeaResponse response = new GaeaResponse();
        response.setCode(userResponse.getCode());
        response.setMessage(userResponse.getMessage());
        return response;

    }

    public static GaeaResponse checkLoginType(String companyName, String loginId) throws TException {
        UserBaseService.Client client = GaeaClientFactory.getUserUtilsSingleClient();
        int type = client.checkLoginType(companyName, loginId);
        GaeaResponse response = new GaeaResponse();
        response.setCode(200);
        response.setMessage("查询登录方式成功");
        response.setData(type);
        return response;

    }
}

