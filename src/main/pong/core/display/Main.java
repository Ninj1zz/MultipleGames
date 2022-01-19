package main.pong.core.display;

import main.pong.core.Game;
import main.pong.entity.utils.Difficulty;
import main.pong.entity.utils.Sound;
import main.pong.entity.utils.SoundManager;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

	public static Main frame;
	private static final int WIDTH = 1800, HEIGHT = 1000;
	private static Game game;

	public static void main(String[] args) {
		Menu menu = new Menu();
		menu.btnStart.addActionListener(e -> {
			game = new Game(WIDTH, HEIGHT, menu.isP1IsPlayer(), menu.isP2IsPlayer());
			game.start();
			if(menu.isP1IsPlayer()){
				game.setPaddleDifficulty(2, (Difficulty) menu.cmbxDifficultyAi1.getSelectedItem());
			}else if(!(menu.isP1IsPlayer() && menu.isP2IsPlayer())){
				game.setPaddleDifficulty(1, (Difficulty) menu.cmbxDifficultyAi1.getSelectedItem());
				game.setPaddleDifficulty(2, (Difficulty) menu.cmbxDifficultyAi2.getSelectedItem());
			}

			frame = new Main();
			menu.dispose();
		});
	}

	public Main() {
		this.setSize(new Dimension(WIDTH,HEIGHT+30));
		this.setTitle("Pong");
		this.setLayout(null);
		this.setResizable(false);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel contentPane = new JPanel();
		contentPane.setBounds(0,0,WIDTH,HEIGHT);
		contentPane.setVisible(true);
		contentPane.setLayout(null);
		this.setContentPane(contentPane);

		this.addKeyListener(game.getKeyboard());

		Draw lblDraw = new Draw(game);
		lblDraw.setBounds(0,0,this.getWidth(),this.getHeight());
		lblDraw.setVisible(true);

		contentPane.add(lblDraw);

		initSounds();
	}

	private void initSounds(){
		SoundManager.addSound(new Sound("deflect.wav","deflect",20));
		SoundManager.addSound(new Sound("goal.wav","goal",20));
	}


}
