import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {
    //x y 代表坦克的起始坐标
    private int x,y;
    private Dir dir;
    private boolean bl,bu,br,bd;
    private static final int SPEED=5;
    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }
    public Tank(int x, int y, Dir dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
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
        g.fillRect(x, y, 50, 50);
        move();
    }
    public void KeyPressed(KeyEvent e){
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
        setMainDir();
    }

    public void KeyReleased(KeyEvent e){
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
        if(!bl&&!bu&&!br&&!bd){
            dir=Dir.STOP;
        }
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

    private void move() {
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
