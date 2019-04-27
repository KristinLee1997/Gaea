package com.aries.user.gaea.client;

import com.aries.user.gaea.contact.service.CompanyBaseService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.Test;

public class CompanyBaseServiceTest {
    @Test
    public void pingTest() throws TException {
        TTransport transport = new TSocket("localhost", 6010);
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);
        CompanyBaseService.Client client = new CompanyBaseService.Client(protocol);

        final String pong = client.ping();

        System.out.println(pong);

        transport.close();
//        System.out.println("234");
    }
}
