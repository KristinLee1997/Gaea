package com.aries.user.gaea.server.service.impl;

import com.aries.user.gaea.server.dao.CompanyDao;
import com.aries.user.gaea.server.service.CompanyService;

public class CompanyServiceImpl implements CompanyService {
    @Override
    public String register(String companyName, String password) {
        return CompanyDao.register(companyName, password);
    }

    @Override
    public String getRegisterNo(String companyName, String password) {
        return CompanyDao.getRegisterNO(companyName, password);
    }
}
