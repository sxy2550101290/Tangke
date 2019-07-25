package com.sxy.tank.chainofresponsibility;

import com.sxy.tank.AbstractGameObject;
import com.sxy.tank.Bullet;
import com.sxy.tank.Tank;
import com.sxy.tank.Wall;

public class BulletWallCollider implements Collider {
    @Override
    public boolean collide(AbstractGameObject obj1, AbstractGameObject obj2) {
        if(obj1 instanceof Bullet && obj2 instanceof Wall){
            Bullet b=(Bullet)obj1;
            Wall w=(Wall)obj2;
            if(b.isLive()){
                if(w.getRect()!=null&&b.getRect()!=null) {
                    if (w.getRect().intersects(b.getRect())) {
                        b.die();
                        return false;
                    }
                }
            }
        } else if (obj1 instanceof  Tank && obj2 instanceof Bullet){
            return collide(obj2,obj1);
        }
        return true;
    }
}
