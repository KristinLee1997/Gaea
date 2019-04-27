package com.aries.user.gaea.client.util;

import com.aries.user.gaea.client.model.GaeaResponse;
import com.aries.user.gaea.client.utils.UserUtils;
import com.aries.user.gaea.contact.model.UserLoginDTO;
import com.aries.user.gaea.contact.model.UserRegisterDTO;
import org.apache.thrift.TException;
import org.junit.Test;

public class UserUtilsTest {
    /**
     * 用户注册
     * 必填参数：companyName,bizType,bizId
     * 选填参数：account,phoneNumber,email,password,wechat,qq
     */
    @Test
    public void registerTest() throws TException {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setCompanyName("aries");
        userRegisterDTO.setWechat("weixin-kris");
        userRegisterDTO.setBizType(1);
        userRegisterDTO.setBizId(107);
        GaeaResponse response = UserUtils.register(userRegisterDTO);
        System.out.println(response);
    }
    /**
     * 用户登录
     * 必填参数：companyName,loginId,loginType(0:loginid 4:微信 5:QQ)
     * 选填参数：password
     */
    @Test
    public void loginTest() throws TException {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setCompanyName("aries");
        userLoginDTO.setLoginId("weixin-kris");
        userLoginDTO.setLoginType(4);
        GaeaResponse response = UserUtils.login(userLoginDTO);
        System.out.println(response.getMessage());
    }

    /**
     * 用户登出
     * 必填参数：companyName,loginId
     * 选填参数：
     */
    @Test
    public void logoutTest() throws TException {
        GaeaResponse logout = UserUtils.logout("aries", "weixin-kris");
        System.out.println(logout.getCode());
        System.out.println(logout.getMessage());
    }

    /**
     * 通过登录号获取用户登录方式
     * 必填参数：companyName,loginId
     * 选填参数：password
     */
    @Test
    public void checkLoginTypeTest() throws TException {
        GaeaResponse response = UserUtils.checkLoginType("aries", "weixin-kris");
        System.out.println(response.getMessage());
        System.out.println(response.getData());
    }
}
