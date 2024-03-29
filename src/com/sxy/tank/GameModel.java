package com.sxy.tank;

import com.sxy.tank.chainofresponsibility.ColliderChain;
import com.sxy.tank.chainofresponsibility.PlayerCollider;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class GameModel implements Serializable {
    private Player mytank;
    //private Wall wall;
    private List<AbstractGameObject> objects;

    private PlayerCollider playerCollider;
    private ColliderChain colliderChain=new ColliderChain();

    private Random r=new Random();

    public GameModel() {
        initGameObject();
    }

    private void initGameObject() {
        objects=new ArrayList<>();
        //wall=new Wall(300, 150, 200, 60);
        playerCollider=new PlayerCollider();

        //我方坦克起始坐标

        mytank=new Player(50+r.nextInt(700),50+r.nextInt(500),Dir.values()[r.nextInt(Dir.values().length)], Group.values()[r.nextInt(Group.values().length)]);
        int tankCount=Integer.parseInt(PropertyMgr.get("initTankCount"));
        for(int i=0;i<tankCount;i++){
            this.add(new Tank(50+i*80,200,Dir.R,Group.BAD));
        }
        // objects.add(wall);
    }
    public void add(AbstractGameObject go){
        //实现
        objects.add(go);
    }
    public void paint(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.white);
        g.drawString("objects"+objects.size(), 10, 50);
        g.setColor(c);
        //自己的坦克
        if(mytank.isLive()) {
            mytank.paint(g);
            for (int i = 0; i < objects.size(); i++) {
                AbstractGameObject go1 = objects.get(i);
                playerCollider.collide(go1,mytank);
                if (!go1.isLive()) {
                    objects.remove(go1);
                    break;
                }
            }
            for (int i = 0; i < objects.size(); i++) {
                AbstractGameObject go1 = objects.get(i);
                for (int j = 0; j < objects.size(); j++) {
                    AbstractGameObject go2 = objects.get(j);
                    colliderChain.collide(go1, go2);
                }
                if (go1.isLive()) {
                    go1.paint(g);
                }
            }
        }else{
            Color c1=g.getColor();
            g.setColor(Color.RED);
            g.drawString("游戏结束", TankFrame.GAME_WIDTH/2, TankFrame.GAME_HEIGHT/2);
            g.setColor(c1);
        }
    }
    public Player getMytank(){
        return mytank;
    }

    public Tank findTankByUUID(UUID id) {
        for(AbstractGameObject o:objects){
            if(o instanceof Tank){
                Tank t=(Tank)o;
                if(id.equals(t.getId())){
                    return t;
                }
            }
        }
        return null;
    }

    public Bullet findBulletByUUID(UUID bulletId) {
        for(AbstractGameObject o:objects){
            if(o instanceof Bullet){
                Bullet b=(Bullet)o;
                if(bulletId.equals(b.getId())){
                    return b;
                }
            }
        }
        return null;
    }

    public void remove(AbstractGameObject go) {
        objects.remove(go);
    }
}
