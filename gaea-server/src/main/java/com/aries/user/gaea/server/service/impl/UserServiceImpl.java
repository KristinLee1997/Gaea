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
    public Long register(UserRegisterDTO userRegisterDTO) {
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
            List<User> userList = UserDao.getUserListByExample(userRegisterDTO.getCompanyName(), example);
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
        return UserDao.register(userRegisterDTO.getCompanyName(), user);
    }

    @Override
    public int login(String companyName, String loginId, String password, int loginType) {
        boolean loginResult = false;
        switch (loginType) {
            case SysConstants.WECHAT_LOGIN_TYPE: {
                loginResult = UserDao.wechatLogin(companyName, loginId);
                break;
            }
            case SysConstants.QQ_LOGIN_TYPE: {
                loginResult = UserDao.qqLogin(companyName, loginId);
                break;
            }
            case SysConstants.LOGINID_LOGIN_TYPE: {
                loginResult = UserDao.loginIdLogin(companyName, loginId, password,
                        getTypeByLoginId(companyName, loginId));
                break;
            }
        }
        if (!loginResult) {
            return 0;
        }
        return LoginCookieDao.insertCookie(companyName, loginId, getTypeByLoginId(companyName, loginId));
    }


    @Override
    public int logout(String companyName, String loginId) {
        return LoginCookieDao.deleteCookie(companyName, loginId);
    }

    @Override
    public int getTypeByLoginId(String companyName, String loginId) {
        if (loginId.substring(0, 6).equals("weixin")) {
            return SysConstants.WECHAT_LOGIN_TYPE;
        }
        if (loginId.substring(0, 2).equals("qq")) {
            return SysConstants.QQ_LOGIN_TYPE;
        }
        return getTypeByLoginId(loginId);
    }

    @Override
    public int getLoginType(String companyName, String loginId) {
        LoginCookie loginCookie = LoginCookieDao.getLoginType(companyName, loginId);
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
