package main.pong.entity;

import main.pong.core.Game;
import main.pong.entity.utils.SoundManager;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class Ball {
	private final int SIZE;
	private final int SPEED;
	private final Game GAME;
	private final Random RANDOM;

	private int x, y;
	private int speedX, speedY;
	private Color color;


	public Ball(int x, int y, int size, int speed, Game game) {
		this.x = x;
		this.y = y;
		this.SIZE = size;
		this.SPEED = speed;
		this.GAME = game;
		this.color = Color.WHITE;
		this.RANDOM = new Random();
		resetBall();
	}

	public void update() {
		move();
	}

	private void move() {
		collide();
		x += speedX;
		y += speedY;

		if (x <= 0) {
			setSpeedX(SPEED);
			GAME.paddles[1].addScore();
			resetBall();
		}

		if (x >= GAME.paddles[1].getX()) {
			setSpeedX(-SPEED);
			GAME.paddles[0].addScore();
			resetBall();
		}

		if (y <= 0) {
			Objects.requireNonNull(SoundManager.getSound("deflect")).play();
			setSpeedY(SPEED);
		}
		if (y >= GAME.getHEIGHT() - this.SIZE) {
			Objects.requireNonNull(SoundManager.getSound("deflect")).play();
			setSpeedY(-SPEED);
		}
	}

	private void collide() {
		if (getBounds().intersects(GAME.paddles[0].getBounds())) {
			Objects.requireNonNull(SoundManager.getSound("deflect")).play();
			randomColor();
			setSpeedX(SPEED);
		}
		if (getBounds().intersects(GAME.paddles[1].getBounds())) {
			Objects.requireNonNull(SoundManager.getSound("deflect")).play();
			randomColor();
			setSpeedX(-SPEED);
		}
	}

	private void randomColor() {
		int r = RANDOM.nextInt(255);
		int g = RANDOM.nextInt(255);
		int b = RANDOM.nextInt(255);
		color = new Color(r, g, b);
	}

	private void resetBall() {

		x = GAME.getWIDTH() / 2 - SIZE / 2;
		y = GAME.getHEIGHT() / 2 - SIZE / 2;

		int randDirX = RANDOM.nextInt(2);

		if (randDirX == 0) {
			randDirX = -SPEED;
		} else {
			randDirX = SPEED;
		}
		setSpeedX(randDirX);

		int randDirY = RANDOM.nextInt(2);
		if (randDirY == 0) {
			randDirY = -SPEED;
		} else {
			randDirY = SPEED;
		}
		setSpeedY(randDirY);
	}

	private void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	private void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSize() {
		return SIZE;
	}

	public Color getColor() {
		return color;
	}

	private Rectangle getBounds() {
		return new Rectangle(x, y, SIZE, SIZE);
	}
}
