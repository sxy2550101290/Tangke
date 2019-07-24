package com.sxy.tank;

import java.awt.*;

/**
 * 炮弹类
 */
public class Bullet  extends  AbstractGameObject{
    private int x, y;
    private Dir dir;
    private Group group;
    public static final int SPEED = Integer.parseInt(PropertyMgr.get("initBulletSpeed"));
    private Boolean live =true;
    public Boolean getLive() {
        return live;
    }

    public void setLive(Boolean live) {
        this.live = live;
    }

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

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public static int getSPEED() {
        return SPEED;
    }

    public Bullet(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
    }

    public void paint(Graphics g) {
        switch (dir) {
            case L:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case U:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case R:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case D:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
        }
        move();
    }

    private void move() {
        switch (dir) {
            case L:
                x -= SPEED;
                break;
            case U:
                y -= SPEED;
                break;
            case R:
                x += SPEED;
                break;
            case D:
                y += SPEED;
                break;
        }
    }

    public void collidesWithTank(Tank tank) {
        //子弹 坦克是否死掉
        if(!this.getLive() || !tank.getLive())return;

        if(this.group==tank.getGroup()) return;

        Rectangle rect = new Rectangle(x, y, ResourceMgr.bulletU.getWidth(), ResourceMgr.bulletU.getHeight());
        Rectangle rectTank = new Rectangle(tank.getX(), tank.getY(), ResourceMgr.goodTankU.getWidth(), ResourceMgr.goodTankU.getHeight());
        if(rect.intersects(rectTank)){
            this.die();
            tank.die();
        }
    }
    public void die(){
        this.setLive(false);
    }


}
