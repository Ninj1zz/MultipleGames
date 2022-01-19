package main.pong.core.display;

import main.pong.entity.utils.Difficulty;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {

	private boolean p1IsPlayer = true,p2IsPlayer = true;
	private final JPanel contentPane;
	private JRadioButton btnPvAi,btnPvP,btnAiVAi;
	public JComboBox<Difficulty> cmbxDifficultyAi1;
	public JComboBox<Difficulty> cmbxDifficultyAi2;
	public JButton btnStart;

	public Menu(){
		this.setSize(new Dimension(280,350));
		this.setTitle("Pong");
		this.setLayout(null);
		this.setResizable(false);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = new JPanel();
		contentPane.setSize(this.getSize());
		contentPane.setLayout(null);
		contentPane.setVisible(true);
		this.setContentPane(contentPane);

		setButtons();
	}

	private void setButtons(){

		btnPvP = new JRadioButton();
		btnPvP.setText("Player vs Player");
		btnPvP.setBounds(10,10,200,40);
		btnPvP.setVisible(true);
		btnPvP.setSelected(true);
		btnPvP.setFocusPainted(false);
		btnPvP.addActionListener(e -> {
			btnPvAi.setSelected(false);
			btnAiVAi.setSelected(false);

			cmbxDifficultyAi1.setEnabled(false);
			cmbxDifficultyAi2.setEnabled(false);

			setPlayerVPlayer();
		});
		contentPane.add(btnPvP);

		btnPvAi = new JRadioButton();
		btnPvAi.setText("Player vs AI");
		btnPvAi.setBounds(10,40,200,40);
		btnPvAi.setVisible(true);
		btnPvAi.setFocusPainted(false);
		btnPvAi.addActionListener(e -> {
			btnPvP.setSelected(false);
			btnAiVAi.setSelected(false);

			cmbxDifficultyAi1.setEnabled(true);
			cmbxDifficultyAi2.setEnabled(false);

			setPlayerVAi();
		});
		contentPane.add(btnPvAi);

		btnAiVAi = new JRadioButton();
		btnAiVAi.setText("AI vs AI");
		btnAiVAi.setBounds(10,70,200,40);
		btnAiVAi.setVisible(true);
		btnAiVAi.setFocusPainted(false);
		btnAiVAi.addActionListener(e -> {
			btnPvAi.setSelected(false);
			btnPvP.setSelected(false);

			cmbxDifficultyAi1.setEnabled(true);
			cmbxDifficultyAi2.setEnabled(true);

			setAiVAi();
		});
		contentPane.add(btnAiVAi);

		JPanel difficultyPanel = new JPanel();
		difficultyPanel.setBorder(BorderFactory.createTitledBorder("Difficulties"));
		difficultyPanel.setBounds(10,110,240,125);
		difficultyPanel.setLayout(null);
		difficultyPanel.setVisible(true);
		contentPane.add(difficultyPanel);

		JLabel lblDifficultyAi1 = new JLabel("1. AI : ");
		lblDifficultyAi1.setBounds(10,30,100,20);
		JLabel lblDifficultyAi2 = new JLabel("2. AI : ");
		lblDifficultyAi2.setBounds(10,80,100,20);

		difficultyPanel.add(lblDifficultyAi1);
		difficultyPanel.add(lblDifficultyAi2);

		cmbxDifficultyAi1 = new JComboBox<>(Difficulty.values());
		cmbxDifficultyAi1.setBounds(50,20,150,40);
		cmbxDifficultyAi1.setEnabled(false);
		cmbxDifficultyAi1.setVisible(true);
		difficultyPanel.add(cmbxDifficultyAi1);

		cmbxDifficultyAi2 = new JComboBox<>(Difficulty.values());
		cmbxDifficultyAi2.setBounds(50,70,150,40);
		cmbxDifficultyAi2.setEnabled(false);
		cmbxDifficultyAi2.setVisible(true);
		difficultyPanel.add(cmbxDifficultyAi2);

		difficultyPanel.repaint();

		btnStart = new JButton("Start Game");
		btnStart.setBounds(10,240,240,40);
		btnStart.setVisible(true);
		btnStart.setFocusPainted(false);

		contentPane.add(btnStart);
		contentPane.repaint();
	}

	private void setPlayerVAi(){
		p1IsPlayer = true;
		p2IsPlayer = false;
	}

	private void setPlayerVPlayer(){
		p1IsPlayer = true;
		p2IsPlayer = true;
	}
	private void setAiVAi(){
		p1IsPlayer = false;
		p2IsPlayer = false;
	}

	public boolean isP1IsPlayer() {
		return p1IsPlayer;
	}

	public boolean isP2IsPlayer() {
		return p2IsPlayer;
	}
}
