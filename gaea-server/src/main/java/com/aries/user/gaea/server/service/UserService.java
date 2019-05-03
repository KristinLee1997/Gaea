package com.aries.user.gaea.server.service;

import com.aries.user.gaea.contact.model.UserInfo;
import com.aries.user.gaea.contact.model.UserRegisterDTO;
import com.aries.user.gaea.server.model.po.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    Long register(String database, UserRegisterDTO userRegisterDTO);

    User login(String database, String loginId, String password, int loginType);

    int logout(String database, String loginId);

    User getUserInfoById(String database, Long id);

    Map<Long, UserInfo> getUserInfoByIdList(String database, List idList);

    int getTypeByLoginId(String database, String loginId, int loginType);

    int getLoginType(String database, String loginId);

    int getTypeByLoginId(String loginId);
}
