package com.aries.user.gaea.server.thrift;

import com.alibaba.fastjson.JSON;
import com.aries.user.gaea.contact.model.CompanyDTO;
import com.aries.user.gaea.contact.model.ThriftListResponse;
import com.aries.user.gaea.contact.model.ThriftResponse;
import com.aries.user.gaea.contact.model.UserInfo;
import com.aries.user.gaea.contact.model.UserInfoResponse;
import com.aries.user.gaea.contact.model.UserLoginDTO;
import com.aries.user.gaea.contact.model.UserRegisterDTO;
import com.aries.user.gaea.contact.service.UserBaseService;
import com.aries.user.gaea.server.model.po.User;
import com.aries.user.gaea.server.model.vo.UserVo;
import com.aries.user.gaea.server.service.UserService;
import com.aries.user.gaea.server.service.impl.UserServiceImpl;
import com.aries.user.gaea.server.utils.CompanyHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.thrift.TException;

import java.util.List;
import java.util.Map;

import static com.aries.user.gaea.server.constants.GaeaResponseEnum.DATABASE_ERROR;
import static com.aries.user.gaea.server.constants.GaeaResponseEnum.DATA_NULL;
import static com.aries.user.gaea.server.constants.GaeaResponseEnum.PARAM_ILLEGAL;
import static com.aries.user.gaea.server.constants.GaeaResponseEnum.PARAM_NULL;
import static com.aries.user.gaea.server.constants.GaeaResponseEnum.SUCCESS;
import static com.aries.user.gaea.server.constants.GaeaResponseEnum.SYSTEM_ERROR;

@Slf4j
public class UserBaseServiceImpl implements UserBaseService.Iface {
    private static UserService userService = new UserServiceImpl();

    @Override
    public ThriftResponse userRegister(CompanyDTO companyDTO, UserRegisterDTO userRegisterDTO) throws TException {
        ThriftResponse response = new ThriftResponse();
        CompanyHelper companyHelper = new CompanyHelper(companyDTO).check();
        if (companyHelper.isError()) {
            log.error("调用用户注册接口无权限，公司信息{}", JSON.toJSONString(companyDTO));
            return companyHelper.getResponse();
        }
        if ((userRegisterDTO.getAccount() == null && userRegisterDTO.getPassword() == null)
                && (userRegisterDTO.getPhoneNumber() == null && userRegisterDTO.getPassword() == null)
                && userRegisterDTO.getWechat() == null
                && userRegisterDTO.getQq() == null) {
            log.warn("用户注册参数:{}信息不完整", JSON.toJSONString(userRegisterDTO));
            return PARAM_ILLEGAL.of();
        }
        Long id = userService.register(companyHelper.getDatabaseName(), userRegisterDTO);
        if (id == null) {
            log.warn("用户注册时数据异常,参数{}", JSON.toJSONString(userRegisterDTO));
            return DATABASE_ERROR.of();
        }
        response.setCode(SUCCESS.of().getCode());
        response.setMessage(SUCCESS.of().getMessage());
        response.setData(String.valueOf(id));
        log.info("用户server端注册成功，参数{}", JSON.toJSONString(userRegisterDTO));
        return response;
    }

    @Override
    public ThriftResponse userLogin(CompanyDTO companyDTO, UserLoginDTO userLoginDTO) throws TException {
        ThriftResponse response = new ThriftResponse();
        CompanyHelper companyHelper = new CompanyHelper(companyDTO).check();
        if (companyHelper.isError()) {
            log.error("调用用户登录接口无权限，公司信息{}", JSON.toJSONString(companyDTO));
            return companyHelper.getResponse();
        }
        UserVo userVo = userService.login(companyHelper.getDatabaseName(), userLoginDTO.getLoginId(),
                userLoginDTO.getPassword(), userLoginDTO.getLoginType());
        if (userVo == null) {
            log.warn("用户登录时数据异常，参数{}", JSON.toJSONString(userLoginDTO));
            return DATABASE_ERROR.of();
        }
        response.setCode(SUCCESS.of().getCode());
        response.setMessage(SUCCESS.of().getMessage());
        response.setData(JSON.toJSONString(userVo));
        log.info("用户登录成功！，参数{}", JSON.toJSONString(userLoginDTO));
        return response;
    }

    @Override
    public ThriftResponse userLogout(CompanyDTO companyDTO, String loginId) throws TException {
        CompanyHelper companyHelper = new CompanyHelper(companyDTO).check();
        if (companyHelper.isError()) {
            log.error("调用方无权限，公司信息{}", JSON.toJSONString(companyDTO));
            return companyHelper.getResponse();
        }
        int res = userService.logout(companyHelper.getDatabaseName(), loginId);
        if (res == 0) {
            log.warn("用户退出登录时数据异常，用户loginid:{}", loginId);
            return SYSTEM_ERROR.of();
        }
        log.info("用户loginid:{},退出登录操作成功！", loginId);
        return SUCCESS.of();
    }

    @Override
    public ThriftResponse checkLoginType(CompanyDTO companyDTO, String loginId) throws TException {
        CompanyHelper companyHelper = new CompanyHelper(companyDTO).check();
        ThriftResponse response = new ThriftResponse();
        if (companyHelper.isError()) {
            log.error("调用方无权限，公司信息{}", JSON.toJSONString(companyDTO));
            return companyHelper.getResponse();
        }
        int typeByLoginId = userService.getTypeByLoginId(loginId);
        if (typeByLoginId == 0) {
            log.error("用户查询登录类型失败，loginid:{}", loginId);
            return SYSTEM_ERROR.of();
        }
        response.setCode(SUCCESS.of().getCode());
        response.setMessage(SUCCESS.of().getMessage());
        response.setData(String.valueOf(typeByLoginId));
        log.info("用户loginid:{},查询类型成功", loginId);
        return response;
    }

