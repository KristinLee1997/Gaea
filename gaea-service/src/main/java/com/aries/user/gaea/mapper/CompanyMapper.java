package com.aries.user.gaea.mapper;

import com.aries.user.gaea.model.po.Company;
import com.aries.user.gaea.model.po.CompanyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyMapper {
    int countByExample(CompanyExample example);

    int deleteByExample(CompanyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Company record);

    int insertSelective(Company record);

    List<Company> selectByExample(CompanyExample example);

    Company selectByName(String name);

    Company selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Company record, @Param("example") CompanyExample example);

    int updateByExample(@Param("record") Company record, @Param("example") CompanyExample example);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);
}