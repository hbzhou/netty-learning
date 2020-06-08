package com.itsz.netty.fast.protocol.client;

import com.itsz.netty.fast.protocol.HeatBeatPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@ChannelHandler.Sharable
public abstract class ConnectionWatchdog extends SimpleChannelInboundHandler<HeatBeatPacket>
		implements TimerTask, ChannelHandlerHolder {

	private static final Logger logger = LoggerFactory.getLogger(ConnectionWatchdog.class);

	private Bootstrap bootstrap;
	private Timer timer;
	private final String host;
	private final int port;

	private volatile boolean reconnect = true;
	private int attempts;
	private volatile long refreshTime = 0L;
	private volatile boolean heartBeatCheck = false;
	private volatile Channel channel;

	public ConnectionWatchdog(Bootstrap boot, Timer timert, String host, int port) {
		this.bootstrap = boot;
		this.timer = timert;
		this.host = host;
		this.port = port;
	}

	public boolean isReconnect() {
		return reconnect;
	}

	public void setReconnect(boolean reconnect) {
		this.reconnect = reconnect;
	}

	@Override
	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
		sendLogon(ctx);
		channel = ctx.channel();
		attempts = 0;
		reconnect =true;
		refreshTime = new Date().getTime();
		if (!heartBeatCheck) {
			heartBeatCheck = true;
			channel.eventLoop().scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					if (new Date().getTime() - refreshTime > 10 * 1000L) {
						channel.close();
						logger.info("心跳检查失败,等待重连服务器---------");
					} else {
						logger.info("心跳检查Successs");
					}
				}
			}, 5L, 5L, TimeUnit.SECONDS);
		}
		logger.info("Connects with {}.", channel);
		ctx.fireChannelActive();
	}

	private void sendLogon(final ChannelHandlerContext ctx) {
		ByteBuf byteBuf = Unpooled.buffer(StepUtil.Logon().getBytes().length);
		byteBuf.writeBytes(StepUtil.Logon().getBytes());
		ctx.writeAndFlush(byteBuf);
	}

	

	/**
	 * 因为链路断掉之后，会触发channelInActive方法，进行重连 重连11次后 不再重连
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.warn("Disconnects with {}, doReconnect = {},attemps == {}", ctx.channel(), reconnect, attempts);
		if (reconnect) {
			if (attempts < 12) {
				attempts++;
			} else {
				reconnect = false;
			}
			long timeout = 2 << attempts;
			logger.info("After {} seconds client will do reconnect",timeout);
			timer.newTimeout(this, timeout, TimeUnit.SECONDS);
		}
		
	}

	public void run(Timeout timeout) throws Exception {

		final ChannelFuture future;
		synchronized (bootstrap) {
			future = bootstrap.connect(host, port);
		}
		future.addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(final ChannelFuture f) throws Exception {
				boolean succeed = f.isSuccess();
				logger.warn("Reconnects with {}, {}.", host + ":" + port, succeed ? "succeed" : "failed");
				if (!succeed) {
					f.channel().pipeline().fireChannelInactive();
				}
			}
		});

	}
	
	public void refreshTime(){
		refreshTime = new Date().getTime();
	}


}
