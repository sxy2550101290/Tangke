package com.sxy.tank.chainofresponsibility;

import com.sxy.tank.AbstractGameObject;
import com.sxy.tank.PropertyMgr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 碰撞责任链
 */
public class ColliderChain implements Collider{

    private List<Collider> colliders;

    public ColliderChain(){
        initColliders();
    }
    private void initColliders() {
        colliders=new ArrayList<>();
        String[] classNames = PropertyMgr.get("colliders").split(",");
        for (String className : classNames) {
            try {
                Class clazz=Class.forName("com.sxy.tank.chainofresponsibility."+className);
                Collider c = (Collider) clazz.newInstance();
                colliders.add(c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public boolean collide(AbstractGameObject go1,AbstractGameObject go2){
        for (Collider collider : colliders) {
            if(!collider.collide(go1, go2)){
                break;
            }
        }
        return true;
    }
}
