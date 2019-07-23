package com.sxy.tank;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {
    //x y 代表坦克的起始坐标
    private int x,y;
    private Dir dir;
    private boolean bl,bu,br,bd;
    private static final int SPEED=2;
    private boolean moving=false;
    private Group group;
    private TankFrame tf;

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }
    public Tank(int x, int y, Dir dir,Group group,TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group=group;
        this.tf=tf;
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
        if(this.group == Group.GOOD) {
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
        }
        if(this.group == Group.BAD) {
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
            case KeyEvent.VK_CONTROL:
                fire();
                break;
        }
        setMainDir();
    }

    private void fire() {
        tf.add(new Bullet(x, y, dir, group));
    }

    public void KeyPressed(KeyEvent e){
        int key=e.getExtendedKeyCode();
        switch(key) {
            case KeyEvent.VK_LEFT:
                bl = true;
                break;
            case KeyEvent.VK_UP:
                bu = true;
                break;
            case KeyEvent.VK_RIGHT:
                br = true;
                break;
            case KeyEvent.VK_DOWN:
                bd = true;
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
}
