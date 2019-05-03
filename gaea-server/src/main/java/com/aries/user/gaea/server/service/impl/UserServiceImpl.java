package com.aries.user.gaea.server.service.impl;

import com.aries.user.gaea.contact.model.UserRegisterDTO;
import com.aries.user.gaea.server.constants.SysConstants;
import com.aries.user.gaea.server.dao.LoginCookieDao;
import com.aries.user.gaea.server.dao.UserDao;
import com.aries.user.gaea.server.model.po.LoginCookie;
import com.aries.user.gaea.server.model.po.User;
import com.aries.user.gaea.server.model.po.UserExample;
import com.aries.user.gaea.server.service.UserService;
import com.aries.user.gaea.server.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
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
            if (userRegisterDTO.getAccount() != null) {
                UserExample.Criteria criteria1 = example.createCriteria();
                criteria1.andAccountEqualTo(userRegisterDTO.getAccount());
                example.or(criteria1);
            }
            if (userRegisterDTO.getPhoneNumber() != null) {
                UserExample.Criteria criteria2 = example.createCriteria();
                criteria2.andPhoneNumberEqualTo(userRegisterDTO.getPhoneNumber());
                example.or(criteria2);
            }
            if (userRegisterDTO.getEmail() != null) {
                UserExample.Criteria criteria3 = example.createCriteria();
                criteria3.andEmailEqualTo(userRegisterDTO.getEmail());
                example.or(criteria3);
            }
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
        if (userRegisterDTO.getImage() == null || userRegisterDTO.getImage().length <= 0) {
            try {
                byte[] imageByte = getDefaultImage();
                user.setImage(imageByte);
            } catch (IOException e) {
                log.error("用户注册时获取默认图片失败");
                return 0L;
            }
        } else {
            user.setImage(userRegisterDTO.getImage());
        }
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
    public User getUserInfoById(String database, Long id) {
        return UserDao.getUserInfoById(database, id);
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

    public byte[] getDefaultImage() throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        InputStream inputStream = new FileInputStream(SysConstants.DEFAULT_USER_PROFILE_PHOTO_PATH);
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }


        inputStream.close();
        return outStream.toByteArray();
    }
}
