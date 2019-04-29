package com.aries.user.gaea.client.model;

import lombok.Data;

@Data
public class User {
    private Long id;

    private String nickname;

    private String account;

    private String phoneNumber;

    private String email;

    private String password;

    private String salt;

    private String wechat;

    private String qq;

    private Integer bizType;

    private byte[] image;
}
