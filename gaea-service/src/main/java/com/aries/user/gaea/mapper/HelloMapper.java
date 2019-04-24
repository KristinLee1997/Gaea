package com.aries.user.gaea.mapper;


import com.aries.user.gaea.model.Hello;

public interface HelloMapper {

    Hello selectByPrimaryKey(Integer id);
}