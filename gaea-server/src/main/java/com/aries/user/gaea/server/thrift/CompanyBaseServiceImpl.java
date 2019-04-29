package com.aries.user.gaea.server.thrift;

import com.aries.user.gaea.contact.model.CompanyDTO;
import com.aries.user.gaea.contact.model.ThriftResponse;
import com.aries.user.gaea.contact.service.CompanyBaseService;
import com.aries.user.gaea.server.model.po.Company;
import com.aries.user.gaea.server.service.CompanyService;
import com.aries.user.gaea.server.service.impl.CompanyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;

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
            response.setCode(400);
            response.setMessage("注册信息不完整，公司名称和密码为必填项，请检查信息后重新注册！");
            return response;
        }
        String registerNO = companyService.register(convert2CompanyPO(companyDTO));
        if (registerNO == null || registerNO.equals("") || registerNO.equals(" ")) {
            response.setCode(500);
            response.setMessage("注册码获取失败，请联系RD查询原因");
            return response;
        }
        response.setCode(200);
        response.setMessage("注册成功，请将公司名称、注册码填写到配置文件中~");
        response.setData(registerNO);
        return response;
    }

    @Override
    public ThriftResponse getRegisterNO(CompanyDTO companyDTO) throws TException {
        ThriftResponse response = new ThriftResponse();
        if (companyDTO.getName() == null || companyDTO.getPassword() == null) {
            response.setCode(400);
            response.setMessage("查询信息不完整，公司名称和密码为必填项，请检查信息后重新查询！");
            return response;
        }
        String registerNO = companyService.getRegisterNo(convert2CompanyPO(companyDTO));
        if (registerNO == null || registerNO.equals("") || registerNO.equals(" ")) {
            response.setCode(500);
            response.setMessage("注册码获取失败，请联系RD查询原因");
            return response;
        }
        response.setCode(200);
        response.setMessage("查询成功，请及时将公司名称、注册码填写到配置文件中~");
        response.setData(registerNO);
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
