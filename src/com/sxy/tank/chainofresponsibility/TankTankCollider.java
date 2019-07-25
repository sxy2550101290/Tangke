package com.sxy.tank.chainofresponsibility;

import com.sxy.tank.AbstractGameObject;
import com.sxy.tank.Tank;

public class TankTankCollider implements Collider {
    @Override
    public boolean collide(AbstractGameObject obj1, AbstractGameObject obj2) {
        if(obj1!=obj2) {
            if (obj1 instanceof Tank && obj2 instanceof Tank) {
                Tank t1 = (Tank) obj1;
                Tank t2 = (Tank) obj2;
                if (t1.isLive() && t2.isLive()) {
                    if (t1.getRect().intersects(t2.getRect())) {
                        t1.back();
                        t2.back();
                    }
                }
            }
        }
        return true;
    }
}
