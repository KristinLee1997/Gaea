package com.aries.user.gaea.server;

import com.aries.user.gaea.contact.service.CompanyBaseService;
import com.aries.user.gaea.server.thrift.CompanyBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

@Slf4j
public class CompanyServer {
    private static CompanyBaseService.Iface companyService;

    private static CompanyBaseService.Processor companyProcessor;

    public static void main(String[] args) {
        try {
            companyService = new CompanyBaseServiceImpl();
            companyProcessor = new CompanyBaseService.Processor(companyService);

            new Thread(() -> {
                try {
                    TServerTransport serverTransport = new TServerSocket(6010);
                    TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(companyProcessor));
                    log.info("服务启动,端口:{}", 6010);
                    server.serve();

                } catch (Exception e) {
                    log.error("服务异常,error:{}", e.getMessage(), e);
                }
            }, "thrift-service-starter-thread-company").start();

        } catch (Exception x) {
            log.error("创建服务失败,error:{}", x.getMessage(), x);
        }
    }
}
