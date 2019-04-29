package com.aries.user.gaea.server.dao;

import com.aries.user.gaea.server.constants.SysConstants;
import com.aries.user.gaea.server.factory.MySqlSessionFactory;
import com.aries.user.gaea.server.mapper.UserMapper;
import com.aries.user.gaea.server.model.po.User;
import com.aries.user.gaea.server.model.po.UserExample;
import com.aries.user.gaea.server.utils.UUIDUtils;
import com.mysql.cj.jdbc.MysqlDataSourceFactory;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class UserDao {
    public static Long register(String database, User user) {
        try (SqlSession sqlSession = MySqlSessionFactory.openSession(database)) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            if (user.getPassword() != null) {
                user.setSalt(UUIDUtils.getUUID());
                user.setPassword(DigestUtils.md5Hex(user.getPassword() + user.getSalt()));
            }
            return (long) userMapper.insert(user);
        }
    }

    public static boolean wechatLogin(String database, String loginId) {
        try (SqlSession sqlSession = MySqlSessionFactory.openSession(database)) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            UserExample userExample = new UserExample();
            userExample.createCriteria().andWechatEqualTo(loginId);
            List<User> userList = userMapper.selectByExample(userExample);
            return !CollectionUtils.isEmpty(userList);
        }
    }

    public static boolean qqLogin(String database, String loginId) {
        try (SqlSession sqlSession = MySqlSessionFactory.openSession(database)) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            UserExample userExample = new UserExample();
            userExample.createCriteria().andQqEqualTo(loginId);
            List<User> userList = userMapper.selectByExample(userExample);
            return !CollectionUtils.isEmpty(userList);
        }
    }

    public static boolean loginIdLogin(String database, String loginId, String password, int loginType) {
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
            if(CollectionUtils.isEmpty(userList)){
                return false;
            }else{
                User user = userList.get(0);
                if (DigestUtils.md5Hex(password + user.getSalt()).equals(user.getPassword())) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    public static List<User> getUserListByExample(String database, UserExample example) {
        try (SqlSession sqlSession = MySqlSessionFactory.openSession(database)) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            mapper.selectByExample(example);
            return mapper.selectByExample(example);
        }
    }
}
