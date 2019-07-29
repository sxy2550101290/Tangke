package nettyChat;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

public class Client {

    private Channel channel=null;

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
                    socketChannel.pipeline().addLast(new MyHandler());
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

    public void send(String s) {
        channel.writeAndFlush(Unpooled.copiedBuffer(s.getBytes()));
    }

    public void closeConnection() {
        send("__bye__");
        channel.close();
    }

    static class MyHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf=null;

            try {
                buf=(ByteBuf)msg;
                byte[] bytes=new byte[buf.readableBytes()];
                buf.getBytes(buf.readerIndex(), bytes);
                String str=new String(bytes);
                System.out.println(str);
                ClientFrame.INSTANCE.updateText(str);
            } finally {
                //System.out.println(buf.refCnt());
                if(null != buf)
                    ReferenceCountUtil.release(buf);
                //System.out.println(msg.toString());
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
