package main.pong.entity;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends Paddle implements KeyListener {

    public Player(int x, int y, int width, int height, int speed, int id, int gameHeight) {
        super(x, y, width, height,speed,id,gameHeight);
    }

    @Override
    protected void move() {
        y += movement;
        checkBounds();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (ID == 1) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                movement = -speed;
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                movement = speed;
            }
        } else {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                movement = -speed;
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                movement = speed;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (ID == 1) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                movement = 0;
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                movement = 0;
            }
        } else {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                movement = 0;
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                movement = 0;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
