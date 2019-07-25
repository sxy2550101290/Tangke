package com.sxy.tank.strategy;

import com.sxy.tank.Player;

import java.io.Serializable;

public interface FireStrategy extends Serializable {
    /**
     * 炮弹发射模式
     * @param p
     */
    void fire(Player p);
}
