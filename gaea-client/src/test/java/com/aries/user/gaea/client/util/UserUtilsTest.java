package com.aries.user.gaea.client.util;

import com.aries.hera.client.thrift.ThriftHelper;
import com.aries.user.gaea.client.model.GaeaResponse;
import com.aries.user.gaea.client.model.UserRegisterVo;
import com.aries.user.gaea.client.utils.UserUtils;
import com.aries.user.gaea.contact.model.CompanyDTO;
import com.aries.user.gaea.contact.model.UserLoginDTO;
import com.aries.user.gaea.contact.model.UserRegisterDTO;
import com.aries.user.gaea.contact.service.UserBaseService;
import org.apache.thrift.TException;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class UserUtilsTest {
    /**
     * 用户注册
     * 必填参数：companyName,公司password
     * 选填参数：account,phoneNumber,email,password,wechat,qq
     */
    @Test
    public void registerTest() throws TException {
        UserRegisterVo userRegisterVo = UserRegisterVo.UserRegisterVoBuilder.anUserRegisterVo().
                account("tu5").password("sdsfaaa").bizType(1).build();
//        GaeaResponse response = UserUtils.register(userRegisterVo);
//        System.out.println(response);
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setName("aries");
        companyDTO.setPassword("123123");
        UserRegisterDTO registerDTO = UserRegisterVo.convert2DTO(userRegisterVo);

        ThriftHelper.call(UserBaseService.Client.class, client -> {
            try {
                return client.userRegister(companyDTO, registerDTO);
            } catch (TException e) {
                throw new RuntimeException("-=-", e);
            }
        }, UserBaseService.class.getSimpleName(), "localhost", 6010);
    }

    public byte[] getDefaultImage() {
        StringBuilder sb = new StringBuilder();
        InputStream is = null;
        try {
            is = this.getClass().getResourceAsStream("/images/default_image.jpeg");
            byte[] buffer = new byte[1024];
            while (is.read(buffer, 0, 1024) != -1) {//-1表示读取结束
                sb.append(new String(buffer));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String str = sb.toString();
        if (sb.equals("")) {
            return null;
        } else {
            return str.getBytes();
        }
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
        GaeaResponse response = UserUtils.login(userLoginDTO);
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
