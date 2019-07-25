package com.sxy.tank;


import java.awt.*;

/**
 * 炮弹类
 */
public class Bullet extends  AbstractGameObject{
    private int x, y;
    private Dir dir;
    private Group group;
    public static final int SPEED = Integer.parseInt(PropertyMgr.get("initBulletSpeed"));
    private boolean live =true;
    private int w=ResourceMgr.bulletU.getWidth();
    private int h=ResourceMgr.bulletU.getHeight();
    private Rectangle rect;
    @Override
    public boolean isLive() {
        return live;
    }
    public void setLive(boolean live) {
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
        rect=new Rectangle(this.x, this.y, w, h);
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
        //更新当前坐标到Rectangle
        rect.x=this.x;
        rect.y=this.y;
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
        isBulletCrossing();

    }

    private void isBulletCrossing() {
        if(this.getX()>TankFrame.GAME_WIDTH||this.getX()<0||this.getY()>TankFrame.GAME_HEIGHT||this.getY()<0){
            this.die();
        }
    }

    public Rectangle getRect(){
        return rect;
    }


    public void die(){
        this.setLive(false);
    }


}
