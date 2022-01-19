package Rummikub;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Button extends JButton implements ActionListener {
	private String id;
	private boolean createStone;
	private boolean pairsFlag;
	private boolean endRound;
	private boolean JokerFlag;
	private boolean MinimumThreeOrderFlag;
	
	public Button(String id) 
	{	
	 	this.setId(id);
		createStone = false;
		pairsFlag = false;
		endRound = false;
		JokerFlag = false;
		MinimumThreeOrderFlag = false;
	 	if(this.id == "draw_stone") 
	 	{
		 	this.setBounds(1193, 762, 151, 228);
		 	this.setText("Draw a new Stone");
		 	this.addActionListener(this);
		}
	 	else if(this.id == "CheckForPairs") 
	 	{
	 		this.setBounds(80, 668, 165, 23);
	 		this.setText(id);
	 		this.addActionListener(this);
	 	}
	 	else if(this.id == "End Round") 
	 	{
	 		this.setBounds(870, 666, 89, 23);
	 		this.setText(id);
	 		this.addActionListener(this);
	 	}
	 	else if(this.id == "CheckForJoker") 
	 	{
			this.setBounds(80, 620, 165, 23);
	 		this.setText(id);
	 		this.addActionListener(this);
	 	}
	 	else if(this.id == "CheckForMinThree") 
	 	{
	 		this.setBounds(80, 593, 165, 23);
	 		this.setText(id);
	 		this.addActionListener(this);
	 	}
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getId() {
		return this.id;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (this.id) {
		case "draw_stone": 
			this.setFlag(true);
			System.out.println("Draw a new stone");
			break;
		case "CheckForPairs":
			this.setFlagPairs(true); //button is Clicked
			System.out.println("Check For Pairs");
			break;
		case "End Round":
			this.setEndRound(true); //button is Clicked
			System.out.println("End Round");
			break;
		case "CheckForJoker":
			this.setJokerFlag(true); //button is Clicked
			System.out.println("Check For Joker");
			break;
		case "CheckForMinThree":
			this.setMinThreeFlag(true); //button is Clicked
			System.out.println("CheckForMiniThreeOrder");
			break;		
		default:
			break;
	}
}
	
	public boolean getFlag() {
		return createStone;
	}

	
	public void setFlag(boolean Flag) {
		this.createStone = Flag;
	}
	
	public boolean getFlagPairs() {
		return this.pairsFlag;
	}
	
	public void setFlagPairs(boolean flag) {
		this.pairsFlag = flag;
	}
	
	public boolean getRoundFlag() {
		return this.endRound;
	}
	
	public void setEndRound(boolean flag) {
		this.endRound = flag;
	}
	
	
	public boolean getJokerFlag() {
		return this.JokerFlag;
	}
	
	public void setJokerFlag(boolean flag) {
		this.JokerFlag = flag;
	}
	
	public boolean getMinThreeFlag() {
		return this.MinimumThreeOrderFlag;
	}
	
	public void setMinThreeFlag(boolean flag) {
		this.MinimumThreeOrderFlag = flag;
	}
	
	
	public void setStatus(boolean status) {
		this.setEnabled(status);
	}
	
	public boolean getStatus() {
		return this.isEnabled();
	}
}