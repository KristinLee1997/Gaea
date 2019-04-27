package com.aries.user.gaea.server.dao;

import com.aries.user.gaea.server.constants.SysConstants;
import com.aries.user.gaea.server.factory.MySqlSessionFactory;
import com.aries.user.gaea.server.mapper.CompanyMapper;
import com.aries.user.gaea.server.model.po.Company;
import org.apache.commons.codec.binary.Base64;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Date;

public class CompanyDao {
    private static SqlSessionFactory usercenterSqlSession = MySqlSessionFactory.getSQLSessionFactory(SysConstants.DATABASE_USERCENTER);

    public static String register(String companyName, String password) {
        try (SqlSession sqlSession = usercenterSqlSession.openSession(true)) {
            CompanyMapper mapper = sqlSession.getMapper(CompanyMapper.class);

            Company existCompany = mapper.selectByName(companyName);
            if (existCompany != null && existCompany.getRegisterno() != null) {
                return existCompany.getRegisterno();
            }

            Company insertCompany = new Company() {{
                setName(companyName);
                setPassword(password);
                setRegisterno(new String(Base64.encodeBase64((companyName + password).getBytes(), true)));
                setAddTime(new Date());
            }};
            mapper.insert(insertCompany);
            return insertCompany.getRegisterno();
        }
    }

    public static String getRegisterNO(String companyName, String password){
        try (SqlSession sqlSession = usercenterSqlSession.openSession(true)) {
            CompanyMapper mapper = sqlSession.getMapper(CompanyMapper.class);

            Company existCompany = mapper.selectByName(companyName);
            if (existCompany != null && existCompany.getRegisterno() != null) {
                return existCompany.getRegisterno();
            }
        }
        return null;
    }
}
