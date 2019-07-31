package com.sxy.tank.net;

import com.sxy.tank.Bullet;
import com.sxy.tank.Tank;
import com.sxy.tank.TankFrame;

import java.io.*;
import java.util.UUID;

public class TankDieMsg extends Msg{
    private UUID id;
    private UUID bulletId;
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getBulletId() {
        return bulletId;
    }

    public void setBulletId(UUID bulletId) {
        this.bulletId = bulletId;
    }

    public TankDieMsg() {
    }

    public TankDieMsg(UUID id, UUID bulletId) {
        this.id = id;
        this.bulletId = bulletId;
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos=null;
        DataOutputStream dos=null;
        byte[] bytes=null;

        try {
            baos=new ByteArrayOutputStream();
            dos=new DataOutputStream(baos);
            dos.writeLong(bulletId.getMostSignificantBits());
            dos.writeLong(bulletId.getLeastSignificantBits());

            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());

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
            this.bulletId=new UUID(dis.readLong(), dis.readLong());
            this.id=new UUID(dis.readLong(), dis.readLong());
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
        Bullet b =TankFrame.INSTANCE.getGm().findBulletByUUID(this.bulletId);

        //击中的坦克
        Tank t=TankFrame.INSTANCE.getGm().findTankByUUID(this.id);

        //自己发的 return
        if(this.id.equals(TankFrame.INSTANCE.getGm().getMytank().getId())) {
            TankFrame.INSTANCE.getGm().getMytank().die();

        }else{
            if(null != t) {
                t.die();
                TankFrame.INSTANCE.getGm().remove(t);
            }
        }

    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankStopMoving;
    }
}
