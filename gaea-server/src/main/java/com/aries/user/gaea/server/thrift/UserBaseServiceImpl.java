package com.aries.user.gaea.server.thrift;

import com.aries.user.gaea.contact.model.CompanyDTO;
import com.aries.user.gaea.contact.model.ThriftResponse;
import com.aries.user.gaea.contact.service.UserBaseService;
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
        String format = "account:%s,phone-number:$s,wechat:%s,qq:%s注册失败！";
        if (id == null) {
            return DATABASE_ERROR.of();
        }
        return SUCCESS.of();
    }

    @Override
    public ThriftResponse userLogin(CompanyDTO companyDTO, UserLoginDTO userLoginDTO) throws TException {
        CompanyHelper companyHelper = new CompanyHelper(companyDTO).check();
        if (companyHelper.isError()) {
            companyHelper.getResponse();
        }
        int res = userService.login(companyHelper.getDatabaseName(), userLoginDTO.getLoginId(),
                userLoginDTO.getPassword(), userLoginDTO.getLoginType());
        if (res <= 0) {
            return DATABASE_ERROR.of();
        }
        return SUCCESS.of();
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
    public int checkLoginType(CompanyDTO companyDTO, String loginId) throws TException {
        return 0;
    }

    @Override
    public int checkOnline(CompanyDTO companyDTO, String loginId) throws TException {
        return 0;
    }
}
