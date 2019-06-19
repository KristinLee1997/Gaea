package com.aries.user.gaea.server.dao;

import com.alibaba.fastjson.JSON;
import com.aries.user.gaea.server.constants.SysConstants;
import com.aries.user.gaea.server.factory.MySqlSessionFactory;
import com.aries.user.gaea.server.mapper.LoginCookieMapper;
import com.aries.user.gaea.server.mapper.UserMapper;
import com.aries.user.gaea.server.model.po.LoginCookie;
import com.aries.user.gaea.server.model.po.LoginCookieExample;
import com.aries.user.gaea.server.model.po.User;
import com.aries.user.gaea.server.model.po.UserExample;
import com.aries.user.gaea.server.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

@Slf4j
public class UserDao {
    public static Long register(String database, User user) {
        try (SqlSession sqlSession = MySqlSessionFactory.openSession(database)) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            if (user.getPassword() != null) {
                user.setSalt(UUIDUtils.getUUID());
                user.setPassword(DigestUtils.md5Hex(user.getPassword() + user.getSalt()));
            }
            userMapper.insertSelective(user);
            return user.getId();
        }
    }

    public static User wechatLogin(String database, String loginId) {
        try (SqlSession sqlSession = MySqlSessionFactory.openSession(database)) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            UserExample userExample = new UserExample();
            userExample.createCriteria().andWechatEqualTo(loginId);
            List<User> userList = userMapper.selectByExampleWithBLOBs(userExample);
            if (!CollectionUtils.isEmpty(userList)) {
                return userList.get(0);
            } else {
                return null;
            }
        }
    }

    public static User qqLogin(String database, String loginId) {
        try (SqlSession sqlSession = MySqlSessionFactory.openSession(database)) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            UserExample userExample = new UserExample();
            userExample.createCriteria().andQqEqualTo(loginId);
            List<User> userList = userMapper.selectByExampleWithBLOBs(userExample);
            if (!CollectionUtils.isEmpty(userList)) {
                return userList.get(0);
            } else {
                return null;
            }
        }
    }

    public static User loginIdLogin(String database, String loginId, String password, int loginType) {
        try (SqlSession sqlSession = MySqlSessionFactory.openSession(database)) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            UserExample userExample = new UserExample();
            UserExample.Criteria criteria = userExample.createCriteria();
            if (loginType == SysConstants.ACCOUNT_LOGIN_TYPE) {
                criteria.andAccountEqualTo(loginId);
            }
            if (loginType == SysConstants.PHONENUMBER_LOGIN_TYPE) {
                criteria.andPhoneNumberEqualTo(loginId);
            }
            if (loginType == SysConstants.EMAIL_LOGIN_TYPE) {
                criteria.andEmailEqualTo(loginId);
            }
            List<User> userList = userMapper.selectByExample(userExample);
            if (CollectionUtils.isEmpty(userList)) {
                return null;
            } else {
                User user = userList.get(0);
                if (DigestUtils.md5Hex(password + user.getSalt()).equals(user.getPassword())) {
                    return user;
                } else {
                    return null;
                }
            }
        }
    }

    public static List<User> getUserListByExample(String database, UserExample example) {
        try (SqlSession sqlSession = MySqlSessionFactory.openSession(database)) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            return mapper.selectByExampleWithBLOBs(example);
        }
    }

    public static User getUserInfoById(String database, Long id) {
        try (SqlSession sqlSession = MySqlSessionFactory.openSession(database)) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            return mapper.selectByPrimaryKey(id);
        }
    }

    public static List<User> getUserInfoListByBizType(String database, Integer bizType) {
        try (SqlSession sqlSession = MySqlSessionFactory.openSession(database)) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            UserExample example = new UserExample();
            example.createCriteria().andBizTypeEqualTo(bizType);
            return mapper.selectByExample(example);
        }
    }

    public static int updateUserInfoById(String database, User user) {
        try (SqlSession sqlSession = MySqlSessionFactory.openSession(database)) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            return mapper.updateByPrimaryKeySelective(user);
        }
    }

    public static LoginCookie getLoginInfoByCookie(String database, String cookie) {
        try (SqlSession sqlSession = MySqlSessionFactory.openSession(database)) {
            LoginCookieMapper loginCookieMapper = sqlSession.getMapper(LoginCookieMapper.class);
            LoginCookieExample example = new LoginCookieExample();
            example.createCriteria().andCookieEqualTo(cookie);
            List<LoginCookie> loginCookies = loginCookieMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(loginCookies)) {
                log.warn("通过cookie：{}，查询用户登录信息结果为空", cookie);
                return null;
            }
            return loginCookies.get(0);
        }
    }

    public static User getUserInfoByLoginId(String database, UserExample userExample) {
        try (SqlSession sqlSession = MySqlSessionFactory.openSession(database)) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<User> userList = userMapper.selectByExample(userExample);
            if (CollectionUtils.isEmpty(userList)) {
                log.warn("通过LoginId：{}，查询用户登录信息结果为空", JSON.toJSONString(userExample));
                return null;
            }
            return userList.get(0);
        }
    }

    public static List<User> getUserInfoByIdList(String database, List idList) {
        try (SqlSession sqlSession = MySqlSessionFactory.openSession(database)) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            UserExample userExample = new UserExample();
            UserExample.Criteria criteria = userExample.createCriteria();
            criteria.andIdIn(idList);
            return mapper.selectByExampleWithBLOBs(userExample);
        }
    }
}
