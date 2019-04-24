package com.aries.user.gaea.service;


import com.aries.user.gaea.mapper.HelloMapper;
import com.aries.user.gaea.model.Hello;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class HelloService {
    public void selectById() throws IOException {
        String resource = "mybatis/mybatis-config.xml";
        InputStream is = HelloService.class.getClassLoader().getResourceAsStream(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        SqlSession sqlSession = factory.openSession();


//        String resource = "mybatis/mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
//        SqlSessionFactory factory = builder.build(inputStream);
//        SqlSession sqlSession = factory.openSession();
        HelloMapper mapper = sqlSession.getMapper(HelloMapper.class);
        Hello hello = mapper.selectByPrimaryKey(1);
        if (hello != null) {
            System.out.println(hello);
        } else {
            System.out.println("啥也没取到。。。");
        }

    }
}
