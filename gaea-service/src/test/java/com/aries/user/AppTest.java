package com.aries.user;

import com.aries.user.gaea.factory.MySqlSessionFactory;
import com.aries.user.gaea.mapper.CompanyMapper;
import com.aries.user.gaea.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.io.IOException;

public class AppTest {
    @Test
    public void UserCenterTest() throws IOException {
        SqlSessionFactory newSqlSessionFactory = MySqlSessionFactory.getSQLSessionFactory("usercenter");

        try (SqlSession sqlSession = newSqlSessionFactory.openSession(true)) {
            CompanyMapper mapper = sqlSession.getMapper(CompanyMapper.class);
            System.out.println(mapper.selectByPrimaryKey(1L).getName());
        }
    }

    @Test
    public void OrionTest() throws IOException {
        SqlSessionFactory newSqlSessionFactory = MySqlSessionFactory.getSQLSessionFactory("aries");

        try (SqlSession sqlSession = newSqlSessionFactory.openSession(true)) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            System.out.println(mapper.selectByPrimaryKey(1L).getAccount());
        }
    }
}
