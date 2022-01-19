package main.pong.entity;

import main.pong.core.Game;
import main.pong.entity.utils.Difficulty;

public class CPU extends Paddle {

	private final Game GAME;
	private int viewDistance;

	public CPU(int x, int y, int WIDTH, int HEIGHT, int speed, int id, Game game) {
		super(x, y, WIDTH, HEIGHT, speed, id, game.getHEIGHT());
		viewDistance = game.getWIDTH() / 2;     //Sets the default viewDistance
		this.GAME = game;
	}

	public void setDifficulty(Difficulty difficulty) {
		if (ID == 1) {
			if (difficulty == Difficulty.UNBEATABLE) {
				viewDistance = GAME.getWIDTH() / 2;
			} else if (difficulty == Difficulty.NORMAL) {
				viewDistance = 500;
			} else if (difficulty == Difficulty.EASY) {
				viewDistance = 300;
			}
		} else if (ID == 2) {
			if (difficulty == Difficulty.UNBEATABLE) {
				viewDistance = GAME.getWIDTH() / 2;
			} else if (difficulty == Difficulty.NORMAL) {
				viewDistance = GAME.getWIDTH() - 500;
			} else if (difficulty == Difficulty.EASY) {
				viewDistance = GAME.getWIDTH() - 300;
			}
		}
	}

	@Override
	protected void move() {
		if (ID == 1) {
			if (GAME.getBall().getY() < this.y + 150 && GAME.getBall().getX() < viewDistance) {
				y -= speed;
			} else if (GAME.getBall().getY() > this.y + 150 && GAME.getBall().getX() < viewDistance) {
				y += speed;
			}
		} else {
			if (GAME.getBall().getY() < this.y + 150 && GAME.getBall().getX() > viewDistance) {
				y -= speed;
			} else if (GAME.getBall().getY() > this.y + 150 && GAME.getBall().getX() > viewDistance) {
				y += speed;
			}
		}
		checkBounds();
	}
}
