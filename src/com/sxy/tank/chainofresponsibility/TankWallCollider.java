package com.sxy.tank.chainofresponsibility;

import com.sxy.tank.AbstractGameObject;
import com.sxy.tank.Bullet;
import com.sxy.tank.Tank;
import com.sxy.tank.Wall;

public class TankWallCollider implements Collider {
    @Override
    public boolean collide(AbstractGameObject obj1, AbstractGameObject obj2) {
        if(obj1 instanceof Wall && obj2 instanceof Tank){
            Wall w=(Wall)obj1;
            Tank t=(Tank)obj2;
            if(t.isLive()){
                if(t.getRect().intersects(w.getRect())){
                    t.back();
                }
            }
        } else if (obj1 instanceof  Tank && obj2 instanceof Bullet){
            collide(obj2,obj1);
        }
        return true;
    }
}
