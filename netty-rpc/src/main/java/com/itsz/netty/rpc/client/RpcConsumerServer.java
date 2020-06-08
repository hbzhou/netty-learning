package com.itsz.netty.rpc.client;

import com.itsz.netty.rpc.api.HelloWorldService;

public class RpcConsumerServer {

    public static final String PROVIDER_NAME = "HelloWorldService#sayHello#";

    public static void main(String[] args) throws InterruptedException {

        NettyClient nettyClient = new NettyClient();

        new Thread(()->{
            nettyClient.startup();
        }).start();

        Thread.sleep(2000);

        HelloWorldService helloWorldService = ((HelloWorldService) nettyClient.getObject(HelloWorldService.class, PROVIDER_NAME));
        System.out.println(helloWorldService.sayHello(" Rpc~"));


    }
}
