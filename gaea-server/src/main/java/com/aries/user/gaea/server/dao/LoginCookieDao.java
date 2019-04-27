package com.aries.user.gaea.server.dao;

import com.aries.user.gaea.server.factory.MySqlSessionFactory;
import com.aries.user.gaea.server.mapper.LoginCookieMapper;
import com.aries.user.gaea.server.model.po.LoginCookie;
import com.aries.user.gaea.server.model.po.LoginCookieExample;
import com.aries.user.gaea.server.utils.UUIDUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Date;
import java.util.List;

public class LoginCookieDao {
    public static int insertCookie(String companyName, String loginId, int loginType) {
        SqlSessionFactory sqlSessionFactory = MySqlSessionFactory.getSQLSessionFactory(companyName);
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            LoginCookieMapper loginCookieMapper = sqlSession.getMapper(LoginCookieMapper.class);
            LoginCookie loginCookie = new LoginCookie();
            loginCookie.setLoginId(loginId);
            loginCookie.setLoginType(loginType);
            loginCookie.setAddTime(new Date());
            loginCookie.setCookie(UUIDUtils.getCookie());
            return loginCookieMapper.insert(loginCookie);
        }
    }

    public static int deleteCookie(String companyName, String loginId) {
        SqlSessionFactory sqlSessionFactory = MySqlSessionFactory.getSQLSessionFactory(companyName);
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            LoginCookieMapper loginCookieMapper = sqlSession.getMapper(LoginCookieMapper.class);
            LoginCookieExample example = new LoginCookieExample();
            example.createCriteria().andLoginIdEqualTo(loginId);
            return loginCookieMapper.deleteByExample(example);
        }
    }

    public static LoginCookie getLoginType(String companyName, String loginId) {
        SqlSessionFactory sqlSessionFactory = MySqlSessionFactory.getSQLSessionFactory(companyName);
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            LoginCookieMapper loginCookieMapper = sqlSession.getMapper(LoginCookieMapper.class);
            LoginCookieExample example = new LoginCookieExample();
            example.createCriteria().andLoginIdEqualTo(loginId);
            List<LoginCookie> list = loginCookieMapper.selectByExample(example);
            if (list == null || list.size() == 0) {
                return null;
            }
            return list.get(0);
        }
    }
}
