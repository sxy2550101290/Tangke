package com.sxy.tank.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import nettycodec.TankMsg;
import nettycodec.TankMsgDecoder;

public class Server {
    private static ChannelGroup clients=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public void serverStart() {
        //负责接客
        EventLoopGroup bossGroup=new NioEventLoopGroup(2);
        //负责服务
        EventLoopGroup workerGroup=new NioEventLoopGroup(4);
        try {
            ServerBootstrap b=new ServerBootstrap();
            ChannelFuture future=b.group(bossGroup,workerGroup)
                //异步全双工
                .channel(NioServerSocketChannel.class)
                //netty 帮我们内部处理了accept的过程
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                //.addLast(new TankMsgDecoder())
                                .addLast(new MyChildHandler());
                    }
                })
                .bind(8888)
                .sync();
            ServerFram.INSTANCE.updateServerMsg("server started!");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    static class MyChildHandler extends ChannelInboundHandlerAdapter{

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            Server.clients.add(ctx.channel());
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            //ServerFram.INSTANCE.updateClientMsg(msg.toString());
            clients.writeAndFlush(msg);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            Server.clients.remove(ctx.channel());
            ctx.close();
        }
    }
}



