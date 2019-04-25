package com.aries.user.gaea.service;

import com.aries.user.gaea.model.po.User;

public interface UserService {
    Long register(String company, User user);
}
