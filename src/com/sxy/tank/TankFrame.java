package com.sxy.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 画出窗口，坦克和子弹
 */
public class TankFrame extends Frame {
    private Tank mytank;
    private Tank enemy;
    private Bullet bullet;

    public static final int GAME_WIDTH=800,GAME_HEIGHT=600;

    public TankFrame(){
        this.setTitle("sxy坦克大战");
        this.setLocation(400, 100);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);

        this.addKeyListener(new TankKeyListener());
        //坦克起始坐标
        mytank=new Tank(100,100,Dir.D,Group.GOOD,this);
        enemy=new Tank(200, 200, Dir.R,Group.BAD,this);
        bullet=new Bullet(100, 100, Dir.D,Group.BAD);
    }
    public void add(Bullet bullet){
        this.bullet=bullet;
    }

    /**
     * 本方法自動調用
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        //第一个坦克
        mytank.paint(g);
        //第二个坦克
        enemy.paint(g);
        bullet.paint(g);
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
    Image offScreenImage=null;
    /**
     *  解决游戏闪烁
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
