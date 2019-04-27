package com.aries.user.gaea.server;

import com.aries.user.gaea.contact.service.CompanyBaseService;
import com.aries.user.gaea.contact.service.UserBaseService;
import com.aries.user.gaea.server.thrift.CompanyBaseServiceImpl;
import com.aries.user.gaea.server.thrift.UserBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

@Slf4j
public class UserServer {
    private static UserBaseService.Iface service;

    private static UserBaseService.Processor processor;

    public static void main(String[] args) {
        try {
            service = new UserBaseServiceImpl();
            processor = new UserBaseService.Processor(service);

            new Thread(() -> {
                try {
                    TServerTransport serverTransport = new TServerSocket(6010);
                    TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
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
