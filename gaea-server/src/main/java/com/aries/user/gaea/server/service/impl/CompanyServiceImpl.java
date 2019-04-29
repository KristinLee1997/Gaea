package com.aries.user.gaea.server.service.impl;

import com.aries.user.gaea.server.bean.CompanyBean;
import com.aries.user.gaea.server.dao.CompanyDao;
import com.aries.user.gaea.server.model.po.Company;
import com.aries.user.gaea.server.service.CompanyService;

public class CompanyServiceImpl implements CompanyService {
    @Override
    public String register(Company company) {
        return CompanyDao.register(company);
    }

    @Override
    public String getRegisterNo(Company company) {
        return CompanyDao.getRegisterNO(company);
    }

    public static String getDatabase(CompanyBean company) {
        return CompanyDao.queryDatabaseByPassword(company.getName(), company.getPassword());
    }
}
