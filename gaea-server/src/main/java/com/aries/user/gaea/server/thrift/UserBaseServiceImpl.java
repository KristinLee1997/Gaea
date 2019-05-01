package com.aries.user.gaea.server.thrift;

import com.alibaba.fastjson.JSON;
import com.aries.user.gaea.contact.model.CompanyDTO;
import com.aries.user.gaea.contact.model.ThriftResponse;
import com.aries.user.gaea.contact.service.UserBaseService;
import com.aries.user.gaea.server.model.po.User;
import com.aries.user.gaea.server.service.UserService;
import com.aries.user.gaea.server.service.impl.UserServiceImpl;
import com.aries.user.gaea.contact.model.UserLoginDTO;
import com.aries.user.gaea.contact.model.UserRegisterDTO;
import com.aries.user.gaea.server.utils.CompanyHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;

import static com.aries.user.gaea.server.constants.GaeaResponseEnum.*;

@Slf4j
public class UserBaseServiceImpl implements UserBaseService.Iface {
    private static UserService userService = new UserServiceImpl();

    @Override
    public ThriftResponse userRegister(CompanyDTO companyDTO, UserRegisterDTO userRegisterDTO) throws TException {
        ThriftResponse response = new ThriftResponse();
        CompanyHelper companyHelper = new CompanyHelper(companyDTO).check();
        if (companyHelper.isError()) {
            companyHelper.getResponse();
        }
        if ((userRegisterDTO.getAccount() == null && userRegisterDTO.getPassword() == null)
                && (userRegisterDTO.getPhoneNumber() == null && userRegisterDTO.getPassword() == null)
                && userRegisterDTO.getWechat() == null
                && userRegisterDTO.getQq() == null) {
            return PARAM_ILLEGAL.of();
        }
        Long id = userService.register(companyHelper.getDatabaseName(), userRegisterDTO);
        if (id == null) {
            return DATABASE_ERROR.of();
        }
        response.setCode(SUCCESS.of().getCode());
        response.setMessage(SUCCESS.of().getMessage());
        response.setData(String.valueOf(id));
        return response;
    }

    @Override
    public ThriftResponse userLogin(CompanyDTO companyDTO, UserLoginDTO userLoginDTO) throws TException {
        ThriftResponse response = new ThriftResponse();
        CompanyHelper companyHelper = new CompanyHelper(companyDTO).check();
        if (companyHelper.isError()) {
            companyHelper.getResponse();
        }
        User user = userService.login(companyHelper.getDatabaseName(), userLoginDTO.getLoginId(),
                userLoginDTO.getPassword(), userLoginDTO.getLoginType());
        if (user == null) {
            return DATABASE_ERROR.of();
        }
        response.setCode(SUCCESS.of().getCode());
        response.setMessage(SUCCESS.of().getMessage());
        response.setData(JSON.toJSONString(user));
        return response;
    }

    @Override
    public ThriftResponse userLogout(CompanyDTO companyDTO, String loginId) throws TException {
        CompanyHelper companyHelper = new CompanyHelper(companyDTO).check();
        if (companyHelper.isError()) {
            companyHelper.getResponse();
        }
        int res = userService.logout(companyHelper.getDatabaseName(), loginId);
        if (res == 0) {
            return SYSTEM_ERROR.of();
        }
        return SUCCESS.of();
    }

    @Override
    public ThriftResponse checkLoginType(CompanyDTO companyDTO, String loginId) throws TException {
        CompanyHelper companyHelper = new CompanyHelper(companyDTO).check();
        ThriftResponse response = new ThriftResponse();
        if (companyHelper.isError()) {
            companyHelper.getResponse();
        }
        int typeByLoginId = userService.getTypeByLoginId(loginId);
        if (typeByLoginId == 0) {
            return SYSTEM_ERROR.of();
        }
        response.setCode(SUCCESS.of().getCode());
        response.setMessage(SUCCESS.of().getMessage());
        response.setData(String.valueOf(typeByLoginId));
        return response;
    }

    @Override
    public ThriftResponse checkOnline(CompanyDTO companyDTO, String loginId) throws TException {
        return SUCCESS.of();
    }
}
