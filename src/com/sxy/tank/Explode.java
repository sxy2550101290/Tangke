package com.sxy.tank;

import java.awt.*;

public class Explode  extends  AbstractGameObject{
    private int x, y;
    private int width,height;
    private int step=0;
    private boolean live=true;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public void setLive(boolean over) {
        this.live = over;
    }

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
        this.width=ResourceMgr.explodes[0].getWidth();
        this.height=ResourceMgr.explodes[0].getHeight();

        new Thread(()->new Audio("audio/explode.wav").play()).start();
    }

    public void paint(Graphics g) {
        if(!live) return;

        g.drawImage(ResourceMgr.explodes[step],x,y,null);
        step ++;
        if(step >=ResourceMgr.explodes.length){
            this.die();
        }
    }

    @Override
    public boolean isLive() {
        return live;
    }

    private void die() {
        this.live=false;
    }


}
