package com.aries.user.gaea.service.impl;

import com.aries.user.gaea.factory.MySqlSessionFactory;
import com.aries.user.gaea.mapper.UserMapper;
import com.aries.user.gaea.model.po.User;
import com.aries.user.gaea.service.UserService;
import com.aries.user.gaea.utils.UUIDUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;


public class UserServiceImpl implements UserService {

    @Override
    public Long register(String companyName, User user) {
        SqlSessionFactory sqlSessionFactory = MySqlSessionFactory.getSQLSessionFactory(companyName);
        int id = 0;
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            user.setSalt(UUIDUtils.getUUID());
            user.setPassword(DigestUtils.md5Hex(user.getPassword() + user.getSalt()));
            user.setBizType(0);
            user.setBizId(1L);
            id = mapper.insert(user);
        }
        return (long) id;
    }
}
