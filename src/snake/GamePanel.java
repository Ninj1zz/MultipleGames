package snake;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.*;

public class GamePanel extends JPanel implements ActionListener
{
	private ArrayList<Player> players;
	private Items items;
	private Timer timer;
	private JLabel gameOverLbl;

	public GamePanel(ArrayList<Player> players, Items items)
	{
		setFocusable(true);
		setBackground(Color.BLACK);
		timer = new Timer(0, this);

		gameOverLbl = new JLabel("Game Over", SwingConstants.CENTER);
		gameOverLbl.setFont(new Font("BOLD", Font.PLAIN, 30));
		gameOverLbl.setForeground(Color.WHITE);
		gameOverLbl.setVisible(false);

		add(gameOverLbl, BorderLayout.CENTER);

		this.players = players;
		this.items = items;
	}

	public void startGraficUpdate()
	{
		timer.start();
	}

	public void stopGraficUpdate()
	{
		timer.stop();
	}

	public void gameOver()
	{
		items.removeAll();
		gameOverLbl.setVisible(true);
		stopGraficUpdate();
		try
		{
			Thread.sleep(2000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		gameOverLbl.setVisible(false);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for (int i = 0; i < players.size(); i++)
		{
			players.get(i).paint(g);
		}
		items.paint(g);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		repaint();
	}
}