package com.sxy.tank.net;

import com.sxy.tank.Dir;
import com.sxy.tank.Player;
import com.sxy.tank.Tank;
import com.sxy.tank.TankFrame;

import java.io.*;
import java.util.UUID;

public class TankMovingOrDirChangeMsg extends Msg{
    private UUID id;
    private int x,y;
    private Dir dir;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public TankMovingOrDirChangeMsg(UUID id, int x, int y, Dir dir) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.dir = dir;
    }
    public TankMovingOrDirChangeMsg() {
    }
    @Override
    public String toString() {
        return "TankMovingOrDirChangeMsg{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                '}';
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos=null;
        DataOutputStream dos=null;
        byte[] bytes=null;

        try {
            baos=new ByteArrayOutputStream();
            dos=new DataOutputStream(baos);
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());

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
            this.id=new UUID(dis.readLong(), dis.readLong());
            this.x=dis.readInt();
            this.y=dis.readInt();
            this.dir=Dir.values()[dis.readInt()];
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
        if(this.id.equals(TankFrame.INSTANCE.getGm().getMytank().getId())) return;
        Tank t=TankFrame.INSTANCE.getGm().findTankByUUID(this.id);
        if(null != t){
            t.setMoving(true);
            t.setX(this.x);
            t.setY(this.y);
            t.setDir(this.dir);
        }
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankMovingOrDirChange;
    }
}
