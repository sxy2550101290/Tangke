package com.sxy.tank;

import com.sxy.tank.net.TankJoinMsg;

import java.awt.*;
import java.util.Random;
import java.util.UUID;

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

    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    private Rectangle rect;

    private int oldx,oldy;

    private int width,height;

    private Random random=new Random();

    public Tank(TankJoinMsg msg) {
        this.x=msg.getX();
        this.y=msg.getY();
        this.dir=msg.getDir();
        this.moving=msg.isMoving();
        this.group=msg.getGroup();
        this.id=msg.getId();

        this.width=ResourceMgr.badTankU.getWidth();
        this.height=ResourceMgr.badTankU.getHeight();
        //记录之前坐标
        oldx=x;
        oldy=y;
        rect=new Rectangle(this.x, this.y,this.width,this.height);
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isLive() {
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
        //记录之前坐标
        oldx=x;
        oldy=y;
        rect=new Rectangle(this.x, this.y,this.width,this.height);
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
        if(!this.isLive())return;

        switch (dir) {
            case L:
                g.drawImage(this.group.equals(Group.BAD)?ResourceMgr.badTankL:ResourceMgr.goodTankL, x, y, null);
                break;
            case U:
                g.drawImage(this.group.equals(Group.BAD)?ResourceMgr.badTankU:ResourceMgr.badTankU, x, y, null);
                break;
            case R:
                g.drawImage(this.group.equals(Group.BAD)?ResourceMgr.badTankR:ResourceMgr.goodTankR, x, y, null);
                break;
            case D:
                g.drawImage(this.group.equals(Group.BAD)?ResourceMgr.badTankD:ResourceMgr.goodTankD, x, y, null);
                break;
        }
        //移动
        move();
        rect.x=this.x;
        rect.y=this.y;
    }

    private void boundsCheck() {
        if(x<0||y<30||x>(TankFrame.GAME_WIDTH-this.width)||y>(TankFrame.GAME_HEIGHT-this.height)){
            this.back();
        }

    }

    public void back() {
        this.x=this.oldx;
        this.y=this.oldy;
    }

    /**
     * 子弹发射位置
     */
    private void fire() {
        int bx=x+ResourceMgr.goodTankU.getWidth()/2-ResourceMgr.bulletU.getWidth()/2;
        int by=y+ResourceMgr.goodTankU.getHeight()/2-ResourceMgr.bulletU.getHeight()/2;
        TankFrame.INSTANCE.getGm().add(new Bullet(bx, by, dir, group));
    }


    private void move() {
        if(!moving){return;}

        oldx=x;
        oldy=y;
        switch (dir){
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

        //随机方向
        randomDir();
        //是否越界
        boundsCheck();
        if(random.nextInt(100)<97) return;
        fire();
    }

    private void randomDir() {
        if(random.nextInt(100)>95)
            this.dir=Dir.randomDir();
    }

    public void die(){
        this.setLive(false);
        //爆炸
        TankFrame.INSTANCE.getGm().add(new Explode(this.x, this.y));
    }


    public Rectangle getRect() {
        return rect;
    }
}
