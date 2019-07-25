package com.sxy.tank.chainofresponsibility;

import com.sxy.tank.AbstractGameObject;

import java.io.Serializable;

public interface Collider extends Serializable {
    /**
     * 碰撞类 false chain 继续运行，true 终止运行
     * @param obj1
     * @param obj2
     */
    public boolean collide(AbstractGameObject obj1,AbstractGameObject obj2);
}
