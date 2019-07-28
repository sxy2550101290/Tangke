package com.sxy.tank.net;

import com.sxy.tank.GameModel;
import com.sxy.tank.TankFrame;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;
import nettyChat.ClientFrame;
import nettycodec.TankMsg;
import nettycodec.TankMsgEncoder;

public class Client {

    public static final Client INSTANCE=new Client();

    private Channel channel=null;

    private Client(){
    }

    public void connerc() {
        EventLoopGroup workerGroup=new NioEventLoopGroup(1);
        Bootstrap b=new Bootstrap();

        try {
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    channel=socketChannel;
                    socketChannel.pipeline()
                            .addLast(new MsgEncoder())
                            .addLast(new MsgDecoder())
                            .addLast(new MyHandler());
                }
            });
            ChannelFuture future = b.connect("localhost", 8888).sync();
            System.out.println("connected to server");
            //等待关闭
            future.channel().closeFuture().sync();
            System.out.println("go on");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
        }
    }

    public void send(TankJoinMsg msg) {
        channel.writeAndFlush(msg);
    }

    public void closeConnection() {
        channel.close();
    }

    static class MyHandler extends SimpleChannelInboundHandler<TankJoinMsg> {

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getGm().getMytank()));

        }

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, TankJoinMsg msg) throws Exception {
            System.out.println(msg.toString());
            msg.handle();

        }
    }
    public static void main(String[] args) {
        Client c=new Client();
        c.connerc();
    }
}
