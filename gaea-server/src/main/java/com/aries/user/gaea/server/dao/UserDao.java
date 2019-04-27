package com.aries.user.gaea.server.dao;

import com.aries.user.gaea.server.constants.SysConstants;
import com.aries.user.gaea.server.factory.MySqlSessionFactory;
import com.aries.user.gaea.server.mapper.UserMapper;
import com.aries.user.gaea.server.model.po.User;
import com.aries.user.gaea.server.model.po.UserExample;
import com.aries.user.gaea.server.utils.UUIDUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class UserDao {
    public static Long register(String companyName, User user) {
        SqlSessionFactory sqlSessionFactory = MySqlSessionFactory.getSQLSessionFactory(companyName);
        int id = 0;
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            if (user.getPassword() != null) {
                user.setSalt(UUIDUtils.getUUID());
                user.setPassword(DigestUtils.md5Hex(user.getPassword() + user.getSalt()));
            }
            id = userMapper.insert(user);
        }
        return (long) id;
    }

    public static boolean wechatLogin(String companyName, String loginId) {
        SqlSessionFactory sqlSessionFactory = MySqlSessionFactory.getSQLSessionFactory(companyName);
        List<User> userList = null;
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            UserExample userExample = new UserExample();
            userExample.createCriteria().andWechatEqualTo(loginId);
            userList = userMapper.selectByExample(userExample);
        }
        if (userList == null || userList.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean qqLogin(String companyName, String loginId) {
        SqlSessionFactory sqlSessionFactory = MySqlSessionFactory.getSQLSessionFactory(companyName);
        List<User> userList = null;
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            UserExample userExample = new UserExample();
            userExample.createCriteria().andQqEqualTo(loginId);
            userList = userMapper.selectByExample(userExample);
        }
        if (userList == null || userList.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean loginIdLogin(String companyName, String loginId, String password, int loginType) {
        SqlSessionFactory sqlSessionFactory = MySqlSessionFactory.getSQLSessionFactory(companyName);
        List<User> userList = null;
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
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
            userList = userMapper.selectByExample(userExample);
        }
        if (userList == null || userList.size() == 0) {
            return false;
        } else {
            User user = userList.get(0);
            if (DigestUtils.md5Hex(password + user.getSalt()).equals(user.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static List<User> getUserListByExample(String companyName, UserExample example) {
        SqlSessionFactory sqlSessionFactory = MySqlSessionFactory.getSQLSessionFactory(companyName);
        try (SqlSession sqlsession = sqlSessionFactory.openSession(true)) {
            UserMapper mapper = sqlsession.getMapper(UserMapper.class);
            mapper.selectByExample(example);
            return mapper.selectByExample(example);
        }
    }
}
