package com.aries.user.gaea.server.service;

import com.aries.user.gaea.contact.model.UserRegisterDTO;

public interface UserService {
    Long register(String database, UserRegisterDTO userRegisterDTO);

    int login(String database, String loginId, String password, int loginType);

    int logout(String database, String loginId);

    int getTypeByLoginId(String database, String loginId, int loginType);

    int getLoginType(String database, String loginId);

    int getTypeByLoginId(String loginId);
}
