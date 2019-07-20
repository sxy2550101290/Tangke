import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TankFrame extends Frame {
    Tank mytank;


    public TankFrame(){
        this.setTitle("tank war");
        this.setLocation(400, 100);
        this.setSize(800, 600);

        this.addKeyListener(new TankKeyListener());
        //坦克起始坐标
        mytank=new Tank(100,100,Dir.R);
    }

    /**
     * 本方法自動調用
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        //画一个方块
        mytank.paint(g);
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
}
