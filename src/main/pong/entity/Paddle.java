package main.pong.entity;

import main.pong.entity.utils.SoundManager;

import java.awt.*;
import java.util.Objects;

public abstract class Paddle {
	protected final int ID;
	private final int WIDTH;
	private final int HEIGHT;
	private final int X;
	private final int GAME_HEIGHT;
	protected int y;
	protected int movement;
	protected int score;
	protected int speed;


	public Paddle(int x, int y, int WIDTH, int HEIGHT, int speed, int id, int gameHeight) {
        this.X = x;
        this.y = y;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.speed = speed;
		this.ID = id;
		this.GAME_HEIGHT = gameHeight - 10;
	}

	public int getScore() {
		return score;
	}

	public void addScore() {
		Objects.requireNonNull(SoundManager.getSound("goal")).play();
		this.score += 1;
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public int getX() {
		return X;
	}

	public int getY() {
		return y;
	}

	public Rectangle getBounds() {
		return new Rectangle(X, y, WIDTH, HEIGHT);
	}

	protected void checkBounds() {
		if (y <= 0) {
			y = 0;
		}
		if (y >= this.GAME_HEIGHT - this.HEIGHT) {
			y = this.GAME_HEIGHT - this.HEIGHT;
		}
	}

	public void update(){
		move();
	}

	protected abstract void move();
}
