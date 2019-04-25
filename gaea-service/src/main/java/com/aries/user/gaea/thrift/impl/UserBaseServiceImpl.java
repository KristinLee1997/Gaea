package com.aries.user.gaea.thrift.impl;

import com.aries.user.gaea.model.dto.UserLoginDTO;
import com.aries.user.gaea.model.dto.UserRegisterDTO;
import com.aries.user.gaea.model.dto.UserResponse;
import com.aries.user.gaea.model.po.User;
import com.aries.user.gaea.service.UserService;
import com.aries.user.gaea.service.impl.UserServiceImpl;
import com.aries.user.gaea.thrift.UserBaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;

@Slf4j
public class UserBaseServiceImpl implements UserBaseService.Iface {
    private static UserService userService = new UserServiceImpl();

    @Override
    public UserResponse userRegister(UserRegisterDTO userRegisterDTO) throws TException {
        UserResponse response = new UserResponse();
        if (userRegisterDTO.getCompanyName() == null) {
            response.setCode(400);
            response.setMessage("请准确填写公司名称");
            return response;
        }
        if ((userRegisterDTO.getAccount() == null && userRegisterDTO.getPassword() == null)
                && (userRegisterDTO.getPhoneNumber() == null && userRegisterDTO.getPassword() == null)
                && userRegisterDTO.getWechat() == null
                && userRegisterDTO.getQq() == null) {
            response.setCode(400);
            response.setMessage("注册信息不完整，请填写完整后重新注册");
            return response;
        }
        User user = new User();
        user.setAccount(userRegisterDTO.getAccount());
        user.setPhoneNumber(userRegisterDTO.getPhoneNumber());
        user.setPassword(userRegisterDTO.getPassword());
        user.setWechat(userRegisterDTO.getWechat());
        user.setQq(userRegisterDTO.getQq());
        user.setBizType(userRegisterDTO.getBizType());
        user.setBizId(userRegisterDTO.getBizId());
        Long id = userService.register(userRegisterDTO.getCompanyName(), user);
        String format = "account:%s,phone-number:$s,wechat:%s,qq:%s注册失败！";
        if (id == null) {
            response.setCode(500);
            response.setMessage(String.format(format, userRegisterDTO.getAccount(), userRegisterDTO.getPhoneNumber(),
                    userRegisterDTO.getWechat(), userRegisterDTO.getQq()));
            return response;
        }
        response.setCode(200);
        response.setMessage("注册成功！");
        return response;
    }

    @Override
    public UserResponse userLogin(UserLoginDTO userLoginDTO) throws TException {
        return null;
    }

    @Override
    public UserResponse userLogout(String companyName, String account) throws TException {
        return null;
    }

    @Override
    public int checkLoginType(String companyName, String account) throws TException {
        return 0;
    }

    @Override
    public int checkOnline(String companyName, String account) throws TException {
        return 0;
    }
}
