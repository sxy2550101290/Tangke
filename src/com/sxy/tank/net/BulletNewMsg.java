package com.sxy.tank.net;

import com.sxy.tank.Bullet;
import com.sxy.tank.Dir;
import com.sxy.tank.Group;
import com.sxy.tank.TankFrame;

import java.io.*;
import java.util.UUID;

public class BulletNewMsg extends Msg {
    private int x,y;
    private UUID id;
    private Dir dir;
    private UUID playerId;
    private Group group;

    public BulletNewMsg() {
    }
    public BulletNewMsg(int x, int y, UUID id, Dir dir, UUID playerId, Group group) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.dir = dir;
        this.playerId = playerId;
        this.group = group;
    }

    @Override
    public String toString() {
        return "BulletNewMsg{" +
                "x=" + x +
                ", y=" + y +
                ", id=" + id +
                ", dir=" + dir +
                ", playerId=" + playerId +
                ", group=" + group +
                '}';
    }

    public BulletNewMsg(Bullet bullet){
        this.playerId=bullet.getPlayerId();
        this.id=bullet.getId();
        this.x=bullet.getX();
        this.y=bullet.getY();
        this.dir=bullet.getDir();
        this.group=bullet.getGroup();
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos=null;
        DataOutputStream dos=null;
        byte[] bytes=null;

        try {
            baos=new ByteArrayOutputStream();
            dos=new DataOutputStream(baos);

            dos.writeLong(playerId.getMostSignificantBits());
            dos.writeLong(playerId.getLeastSignificantBits());

            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeInt(x);
            dos.writeInt(y);

            dos.writeInt(dir.ordinal());
            dos.writeInt(group.ordinal());

            dos.flush();
            bytes=baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(null != dos)
                    dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(null != baos)
                    baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return bytes;
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis=new DataInputStream(new ByteArrayInputStream(bytes));

        try {
            this.playerId=new UUID(dis.readLong(), dis.readLong());

            this.id=new UUID(dis.readLong(), dis.readLong());

            this.x=dis.readInt();
            this.y=dis.readInt();

            this.dir=Dir.values()[dis.readInt()];
            this.group=Group.values()[dis.readInt()];

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handle() {
        if(this.playerId.equals(TankFrame.INSTANCE.getGm().getMytank().getId())) return;

        Bullet bullet=new Bullet(this.x, this.y, this.dir, this.group, this.playerId);
        bullet.setId(this.id);
        TankFrame.INSTANCE.getGm().add(bullet);
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.BulletNew;
    }
}
