package com.sxy.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;


/**
 * 画出窗口，坦克和子弹
 */
public class TankFrame extends Frame implements Serializable{
    public static final TankFrame INSTANCE=new TankFrame();
    //游戏页面的长宽
    public static final int GAME_WIDTH=800,GAME_HEIGHT=600;

    private GameModel gm=new GameModel();

    private TankFrame(){
        this.setTitle("sxy坦克大战");
        this.setLocation(400, 100);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);

        this.addKeyListener(new TankKeyListener());
    }
    /**
     * 本方法自動調用
     */
    @Override
    public void paint(Graphics g) {
        gm.paint(g);
    }

    private class TankKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key=e.getExtendedKeyCode();
            if(key==KeyEvent.VK_S) save();
            else if(key == KeyEvent.VK_L) load();
            else gm.getMytank().KeyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            gm.getMytank().KeyReleased(e);
        }
    }

    private void save() {
        FileOutputStream fos=null;
        ObjectOutputStream oos=null;
        try{
            File f=new File("d:/test/tank.dat");
            fos=new FileOutputStream(f);
            oos=new ObjectOutputStream(fos);
            oos.writeObject(this.gm);
            oos.flush();
            System.out.println("存储成功");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(null!=oos)
                    oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(null!=fos)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void load() {
        FileInputStream fis=null;
        ObjectInputStream ois=null;
        try{
            File f=new File("d:/test/tank.dat");
            fis=new FileInputStream(f);
            ois=new ObjectInputStream(fis);
            this.gm= (GameModel)(ois.readObject());
            System.out.println("读取成功");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(null!=ois)
                    ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(null!=fis)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    public GameModel getGm(){
        return this.gm;
    }

}
