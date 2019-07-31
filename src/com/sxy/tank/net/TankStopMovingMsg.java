package com.sxy.tank.net;

import com.sxy.tank.Dir;
import com.sxy.tank.Tank;
import com.sxy.tank.TankFrame;

import java.io.*;
import java.util.UUID;

public class TankStopMovingMsg extends Msg{
    private UUID id;
    private int x,y;

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


    public TankStopMovingMsg(UUID id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
    public TankStopMovingMsg() {
    }

    @Override
    public String toString() {
        return "TankStopMovingMsg{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
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
        if(!TankFrame.INSTANCE.getGm().getMytank().isLive()) return;
        //自己发的 return
        if(this.id.equals(TankFrame.INSTANCE.getGm().getMytank().getId())) return;
        //找到对应的坦克
        Tank t=TankFrame.INSTANCE.getGm().findTankByUUID(this.id);
        if(null != t){
            t.setMoving(false);
            t.setX(this.x);
            t.setY(this.y);
        }
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankStopMoving;
    }
}
