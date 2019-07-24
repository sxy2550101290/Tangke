package com.sxy.tank;

import com.sxy.tank.strategy.DefaultFireStrategy;
import com.sxy.tank.strategy.FireStrategy;
import com.sxy.tank.strategy.FourDirFireStrategy;
import com.sxy.tank.strategy.LeftRightFireStrategy;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Locale;

public class Player  extends  AbstractGameObject{
    //x y 代表坦克的起始坐标
    private int x,y;
    private Dir dir;
    private boolean bl,bu,br,bd;
    private static final int SPEED=Integer.parseInt(PropertyMgr.get("initTankSpeed"));
    //坦克是否运动
    private boolean moving=false;
    private Group group;
    private Boolean live=true;

    private int width,height;

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
    public Player(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group=group;
        this.height=ResourceMgr.goodTankU.getHeight();
        this.width=ResourceMgr.goodTankU.getWidth();
        //初始化炮弹策略
        this.initFireStrategy();
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
                g.drawImage(ResourceMgr.goodTankL, x, y, null);
                break;
            case U:
                g.drawImage(ResourceMgr.goodTankU, x, y, null);
                break;
            case R:
                g.drawImage(ResourceMgr.goodTankR, x, y, null);
                break;
            case D:
                g.drawImage(ResourceMgr.goodTankD, x, y, null);
                break;
        }

        move();
    }

    /**
     * 键盘抬起事件
     * @param e
     */
    public void KeyReleased(KeyEvent e){

        int key=e.getExtendedKeyCode();

        switch(key){
            case KeyEvent.VK_LEFT:
                bl=false;
                break;
            case KeyEvent.VK_UP:
                bu=false;
                break;
            case KeyEvent.VK_RIGHT:
                br=false;
                break;
            case KeyEvent.VK_DOWN:
                bd=false;
                break;
        }
        //发射子弹
        if(key==KeyEvent.VK_CONTROL) fire();
        setMainDir();
    }
    private FireStrategy fireStrategy= null;

    private void initFireStrategy(){
        String className=PropertyMgr.get("tankFireStrategy");
        try {
            Class<?> clazz = Class.forName("com.sxy.tank.strategy."+className);
            fireStrategy = (FireStrategy) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 子弹发射位置
     */
    private void fire() {
        fireStrategy.fire(this);

    }

    public void KeyPressed(KeyEvent e){
        int key=e.getExtendedKeyCode();

        switch(key) {
            case KeyEvent.VK_LEFT:
                bl = true;
                bu = false;
                br = false;
                bd = false;
                break;
            case KeyEvent.VK_UP:
                bu = true;
                bl = false;
                br = false;
                bd = false;
                break;
            case KeyEvent.VK_RIGHT:
                br = true;
                bl = false;
                bu = false;
                bd = false;
                break;
            case KeyEvent.VK_DOWN:
                bd = true;
                br = false;
                bl = false;
                bu = false;
                break;
        }
        setMainDir();
    }

    private void setMainDir() {
        //没有按键 坦克不动
        if(!bl&&!bu&&!br&&!bd){
            moving=false;
        }else {
            moving = true;
            if(bl&&!bu&&!br&&!bd){
                dir=Dir.L;
            }
            if(!bl&&bu&&!br&&!bd){
                dir=Dir.U;
            }
            if(!bl&&!bu&&br&&!bd){
                dir=Dir.R;
            }
            if(!bl&&!bu&&!br&&bd){
                dir=Dir.D;
            }
        }
    }

    private void move() {
        if(!moving){return;}
        switch (dir){
            case L:
                if(x<0) break;
                x -= SPEED;
                break;
            case U:
                if(y<30) break;
                y -= SPEED;
                break;
            case R:
                if(x>TankFrame.GAME_WIDTH-width) break;
                x += SPEED;
                break;
            case D:
                if(y>TankFrame.GAME_HEIGHT-height) break;
                y += SPEED;
                break;
        }
    }

    public void die(){
        this.setLive(false);
    }
}
