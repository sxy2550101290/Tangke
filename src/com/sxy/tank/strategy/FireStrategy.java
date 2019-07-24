package com.sxy.tank.strategy;

import com.sxy.tank.Player;

public interface FireStrategy {
    /**
     * 炮弹发射模式
     * @param p
     */
    void fire(Player p);
}
