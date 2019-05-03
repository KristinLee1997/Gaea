package com.aries.user.gaea.server.dao;

import com.aries.user.gaea.server.constants.SysConstants;
import com.aries.user.gaea.server.factory.MySqlSessionFactory;
import com.aries.user.gaea.server.mapper.UserMapper;
import com.aries.user.gaea.server.model.po.User;
import com.aries.user.gaea.server.model.po.UserExample;
import com.aries.user.gaea.server.utils.UUIDUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

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
            List<User> userList = userMapper.selectByExampleWithBLOBs(userExample);
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
}
