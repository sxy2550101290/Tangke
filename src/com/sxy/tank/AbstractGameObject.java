package com.sxy.tank;

import java.awt.*;
import java.io.Serializable;
import java.util.UUID;

public abstract class AbstractGameObject implements Serializable {
    public abstract void paint(Graphics g);

    public abstract boolean isLive();


}
