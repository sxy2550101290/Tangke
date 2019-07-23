package com.sxy.tank;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        TankFrame tf=new TankFrame();
        tf.setVisible(true);
        for(;;){
            try {
                TimeUnit.MICROSECONDS.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tf.repaint();
        }
    }
}
