package com.aries.user.gaea.server;

import com.aries.hera.client.thrift.DiscoverClient;
import com.aries.hera.client.thrift.ThriftHelper;
import com.aries.hera.contract.thrift.dto.ServiceInfo;
import com.aries.hera.contract.thrift.service.DiscoverService;
import com.aries.hera.core.utils.PropertiesProxy;
import com.aries.user.gaea.contact.service.CompanyBaseService;
import com.aries.user.gaea.contact.service.UserBaseService;
import com.aries.user.gaea.server.thrift.CompanyBaseServiceImpl;
import com.aries.user.gaea.server.thrift.UserBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

@Slf4j
public class GaeaThriftServer {

    public static void main(String[] args) {
        try {
            TMultiplexedProcessor tMultiplexedProcessor = new TMultiplexedProcessor();

            {
                UserBaseService.Iface userBaseService = new UserBaseServiceImpl();
                UserBaseService.Processor userBaseProcessor = new UserBaseService.Processor(userBaseService);
                String simpleName = UserBaseService.class.getSimpleName();
                tMultiplexedProcessor.registerProcessor(simpleName, userBaseProcessor);
                log.info("simpleName:{}", simpleName);
            }

            {
                CompanyBaseService.Iface companyBaseService = new CompanyBaseServiceImpl();
                CompanyBaseService.Processor companyBaseProcessor = new CompanyBaseService.Processor(companyBaseService);
                String simpleName = CompanyBaseService.class.getSimpleName();
                tMultiplexedProcessor.registerProcessor(simpleName, companyBaseProcessor);
                log.info("simpleName:{}", simpleName);
            }

            final int port = 6010;

            new Thread(() -> {
                try {
                    TServerTransport serverTransport = new TServerSocket(port);
                    TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(tMultiplexedProcessor));
                    log.info("服务启动,端口:{}", port);
                    server.serve();

                } catch (Exception e) {
                    log.error("服务异常,error:{}", e.getMessage(), e);
                }
            }, "thrift-service-starter-thread").start();

            // 注册服务
            PropertiesProxy heraProperties = new PropertiesProxy("hera-reg-service.properties");
            String apphost = heraProperties.readProperty("apphost");
            DiscoverClient.registe(new ServiceInfo("Gaea", apphost, port));
        } catch (Exception x) {
            log.error("创建服务失败,error:{}", x.getMessage(), x);
        }
    }
}
