package com.sxy.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MsgEncoder extends MessageToByteEncoder<TankJoinMsg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, TankJoinMsg msg, ByteBuf byteBuf) throws Exception {
        byte[] bytes=msg.toBytes();
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

    }
}
