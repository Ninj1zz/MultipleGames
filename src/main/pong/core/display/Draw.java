package main.pong.core.display;

import main.pong.core.Game;

import javax.swing.*;
import java.awt.*;

public class Draw extends JLabel {

	private final Game GAME;

	public Draw(Game game) {
		this.GAME = game;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		drawBoard(g2d);
		drawBall(g2d);
		drawPaddles(g2d);

		repaint();
	}

	private void drawBoard(Graphics2D g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GAME.getWIDTH(), GAME.getHEIGHT());

		g.setColor(Color.WHITE);
		int separator = GAME.getHEIGHT() / 40;
		for (int i = 0; i <= separator; i++) {
			g.fillRect(GAME.getWIDTH() / 2 - 35, i * 50, 40, 40);
		}

		g.setFont(new Font("Impact",Font.PLAIN,30));
		g.drawString("" + GAME.paddles[0].getScore(), GAME.getWIDTH() / 2 - 95, 75);
		g.drawString("" + GAME.paddles[1].getScore(), GAME.getWIDTH() / 2 + 50, 75);
	}

	private void drawBall(Graphics g){
		g.setColor(GAME.getBall().getColor());
		g.fillRect(GAME.getBall().getX(), GAME.getBall().getY(), GAME.getBall().getSize(), GAME.getBall().getSize());
	}

	private void drawPaddles(Graphics g){
		g.setColor(Color.white);
		g.fillRect(GAME.paddles[0].getX(), GAME.paddles[0].getY(), GAME.paddles[0].getWIDTH(), GAME.paddles[0].getHEIGHT());
		g.fillRect(GAME.paddles[1].getX(), GAME.paddles[1].getY(), GAME.paddles[1].getWIDTH(), GAME.paddles[1].getHEIGHT());
	}
}
