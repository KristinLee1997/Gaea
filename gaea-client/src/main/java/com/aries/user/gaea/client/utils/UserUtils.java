package com.aries.user.gaea.client.utils;

import com.aries.user.gaea.client.factory.GaeaClientFactory;
import com.aries.user.gaea.client.model.GaeaResponse;
import com.aries.user.gaea.contact.model.CompanyDTO;
import com.aries.user.gaea.contact.model.ThriftResponse;
import com.aries.user.gaea.contact.model.UserLoginDTO;
import com.aries.user.gaea.contact.model.UserRegisterDTO;
import com.aries.user.gaea.contact.service.UserBaseService;
import org.apache.thrift.TException;


public class UserUtils {
    public static GaeaResponse register(CompanyDTO companyDTO, UserRegisterDTO userRegisterDTO) throws TException {
        UserBaseService.Client client = GaeaClientFactory.getUserUtilsSingleClient();
        ThriftResponse userResponse = client.userRegister(companyDTO, userRegisterDTO);
        GaeaResponse response = new GaeaResponse();
        response.setCode(userResponse.getCode());
        response.setMessage(userResponse.getMessage());
        return response;
    }

    public static GaeaResponse login(CompanyDTO companyDTO, UserLoginDTO loginDTO) throws TException {
        UserBaseService.Client client = GaeaClientFactory.getUserUtilsSingleClient();
        ThriftResponse userResponse = client.userLogin(companyDTO, loginDTO);
        GaeaResponse response = new GaeaResponse();
        response.setCode(userResponse.getCode());
        response.setMessage(userResponse.getMessage());
        return response;
    }

    public static GaeaResponse logout(CompanyDTO companyDTO, String loginId) throws TException {
        UserBaseService.Client client = GaeaClientFactory.getUserUtilsSingleClient();
        ThriftResponse userResponse = client.userLogout(companyDTO, loginId);
        GaeaResponse response = new GaeaResponse();
        response.setCode(userResponse.getCode());
        response.setMessage(userResponse.getMessage());
        return response;

    }

    public static GaeaResponse checkLoginType(CompanyDTO companyDTO, String loginId) throws TException {
        UserBaseService.Client client = GaeaClientFactory.getUserUtilsSingleClient();
        int type = client.checkLoginType(companyDTO, loginId);
        GaeaResponse response = new GaeaResponse();
        response.setCode(200);
        response.setMessage("查询登录方式成功");
        response.setData(type);
        return response;

    }
}

