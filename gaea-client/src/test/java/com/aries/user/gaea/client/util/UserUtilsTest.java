package com.aries.user.gaea.client.util;

import com.aries.user.gaea.client.model.GaeaResponse;
import com.aries.user.gaea.client.utils.UserUtils;
import com.aries.user.gaea.contact.model.CompanyDTO;
import com.aries.user.gaea.contact.model.UserLoginDTO;
import com.aries.user.gaea.contact.model.UserRegisterDTO;
import org.apache.thrift.TException;
import org.junit.Test;

public class UserUtilsTest {
    /**
     * 用户注册
     * 必填参数：companyName,公司password
     * 选填参数：account,phoneNumber,email,password,wechat,qq
     */
    @Test
    public void registerTest() throws TException {
        CompanyDTO companyDTO = new CompanyDTO() {{
            setName("aries");
            setPassword("123123");
        }};
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setWechat("weixin-kris");
        userRegisterDTO.setBizType(1);
        userRegisterDTO.setBizId(107);
        GaeaResponse response = UserUtils.register(companyDTO, userRegisterDTO);
        System.out.println(response);
    }

    /**
     * 用户登录
     * 必填参数：companyName,公司password,loginId,loginType(0:loginid 4:微信 5:QQ)
     * 选填参数：
     */
    @Test
    public void loginTest() throws TException {
        CompanyDTO companyDTO = new CompanyDTO() {{
            setName("aries");
            setPassword("123123");
        }};
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setLoginId("weixin-kris");
        userLoginDTO.setLoginType(4);
        GaeaResponse response = UserUtils.login(companyDTO, userLoginDTO);
        System.out.println(response.getMessage());
    }

    /**
     * 用户登出
     * 必填参数：companyName,公司password，loginId
     * 选填参数：
     */
    @Test
    public void logoutTest() throws TException {
        CompanyDTO companyDTO = new CompanyDTO() {{
            setName("aries");
            setPassword("123123");
        }};
        GaeaResponse logout = UserUtils.logout(companyDTO, "weixin-kris");
        System.out.println(logout.getCode());
        System.out.println(logout.getMessage());
    }

    /**
     * 通过登录号获取用户登录方式
     * 必填参数：companyName,公司password，loginId
     * 选填参数：password
     */
    @Test
    public void checkLoginTypeTest() throws TException {
        CompanyDTO companyDTO = new CompanyDTO() {{
            setName("aries");
            setPassword("123123");
        }};
        GaeaResponse response = UserUtils.checkLoginType(companyDTO, "weixin-kris");
        System.out.println(response.getMessage());
        System.out.println(response.getData());
    }
}
