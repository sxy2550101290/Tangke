package com.sxy.tank;

import com.sxy.tank.net.BulletNewMsg;
import com.sxy.tank.net.Client;
import com.sxy.tank.net.TankMovingOrDirChangeMsg;
import com.sxy.tank.net.TankStopMovingMsg;
import com.sxy.tank.strategy.FireStrategy;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.UUID;

/**
 * 我方坦克
 */
public class Player  extends  AbstractGameObject{
    //x y 代表坦克的起始坐标
    private int x,y;
    private Dir dir;
    private boolean bl,bu,br,bd;
    private static final int SPEED=Integer.parseInt(PropertyMgr.get("initTankSpeed"));
    //坦克是否运动
    private boolean moving=false;
    private Group group;
    private boolean live=true;

    public UUID getId() {
        return id;
    }
    public boolean isMoving() {
        return moving;
    }

    private int width,height;
    private UUID id=UUID.randomUUID();
    private int oldx,oldy;
    private Rectangle rect;
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
    @Override
    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
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

        oldx=x;
        oldy=y;

        rect=new Rectangle(this.x, this.y, this.width, this.height);
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
        if(!this.isLive())return;

        Color c=g.getColor();
        g.setColor(Color.yellow);
        g.drawString(id.toString(), x, y-10);
        g.setColor(c);

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
        rect.x=this.x;
        rect.y=this.y;
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
        if(this.isLive())
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
        Dir oldDir=this.getDir();
        boolean oldMoving=moving;
        //没有按键 坦克不动
        if(!bl&&!bu&&!br&&!bd){
            moving=false;
            //发送停止消息
            Client.INSTANCE.send(new TankStopMovingMsg(this.id, this.x, this.y));
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
            //发送移动消息
            if(!oldMoving)Client.INSTANCE.send(new TankMovingOrDirChangeMsg(this.id,this.x,this.y,this.dir));

            if(!this.dir.equals(oldDir))Client.INSTANCE.send(new TankMovingOrDirChangeMsg(this.id,this.x,this.y,this.dir));
        }
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
        boundsCheck();
    }
    private void boundsCheck() {
        if(x<0||y<30||x>(TankFrame.GAME_WIDTH-this.width)||y>(TankFrame.GAME_HEIGHT-this.height)){
            this.back();
        }

    }
    public void die(){
        this.setLive(false);
        TankFrame.INSTANCE.getGm().add(new Explode(this.x, this.y));
    }

    public Rectangle getRect() {
        return rect;
    }

    public void back() {
        this.x=this.oldx;
        this.y=this.oldy;
    }
}
