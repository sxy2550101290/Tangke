package com.sxy.tank.net;

import com.sxy.tank.Dir;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TankMovingOrDirChangeTest {

    @Test
    void decode() {
        EmbeddedChannel ch=new EmbeddedChannel();
        ch.pipeline().addLast(new MsgDecoder());

        ByteBuf buf= Unpooled.buffer();
        UUID id=UUID.randomUUID();
        buf.writeInt(MsgType.TankMovingOrDirChange.ordinal());
        buf.writeInt(28);
        buf.writeLong(id.getMostSignificantBits());
        buf.writeLong(id.getLeastSignificantBits());
        buf.writeInt(5);
        buf.writeInt(8);
        buf.writeInt(Dir.D.ordinal());
        ch.writeInbound(buf);

        TankMovingOrDirChangeMsg tsmm = ch.readInbound();
        assertEquals(id, tsmm.getId());
        assertEquals(5,tsmm.getX());
        assertEquals(8,tsmm.getY() );
        assertEquals(Dir.D, tsmm.getDir());

    }
    @Test
    void encode() {
        EmbeddedChannel ch=new EmbeddedChannel();

        ch.pipeline().addLast(new MsgEncoder());
        TankMovingOrDirChangeMsg msg=new TankMovingOrDirChangeMsg(UUID.randomUUID(),50,100,Dir.D);

        ch.writeOutbound(msg);

        ByteBuf buf = ch.readOutbound();
        MsgType msgType=MsgType.values()[buf.readInt()];
        int length=buf.readInt();
        UUID id=new UUID(buf.readLong(), buf.readLong());
        int x=buf.readInt();
        int y=buf.readInt();
        Dir dir=Dir.values()[buf.readInt()];

        assertEquals(MsgType.TankMovingOrDirChange, msgType );
        assertEquals(28,length );
        assertEquals(50,x);
        assertEquals(100,y);
        assertEquals(Dir.D,dir );
    }
}