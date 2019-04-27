package com.aries.user.gaea.server.service;

import com.aries.user.gaea.contact.model.UserRegisterDTO;

public interface UserService {
    Long register(UserRegisterDTO userRegisterDTO);

    int login(String companyName, String loginId, String password, int loginType);

    int logout(String companyName, String loginId);

    int getTypeByLoginId(String companyName, String loginId);

    int getLoginType(String companyName, String loginId);

    int getTypeByLoginId(String loginId);
}
