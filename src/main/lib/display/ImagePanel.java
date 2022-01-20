package main.lib.display;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1228028210848482040L;
	private Image img;

	public ImagePanel(Image img) {
		int margin = 10;
		this.setBounds(margin, margin, 350 - (2 * margin), 250 - (2 * margin + 20));
		this.img = img.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this);
	}

}
