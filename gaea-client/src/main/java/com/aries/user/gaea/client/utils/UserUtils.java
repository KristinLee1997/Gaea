package com.aries.user.gaea.client.utils;

import com.alibaba.fastjson.JSON;
import com.aries.hera.client.thrift.ThriftHelper;
import com.aries.hera.client.thrift.exception.ServiceNotFoundException;
import com.aries.hera.core.utils.PropertiesProxy;
import com.aries.user.gaea.client.constants.ClientConstants;
import com.aries.user.gaea.client.model.GaeaResponse;
import com.aries.user.gaea.client.model.User;
import com.aries.user.gaea.client.model.UserRegisterVo;
import com.aries.user.gaea.client.model.UserVo;
import com.aries.user.gaea.contact.model.CompanyDTO;
import com.aries.user.gaea.contact.model.ThriftListResponse;
import com.aries.user.gaea.contact.model.ThriftResponse;
import com.aries.user.gaea.contact.model.UserInfo;
import com.aries.user.gaea.contact.model.UserInfoResponse;
import com.aries.user.gaea.contact.model.UserLoginDTO;
import com.aries.user.gaea.contact.service.UserBaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.transport.TTransportException;

import java.util.List;

import static com.aries.user.gaea.client.constants.GaeaResponseEnum.SYSTEM_ERROR;


@Slf4j
public class UserUtils {
    private static final CompanyDTO companyDTO;

    static {
        PropertiesProxy propertiesProxy = new PropertiesProxy("gaea-pass.properties");
        String company = propertiesProxy.readProperty("company");
        String password = propertiesProxy.readProperty("password");
        companyDTO = new CompanyDTO(company, password);
    }

    public static GaeaResponse register(UserRegisterVo userRegisterVo) {
        ThriftResponse thriftResponse = null;
        try {
            thriftResponse = ThriftHelper.call(ClientConstants.PROJECT_NAME, UserBaseService.Client.class,
                    client -> client.userRegister(companyDTO, UserRegisterVo.convert2DTO(userRegisterVo)));
        } catch (TTransportException e) {
            log.error("用户注册异常！");
            return SYSTEM_ERROR.of();
        } catch (ServiceNotFoundException e) {
            log.error("Hera系统服务找不到，服务异常！");
            return SYSTEM_ERROR.of();
        }
        GaeaResponse response = new GaeaResponse();
        response.setCode(thriftResponse.getCode());
        response.setMessage(thriftResponse.getMessage());
        response.setData(thriftResponse.getData());
        log.info("用户id:{},注册成功！", thriftResponse.getData());
        return response;
    }

    public static GaeaResponse login(UserLoginDTO loginDTO) {
        ThriftResponse userResponse = null;
        try {
            userResponse = ThriftHelper.call(ClientConstants.PROJECT_NAME, UserBaseService.Client.class, client -> client.userLogin(companyDTO, loginDTO));
        } catch (TTransportException e) {
            log.error("用户登录异常！");
            return SYSTEM_ERROR.of();
        } catch (ServiceNotFoundException e) {
            log.error("Hera系统服务找不到，服务异常！");
            return SYSTEM_ERROR.of();
        }
        GaeaResponse response = new GaeaResponse();
        response.setCode(userResponse.getCode());
        response.setMessage(userResponse.getMessage());
        response.setData(JSON.parseObject(userResponse.getData(), UserVo.class));
        log.info("用户:{}登录成功", userResponse.getData());
        return response;
    }

    public static GaeaResponse logout(String loginId) {
        ThriftResponse userResponse = null;
        try {
            userResponse = ThriftHelper.call(ClientConstants.PROJECT_NAME, UserBaseService.Client.class, client -> client.userLogout(companyDTO, loginId));
        } catch (TTransportException e) {
            log.error("用户登出异常！");
            return SYSTEM_ERROR.of();
        } catch (ServiceNotFoundException e) {
            log.error("Hera系统服务找不到，服务异常！");
            return SYSTEM_ERROR.of();
        }
        GaeaResponse response = new GaeaResponse();
        response.setCode(userResponse.getCode());
        response.setMessage(userResponse.getMessage());
        return response;

    }

    public static GaeaResponse checkLoginType(String loginId) {
        ThriftResponse thriftResponse = null;
        try {
            thriftResponse = ThriftHelper.call(ClientConstants.PROJECT_NAME, UserBaseService.Client.class, client -> client.checkLoginType(companyDTO, loginId));
        } catch (TTransportException e) {
            log.error("用户获取登录类型异常！");
            return SYSTEM_ERROR.of();
        } catch (ServiceNotFoundException e) {
            log.error("Hera系统服务找不到，服务异常！");
            return SYSTEM_ERROR.of();
        }
        GaeaResponse response = new GaeaResponse();
        response.setCode(thriftResponse.getCode());
        response.setMessage(thriftResponse.getMessage());
        response.setData(thriftResponse.getData());
        return response;
    }

