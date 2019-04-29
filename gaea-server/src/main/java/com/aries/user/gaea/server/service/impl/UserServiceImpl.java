package com.aries.user.gaea.server.service.impl;

import com.aries.user.gaea.server.constants.SysConstants;
import com.aries.user.gaea.server.dao.LoginCookieDao;
import com.aries.user.gaea.server.dao.UserDao;
import com.aries.user.gaea.server.model.po.LoginCookie;
import com.aries.user.gaea.server.model.po.User;
import com.aries.user.gaea.server.model.po.UserExample;
import com.aries.user.gaea.server.service.UserService;
import com.aries.user.gaea.contact.model.UserRegisterDTO;
import com.aries.user.gaea.server.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class UserServiceImpl implements UserService {
    @Override
    public Long register(String database, UserRegisterDTO userRegisterDTO) {
        if (userRegisterDTO.getAccount() != null || userRegisterDTO.getPhoneNumber() != null
                || userRegisterDTO.getEmail() != null) {
            UserExample example = new UserExample();
            UserExample.Criteria criteria1 = example.createCriteria();
            criteria1.andAccountEqualTo(userRegisterDTO.getAccount());
            UserExample.Criteria criteria2 = example.createCriteria();
            criteria2.andPhoneNumberEqualTo(userRegisterDTO.getPhoneNumber());
            UserExample.Criteria criteria3 = example.createCriteria();
            criteria3.andEmailEqualTo(userRegisterDTO.getEmail());
            example.or(criteria1);
            example.or(criteria2);
            example.or(criteria3);
            List<User> userList = UserDao.getUserListByExample(database, example);
            if (userList != null && userList.size() > 0) {
                return null;
            }
        }
        User user = new User();
        user.setAccount(userRegisterDTO.getAccount());
        user.setPhoneNumber(userRegisterDTO.getPhoneNumber());
        user.setPassword(userRegisterDTO.getPassword());
        user.setEmail(userRegisterDTO.getEmail());
        if (userRegisterDTO.getWechat() != null) {
            user.setWechat(userRegisterDTO.getWechat());
        }
        if (userRegisterDTO.getQq() != null) {
            user.setQq(userRegisterDTO.getQq());
        }
        user.setBizType(userRegisterDTO.getBizType());
        user.setNickname(userRegisterDTO.getNickname() == null ? "用户_" + UUIDUtils.getUUID() : userRegisterDTO.getNickname());
        user.setImage(userRegisterDTO.getImage() == null ? getDefaultImage() : userRegisterDTO.getImage());
        return UserDao.register(database, user);
    }

    @Override
    public User login(String database, String loginId, String password, int loginType) {
        User user = null;
        switch (loginType) {
            case SysConstants.WECHAT_LOGIN_TYPE: {
                user = UserDao.wechatLogin(database, loginId);
                break;
            }
            case SysConstants.QQ_LOGIN_TYPE: {
                user = UserDao.qqLogin(database, loginId);
                break;
            }
            case SysConstants.LOGINID_LOGIN_TYPE: {
                user = UserDao.loginIdLogin(database, loginId, password,
                        getTypeByLoginId(database, loginId, loginType));
                break;
            }
        }
        if (user == null) {
            return null;
        }
        LoginCookieDao.insertCookie(database, loginId, getTypeByLoginId(database, loginId, loginType));
        return user;
    }


    @Override
    public int logout(String database, String loginId) {
        return LoginCookieDao.deleteCookie(database, loginId);
    }

    @Override
    public int getTypeByLoginId(String database, String loginId, int loginType) {
        if (loginType == SysConstants.WECHAT_LOGIN_TYPE) {
            return SysConstants.WECHAT_LOGIN_TYPE;
        }
        if (loginType == SysConstants.QQ_LOGIN_TYPE) {
            return SysConstants.QQ_LOGIN_TYPE;
        }
        return getTypeByLoginId(loginId);
    }

    @Override
    public int getLoginType(String database, String loginId) {
        LoginCookie loginCookie = LoginCookieDao.getLoginType(database, loginId);
        if (loginCookie == null) {
            return -1;
        }
        return loginCookie.getLoginType();
    }

    @Override
    public int getTypeByLoginId(String loginId) {
        Pattern accountPattern = Pattern.compile(SysConstants.ACCOUNT_REGEX);
        Matcher accountMatcher = accountPattern.matcher(loginId);
        if (accountMatcher.matches()) {
            return SysConstants.ACCOUNT_LOGIN_TYPE;
        }

        Pattern phoneNumberPattern = Pattern.compile(SysConstants.PHONENUMBER_REGEX);
        Matcher phoneNumberMatcher = phoneNumberPattern.matcher(loginId);
        if (phoneNumberMatcher.matches()) {
            return SysConstants.PHONENUMBER_LOGIN_TYPE;
        }

        Pattern emailPattern = Pattern.compile(SysConstants.EMAIL_REGEX);
        Matcher emailMatcher = emailPattern.matcher(loginId);
        if (emailMatcher.matches()) {
            return SysConstants.EMAIL_LOGIN_TYPE;
        }
        return 0;
    }

    public byte[] getDefaultImage() {
        StringBuilder sb = new StringBuilder();
        InputStream is = null;
        try {
            String path = System.getProperty("user.dir") + "/src/main/resources/images/default_image.jpeg";
            is = new FileInputStream(path);
            byte[] buffer = new byte[1024];
            while (is.read(buffer, 0, 1024) != -1) {//-1表示读取结束
                sb.append(new String(buffer));
            }
        } catch (Exception e) {
            log.error("读取头像图片失败");
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String str = sb.toString();
        if (StringUtils.isBlank(sb)) {
            return null;
        } else {
            return str.getBytes();
        }
    }
}
