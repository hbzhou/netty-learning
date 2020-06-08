package com.itsz.netty.rpc.server.service.impl;

import com.itsz.netty.rpc.api.HelloWorldService;

public class HelloWorldServiceImpl implements HelloWorldService {

    @Override
    public String sayHello(String greeting) {
        return "Hello, " + greeting;
    }
}
