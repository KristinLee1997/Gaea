package com.aries.user.gaea.client.utils;

import com.alibaba.fastjson.JSON;
import com.aries.hera.core.utils.PropertiesProxy;
import com.aries.user.gaea.client.factory.GaeaClientFactory;
import com.aries.user.gaea.client.model.GaeaResponse;
import com.aries.user.gaea.client.model.User;
import com.aries.user.gaea.contact.model.CompanyDTO;
import com.aries.user.gaea.contact.model.ThriftResponse;
import com.aries.user.gaea.contact.model.UserLoginDTO;
import com.aries.user.gaea.contact.model.UserRegisterDTO;
import com.aries.user.gaea.contact.service.UserBaseService;
import org.apache.thrift.TException;


public class UserUtils {
    private static final CompanyDTO companyDTO;

    static {
        PropertiesProxy propertiesProxy = new PropertiesProxy("gaea-pass.properties");
        String company = propertiesProxy.readProperty("company");
        String password = propertiesProxy.readProperty("password");
        companyDTO = new CompanyDTO(company, password);
    }

    public static GaeaResponse register(UserRegisterDTO userRegisterDTO) throws TException {
        UserBaseService.Client client = GaeaClientFactory.getUserUtilsSingleClient();
        ThriftResponse userResponse = client.userRegister(companyDTO, userRegisterDTO);
        GaeaResponse response = new GaeaResponse();
        response.setCode(userResponse.getCode());
        response.setMessage(userResponse.getMessage());
        return response;
    }

    public static GaeaResponse login(UserLoginDTO loginDTO) throws TException {
        UserBaseService.Client client = GaeaClientFactory.getUserUtilsSingleClient();
        ThriftResponse userResponse = client.userLogin(companyDTO, loginDTO);
        GaeaResponse response = new GaeaResponse();
        response.setCode(userResponse.getCode());
        response.setMessage(userResponse.getMessage());
        response.setData(JSON.parseObject(userResponse.getData(), User.class));
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

