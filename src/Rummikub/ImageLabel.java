package Rummikub;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageLabel extends JLabel {
	private Image image;
	private int id;
	
	
	public ImageLabel(String imagename, int id) {
		this.id = id;
		try {
			image = ImageIO.read(new File(imagename));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public Image getImage() {
		return this.image;
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(image, 0, 0, this);
	}
}




