package com.itsz.netty.rpc.server;

public class RpcProviderServer {

    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.startup(9999);
    }
}
