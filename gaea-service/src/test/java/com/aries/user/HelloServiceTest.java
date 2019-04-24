package com.aries.user;

import com.aries.user.gaea.service.HelloService;
import org.junit.Test;

import java.io.IOException;

public class HelloServiceTest {
    @Test
    public void helloService() {
        try {
            new HelloService().selectById();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}