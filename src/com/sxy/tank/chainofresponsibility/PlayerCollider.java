package com.sxy.tank.chainofresponsibility;

import com.sxy.tank.*;
import com.sxy.tank.net.Client;
import com.sxy.tank.net.TankDieMsg;


public class PlayerCollider  {

    public void collide(AbstractGameObject obj, Player myTank) {
        if(obj instanceof Bullet) {
            Bullet b=(Bullet) obj;
            if(!b.isLive()||!myTank.isLive()) return;
            if(b.getRect().intersects(myTank.getRect())){
                if(!b.getPlayerId().equals(myTank.getId())){
                    b.die();
                    myTank.die();
                    Client.INSTANCE.send(new TankDieMsg(myTank.getId(),b.getId()));
                    return;
                }
            }
        }else if(obj instanceof Tank){
            Tank t=(Tank) obj;
            if(!t.isLive()||!myTank.isLive()) return;
            if(t.getRect().intersects(myTank.getRect())) {
                t.back();
                myTank.back();
                return;
            }
        }else if(obj instanceof Wall){
            Wall w=(Wall)obj;
            if(myTank.isLive()){
                if(myTank.getRect().intersects(w.getRect())){
                    myTank.back();
                    return;
                }
            }
        }else{
            return;
        }
    }
}
