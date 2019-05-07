package com.aries.user.gaea.client.model;

import com.aries.user.gaea.contact.model.UserRegisterDTO;

import java.nio.ByteBuffer;

public class UserRegisterVo {
    private String account;
    private String phoneNumber;
    private String email;
    private String password;
    private String wechat;
    private String qq;
    private int bizType;
    private ByteBuffer image;
    private Long imageId;
    private String nickname;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getBizType() {
        return bizType;
    }

    public void setBizType(int bizType) {
        this.bizType = bizType;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public ByteBuffer getImage() {
        return image;
    }

    public void setImage(ByteBuffer image) {
        this.image = image;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public static UserRegisterDTO convert2DTO(UserRegisterVo userRegisterVo) {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setAccount(userRegisterVo.getAccount());
        userRegisterDTO.setPhoneNumber(userRegisterVo.getPhoneNumber());
        userRegisterDTO.setEmail(userRegisterVo.getEmail());
        userRegisterDTO.setPassword(userRegisterVo.getPassword());
        userRegisterDTO.setWechat(userRegisterVo.getWechat());
        userRegisterDTO.setQq(userRegisterVo.getQq());
        userRegisterDTO.setBizType(userRegisterVo.getBizType());
        userRegisterDTO.setImage(userRegisterVo.getImage());
        userRegisterDTO.setImageId(userRegisterVo.getImageId());
        userRegisterDTO.setNickname(userRegisterVo.getNickname());
        return userRegisterDTO;
    }

    public static final class UserRegisterVoBuilder {
        private String account;
        private String phoneNumber;
        private String email;
        private String password;
        private String wechat;
        private String qq;
        private int bizType;
        private ByteBuffer image;
        private Long imageId;
        private String nickname;

        private UserRegisterVoBuilder() {
        }

        public static UserRegisterVoBuilder anUserRegisterVo() {
            return new UserRegisterVoBuilder();
        }

        public UserRegisterVoBuilder account(String account) {
            this.account = account;
            return this;
        }

        public UserRegisterVoBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserRegisterVoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserRegisterVoBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserRegisterVoBuilder wechat(String wechat) {
            this.wechat = wechat;
            return this;
        }

        public UserRegisterVoBuilder qq(String qq) {
            this.qq = qq;
            return this;
        }

        public UserRegisterVoBuilder bizType(int bizType) {
            this.bizType = bizType;
            return this;
        }

        public UserRegisterVoBuilder image(ByteBuffer image) {
            this.image = image;
            return this;
        }

        public UserRegisterVoBuilder imageId(Long imageId) {
            this.imageId = imageId;
            return this;
        }

        public UserRegisterVoBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public UserRegisterVo build() {
            UserRegisterVo userRegisterVo = new UserRegisterVo();
            userRegisterVo.setAccount(account);
            userRegisterVo.setPhoneNumber(phoneNumber);
            userRegisterVo.setEmail(email);
            userRegisterVo.setPassword(password);
            userRegisterVo.setWechat(wechat);
            userRegisterVo.setQq(qq);
            userRegisterVo.setBizType(bizType);
            userRegisterVo.setImage(image);
            userRegisterVo.setImageId(imageId);
            userRegisterVo.setNickname(nickname);
            return userRegisterVo;
        }
    }

    public static final class AccountBuilder {
        private String account;
        private String password;
        private int bizType;
        private ByteBuffer image;
        private Long imageId;
        private String nickname;

        private AccountBuilder() {
        }

        public static AccountBuilder anUserRegisterVo() {
            return new AccountBuilder();
        }

        public AccountBuilder account(String account) {
            this.account = account;
            return this;
        }

        public AccountBuilder password(String password) {
            this.password = password;
            return this;
        }

        public AccountBuilder bizType(int bizType) {
            this.bizType = bizType;
            return this;
        }

        public AccountBuilder image(ByteBuffer image) {
            this.image = image;
            return this;
        }

        public AccountBuilder imageId(Long imageId) {
            this.imageId = imageId;
            return this;
        }

        public AccountBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public UserRegisterVo build() {
            UserRegisterVo userRegisterVo = new UserRegisterVo();
            userRegisterVo.setAccount(account);
            userRegisterVo.setPassword(password);
            userRegisterVo.setBizType(bizType);
            userRegisterVo.setImage(image);
            userRegisterVo.setImageId(imageId);
            userRegisterVo.setNickname(nickname);
            return userRegisterVo;
        }
    }

    public static final class PhoneNumberBuilder {
        private String phoneNumber;
        private String password;
        private int bizType;
        private ByteBuffer image;
        private Long imageId;
        private String nickname;

        private PhoneNumberBuilder() {
        }

        public static PhoneNumberBuilder anUserRegisterVo() {
            return new PhoneNumberBuilder();
        }

        public PhoneNumberBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public PhoneNumberBuilder password(String password) {
            this.password = password;
            return this;
        }

        public PhoneNumberBuilder bizType(int bizType) {
            this.bizType = bizType;
            return this;
        }

        public PhoneNumberBuilder image(ByteBuffer image) {
            this.image = image;
            return this;
        }

        public PhoneNumberBuilder imageId(Long imageId) {
            this.imageId = imageId;
            return this;
        }

        public PhoneNumberBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public UserRegisterVo build() {
            UserRegisterVo userRegisterVo = new UserRegisterVo();
            userRegisterVo.setPhoneNumber(phoneNumber);
            userRegisterVo.setPassword(password);
            userRegisterVo.setBizType(bizType);
            userRegisterVo.setImage(image);
            userRegisterVo.setImageId(imageId);
            userRegisterVo.setNickname(nickname);
            return userRegisterVo;
        }
    }

    public static final class EmailBuilder {
        private String email;
        private String password;
        private int bizType;
        private ByteBuffer image;
        private Long imageId;
        private String nickname;

        private EmailBuilder() {
        }

        public static EmailBuilder anUserRegisterVo() {
            return new EmailBuilder();
        }

        public EmailBuilder email(String email) {
            this.email = email;
            return this;
        }

        public EmailBuilder password(String password) {
            this.password = password;
            return this;
        }

        public EmailBuilder bizType(int bizType) {
            this.bizType = bizType;
            return this;
        }

        public EmailBuilder image(ByteBuffer image) {
            this.image = image;
            return this;
        }

        public EmailBuilder imageId(Long imageId) {
            this.imageId = imageId;
            return this;
        }

        public EmailBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public UserRegisterVo build() {
            UserRegisterVo userRegisterVo = new UserRegisterVo();
            userRegisterVo.setEmail(email);
            userRegisterVo.setPassword(password);
            userRegisterVo.setBizType(bizType);
            userRegisterVo.setImage(image);
            userRegisterVo.setImageId(imageId);
            userRegisterVo.setNickname(nickname);
            return userRegisterVo;
        }
    }

    public static final class WechatBuilder {
        private String wechat;
        private int bizType;
        private ByteBuffer image;
        private Long imageId;
        private String nickname;

        private WechatBuilder() {
        }

        public static WechatBuilder anUserRegisterVo() {
            return new WechatBuilder();
        }

        public WechatBuilder wechat(String wechat) {
            this.wechat = wechat;
            return this;
        }

        public WechatBuilder bizType(int bizType) {
            this.bizType = bizType;
            return this;
        }

        public WechatBuilder image(ByteBuffer image) {
            this.image = image;
            return this;
        }

        public WechatBuilder imageId(Long imageId) {
            this.imageId = imageId;
            return this;
        }

        public WechatBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public UserRegisterVo build() {
            UserRegisterVo userRegisterVo = new UserRegisterVo();
            userRegisterVo.setWechat(wechat);
            userRegisterVo.setBizType(bizType);
            userRegisterVo.setImage(image);
            userRegisterVo.setImageId(imageId);
            userRegisterVo.setNickname(nickname);
            return userRegisterVo;
        }
    }

    public static final class QQBuilder {
        private String qq;
        private int bizType;
        private ByteBuffer image;
        private Long imageId;
        private String nickname;

        private QQBuilder() {
        }

        public static QQBuilder anUserRegisterVo() {
            return new QQBuilder();
        }

        public QQBuilder qq(String qq) {
            this.qq = qq;
            return this;
        }

        public QQBuilder bizType(int bizType) {
            this.bizType = bizType;
            return this;
        }

        public QQBuilder image(ByteBuffer image) {
            this.image = image;
            return this;
        }

        public QQBuilder imageId(Long imageId) {
            this.imageId = imageId;
            return this;
        }

        public QQBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public UserRegisterVo build() {
            UserRegisterVo userRegisterVo = new UserRegisterVo();
            userRegisterVo.setQq(qq);
            userRegisterVo.setBizType(bizType);
            userRegisterVo.setImage(image);
            userRegisterVo.setImageId(imageId);
            userRegisterVo.setNickname(nickname);
            return userRegisterVo;
        }
    }
}
