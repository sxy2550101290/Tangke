package com.sxy.tank.net;

import com.sxy.tank.Dir;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TankStopMovingTest {

    @Test
    void decode() {
        EmbeddedChannel ch=new EmbeddedChannel();
        ch.pipeline().addLast(new MsgDecoder());

        ByteBuf buf= Unpooled.buffer();
        UUID id=UUID.randomUUID();
        buf.writeInt(MsgType.TankStopMoving.ordinal());
        buf.writeInt(24);
        buf.writeLong(id.getMostSignificantBits());
        buf.writeLong(id.getLeastSignificantBits());
        buf.writeInt(5);
        buf.writeInt(8);
        ch.writeInbound(buf);

        TankStopMovingMsg tsmm = ch.readInbound();
        assertEquals(id, tsmm.getId());
        assertEquals(5,tsmm.getX());
        assertEquals(8,tsmm.getY() );

    }
    @Test
    void encode() {
        EmbeddedChannel ch=new EmbeddedChannel();

        ch.pipeline().addLast(new MsgEncoder());
        TankStopMovingMsg msg=new TankStopMovingMsg(UUID.randomUUID(),50,100);

        ch.writeOutbound(msg);

        ByteBuf buf = ch.readOutbound();
        MsgType msgType=MsgType.values()[buf.readInt()];
        int length=buf.readInt();
        UUID id=new UUID(buf.readLong(), buf.readLong());
        int x=buf.readInt();
        int y=buf.readInt();

        assertEquals(MsgType.TankStopMoving, msgType );
        assertEquals(24,length );
        assertEquals(50,x);
        assertEquals(100,y);
    }
}