package com.aries.user.gaea.client.utils;

import com.aries.hera.client.thrift.ThriftHelper;
import com.aries.hera.client.thrift.exception.ServiceNotFoundException;
import com.aries.user.gaea.client.constants.ClientConstants;
import com.aries.user.gaea.client.model.GaeaResponse;
import com.aries.user.gaea.contact.model.CompanyDTO;
import com.aries.user.gaea.contact.model.ThriftResponse;
import com.aries.user.gaea.contact.service.CompanyBaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;

import static com.aries.user.gaea.client.constants.GaeaResponseEnum.SYSTEM_ERROR;

@Slf4j
public class CompanyUtils {
    public static GaeaResponse register(CompanyDTO companyDTO) throws TException {
        ThriftResponse companyResponse = null;
        try {
            companyResponse = ThriftHelper.call(ClientConstants.PROJECT_NAME, CompanyBaseService.Client.class, client -> client.companyRegister(companyDTO));
        } catch (ServiceNotFoundException e) {
            log.error("Hera系统服务找不到，服务异常！");
            return SYSTEM_ERROR.of();
        }
        GaeaResponse response = new GaeaResponse();
        response.setCode(companyResponse.getCode());
        response.setMessage(companyResponse.getMessage());
        return response;
    }

    public static GaeaResponse getRegisterNO(CompanyDTO companyDTO) throws TException {
        ThriftResponse companyResponse = null;
        try {
            companyResponse = ThriftHelper.call(ClientConstants.PROJECT_NAME, CompanyBaseService.Client.class, client -> client.getRegisterNO(companyDTO));
        } catch (ServiceNotFoundException e) {
            log.error("Hera系统服务找不到，服务异常！");
            return SYSTEM_ERROR.of();
        }
        GaeaResponse response = new GaeaResponse();
        response.setCode(companyResponse.getCode());
        response.setMessage(companyResponse.getMessage());
        response.setData(companyResponse.getData());
        return response;
    }
}
