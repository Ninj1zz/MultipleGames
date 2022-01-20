package main.lib.display;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.lib.application.Application;
import main.lib.application.ApplicationManager;

public class Frame extends JFrame {

	@Serial
	private static final long serialVersionUID = -8544028199647928453L;

	private final JPanel gamePanel;
	public static ApplicationManager appMan;
	private final List<ApplicationPanel> applicationPanelList;
	public static JLabel lblCurrentSelection;
	public static JLabel lblCurrentStatus;
	private final JButton btnStart;
	private Application selectedApp;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Frame frame = new Frame();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public Frame() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		applicationPanelList = new ArrayList<>();

		setTitle("My Game Library");

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 650);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblCurrentSelection = new JLabel("Currently selected: ");
		lblCurrentSelection.setBounds(10, 11, 256, 16);
		contentPane.add(lblCurrentSelection);

		lblCurrentStatus = new JLabel("Status: ");
		lblCurrentStatus.setBounds(276, 13, 178, 14);
		contentPane.add(lblCurrentStatus);

		gamePanel = new JPanel();
		gamePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		gamePanel.setBounds(10, 38, 700, 500);
		contentPane.add(gamePanel);
		gamePanel.setLayout(null);

		btnStart = new JButton("Start");
		btnStart.setEnabled(false);
		btnStart.setFocusPainted(false);
		btnStart.setBackground(new Color(0, 128, 0));
		btnStart.setBounds(495, 8, 89, 23);
		btnStart.addActionListener((ActionEvent e1) -> {
			for (Application a : appMan.getApplicationList()) {
				if (a.getAppName().equals(selectedApp.getAppName())) {

					if (a.isRunning()) {
						a.start();
					}
					btnStart.setEnabled(false);
					lblCurrentStatus.setText("<html>Status: <font color='green'>Running</font></html>");
				}
			}
		});
		contentPane.add(btnStart);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		JMenuItem closeProgram = new JMenuItem("Close");
		closeProgram.addActionListener((ActionEvent e) -> dispose());
		fileMenu.add(closeProgram);

		appMan = new ApplicationManager();

		drawApps();
		initMouse();

	}

	private void initMouse() {
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				for (ApplicationPanel p : applicationPanelList) {
					Rectangle r = p.getBounds();
					r.setLocation(p.getLocationOnScreen());
					if (r.contains(MouseInfo.getPointerInfo().getLocation())) {
						for (Application a : appMan.getApplicationList()) {
							if (a.getAppName().equals(p.getAppName())) {
								selectApplication(a);
							}
						}
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

		});
	}

	private void selectApplication(Application a) {
		selectedApp = a;
		lblCurrentSelection.setText("Currently selected: " + a.getAppName());
		if (a.isRunning()) {
			lblCurrentStatus.setText("<html>Status: <font color='red'>Not Running</font></html>");
			btnStart.setEnabled(true);
		} else {
			lblCurrentStatus.setText("<html>Status: <font color='green'>Running</font></html>");
			btnStart.setEnabled(false);
		}
	}

	private void clearPanel() {
		gamePanel.removeAll();
		gamePanel.repaint();
		applicationPanelList.removeAll(applicationPanelList);
	}

	private void drawApps() {
		clearPanel();
		int posX = 0, posY = 0;
		Image img = null;

		for (Application a : appMan.getApplicationList()) {
			try {
				if(new File("data/"+a.getAppName()+"/thumb.png").exists()){
					img = ImageIO.read(new File("data/"+a.getAppName()+"/thumb.png"));
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			ApplicationPanel appPanel = new ApplicationPanel(posX, posY, 350, 250, a.getAppName(), img);
			applicationPanelList.add(appPanel);
			gamePanel.add(appPanel);
			if (posX == 0 && posY == 0) {
				posX = 350;
			} else if (posX == 350 && posY == 0) {
				posX = 0;
				posY = 250;
			} else if (posX == 0) {
				posX = 350;
				posY = 250;
			}

		}
	}
}
