package main.pong.core;

import main.pong.core.display.Main;
import main.pong.entity.Ball;
import main.pong.entity.CPU;
import main.pong.entity.Paddle;
import main.pong.entity.Player;
import main.pong.entity.utils.Difficulty;
import main.pong.input.Keyboard;

public class Game extends Thread {
    private Ball ball;
    public final Paddle[] paddles = new Paddle[2];
	private final Keyboard KEYBOARD;
	private final int WIDTH;
	private final int HEIGHT;
	private final int paddleSpeed;
	private int fps;
	private boolean isRunning;

	public Game(int width, int height, boolean isP1, boolean isP2) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.KEYBOARD = new Keyboard(this);
		paddleSpeed = 10;
        init(isP1,isP2);
	}

	@Override
	public void run() {
		isRunning = true;
		gameLoop();
	}

	private void gameLoop() {
		final long NANOSECOND = 1000000000L;
		final float FRAMERATE = 60;
		float frameTime = 1.0f / FRAMERATE;
		long fpsCounter = 0;
		long lastTime = System.nanoTime();
		double unprocessedTime = 0;

		while (isRunning) {
			boolean render = false;
			long now = System.nanoTime();
			long passedTime = now - lastTime;

			lastTime = now;
			unprocessedTime += passedTime / (double) NANOSECOND;
			fpsCounter += passedTime;

			while (unprocessedTime > frameTime) {
				render = true;
				unprocessedTime -= frameTime;

				if (fpsCounter >= NANOSECOND) {
					setTitle("Pong | FPS: " + fps);
					fps = 0;
					fpsCounter = 0;
				}
			}
			if (render) {
				update();
				fps++;
			}
		}
	}

    private void update() {
        ball.update();
        paddles[0].update();
        paddles[1].update();
    }

	private void setTitle(String title) {
		if (Main.frame != null) {
			Main.frame.setTitle(title);
		}
	}

	private void init(boolean isP1,boolean isP2) {
		ball = new Ball(200, 300, 50, 10, this);
		if (isP1) {
            paddles[0] = new Player(10, 500, 40, 300, paddleSpeed, 1, this.HEIGHT);
		} else {
            paddles[0] = new CPU(10, 500, 40, 300, paddleSpeed, 1, this);
		}
		if (isP2) {
            paddles[1] = new Player(WIDTH - 65, 500, 40, 300, paddleSpeed, 2, this.HEIGHT);
		} else {
            paddles[1] = new CPU(WIDTH - 65, 500, 40, 300, paddleSpeed, 2, this);
		}
	}

	public void setPaddleDifficulty(int id, Difficulty difficulty) {
		if (paddles[id-1] instanceof CPU) {
			((CPU) paddles[id-1]).setDifficulty(difficulty);
		}
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

    public Ball getBall() {
        return ball;
    }

    public Keyboard getKeyboard() {
		return KEYBOARD;
	}
}
