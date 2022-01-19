package Rummikub;

import javax.swing.*;
import java.awt.*;

public class Stone extends JLabel {
	private ImageIcon imageicon;
	private int value;
	private boolean isJoker = false;
	private int color;
	/**
	 * 
	 * @param 
	 * @param 
	 * @param color => 1: black, 2: red, 3: yellow, 4:blue
	 */
	
	public Stone(ImageIcon i, int value, int color) {
		this.setImageIcon(i);
		this.setValue(value); // if value is 0, than it is a joker
		this.setColor(color);
		if(this.getValue() == 0) this.setJoker(true);	
	}
	
	public void setImageIcon(ImageIcon imageicon) {
		this.imageicon = imageicon;
	}
	
	public ImageIcon getImageIcon() {
		return this.imageicon;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void setJoker(boolean flag) {
		this.isJoker = flag;
	}
	
	public boolean getJoker() {
		return this.isJoker;
	}
	
	 
	
	public void setColor(int color) {
		this.color = color;
	}
	
	public int getColor() {
		return color;
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(imageicon.getImage(), 0, 0, this);
	}
	
}
