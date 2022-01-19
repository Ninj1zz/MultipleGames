package Rummikub;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SystemRequirements {
private int stonesNumber; //wieviele Steine insgesamt im Spiel sind!
private int stones_per_player; //wieviele Steine jeder am Anfang des Spiel bekommt
private List<Stone> stoneList;
private StoneManager sm;

public SystemRequirements() {
	sm = new StoneManager("stone.jpeg");
	stoneList = new ArrayList<Stone>();
	Constants_Variables variables = new Constants_Variables();
	this.setStoneList();
	this.setStonesCount(variables.getMaxStones());
	this.setStonesPerPlayer(variables.getMaxStonesPerPlayer()); //13 Steine bekommt jeder Spieler am Anfang / Zahl willk�rlich gew�hlt
}

public void reduceStonesOnStack() {
	this.stonesNumber = this.stonesNumber - 1;
}

public void setStonesCount(int number) {
	this.stonesNumber = number;
}

public int getStonesCount() {
	return this.stonesNumber;
}

public boolean stonesLeft() {
	return stonesNumber > 0;
}

public void setStoneList() {
	this.initializeStones();
}

public List<Stone> getStonesList() {
	return this.stoneList;
}

public void setStonesPerPlayer(int player_stones) {
	this.stones_per_player = player_stones;
}

public int getStonesPerPlayer() {
	return this.stones_per_player;
}


public void initializeStones() 
{
	int counter = 0;
	for(int i=0; i<sm.getImageList().size(); i++) {
		
		if(i >=0 && i<=13) 
		{
			stoneList.add(new Stone(new ImageIcon(sm.getImageList().get(i)),counter,1));
		}
		else if(i >=14 && i<=27) 
		{
			stoneList.add(new Stone(new ImageIcon(sm.getImageList().get(i)),counter,2));
		}
		else if(i >=28 && i<=41) 
		{
			stoneList.add(new Stone(new ImageIcon(sm.getImageList().get(i)),counter,3));
		}
		else if(i >=42 && i<=55)
		{
			stoneList.add(new Stone(new ImageIcon(sm.getImageList().get(i)),counter,4));
		}	
		
		if(counter == 13) {
			counter = 0;
			continue;
		}
			counter++;
	}
}

}