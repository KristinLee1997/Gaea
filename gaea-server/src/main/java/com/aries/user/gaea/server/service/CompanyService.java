package com.aries.user.gaea.server.service;

import com.aries.user.gaea.server.model.po.Company;

public interface CompanyService {
    String register(Company company);

    String getRegisterNo(Company company);
}
