package com.aries.user.gaea.server.service;

public interface CompanyService {
    String register(String companyName, String password);

    String getRegisterNo(String companyName, String password);
}
