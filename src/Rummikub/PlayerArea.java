package Rummikub;

import javax.swing.*;
import java.awt.*;

/*
 * later on this class is also used for the Players Inventory
 * to initialize the stones on JPanel Component
 */
public class PlayerArea extends JPanel {
private String id;



	public PlayerArea(String id) {	
	this.id = id;
	this.setBounds(80, 698, 1101, 292);
	this.setLayout(new GridLayout(2, 10));
	this.setName(id);
}
}
