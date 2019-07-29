package netty1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public class Server {
    public static void main(String[] args) {
        //接客线程
        EventLoopGroup boss=new NioEventLoopGroup(2);
        //服务线程
        EventLoopGroup work=new NioEventLoopGroup(4);

        ServerBootstrap b=new ServerBootstrap();
        b.group(boss,work);



    }
}