    public static GaeaResponse getUserInfoById(Long id) {
        ThriftResponse thriftResponse = null;
        try {
            thriftResponse = ThriftHelper.call(ClientConstants.PROJECT_NAME, UserBaseService.Client.class, client -> client.getUserInfoById(companyDTO, id));
        } catch (TTransportException e) {
            log.error("通过用户id:{}查询用户信息失败", id);
            return SYSTEM_ERROR.of();
        } catch (ServiceNotFoundException e) {
            log.error("Hera系统服务找不到，服务异常！");
            return SYSTEM_ERROR.of();
        }
        GaeaResponse response = new GaeaResponse();
        response.setCode(thriftResponse.getCode());
        response.setMessage(thriftResponse.getMessage());
        response.setData(JSON.parseObject(thriftResponse.getData(), User.class));
        return response;
    }

    public static GaeaResponse getUserListByBizType(Integer bizType) {
        ThriftListResponse thriftResponse = null;
        try {
            thriftResponse = ThriftHelper.call(ClientConstants.PROJECT_NAME, UserBaseService.Client.class, client -> client.getUserInfoByBizType(companyDTO, bizType));
        } catch (TTransportException e) {
            log.error("通过用户bizType:{}查询用户信息失败", bizType);
            return SYSTEM_ERROR.of();
        } catch (ServiceNotFoundException e) {
            log.error("Hera系统服务找不到，服务异常！");
            return SYSTEM_ERROR.of();
        }
        GaeaResponse response = new GaeaResponse();
        response.setCode(thriftResponse.getCode());
        response.setMessage(thriftResponse.getMessage());
        response.setData(thriftResponse.getData());
        return response;
    }

    public static GaeaResponse updateUserInfoByBizType(Long id, Integer bizType) {
        GaeaResponse response = new GaeaResponse();
        if (id == null || bizType == null || id <= 0 || bizType <= 0) {
            response.setCode(400);
            response.setMessage("请求参数不合法");
            return response;
        }
        ThriftResponse thriftResponse = null;
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setBizType(bizType);
        try {
            thriftResponse = ThriftHelper.call(ClientConstants.PROJECT_NAME, UserBaseService.Client.class, client -> client.updateUserInfoById(companyDTO, userInfo));
        } catch (TTransportException e) {
            log.error("通过用户bizType:{}查询用户信息失败", bizType);
            return SYSTEM_ERROR.of();
        } catch (ServiceNotFoundException e) {
            log.error("Hera系统服务找不到，服务异常！");
            return SYSTEM_ERROR.of();
        }
        response.setCode(thriftResponse.getCode());
        response.setMessage(thriftResponse.getMessage());
        return response;
    }

    public static GaeaResponse getUserInfoByCookie(String cookie) {
        ThriftResponse thriftResponse = null;
        try {
            thriftResponse = ThriftHelper.call(ClientConstants.PROJECT_NAME, UserBaseService.Client.class, client -> client.getUserInfoByCookie(companyDTO, cookie));
        } catch (TTransportException e) {
            log.error("通过用户cookie:{}查询用户信息失败", cookie);
            return SYSTEM_ERROR.of();
        } catch (ServiceNotFoundException e) {
            log.error("Hera系统服务找不到，服务异常！");
            return SYSTEM_ERROR.of();
        }
        GaeaResponse response = new GaeaResponse();
        response.setCode(thriftResponse.getCode());
        response.setMessage(thriftResponse.getMessage());
        response.setData(JSON.parseObject(thriftResponse.getData(), UserVo.class));
        return response;
    }

    public static GaeaResponse getUserInfoByIdList(List<Long> idList) {
        UserInfoResponse userInfoResponse = null;
        try {
            userInfoResponse = ThriftHelper.call(ClientConstants.PROJECT_NAME, UserBaseService.Client.class, client -> client.getUserInfoByIdList(companyDTO, idList));
        } catch (TTransportException e) {
            log.error("通过用户id查询用户信息失败");
            return SYSTEM_ERROR.of();
        } catch (ServiceNotFoundException e) {
            log.error("Hera系统服务找不到，服务异常！");
            return SYSTEM_ERROR.of();
        }
        GaeaResponse response = new GaeaResponse();
        response.setCode(userInfoResponse.getCode());
        response.setMessage(userInfoResponse.getMessage());
        response.setData(userInfoResponse.getData());
        return response;
    }
}

