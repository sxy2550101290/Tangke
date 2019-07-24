package com.sxy.tank.strategy;

import com.sxy.tank.*;

/**
 * 基础炮弹
 */
public class DefaultFireStrategy implements FireStrategy{
    @Override
    public void fire(Player p) {
        int bx=p.getX()+ ResourceMgr.goodTankU.getWidth()/2-ResourceMgr.bulletU.getWidth()/2;
        int by=p.getY()+ResourceMgr.goodTankU.getHeight()/2-ResourceMgr.bulletU.getHeight()/2;
        TankFrame.INSTANCE.add(new Bullet(bx, by, p.getDir(), p.getGroup()));
    }
}
