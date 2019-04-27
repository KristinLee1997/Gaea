package com.aries.user.gaea.client.utils;

import com.aries.user.gaea.client.factory.GaeaClientFactory;
import com.aries.user.gaea.client.model.GaeaResponse;
import com.aries.user.gaea.contact.model.CompanyRegisterDTO;
import com.aries.user.gaea.contact.model.CompanyResponse;
import com.aries.user.gaea.contact.service.CompanyBaseService;
import org.apache.thrift.TException;


public class CompanyUtils {
    public static GaeaResponse register(CompanyRegisterDTO companyRegisterDTO) throws TException {
        CompanyBaseService.Client client = GaeaClientFactory.getCompanyUtilsSingleClient();
        CompanyResponse companyResponse = client.companyRegister(companyRegisterDTO);
        GaeaResponse response = new GaeaResponse();
        response.setCode(companyResponse.getCode());
        response.setMessage(companyResponse.getMessage());
        return response;
    }

    public static GaeaResponse getRegisterNO(CompanyRegisterDTO companyRegisterDTO) throws TException {
        CompanyBaseService.Client client = GaeaClientFactory.getCompanyUtilsSingleClient();
        CompanyResponse companyResponse = client.getRegisterNO(companyRegisterDTO);
        GaeaResponse response = new GaeaResponse();
        response.setCode(companyResponse.getCode());
        response.setMessage(companyResponse.getMessage());
        response.setData(companyResponse.getData());
        return response;
    }
}
