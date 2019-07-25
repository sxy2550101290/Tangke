package com.sxy.tank;
import com.sxy.tank.chainofresponsibility.Collider;
import com.sxy.tank.chainofresponsibility.ColliderChain;

import java.util.List;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * 画出窗口，坦克和子弹
 */
public class TankFrame extends Frame {
    public static final TankFrame INSTANCE=new TankFrame();
    private Player mytank;
    private Wall wall;
    private List<AbstractGameObject> objects;
    private ColliderChain colliderChain=new ColliderChain();

    //游戏页面的长宽
    public static final int GAME_WIDTH=800,GAME_HEIGHT=600;

    private TankFrame(){
        this.setTitle("sxy坦克大战");
        this.setLocation(400, 100);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);

        this.addKeyListener(new TankKeyListener());

        initGameObject();
    }



    private void initGameObject() {
        objects=new ArrayList<>();
        wall=new Wall(300, 150, 200, 60);
        //我方坦克起始坐标
        mytank=new Player(100,100,Dir.D, Group.GOOD);
        int tankCount=Integer.parseInt(PropertyMgr.get("initTankCount"));
        for(int i=0;i<tankCount;i++){
            objects.add(new Tank(50+i*50,200,Dir.R,Group.BAD));
        }
        objects.add(wall);
    }

    public void add(AbstractGameObject go){
        //实现
        objects.add(go);
    }

    /**
     * 本方法自動調用
     */
    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.white);
        g.drawString("objects"+objects.size(), 10, 50);
        g.setColor(c);
        wall.paint(g);
        //第一个坦克
        mytank.paint(g);
        for (int i = 0; i < objects.size(); i++) {
            AbstractGameObject go1 = objects.get(i);
            if(!go1.isLive()){
                objects.remove(go1);
                break;
            }
            for (int j = 0; j < objects.size(); j++) {
                AbstractGameObject go2 = objects.get(j);
                colliderChain.collide(go1,go2);
            }
            if(go1.isLive()){
                go1.paint(g);
            }

        }
    }

    private class TankKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            mytank.KeyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            mytank.KeyReleased(e);
        }
    }
    private Image offScreenImage=null;
    /**
     *  解决游戏闪烁(双缓存)
     */
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }
}
