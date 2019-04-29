package com.aries.user.gaea.server.dao;

import com.aries.user.gaea.server.constants.SysConstants;
import com.aries.user.gaea.server.factory.MySqlSessionFactory;
import com.aries.user.gaea.server.mapper.CompanyMapper;
import com.aries.user.gaea.server.model.po.Company;
import com.aries.user.gaea.server.model.po.CompanyExample;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.SqlSession;


import java.util.Date;
import java.util.List;

public class CompanyDao {
    public static String register(Company company) {
        try (SqlSession sqlSession = MySqlSessionFactory.openSession(SysConstants.DATABASE_USERCENTER)) {
            CompanyMapper mapper = sqlSession.getMapper(CompanyMapper.class);

            Company existCompany = mapper.selectByName(company.getName());
            if (existCompany != null && existCompany.getRegisterno() != null) {
                return existCompany.getRegisterno();
            }

            Company insertCompany = new Company() {{
                setName(company.getName());
                setPassword(company.getPassword());
                setRegisterno(new String(Base64.encodeBase64((company.getName() + company.getPassword()).getBytes(), true)));
                setAddTime(new Date());
                setDbName(SysConstants.DATABASE_USERCENTER + "_" + company.getName());
            }};
            mapper.insert(insertCompany);
            return insertCompany.getRegisterno();
        }
    }

    public static String getRegisterNO(Company company) {
        try (SqlSession sqlSession = MySqlSessionFactory.openSession(SysConstants.DATABASE_USERCENTER)) {
            CompanyMapper mapper = sqlSession.getMapper(CompanyMapper.class);
            CompanyExample example = new CompanyExample();
            example.createCriteria().andNameEqualTo(company.getName()).andPasswordEqualTo(company.getPassword());
            List<Company> companyList = mapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(companyList)) {
                return companyList.get(0).getRegisterno();
            }
        }
        return null;
    }

    /**
     * 通过公司名或者密码查询公司
     *
     * @param companyName
     * @return
     */
    public static String queryDatabaseByPassword(String companyName, String password) {
        try (SqlSession session = MySqlSessionFactory.openSession(SysConstants.DATABASE_USERCENTER)) {
            CompanyMapper companyMapper = session.getMapper(CompanyMapper.class);
            CompanyExample example = new CompanyExample();
            example.createCriteria().andNameEqualTo(companyName).andPasswordEqualTo(password);
            List<Company> companyList = companyMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(companyList)) {
                return null;
            } else {
                return companyList.get(0).getDbName();
            }
        }
    }
}
