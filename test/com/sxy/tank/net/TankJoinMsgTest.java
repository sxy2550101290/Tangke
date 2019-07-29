package com.sxy.tank.net;

import com.sxy.tank.Dir;
import com.sxy.tank.Group;
import com.sxy.tank.Player;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import nettycodec.TankMsg;
import nettycodec.TankMsgDecoder;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TankJoinMsgTest {

    @Test
    void decode() {
        EmbeddedChannel ch=new EmbeddedChannel();
        ch.pipeline().addLast(new MsgDecoder());

        ByteBuf buf= Unpooled.buffer();
        UUID id=UUID.randomUUID();
        buf.writeInt(MsgType.TankJoin.ordinal());
        buf.writeInt(33);
        buf.writeInt(5);
        buf.writeInt(8);
        buf.writeInt(Dir.D.ordinal());
        buf.writeBoolean(true);
        buf.writeInt(Group.GOOD.ordinal());
        buf.writeLong(id.getMostSignificantBits());
        buf.writeLong(id.getLeastSignificantBits());

        ch.writeInbound(buf);

        TankJoinMsg tm = ch.readInbound();

        assertEquals(5,tm.getX());
        assertEquals(8,tm.getY() );
        assertEquals(Dir.D, tm.getDir());
        assertTrue( tm.isMoving());
        assertEquals(Group.GOOD, tm.getGroup());
        assertEquals(id, tm.getId());
    }
    @Test
    void encode() {
        EmbeddedChannel ch=new EmbeddedChannel();

        ch.pipeline().addLast(new MsgEncoder());
        Player p = new Player(50,100, Dir.D, Group.BAD);
        TankJoinMsg tjm=new TankJoinMsg(p);

        ch.writeOutbound(tjm);

        ByteBuf buf = ch.readOutbound();
        MsgType msgType=MsgType.values()[buf.readInt()];
        int length=buf.readInt();

        int x=buf.readInt();
        int y=buf.readInt();
        Dir dir=Dir.values()[buf.readInt()];
        boolean moving=buf.readBoolean();
        Group group=Group.values()[buf.readInt()];
        UUID id=new UUID(buf.readLong(), buf.readLong());
        assertEquals(MsgType.TankJoin, msgType );
        assertEquals(33,length );
        assertEquals(50,x);
        assertEquals(100,y);
        assertEquals(Dir.D,dir );
        assertEquals(Group.BAD,group);
        assertEquals(p.getId(),id);
    }
}