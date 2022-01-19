package Rummikub;

import java.util.ArrayList;
import java.util.List;

/*
 * this class will later getting replaced by Can's "Human" class
 */
public class Player {
private boolean isMyTurn;
private List<Stone> stoneList;  //Inventory of Player
private int MaxInventorySpace;
private int PairCounter;

public Player() {
	this.setTurn(true);
	this.stoneList = new ArrayList<Stone>();
	Constants_Variables cv = new Constants_Variables();
	this.setMaxInventory(cv.getMaxInvSpace());
	this.setPairCounter(0);
}

public void setInventory(List<Stone> list) 
{
	for(int i = 0; i<list.size(); i++) {
		this.stoneList.add(list.get(i));	
	}
}

public void setInventory(Stone stone) 
{
	this.stoneList.add(stone);
}

public List<Stone> getInventory() 
{
	return this.stoneList;
}

public void setMaxInventory (int space) 
{
	this.MaxInventorySpace = space;
}

public int getMaxInventory () 
{
	return this.MaxInventorySpace;
}

public void setPairCounter(int counter) 
{
	this.PairCounter += counter;
}

public int getPairCounter() 
{
	return this.PairCounter;
}

public void setTurn (boolean turn) 
{
	this.isMyTurn = turn;
}

public boolean getTurn () 
{
	return this.isMyTurn;
}

}
