package com.aries.user.gaea.client.factory;

import com.aries.hera.client.thrift.ThriftHelper;
import com.aries.user.gaea.contact.service.CompanyBaseService;
import com.aries.user.gaea.contact.service.UserBaseService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

@Deprecated
public class GaeaClientFactory {
    public static CompanyBaseService.Client getCompanyUtilsSingleClient() throws TTransportException {
        TTransport transport = TransportFactory.getSingleTransport();
        TProtocol protocol = new TBinaryProtocol(transport);
        return new CompanyBaseService.Client(protocol);
    }

    public static UserBaseService.Client getUserUtilsSingleClient() throws TTransportException {
        TTransport transport = TransportFactory.getSingleTransport();
        TProtocol protocol = new TBinaryProtocol(transport);
        return new UserBaseService.Client(protocol);
    }
}
