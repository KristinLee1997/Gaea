package com.aries.user.gaea;

import com.aries.user.gaea.factory.MySqlSessionFactory;
import com.aries.user.gaea.mapper.CompanyMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.IOException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
        SqlSessionFactory newSqlSessionFactory = MySqlSessionFactory.getSQLSessionFactory("usercenter");

        try (SqlSession sqlSession = newSqlSessionFactory.openSession(true)) {
            CompanyMapper mapper = sqlSession.getMapper(CompanyMapper.class);
            System.out.println(mapper.selectByPrimaryKey(5L).getName());
        }
    }
}
