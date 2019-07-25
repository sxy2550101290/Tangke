package com.sxy.tank.chainofresponsibility;

import com.sxy.tank.AbstractGameObject;
import com.sxy.tank.Bullet;
import com.sxy.tank.ResourceMgr;
import com.sxy.tank.Tank;

import java.awt.*;

public class BulletTankCollider implements Collider {
    @Override
    public boolean collide(AbstractGameObject obj1, AbstractGameObject obj2) {
        if(obj1 instanceof Bullet && obj2 instanceof Tank){
            Bullet b=(Bullet)obj1;
            Tank t=(Tank)obj2;
            //子弹 坦克是否死掉
            if(!b.isLive() || !t.isLive())return false;

            if(b.getGroup()==t.getGroup()) return true;

            //Rectangle rect = new Rectangle(x, y,w , h);
            Rectangle rectTank = t.getRect();
            if(b.getRect().intersects(rectTank)){
                b.die();
                t.die();
                return false;
            }

        } else if (obj1 instanceof  Tank && obj2 instanceof Bullet){
            return collide(obj2,obj1);
        }
        return true;
    }
}
