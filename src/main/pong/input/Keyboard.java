package main.pong.input;

import main.pong.core.Game;
import main.pong.entity.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keyboard extends KeyAdapter {

    private final Game GAME;

    public Keyboard (Game game){
        this.GAME = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for(int i = 0;i<GAME.paddles.length;i++){
            if(GAME.paddles[i] instanceof Player){
                ((Player) GAME.paddles[i]).keyPressed(e);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for(int i = 0;i<GAME.paddles.length;i++){
            if(GAME.paddles[i] instanceof Player){
                ((Player) GAME.paddles[i]).keyReleased(e);
            }
        }
    }
}
