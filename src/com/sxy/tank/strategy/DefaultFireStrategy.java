package com.sxy.tank.strategy;

import com.sxy.tank.*;
import com.sxy.tank.net.BulletNewMsg;
import com.sxy.tank.net.Client;

/**
 * 基础炮弹
 */
public class DefaultFireStrategy implements FireStrategy{
    @Override
    public void fire(Player p) {
        int bx=p.getX()+ ResourceMgr.goodTankU.getWidth()/2-ResourceMgr.bulletU.getWidth()/2;
        int by=p.getY()+ResourceMgr.goodTankU.getHeight()/2-ResourceMgr.bulletU.getHeight()/2;
        Bullet b=new Bullet(bx, by, p.getDir(), p.getGroup(),p.getId());
        TankFrame.INSTANCE.getGm().add(b);
        //send a bullet msg to server when a bullet is born
        Client.INSTANCE.send(new BulletNewMsg(b));
    }
}
