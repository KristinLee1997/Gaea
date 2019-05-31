package com.aries.user.gaea.server.model.vo;

import java.util.Date;

public class UserVo {
    private Long id;

    private String nickname;

    private String account;

    private String phoneNumber;

    private String email;

    private String wechat;

    private String qq;

    private Long imageId;

    private Date addTime;

    private Integer bizType;

    private String loginId;

    private String cookie;

    private Integer loginType;

    private Date loginTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getBizType() {
        return bizType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }


    public static final class UserVoBuilder {
        private Long id;
        private String nickname;
        private String account;
        private String phoneNumber;
        private String email;
        private String wechat;
        private String qq;
        private Long imageId;
        private Date addTime;
        private Integer bizType;
        private String loginId;
        private String cookie;
        private Integer loginType;
        private Date loginTime;

        private UserVoBuilder() {
        }

        public static UserVoBuilder anUserVo() {
            return new UserVoBuilder();
        }

        public UserVoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserVoBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public UserVoBuilder account(String account) {
            this.account = account;
            return this;
        }

        public UserVoBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserVoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserVoBuilder wechat(String wechat) {
            this.wechat = wechat;
            return this;
        }

        public UserVoBuilder qq(String qq) {
            this.qq = qq;
            return this;
        }

        public UserVoBuilder imageId(Long imageId) {
            this.imageId = imageId;
            return this;
        }

        public UserVoBuilder addTime(Date addTime) {
            this.addTime = addTime;
            return this;
        }

        public UserVoBuilder bizType(Integer bizType) {
            this.bizType = bizType;
            return this;
        }

        public UserVoBuilder loginId(String loginId) {
            this.loginId = loginId;
            return this;
        }

        public UserVoBuilder cookie(String cookie) {
            this.cookie = cookie;
            return this;
        }

        public UserVoBuilder loginType(Integer loginType) {
            this.loginType = loginType;
            return this;
        }

        public UserVoBuilder loginTime(Date loginTime) {
            this.loginTime = loginTime;
            return this;
        }

        public UserVo build() {
            UserVo userVo = new UserVo();
            userVo.setId(id);
            userVo.setNickname(nickname);
            userVo.setAccount(account);
            userVo.setPhoneNumber(phoneNumber);
            userVo.setEmail(email);
            userVo.setWechat(wechat);
            userVo.setQq(qq);
            userVo.setImageId(imageId);
            userVo.setAddTime(addTime);
            userVo.setBizType(bizType);
            userVo.setLoginId(loginId);
            userVo.setCookie(cookie);
            userVo.setLoginType(loginType);
            userVo.setLoginTime(loginTime);
            return userVo;
        }
    }
}
