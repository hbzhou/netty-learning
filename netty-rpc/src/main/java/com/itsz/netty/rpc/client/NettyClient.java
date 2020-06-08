package com.itsz.netty.rpc.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ForkJoinPool;

public class NettyClient {

    private ClientChannelHandler clientChannelHandler;

    private static final String HOSTNAME = "127.0.0.1";

    private static final int PORT = 9999;

    public Object getObject(Class<?> clazz, String providerName) {

        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{clazz}, (proxy, method, args) -> {
            clientChannelHandler.setParam(providerName + args[0]);
            return ForkJoinPool.commonPool().submit(clientChannelHandler).get();

        });
    }

    public void startup() {
        clientChannelHandler = new ClientChannelHandler();
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(clientChannelHandler);

                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(HOSTNAME, PORT);
            channelFuture.addListener(future -> {
                if (future.isSuccess()){
                    clientChannelHandler.setChannel( channelFuture.channel());
                    System.out.println("connected succsss!!!" + Thread.currentThread().getName());
                }
            } );
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            eventLoopGroup.shutdownGracefully();
        }

    }

    public ClientChannelHandler getClientChannelHandler() {
        return clientChannelHandler;
    }

}
