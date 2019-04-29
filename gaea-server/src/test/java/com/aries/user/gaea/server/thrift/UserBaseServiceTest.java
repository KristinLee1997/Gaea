package com.aries.user.gaea.server.thrift;

import com.aries.user.gaea.contact.model.CompanyDTO;
import com.aries.user.gaea.contact.model.ThriftResponse;
import com.aries.user.gaea.server.constants.SysConstants;
import com.aries.user.gaea.contact.model.UserLoginDTO;
import com.aries.user.gaea.contact.model.UserRegisterDTO;
import org.apache.thrift.TException;
import org.junit.Test;

public class UserBaseServiceTest {
    /**
     * 用户注册
     * 必填参数：companyName,公司password
     * 选填参数：account,phoneNumber,email,password,wechat,qq
     */
    @Test
    public void useWechatRegisterTest() {
        CompanyDTO companyDTO = new CompanyDTO() {{
            setName("aries");
            setPassword("123123");
        }};
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setWechat("glod");
        userRegisterDTO.setBizType(1);
        userRegisterDTO.setBizId(107);
        try {
            ThriftResponse response = new UserBaseServiceImpl().userRegister(companyDTO, userRegisterDTO);
            System.out.println(response.getCode());
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void useAccountRegister() {
        CompanyDTO companyDTO = new CompanyDTO() {{
            setName("aries");
            setPassword("123123");
        }};
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setAccount("peiqi");
        userRegisterDTO.setPassword("12341234");
        userRegisterDTO.setPhoneNumber("18212341234");
        userRegisterDTO.setEmail("18212341234@163.com");
        userRegisterDTO.setBizType(1);
        userRegisterDTO.setBizId(107);
        try {
            ThriftResponse response = new UserBaseServiceImpl().userRegister(companyDTO, userRegisterDTO);
            System.out.println(response.getCode());
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void useQQRegister() {
        CompanyDTO companyDTO = new CompanyDTO() {{
            setName("aries");
            setPassword("123123");
        }};
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setQq("krisqq");
        userRegisterDTO.setBizType(1);
        userRegisterDTO.setBizId(107);
        try {
            ThriftResponse response = new UserBaseServiceImpl().userRegister(companyDTO, userRegisterDTO);
            System.out.println(response.getCode());
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户登录
     * 必填参数：companyName,公司password,loginId,loginType
     * 选填参数：password
     */
    @Test
    public void userLoginTest() throws TException {
        CompanyDTO companyDTO = new CompanyDTO() {{
            setName("aries");
            setPassword("123123");
        }};
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setLoginId("peiqi");
        userLoginDTO.setPassword("12341234");
        userLoginDTO.setLoginType(SysConstants.LOGINID_LOGIN_TYPE);
        ThriftResponse response = new UserBaseServiceImpl().userLogin(companyDTO, userLoginDTO);
        System.out.println(response.getCode());
    }

    /**
     * 用户登出
     * 必填参数：companyName,公司password,loginId
     * 选填参数：
     */
    @Test
    public void logout() throws TException {
        CompanyDTO companyDTO = new CompanyDTO() {{
            setName("aries");
            setPassword("123123");
        }};
        ThriftResponse response = new UserBaseServiceImpl().userLogout(companyDTO, "peiqi");
        System.out.println(response);
        if (response != null) {
            System.out.println(response.getCode());
        }
    }

    /**
     * 通过登录号获取用户登录方式
     * 必填参数：companyName,公司password,loginId
     * 选填参数：password
     */
    @Test
    public void getLoginType() throws TException {
        CompanyDTO companyDTO = new CompanyDTO() {{
            setName("aries");
            setPassword("123123");
        }};
        int type = new UserBaseServiceImpl().checkLoginType(companyDTO, "baibai");
        System.out.println(type);
    }
}
