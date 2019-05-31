package com.aries.user.gaea.server.dao;

import com.aries.user.gaea.server.constants.SysConstants;
import com.aries.user.gaea.server.factory.MySqlSessionFactory;
import com.aries.user.gaea.server.mapper.LoginCookieMapper;
import com.aries.user.gaea.server.model.po.LoginCookie;
import com.aries.user.gaea.server.model.po.LoginCookieExample;
import com.aries.user.gaea.server.utils.UUIDUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Date;
import java.util.List;

public class LoginCookieDao {
    public static LoginCookie getLoginInfoByLoginId(String companyName, String loginId) {
        SqlSessionFactory sqlSessionFactory = MySqlSessionFactory.getSQLSessionFactory(companyName);
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            LoginCookieMapper loginCookieMapper = sqlSession.getMapper(LoginCookieMapper.class);
            LoginCookieExample example = new LoginCookieExample();
            example.createCriteria().andLoginIdEqualTo(loginId);
            List<LoginCookie> loginCookieList = loginCookieMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(loginCookieList)) {
                return null;
            }
            return loginCookieList.get(0);
        }
    }

    public static LoginCookie insertCookie(String companyName, String loginId, int loginType) {
        SqlSessionFactory sqlSessionFactory = MySqlSessionFactory.getSQLSessionFactory(companyName);
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            LoginCookieMapper loginCookieMapper = sqlSession.getMapper(LoginCookieMapper.class);
            LoginCookie loginCookie = new LoginCookie();
            loginCookie.setLoginId(loginId);
            loginCookie.setLoginType(loginType);
            loginCookie.setAddTime(new Date());
            loginCookie.setCookie(UUIDUtils.getCookie());
            loginCookieMapper.insertSelective(loginCookie);
            return loginCookie;
        }
    }

    public static int deleteCookie(String companyName, String loginId) {
        SqlSessionFactory sqlSessionFactory = MySqlSessionFactory.getSQLSessionFactory(companyName);
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            LoginCookieMapper loginCookieMapper = sqlSession.getMapper(LoginCookieMapper.class);
            LoginCookieExample example = new LoginCookieExample();
            LoginCookieExample.Criteria criteria1 = example.createCriteria();
            criteria1.andLoginIdEqualTo(SysConstants.WEIXIN_PREFIX + loginId);
            LoginCookieExample.Criteria criteria2 = example.createCriteria();
            criteria2.andLoginIdEqualTo(SysConstants.QQ_PREFIX + loginId);
            LoginCookieExample.Criteria criteria3 = example.createCriteria();
            criteria3.andLoginIdEqualTo(loginId);
            example.or(criteria1);
            example.or(criteria2);
            example.or(criteria3);
            List<LoginCookie> loginCookies = loginCookieMapper.selectByExample(example);
            if (loginCookies == null || loginCookies.size() == 0) {
                return 0;
            }
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
