package com.aries.user.gaea.server.thrift;

import com.aries.user.gaea.server.service.UserService;
import com.aries.user.gaea.server.service.impl.UserServiceImpl;
import com.aries.user.gaea.contact.model.UserLoginDTO;
import com.aries.user.gaea.contact.model.UserRegisterDTO;
import com.aries.user.gaea.contact.model.UserResponse;
import com.aries.user.gaea.contact.service.UserBaseService;
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
        Long id = userService.register(userRegisterDTO);
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
        UserResponse response = new UserResponse();
        if (userLoginDTO.getCompanyName() == null || userLoginDTO.getLoginId() == null) {
            response.setCode(400);
            response.setMessage("登录信息不完整，请填写完整后重新登录");
            return response;
        }
        int res = userService.login(userLoginDTO.getCompanyName(), userLoginDTO.getLoginId(),
                userLoginDTO.getPassword(), userLoginDTO.getLoginType());
        if (res <= 0) {
            response.setCode(500);
            response.setMessage("登录失败，请联系RD进行检查");
            return response;
        }
        response.setCode(200);
        response.setMessage("登录成功");
        return response;
    }

    @Override
    public UserResponse userLogout(String companyName, String loginId) throws TException {
        UserResponse response = new UserResponse();
        if (companyName == null || loginId == null) {
            response.setCode(400);
            response.setMessage("公司名和");
        }
        userService.logout(companyName, loginId);
        response.setCode(200);
        response.setMessage(String.format("登录号:%s,用户成功退出登录！", loginId));
        return response;
    }

    @Override
    public int checkLoginType(String companyName, String loginId) throws TException {
        return userService.getLoginType(companyName, loginId);
    }

    @Override
    public int checkOnline(String companyName, String loginId) throws TException {
        return 0;
    }
}
