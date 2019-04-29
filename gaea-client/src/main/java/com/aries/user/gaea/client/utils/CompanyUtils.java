package com.aries.user.gaea.client.utils;

import com.aries.user.gaea.client.factory.GaeaClientFactory;
import com.aries.user.gaea.client.model.GaeaResponse;
import com.aries.user.gaea.contact.model.CompanyDTO;
import com.aries.user.gaea.contact.model.ThriftResponse;
import com.aries.user.gaea.contact.service.CompanyBaseService;
import org.apache.thrift.TException;


public class CompanyUtils {
    public static GaeaResponse register(CompanyDTO companyDTO) throws TException {
        CompanyBaseService.Client client = GaeaClientFactory.getCompanyUtilsSingleClient();
        ThriftResponse companyResponse = client.companyRegister(companyDTO);
        GaeaResponse response = new GaeaResponse();
        response.setCode(companyResponse.getCode());
        response.setMessage(companyResponse.getMessage());
        return response;
    }

    public static GaeaResponse getRegisterNO(CompanyDTO companyDTO) throws TException {
        CompanyBaseService.Client client = GaeaClientFactory.getCompanyUtilsSingleClient();
        ThriftResponse companyResponse = client.getRegisterNO(companyDTO);
        GaeaResponse response = new GaeaResponse();
        response.setCode(companyResponse.getCode());
        response.setMessage(companyResponse.getMessage());
        response.setData(companyResponse.getData());
        return response;
    }
}
