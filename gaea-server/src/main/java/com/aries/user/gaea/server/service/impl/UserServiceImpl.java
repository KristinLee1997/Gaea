package com.aries.user.gaea.server.service.impl;

import com.aries.user.gaea.contact.model.UserInfo;
import com.aries.user.gaea.contact.model.UserRegisterDTO;
import com.aries.user.gaea.core.utils.DateUtils;
import com.aries.user.gaea.server.constants.SysConstants;
import com.aries.user.gaea.server.dao.LoginCookieDao;
import com.aries.user.gaea.server.dao.UserDao;
import com.aries.user.gaea.server.model.po.LoginCookie;
import com.aries.user.gaea.server.model.po.User;
import com.aries.user.gaea.server.model.po.UserExample;
import com.aries.user.gaea.server.model.vo.UserVo;
import com.aries.user.gaea.server.service.UserService;
import com.aries.user.gaea.server.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        user.setImageId(userRegisterDTO.getImageId());
//        if (userRegisterDTO.getImage() == null || userRegisterDTO.getImage().length <= 0) {
//            try {
//                byte[] imageByte = getDefaultImage();
//                user.setImage(imageByte);
//            } catch (IOException e) {
//                log.error("用户注册时获取默认图片失败");
//                return 0L;
//            }
//        } else {
//            user.setImage(userRegisterDTO.getImage());
//        }
        return UserDao.register(database, user);
    }

    @Override
    public UserVo login(String database, String loginId, String password, int loginType) {
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
        LoginCookie loginCookie = LoginCookieDao.getLoginInfoByLoginId(database, loginId);
        if (loginCookie == null) {
            loginCookie = LoginCookieDao.insertCookie(database, loginId, getTypeByLoginId(database, loginId, loginType));
        }
        return convertUser2UserVO(user, loginCookie);
    }

    private UserVo convertUser2UserVO(User user, LoginCookie loginCookie) {
        return UserVo.UserVoBuilder.anUserVo()
                .id(user.getId())
                .nickname(user.getNickname())
                .account(user.getAccount())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .wechat(user.getWechat())
                .qq(user.getQq())
                .imageId(user.getImageId())
                .bizType(user.getBizType())
                .addTime(user.getAddTime())
                .loginId(loginCookie.getLoginId())
                .cookie(loginCookie.getCookie())
                .loginType(loginCookie.getLoginType())
                .loginTime(loginCookie.getAddTime())
                .build();
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
    public List<UserInfo> getUserListByBizType(String database, Integer bizType) {
        List<User> userList = UserDao.getUserInfoListByBizType(database, bizType);
        List<UserInfo> userInfoList = new ArrayList<>();
        for (User user : userList) {
            UserInfo userInfo = convertUser2UesrInfo(user);
            userInfoList.add(userInfo);
        }
        return userInfoList;
    }

    @Override
    public int updateUserInfoById(String database, UserInfo userInfo) {
        User user = new User();
        user.setId(userInfo.getId());
        user.setNickname(userInfo.getNickname());
        user.setBizType(userInfo.getBizType());
        return UserDao.updateUserInfoById(database, user);
    }

    @Override
    public UserVo getUserInfoByCookie(String database, String cookie) {
        LoginCookie loginCookie = UserDao.getLoginInfoByCookie(database, cookie);
        if (loginCookie == null) {
            log.warn("不存在cookie:{}的用户", cookie);
            return null;
        }
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        int loginType = getTypeByLoginId(database, loginCookie.getLoginId(), loginCookie.getLoginType());
        switch (loginType) {
            case SysConstants.ACCOUNT_LOGIN_TYPE: {
                criteria.andAccountEqualTo(loginCookie.getLoginId());
                break;
            }
            case SysConstants.PHONENUMBER_LOGIN_TYPE: {
                criteria.andPhoneNumberEqualTo(loginCookie.getLoginId());
                break;
            }
            case SysConstants.EMAIL_LOGIN_TYPE: {
                criteria.andEmailEqualTo(loginCookie.getLoginId());
                break;
            }
            case SysConstants.WECHAT_LOGIN_TYPE: {
                criteria.andWechatEqualTo(loginCookie.getLoginId());
                break;
            }
            case SysConstants.QQ_LOGIN_TYPE: {
                criteria.andQqEqualTo(loginCookie.getLoginId());
                break;
            }
        }
        User user = UserDao.getUserInfoByLoginId(database, userExample);
        return convertUser2UserVO(user, loginCookie);
    }

    @Override
    public Map<Long, UserInfo> getUserInfoByIdList(String database, List idList) {
        List<User> userList = UserDao.getUserInfoByIdList(database, idList);
        Map<Long, UserInfo> userMap = new HashMap<>();
        for (User user : userList) {
            userMap.put(user.getId(), convertUser2UesrInfo(user));
        }
        return userMap;
    }

    private UserInfo convertUser2UesrInfo(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setNickname(user.getNickname());
        userInfo.setPhoneNumber(user.getPhoneNumber());
        userInfo.setAccount(user.getAccount());
        userInfo.setEmail(user.getEmail());
        userInfo.setPassword(user.getPassword());
        userInfo.setSalt(user.getSalt());
        userInfo.setWechat(user.getWechat());
        userInfo.setQq(user.getQq());
        userInfo.setBizType(user.getBizType());
        userInfo.setImage(user.getImage());
        userInfo.setImageId(user.getImageId());
        userInfo.setAddTime(DateUtils.converDate2String(user.getAddTime()));
        return userInfo;
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
