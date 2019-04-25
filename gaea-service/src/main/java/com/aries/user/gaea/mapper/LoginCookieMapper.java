package com.aries.user.gaea.mapper;

import com.aries.user.gaea.model.po.LoginCookie;
import com.aries.user.gaea.model.po.LoginCookieExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoginCookieMapper {
    int countByExample(LoginCookieExample example);

    int deleteByExample(LoginCookieExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LoginCookie record);

    int insertSelective(LoginCookie record);

    List<LoginCookie> selectByExample(LoginCookieExample example);

    LoginCookie selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LoginCookie record, @Param("example") LoginCookieExample example);

    int updateByExample(@Param("record") LoginCookie record, @Param("example") LoginCookieExample example);

    int updateByPrimaryKeySelective(LoginCookie record);

    int updateByPrimaryKey(LoginCookie record);
}