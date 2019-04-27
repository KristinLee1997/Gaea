package com.aries.user.gaea.server.thrift;

import com.aries.user.gaea.server.service.CompanyService;
import com.aries.user.gaea.server.service.impl.CompanyServiceImpl;
import com.aries.user.gaea.contact.model.CompanyRegisterDTO;
import com.aries.user.gaea.contact.model.CompanyResponse;
import com.aries.user.gaea.contact.service.CompanyBaseService;
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
    public CompanyResponse companyRegister(CompanyRegisterDTO companyRegisterDTO) throws TException {
        CompanyResponse response = new CompanyResponse();
        if (companyRegisterDTO.getName() == null || companyRegisterDTO.getPassword() == null) {
            response.setCode(400);
            response.setMessage("注册信息不完整，公司名称和密码为必填项，请检查信息后重新注册！");
            return response;
        }
        String registerNO = companyService.register(companyRegisterDTO.getName(), companyRegisterDTO.getPassword());
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
    public CompanyResponse getRegisterNO(CompanyRegisterDTO companyRegisterDTO) throws TException {
        CompanyResponse response = new CompanyResponse();
        if (companyRegisterDTO.getName() == null || companyRegisterDTO.getPassword() == null) {
            response.setCode(400);
            response.setMessage("查询信息不完整，公司名称和密码为必填项，请检查信息后重新查询！");
            return response;
        }
        String registerNO = companyService.getRegisterNo(companyRegisterDTO.getName(), companyRegisterDTO.getPassword());
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
}
