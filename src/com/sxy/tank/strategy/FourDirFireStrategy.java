package com.sxy.tank.strategy;

import com.sxy.tank.*;

public class FourDirFireStrategy implements FireStrategy {
    @Override
    public void fire(Player p) {
        int bx=p.getX()+ ResourceMgr.goodTankU.getWidth()/2-ResourceMgr.bulletU.getWidth()/2;
        int by=p.getY()+ResourceMgr.goodTankU.getHeight()/2-ResourceMgr.bulletU.getHeight()/2;
        Dir[] dirs=Dir.values();
        for (Dir dir : dirs) {
            TankFrame.INSTANCE.getGm().add(new Bullet(bx, by, dir, p.getGroup()));
        }
    }
}
