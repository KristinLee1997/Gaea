package com.aries.user.gaea.server.service.impl;

import com.aries.user.gaea.server.constants.SysConstants;
import com.aries.user.gaea.server.dao.LoginCookieDao;
import com.aries.user.gaea.server.dao.UserDao;
import com.aries.user.gaea.server.model.po.LoginCookie;
import com.aries.user.gaea.server.model.po.User;
import com.aries.user.gaea.server.model.po.UserExample;
import com.aries.user.gaea.server.service.UserService;
import com.aries.user.gaea.contact.model.UserRegisterDTO;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
        user.setBizId(userRegisterDTO.getBizId());
        return UserDao.register(database, user);
    }

    @Override
    public int login(String database, String loginId, String password, int loginType) {
        boolean loginResult = false;
        switch (loginType) {
            case SysConstants.WECHAT_LOGIN_TYPE: {
                loginResult = UserDao.wechatLogin(database, loginId);
                break;
            }
            case SysConstants.QQ_LOGIN_TYPE: {
                loginResult = UserDao.qqLogin(database, loginId);
                break;
            }
            case SysConstants.LOGINID_LOGIN_TYPE: {
                loginResult = UserDao.loginIdLogin(database, loginId, password,
                        getTypeByLoginId(database, loginId, loginType));
                break;
            }
        }
        if (!loginResult) {
            return 0;
        }
        return LoginCookieDao.insertCookie(database, loginId, getTypeByLoginId(database, loginId, loginType));
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
}
