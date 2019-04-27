package com.aries.user.gaea.server.thrift;

import com.aries.user.gaea.server.constants.SysConstants;
import com.aries.user.gaea.server.thrift.UserBaseServiceImpl;
import com.aries.user.gaea.contact.model.UserLoginDTO;
import com.aries.user.gaea.contact.model.UserRegisterDTO;
import com.aries.user.gaea.contact.model.UserResponse;
import org.apache.thrift.TException;
import org.junit.Test;

public class UserBaseServiceTest {
    /**
     * 用户注册
     * 必填参数：companyName,bizType,bizId
     * 选填参数：account,phoneNumber,email,password,wechat,qq
     */
    @Test
    public void useWechatRegisterTest() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setCompanyName("aries");
        userRegisterDTO.setWechat("glod");
        userRegisterDTO.setBizType(1);
        userRegisterDTO.setBizId(107);
        try {
            UserResponse response = new UserBaseServiceImpl().userRegister(userRegisterDTO);
            System.out.println(response.getCode());
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void useAccountRegister() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setCompanyName("aries");
        userRegisterDTO.setAccount("huahua");
        userRegisterDTO.setPassword("12341234");
        userRegisterDTO.setPhoneNumber("18211001100");
        userRegisterDTO.setEmail("18211001100@163.com");
        userRegisterDTO.setBizType(1);
        userRegisterDTO.setBizId(107);
        try {
            UserResponse response = new UserBaseServiceImpl().userRegister(userRegisterDTO);
            System.out.println(response.getCode());
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void useQQRegister() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setCompanyName("aries");
        userRegisterDTO.setQq("krisqq");
        userRegisterDTO.setBizType(1);
        userRegisterDTO.setBizId(107);
        try {
            UserResponse response = new UserBaseServiceImpl().userRegister(userRegisterDTO);
            System.out.println(response.getCode());
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户登录
     * 必填参数：companyName,loginId,loginType
     * 选填参数：password
     */
    @Test
    public void userLoginTest() throws TException {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setCompanyName("aries");
        userLoginDTO.setLoginId("weixin_glod");
        userLoginDTO.setLoginType(SysConstants.WECHAT_LOGIN_TYPE);
        UserResponse response = new UserBaseServiceImpl().userLogin(userLoginDTO);
        System.out.println(response.getCode());
    }

    /**
     * 用户登出
     * 必填参数：companyName,loginId
     * 选填参数：
     */
    @Test
    public void logout() throws TException {
        UserResponse response = new UserBaseServiceImpl().userLogout("aries", "weixinkristinlee");
        System.out.println(response);
        if (response != null) {
            System.out.println(response.getCode());
        }
    }

    /**
     * 通过登录号获取用户登录方式
     * 必填参数：
     * 选填参数：
     */
    @Test
    public void getLoginType() throws TException {
        int type = new UserBaseServiceImpl().checkLoginType("aries", "weixin_glod2");
        System.out.println(type);
    }
}
