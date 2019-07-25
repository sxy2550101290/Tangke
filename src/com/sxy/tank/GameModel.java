package com.sxy.tank;

import com.sxy.tank.chainofresponsibility.ColliderChain;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameModel implements Serializable {
    private Player mytank;
    //private Wall wall;
    private List<AbstractGameObject> objects;
    private ColliderChain colliderChain=new ColliderChain();

    public GameModel() {
        initGameObject();
    }

    private void initGameObject() {
        objects=new ArrayList<>();
        //wall=new Wall(300, 150, 200, 60);
        //我方坦克起始坐标
        mytank=new Player(100,100,Dir.D, Group.GOOD);
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
        //第一个坦克
        mytank.paint(g);
        for (int i = 0; i < objects.size(); i++) {
            AbstractGameObject go1 = objects.get(i);
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
    }
    public Player getMytank(){
        return mytank;
    }

}