package com.sxy.tank;

import java.awt.*;
import java.util.Random;

/**
 * 敌方坦克
 */
public class Tank  extends AbstractGameObject{
    //x y 代表坦克的起始坐标
    private int x,y;
    private Dir dir;
    private boolean bl,bu,br,bd;
    private static final int SPEED=Integer.parseInt(PropertyMgr.get("initTankSpeed"));
    //坦克是否运动
    private boolean moving=true;
    private Group group;
    //活着
    private Boolean live=true;

    private int width,height;

    private Random random=new Random();
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Boolean getLive() {
        return live;
    }

    public void setLive(Boolean live) {
        this.live = live;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }
    public Tank(int x, int y, Dir dir,Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group=group;
        this.width=ResourceMgr.badTankU.getWidth();
        this.height=ResourceMgr.badTankU.getHeight();
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

    public static int getSPEED() {
        return SPEED;
    }

    public void paint(Graphics g) {
        //坦克是否死掉
        if(!this.getLive())return;

        switch (dir) {
            case L:
                g.drawImage(ResourceMgr.badTankL, x, y, null);
                break;
            case U:
                g.drawImage(ResourceMgr.badTankU, x, y, null);
                break;
            case R:
                g.drawImage(ResourceMgr.badTankR, x, y, null);
                break;
            case D:
                g.drawImage(ResourceMgr.badTankD, x, y, null);
                break;
        }

        move();
        if(random.nextInt(100)<97) return;
        fire();
    }
    /**
     * 子弹发射位置
     */
    private void fire() {
        int bx=x+ResourceMgr.goodTankU.getWidth()/2-ResourceMgr.bulletU.getWidth()/2;
        int by=y+ResourceMgr.goodTankU.getHeight()/2-ResourceMgr.bulletU.getHeight()/2;
        TankFrame.INSTANCE.add(new Bullet(bx, by, dir, group));
    }


    private void move() {
        if(!moving){return;}

        switch (dir){
            case L:
                //越界不往前走
                if(x<this.width) break;
                x -= SPEED;
                break;
            case U:
                //越界不往前走
                if(y<this.height) break;
                y -= SPEED;
                break;
            case R:
                //越界不往前走
                if(x>(TankFrame.GAME_WIDTH-this.width)) break;
                x += SPEED;
                break;
            case D:
                //越界不往前走
                if(y>(TankFrame.GAME_HEIGHT-this.height)) break;
                y += SPEED;
                break;
        }
        //随机方向
        randomDir();

    }

    private void randomDir() {
        if(random.nextInt(100)>95)
            this.dir=Dir.randomDir();
    }

    public void die(){
        this.setLive(false);
        TankFrame.INSTANCE.add(new Explode(this.x, this.y));
    }
}