    @Override
    public ThriftResponse checkOnline(CompanyDTO companyDTO, String loginId) throws TException {
        return SUCCESS.of();
    }

    @Override
    public ThriftResponse getUserInfoById(CompanyDTO companyDTO, long id) throws TException {
        CompanyHelper companyHelper = new CompanyHelper(companyDTO).check();
        ThriftResponse response = new ThriftResponse();
        if (companyHelper.isError()) {
            log.error("调用方无权限，公司信息{}", JSON.toJSONString(companyDTO));
            return companyHelper.getResponse();
        }
        User user = userService.getUserInfoById(companyHelper.getDatabaseName(), Long.valueOf(id));
        if (user == null) {
            log.warn("查询id:{}的用户信息失败", id);
            return DATA_NULL.of();
        }
        response.setCode(SUCCESS.of().getCode());
        response.setMessage(SUCCESS.of().getMessage());
        response.setData(JSON.toJSONString(user));
        log.info("id：{}查询用户信息成功！", id);
        return response;
    }

    @Override
    public ThriftListResponse getUserInfoByBizType(CompanyDTO companyDTO, int bizType) throws TException {
        CompanyHelper companyHelper = new CompanyHelper(companyDTO).check();
        ThriftListResponse response = new ThriftListResponse();
        if (companyHelper.isError()) {
            log.error("调用方无权限，公司信息{}", JSON.toJSONString(companyDTO));
            ThriftResponse thriftResponse = companyHelper.getResponse();
            ThriftListResponse thriftListResponse = new ThriftListResponse();
            thriftListResponse.setCode(thriftResponse.getCode());
            thriftListResponse.setMessage(thriftResponse.getMessage());
            return thriftListResponse;
        }
        if (bizType < 0) {
            log.warn("查询bizType:{}的用户信息失败", bizType);
            return PARAM_NULL.ofResponse();
        }
        List<UserInfo> userInfoList = userService.getUserListByBizType(companyHelper.getDatabaseName(), bizType);
        if (CollectionUtils.isEmpty(userInfoList)) {
            log.warn("查询bizType:{}的用户信息失败", bizType);
            return DATA_NULL.ofResponse();
        }
        response.setCode(SUCCESS.of().getCode());
        response.setMessage(SUCCESS.of().getMessage());
        response.setData(userInfoList);
        log.info("bizType：{}查询用户信息成功！", bizType);
        return response;
    }

    @Override
    public ThriftResponse updateUserInfoById(CompanyDTO companyDTO, UserInfo userInfo) throws TException {
        CompanyHelper companyHelper = new CompanyHelper(companyDTO).check();
        ThriftResponse response = new ThriftResponse();
        if (companyHelper.isError()) {
            log.error("调用方无权限，公司信息{}", JSON.toJSONString(companyDTO));
            return companyHelper.getResponse();
        }
        if (userInfo.getId() <= 0) {
            log.warn("更新id:{}的用户信息失败", userInfo.getId());
            return PARAM_NULL.of();
        }
        int res = userService.updateUserInfoById(companyHelper.getDatabaseName(), userInfo);
        if (res <= 0) {
            log.warn("更新id:{}的用户信息失败", userInfo.getId());
            return DATA_NULL.of();
        }
        response.setCode(SUCCESS.of().getCode());
        response.setMessage(SUCCESS.of().getMessage());
        log.info("id：{}查询用户信息成功！", userInfo.getId());
        return response;
    }

    @Override
    public ThriftResponse getUserInfoByCookie(CompanyDTO companyDTO, String cookie) throws TException {
        CompanyHelper companyHelper = new CompanyHelper(companyDTO).check();
        ThriftResponse response = new ThriftResponse();
        if (companyHelper.isError()) {
            log.error("调用方无权限，公司信息{}", JSON.toJSONString(companyDTO));
            return companyHelper.getResponse();
        }
        UserVo user = userService.getUserInfoByCookie(companyHelper.getDatabaseName(), cookie);
        if (user == null) {
            log.warn("查询cookie:{}的用户信息失败", cookie);
            return DATA_NULL.of();
        }
        response.setCode(SUCCESS.of().getCode());
        response.setMessage(SUCCESS.of().getMessage());
        response.setData(JSON.toJSONString(user));
        log.info("cookie：{}查询用户信息成功！", cookie);
        return response;
    }

    @Override
    public UserInfoResponse getUserInfoByIdList(CompanyDTO companyDTO, List<Long> idList) throws TException {
        CompanyHelper companyHelper = new CompanyHelper(companyDTO).check();
        UserInfoResponse response = new UserInfoResponse();
        if (companyHelper.isError()) {
            log.error("调用方无权限，公司信息{}", JSON.toJSONString(companyDTO));
            response.setCode(companyHelper.getResponse().getCode());
            response.setMessage(companyHelper.getResponse().getMessage());
            return response;
        }
        Map<Long, UserInfo> userInfoMap = userService.getUserInfoByIdList(companyHelper.getDatabaseName(), idList);
        if (userInfoMap == null || userInfoMap.size() == 0) {
            log.warn("查询id:{}的用户信息失败", idList);
            response.setCode(SYSTEM_ERROR.of().getCode());
            response.setMessage(SYSTEM_ERROR.of().getMessage());
            return response;
        }
        response.setCode(SUCCESS.of().getCode());
        response.setMessage(SUCCESS.of().getMessage());
        response.setData(userInfoMap);
        log.info("通过用户id查询用户信息成功，idList:{}", idList);
        return response;
    }
}
