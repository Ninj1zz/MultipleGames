//Alessio - Luan Krasnik
//Matrikelnummer: 1303256
package Rummikub;


import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Game {
	
	public static void main(String[] args) {
		Rummikub ui = new Rummikub();
		SystemRequirements requirements = new SystemRequirements();
		Constants_Variables variables = new Constants_Variables();
		Rules rules = new Rules();
				
		
		Thread countDownComputer = new Thread() {
			private int paircounter = ui.computer.getPairCounter();
			Random random = new Random();
			public void run() {
				
				while(true)
				{	
					boolean countDownFlag = ui.computer.getCountDownFlag();
					this.paircounter = 0;
					System.out.println(ui.computer.getTurn());
					System.out.println(ui.player.getTurn());
					if(ui.computer.getTurn() == true && ui.player.getTurn() == false) 
					{
					System.out.println(countDownFlag);
					if(countDownFlag) 
					{	
					ui.initializeCountDown(variables.getSecondsPerTurn(), 1); //first parameter for second, second parameter for counterType == 1: computer turn, 2:game countdown
					ui.computer.setCountDownFlag(false);
					
					
					//draws a stone automatically if computer is on round	
					boolean InvFullOfStones = rules.checkForFullInventory(ui.computer.getInventory());
	    			if(InvFullOfStones == false) 
	    			{
	    			for(int i = 0; i<ui.computer.getMaxInventory(); i++) 
	    			  {
	    				if(ui.computer.getInventory().get(i).getValue() == -1) 
	    				{
	    					int random_stone = random.nextInt(requirements.getStonesList().size());
	    					int value = requirements.getStonesList().get(random_stone).getValue();
	    					int color = requirements.getStonesList().get(random_stone).getColor();
	    					ui.computer.getInventory().get(i).setValue(value); //used, so not the same stone gets exchanged each time
	    					ui.computer.getInventory().get(i).setColor(color);
	    					break; //nur eins soll eingef�gt werden, deswegen break
	    				}
	    			  }
	    			}
					
					//check for Joker in Computer INventory
	    			boolean hasJokers = rules.checkForJokerInInventory(ui.computer.getInventory());
	    			if(hasJokers) 
	    			{
	    					int number_want_to_use_joker = rules.checkForMostOccurencyValue(ui.computer.getInventory());
	    					int color_stone = random.nextInt(5); //random between the four colours
	    					
	    				        //set the joker to the number you choose and color
	    						for(int i = 0; i<ui.computer.getInventory().size(); i++) 
	    						{
	    							if(ui.computer.getInventory().get(i).getValue() == 0) 
	    							{
	    								ui.computer.getInventory().get(i).setValue(number_want_to_use_joker);
	    								ui.computer.getInventory().get(i).setColor(color_stone);
	    							}
	    						}
	    			 }
	    		
					//check for pair automatically if computer is on turn!
    				boolean hasSameNumberPairsFlag = false;
    				int[] value_comeup = rules.checkForSameNumberPair(ui.computer.getInventory());
    				int j = 0;
    				if(value_comeup != null) 
    				{
    					hasSameNumberPairsFlag  = true;
    				}
    				if(hasSameNumberPairsFlag) 
    				{
    					while(value_comeup[j] != 999) 
    					{
    						if(value_comeup[j] >= 3 && j > 0) //jokers do not count as a pair!
    						{
    						
    							for (int i = 0; i<ui.computer.getInventory().size(); i++) 
    							{
    								if(ui.computer.getInventory().get(i).getValue() == j) 
    								{
    									this.paircounter++;
    									ui.computer.getInventory().get(i).setValue(-1);    							
    								}

    							}
    						}
    						j++;
    					}
	    		
					}
					ui.computer.setPairCounter(this.paircounter);
					ui.amount_pair_com.setText("" + ui.computer.getPairCounter());
					
					List<Integer> numbersOrder = new ArrayList<Integer>();
		    		numbersOrder.addAll(rules.checkMinimumThreeOrder(ui.computer.getInventory()));
		    		boolean hasSameColor = true;
		    		int color = -1;
		    		if(numbersOrder.isEmpty() == false) 
		    			{
		    					for(int l = 0; l<numbersOrder.size(); l++) 
		    					{
		    						if(rules.checkForGreaterOneValueComeUp(numbersOrder.get(l), ui.player.getInventory()) > 1) 
		    						{
		    							for (int k = 0; k < ui.computer.getInventory().size(); k++) 
		    							{
		    								if(numbersOrder.get(l) == ui.computer.getInventory().get(k).getValue()) 
		    								{
		    									color = ui.computer.getInventory().get(k).getColor();
		    								}
		    								for (int z = 0; z<numbersOrder.size(); z++) 
		    								{
		    									for(int y = 0; y<ui.computer.getInventory().size(); y++) 
		    									{
		    									if(numbersOrder.get(z) == ui.computer.getInventory().get(y).getValue()) 
		    									{
		    										if(!(ui.computer.getInventory().get(y).getColor() == color)) 
		    										{
		    											hasSameColor = false;
		    										}
		    									}
		    									}
		    								}
		    							}
		    						}
		    					}
		    						for(int i = 0; i<numbersOrder.size(); i++) {
		    						if(rules.checkForGreaterOneValueComeUp(numbersOrder.get(i), ui.player.getInventory()) == 1)  
		    						{
		    							for(int f = 0; f<ui.computer.getInventory().size(); f++) 
		    							{
		    								if(numbersOrder.get(i) == ui.computer.getInventory().get(f).getValue()) {
		    									color = ui.player.getInventory().get(j).getColor();
		    								}
		    								for(int k = 0; k<numbersOrder.size(); k++) 
		    								{
		    									for(int z = 0; z<ui.player.getInventory().size(); z++) 
		    									{
			    									if(numbersOrder.get(k) == ui.player.getInventory().get(z).getValue()) 
			    									{
		    										if(!(ui.player.getInventory().get(z).getColor() == color)) 
		    										{
		    											hasSameColor = false;
		    										}
			    									}	
		    									}
		    								} 
	
		    							}
		    						}
		    						}
		    						
		    					if(hasSameColor) 
		    					{
		    						for(int i = 0; i<numbersOrder.size(); i++) 
		    						{
		    							for(int f = 0; j<ui.computer.getInventory().size(); f++) 
		    							{
		    								if(numbersOrder.get(i) == ui.computer.getInventory().get(f).getValue()) 
		    								{
				    							paircounter++;
				    							ImageIcon icon = new ImageIcon("placeholder.png");
				    							ui.player.getInventory().get(j).setImageIcon(icon);
				    							ui.player.getInventory().get(j).setValue(-1);
		    								}
		    							}
		    						}
		    					}
				    			ui.computer.setPairCounter(paircounter);
				    			ui.amount_pair_com.setText("" + ui.computer.getPairCounter());
		    				}
				}
			}
			}
			}
		};
		countDownComputer.start();
		
		
		
		
		
		Thread countDownGame = new Thread() {
			private boolean countDownFlag = true; //used for the timer, so the ui.initializecountdown method isnt called every second
			public void run() {
				
				while(true) {	
					if(countDownFlag == true) 
					{
					ui.initializeCountDown(variables.getMaxSecondsGame(), 2); //first parameter for second, second parameter for counterType == 1: player 2: computer turn, 3:game counter
					this.countDownFlag  = false;
					}
					if(ui.computer.getTurn() == false && ui.player.getTurn() == false && ui.getGameFlag() == false) 
					{
						if(ui.player.getPairCounter() > ui.computer.getPairCounter()) {
						JOptionPane.showMessageDialog(ui, "The Game has finished! Congratulations " + ui.username + "\nYou have won the game!");
						}
						else if(ui.player.getPairCounter() < ui.computer.getPairCounter()) 
						{
							JOptionPane.showMessageDialog(ui, "The Game has finished! Sorry you have unfortunately lost the game");
						}
						else  
						{
							JOptionPane.showMessageDialog(ui, "Nobody has won the game!");
						}
					}
					ui.validate(); //use this to repaint the gui
					ui.repaint(); //use this to repaint the gui
			}
			}
		};
		countDownGame.start();
		
		
		Thread buttonActions = new Thread(){
			int stonesNumber = variables.getMaxStones();
			int paircounter = ui.player.getPairCounter();
			boolean newStoneClicked;
			boolean checkForPairsClicked;
			boolean btnJokerClicked;
			boolean btnMiniThreeClicked;
			boolean InvFullOfStones = true;
		    public void run(){
		    	while(true) {
		    		/*
		    		 * button actions are checked from the system every second
		    		 */
		    		this.paircounter = 0;
		    		newStoneClicked = ui.draw_stone.getFlag();
		    		System.out.println(newStoneClicked);
		    		if(newStoneClicked) 
		    		{
		    			if(requirements.getStonesCount() <= 0) {
		    			JOptionPane.showMessageDialog(ui,
		    				    "Sorry, but no Stones are left anymore!");
		    			}
		    			this.InvFullOfStones = rules.checkForFullInventory(ui.player.getInventory());
		    			if(this.InvFullOfStones == true) {
		    			JOptionPane.showMessageDialog(ui,
		    				    "Sorry, but your Inventory is full!");
		    			}
		    			else if(this.InvFullOfStones == false) 
		    			{
		    			Random random = new Random();
		    			for(int i = 0; i<ui.player.getMaxInventory(); i++) {
		    				if(ui.player.getInventory().get(i).getValue() == -1) { //all empty placeholder haben -1 als value
		    					//create a random stone jlabel on playerarea after clicking the button
		    					int random_stone = random.nextInt(requirements.getStonesList().size());
		    					ImageIcon icon = requirements.getStonesList().get(random_stone).getImageIcon();
		    					int value = requirements.getStonesList().get(random_stone).getValue();
		    					int color = requirements.getStonesList().get(random_stone).getColor();
		    					ui.player.getInventory().get(i).setImageIcon(icon);
		    					ui.player.getInventory().get(i).setValue(value); //used, so not the same stone gets exchanged each time
		    					ui.player.getInventory().get(i).setColor(color);
		    					requirements.getStonesList().remove(i); //remove Stone from Liste
		    					break; //nur eins soll eingef�gt werden, deswegen break
		    				}
		    			}
		    			requirements.setStonesCount(stonesNumber--);
		    			if(requirements.stonesLeft() == false) {stonesNumber = 0;}
		    			ui.CardsLeft.setText("Stones Left("+ stonesNumber + "/" + variables.getMaxStones() + ")");
		    			ui.draw_stone.setFlag(false);   //button_flag wird wieder auf false gesetzt, damit man nicht immer neue steine bekommt!
		    			ui.draw_stone.setStatus(false); //button wird enabled nachdem der player wieder in der runde ist, brauchen timer daf�r!
		    		    }
		    	      }
		    		
		    		checkForPairsClicked = ui.hasPairs.getFlagPairs();
		    		System.out.println(checkForPairsClicked);
		    		if(checkForPairsClicked) 
		    		{
		    				boolean hasSameNumberPairsFlag = false;	
		    				int[] value_comeup = rules.checkForSameNumberPair(ui.player.getInventory());
		    				int j = 0;
		    				if(value_comeup != null) 
		    				{
		    					hasSameNumberPairsFlag  = true;
		    				}
		    				if(hasSameNumberPairsFlag == true) 
		    				{
		    					while(value_comeup[j] != 999) 
		    					{
		    						if(value_comeup[j] >= 3 && j > 0) //jokers do not count as a pair!
		    						{
		    						
		    							for (int i = 0; i<ui.player.getInventory().size(); i++) 
		    							{
		    								if(ui.player.getInventory().get(i).getValue() == j) 
		    								{
		    									paircounter++;
		    									ImageIcon icon = new ImageIcon("placeholder.png");
		    									ui.player.getInventory().get(i).setImageIcon(icon);
		    									ui.player.getInventory().get(i).setValue(-1);
		    							
		    								}
	
		    							}
		    						}
		    						j++;
		    					}
								ui.player.setPairCounter(paircounter);
								ui.amount_pair_player.setText("" + ui.player.getPairCounter());
								ui.hasPairs.setFlagPairs(false); //button flag is disabled, so no actions are performed since no pairs are there
		    				}
		    				else if(hasSameNumberPairsFlag == false) 
		    				{
		    				 JOptionPane.showMessageDialog(ui, "Sorry, but you don't have enough equal numbers in your inventory to get a Pair!",
		    					      "Error!", JOptionPane.ERROR_MESSAGE);
		    				 ui.hasPairs.setFlagPairs(false); //set to false, so if you draw a stone and have a pair then, then the system checks again!
		    				}
		    		}
		    			btnJokerClicked  = ui.btnCheckforjoker.getJokerFlag();
		    			System.out.println(btnJokerClicked);
		    			if(btnJokerClicked) {
		    			boolean hasJokers = rules.checkForJokerInInventory(ui.player.getInventory());
		    			int number_want_to_use_joker;
		    			if(hasJokers) 
		    			{
		    					try {
		    					number_want_to_use_joker = Integer.parseInt(JOptionPane.showInputDialog(ui, "Please enter a number you want to have in your inventory."));
		    					}
		    					catch (NumberFormatException e) 
		    					{
		    						JOptionPane.showMessageDialog(ui, "You typed a wrong format, please be sure to type a number",
		  	    					      "Error!", JOptionPane.ERROR_MESSAGE);
		    						number_want_to_use_joker = Integer.parseInt(JOptionPane.showInputDialog(ui, "Please enter a number you want to have in your inventory."));
		    					}
		    					String color = JOptionPane.showInputDialog(ui, "Please enter a proper color.");
		    					int addition = 0;
		    					int color_stone = 0;

		    						switch(color) 
		    						{
		    						case "Black" :
		    							addition = 0;
		    							color_stone = 1;
		    							break;
		    						case "Red" :
		    							addition = 14;
		    							color_stone = 2;
		    							break;
		    						case "Yellow" :
		    							addition = 28;
		    							color_stone = 3;
		    							break;
		    						case "Blue" : 
		    							addition = 42;
		    							color_stone = 4;
		    							break;
		    						}
		    						ImageIcon icon = requirements.getStonesList().get(number_want_to_use_joker + addition).getImageIcon(); //set the joker to the number you choose and color
		    						for(int i = 0; i<ui.player.getInventory().size(); i++) 
		    						{
		    							if(ui.player.getInventory().get(i).getValue() == 0) 
		    							{
		    								ui.player.getInventory().get(i).setImageIcon(icon);
		    								ui.player.getInventory().get(i).setValue(number_want_to_use_joker);
		    								ui.player.getInventory().get(i).setColor(color_stone);
		    							}
		    						}
		    				}
		    			else {
		    				JOptionPane.showMessageDialog(ui, "Sorry, but you don't have any Jokers in your Inventory!",
	    					      "Error!", JOptionPane.ERROR_MESSAGE);
		    				}
		    			ui.btnCheckforjoker.setJokerFlag(false);
		    			hasJokers = false;
		    			}
		    		//check for MinimumThreeOrder
		    		btnMiniThreeClicked = ui.btnMinimumThreeOrder.getMinThreeFlag();
		    		System.out.println(btnMiniThreeClicked);
		    		if(btnMiniThreeClicked) 
		    		{
		    		List<Integer> numbersOrder = new ArrayList<Integer>();
		    		numbersOrder.addAll(rules.checkMinimumThreeOrder(ui.player.getInventory()));
		    		boolean hasSameColor = true;
		    		int color = -1;
		    		if(numbersOrder.isEmpty() == false) 
		    			{
//		    				int paircounter = ui.player.getPairCounter();
		    					for(int l = 0; l<numbersOrder.size(); l++) 
		    					{
		    						if(rules.checkForGreaterOneValueComeUp(numbersOrder.get(l), ui.player.getInventory()) > 1) 
		    						{
		    							for (int k = 0; k < ui.player.getInventory().size(); k++) 
		    							{
		    								if(numbersOrder.get(l) == ui.player.getInventory().get(k).getValue()) 
		    								{
		    									color = ui.player.getInventory().get(k).getColor();
		    								}
		    								for (int z = 0; z<numbersOrder.size(); z++) 
		    								{
		    									for(int y = 0; y<ui.player.getInventory().size(); y++) 
		    									{
		    									if(numbersOrder.get(z) == ui.player.getInventory().get(y).getValue()) 
		    									{
		    										if(!(ui.player.getInventory().get(y).getColor() == color)) 
		    										{
		    											hasSameColor = false;
		    										}
		    									}
		    									}
		    								}
		    							}
		    						}
		    					}
		    						for(int i = 0; i<numbersOrder.size(); i++) {
		    						if(rules.checkForGreaterOneValueComeUp(numbersOrder.get(i), ui.player.getInventory()) == 1) 
		    						{
		    							for(int j = 0; j<ui.player.getInventory().size(); j++) 
		    							{
		    								if(numbersOrder.get(i) == ui.player.getInventory().get(j).getValue()) {
		    									color = ui.player.getInventory().get(j).getColor();
		    								}
		    								for(int k = 0; k<numbersOrder.size(); k++) 
		    								{
		    									for(int z = 0; z<ui.player.getInventory().size(); z++) 
		    									{
			    									if(numbersOrder.get(k) == ui.player.getInventory().get(z).getValue()) 
			    									{
		    										if(!(ui.player.getInventory().get(z).getColor() == color)) 
		    										{
		    											hasSameColor = false;
		    										}
			    									}	
		    									}
		    								} 
	
		    							}
		    						}
		    						}
		    						
		    					if(hasSameColor) 
		    					{
		    						for(int i = 0; i<numbersOrder.size(); i++) 
		    						{
		    							for(int j = 0; j<ui.player.getInventory().size(); j++) 
		    							{
		    								if(numbersOrder.get(i) == ui.player.getInventory().get(j).getValue()) 
		    								{
				    							paircounter++;
				    							ImageIcon icon = new ImageIcon("placeholder.png");
				    							ui.player.getInventory().get(j).setImageIcon(icon);
				    							ui.player.getInventory().get(j).setValue(-1);
		    								}
		    							}
		    						}
		    					}
				    			ui.player.setPairCounter(paircounter);
				    			ui.amount_pair_player.setText("" + ui.player.getPairCounter());
								ui.btnMinimumThreeOrder.setMinThreeFlag(false);
		    				}
		    			else if (hasSameColor == false || numbersOrder.isEmpty()) {
		    				JOptionPane.showMessageDialog(ui, "Sorry, but you don't have minimum Three Order in your Inventory!",
	    					      "Error!", JOptionPane.ERROR_MESSAGE); ui.btnMinimumThreeOrder.setMinThreeFlag(false);
	    					      }
		    			}
		    
		    boolean btnEndRoundFlag = ui.btnEndRound.getRoundFlag();
		    System.out.println(btnEndRoundFlag);
		    if(btnEndRoundFlag == true) 
		    {
		    	ui.draw_stone.setStatus(false);
		    	ui.hasPairs.setStatus(false);
		    	ui.btnMinimumThreeOrder.setStatus(false);
		    	ui.btnCheckforjoker.setStatus(false);
		    	ui.btnEndRound.setStatus(false);
		    	ui.player.setTurn(false);
		    	ui.computer.setTurn(true);
		    	ui.computer.setCountDownFlag(true);
		    	ui.btnEndRound.setEndRound(false); //set to false, so counter of computer isn't initialized everytime!
		    }
		    }
		    }
		};
		  buttonActions.start();
		  
	}
	
}

