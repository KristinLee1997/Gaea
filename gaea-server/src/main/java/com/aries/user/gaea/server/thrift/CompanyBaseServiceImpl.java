package com.aries.user.gaea.server.thrift;

import com.alibaba.fastjson.JSON;
import com.aries.user.gaea.contact.model.CompanyDTO;
import com.aries.user.gaea.contact.model.ThriftResponse;
import com.aries.user.gaea.contact.service.CompanyBaseService;
import com.aries.user.gaea.server.constants.GaeaResponseEnum;
import com.aries.user.gaea.server.constants.SysConstants;
import com.aries.user.gaea.server.model.po.Company;
import com.aries.user.gaea.server.service.CompanyService;
import com.aries.user.gaea.server.service.impl.CompanyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;

import static com.aries.user.gaea.server.constants.GaeaResponseEnum.*;

@Slf4j
public class CompanyBaseServiceImpl implements CompanyBaseService.Iface {
    private static CompanyService companyService = new CompanyServiceImpl();

    @Override
    public String ping() throws TException {
        log.debug("pong- -.");
        return "pong ,i am CompanyBaseServiceImpl...";
    }

    @Override
    public ThriftResponse companyRegister(CompanyDTO companyDTO) throws TException {
        ThriftResponse response = new ThriftResponse();
        if (companyDTO.getName() == null || companyDTO.getPassword() == null) {
            log.warn("公司注册，参数不完整存在null，参数:{}", JSON.toJSONString(companyDTO));
            return PARAM_NULL.of();
        }
        String registerNO = companyService.register(convert2CompanyPO(companyDTO));
        if (registerNO == null || registerNO.equals("") || registerNO.equals(" ")) {
            log.error("公司注册接口调用失败，参数:{}", JSON.toJSONString(companyDTO));
            return SYSTEM_ERROR.of();
        }
        response.setCode(200);
        response.setMessage("注册成功，请将公司名称、注册码填写到配置文件中~");
        response.setData(registerNO);
        log.info("公司name:{},注册成功", companyDTO.getName());
        return response;
    }

    @Override
    public ThriftResponse getRegisterNO(CompanyDTO companyDTO) throws TException {
        ThriftResponse response = new ThriftResponse();
        if (companyDTO.getName() == null || companyDTO.getPassword() == null) {
            log.warn("获取公司注册码失败，参数不完整存在null，参数:{}", JSON.toJSONString(companyDTO));
            return PARAM_NULL.of();
        }
        String registerNO = companyService.getRegisterNo(convert2CompanyPO(companyDTO));
        if (registerNO == null || registerNO.equals("") || registerNO.equals(" ")) {
            log.error("获取公司注册码失败，参数:{}", JSON.toJSONString(companyDTO));
            return SYSTEM_ERROR.of();
        }
        response.setCode(200);
        response.setMessage("查询成功，请及时将公司名称、注册码填写到配置文件中~");
        response.setData(registerNO);
        log.info("获取公司name:{}注册码成功", companyDTO.getName());
        return response;
    }

    private static Company convert2CompanyPO(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setName(companyDTO.getName());
        company.setPassword(companyDTO.getPassword());
        company.setRegisterno(company.getRegisterno());
        return company;
    }
}
