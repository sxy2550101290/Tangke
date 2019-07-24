package com.sxy.tank;
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
    private List<AbstractGameObject> objects;
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
        //我方坦克起始坐标
        mytank=new Player(100,100,Dir.D, Group.GOOD);
        int tankCount=Integer.parseInt(PropertyMgr.get("initTankCount"));
        for(int i=0;i<tankCount;i++){
            objects.add(new Tank(50+i*50,200,Dir.R,Group.BAD));
        }

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
        g.setColor(c);
        //第一个坦克
        mytank.paint(g);

        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).paint(g);
        }
        /*//敌方坦克
        for (int i = 0; i < tanks.size(); i++) {
            if(!tanks.get(i).getLive()) {
                tanks.remove(tanks.get(i));

            }else{
                tanks.get(i).paint(g);
            }
        }
        //画出子弹
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            for (int j = 0; j < tanks.size(); j++) {
                bullet.collidesWithTank(tanks.get(j));
            }
            if(bullet.getX()>GAME_WIDTH||bullet.getX()<0||bullet.getY()>GAME_HEIGHT||bullet.getY()<0){
                bullet.die();
            }
            if(!bullet.getLive()){
                bullets.remove(bullet);
            }
            bullet.paint(g);
        }
        //画出爆炸
        for (int i = 0; i < explodes.size(); i++) {
            if(!explodes.get(i).isLive()){
                explodes.remove(i);
            }else{
                explodes.get(i).paint(g);
            }
        }*/
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
