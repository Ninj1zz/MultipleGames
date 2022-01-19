package Rummikub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/*
 * this class will be used for the rules of the game
 * some conditions will be defined here
 */
public class Rules {

 
 public Rules() {

 }
 
 
 public int[] checkForSameNumberPair(List<Stone> list) 
 {
	 	List<Stone> stoneList = new ArrayList<Stone>();
		for(int i = 0; i<list.size(); i++) {
			stoneList.add(list.get(i));	
		}
	
		int j = 0;
		int[] value_comeup = new int[15];
		for(int i = 0; i<=14; i++) 
		{
			value_comeup[i] = 0;
			if(i == 14) 
			{
				value_comeup[i] = 999; //just to know when array stop
			}
		}

		for(int i = 0; i<stoneList.size(); i++) {
		int value_of_stone = stoneList.get(i).getValue();
		if(value_of_stone != -1) {
		value_comeup[value_of_stone]++;
		   }
		}
		while(value_comeup[j] != 999) 
		{
			if(value_comeup[j] >= 3) 
			{
				return value_comeup;
			}
			j++;
		}
		return null;
 }
 
 public boolean checkForJokerInInventory(List<Stone> list) 
 {
	 	List<Stone> stoneList = new ArrayList<Stone>();
		for(int i = 0; i<list.size(); i++) {
			stoneList.add(list.get(i));	
		}
	
		int j = 0;
		int value_comeup[] = new int[15];
		for(int i = 0; i<=14; i++) 
		{
			value_comeup[i] = 0;
			if(i == 14) 
			{
				value_comeup[i] = 999; //just to know when array stop
			}
		}

		for(int i = 0; i<stoneList.size(); i++) 
		{
		int value_of_stone = stoneList.get(i).getValue();
		if(value_of_stone != -1) 
		   {
			value_comeup[value_of_stone]++;
		   }
		}
		while(value_comeup[j] != 999) 
		{
			if(value_comeup[0] > 0) 
			{
				return true;
			}
			j++;
		}
		return false;
 }
 
 
 public List<Integer> checkMinimumThreeOrder(List<Stone> list) 
 {
		List<Stone> stoneList = new ArrayList<Stone>(); //all stones which are on playerarea
		for(int i = 0; i<list.size(); i++) {
			stoneList.add(list.get(i));	
		}
		List<Integer> all_inv_values = new ArrayList<Integer>();
		List<Integer> order_values = new ArrayList<Integer>();
		List<Integer> filtered_values = new ArrayList<Integer>();
	
		int miniumumThree = 1;
		for(int i = 0; i<stoneList.size(); i++) 
		{			
			if(!(all_inv_values.contains(stoneList.get(i).getValue())) && stoneList.get(i).getValue() != -1 && stoneList.get(i).getValue() != 0)
			{
				all_inv_values.add(stoneList.get(i).getValue()); //add all numbers, player or computer has in inventory!
			}				
		} 
		Collections.sort(all_inv_values); //sort the list, for inner loop down, because there might be case 
		                                 //e.g: you have 4,3,2 in inventory
		                                 //so the loop starts with 2 since it is sorted and checks whether it has a "nachfolger"
		
		for(int i = 0; i<all_inv_values.size(); i++) 
		{
			
				int number = all_inv_values.get(i);
				System.out.println("NUMBER TO CHECK: " + number);
				for(int k = 0; k<all_inv_values.size(); k++) 
				{		
					if(all_inv_values.get(k) == number + 1) 
					{
						miniumumThree++;
						if(!(order_values.contains(number)))
						{
						order_values.add(number); 
						order_values.add(number + 1); //adds also the value above, so this value is also included in the array, because after that the condition isn't fulfilled anymore!
						}
						number = number + 1;  //increase the number to see how long the order is!
					}
				}
				
		 if(miniumumThree < 3) 
		 {
			 order_values.clear();
			 miniumumThree = 1;
		 }
		}
	
	for(int l = 0; l<order_values.size(); l++) 
	{
		System.out.println("Ordered Values: " + order_values.get(l));
	}
	
	if (!order_values.isEmpty()) 
	{
		int minThree = 1;
		for(int i = 0; i<order_values.size(); i++) 
		{
			int number = order_values.get(i);
			for(int j = 0; j<order_values.size(); j++) 
			{
				if(order_values.get(j) == number + 1) 
				{
					minThree++;
					if(!(filtered_values.contains(number)))
					{
					filtered_values.add(number);
					}
					number = number + 1;
					if(!(filtered_values.contains(number)))
					{
					filtered_values.add(number);
					}
				}
			}
			
		 if(minThree < 3) 
		 {
			 filtered_values.clear();
			 minThree = 1;
		 }	
		}
	}
	
	for(int l = 0; l<filtered_values.size(); l++) 
	{
		System.out.println("Filtered Values: " + filtered_values.get(l));
	}
	return filtered_values;
 }
 
 
 
 public boolean checkForSpecificStone (List<Stone> list, int number, String color) 
 {
	 	List<Stone> stoneList = new ArrayList<Stone>();
		int color_stone = 0;
		for(int i = 0; i<list.size(); i++) {
			stoneList.add(list.get(i));	
		}
		
		switch (color) 
		{
		case "Black" :
			color_stone = 1;
			break;
		case "Red" :
			color_stone = 2;
			break;
		case "Yellow" :
			color_stone = 3;
			break;
		case "Blue" : 
			color_stone = 4;
			break;
		}
		
		for (int i = 0; i<stoneList.size(); i++) 
		{
		if(number == stoneList.get(i).getValue() && color_stone == stoneList.get(i).getColor()) 
		{
		return true;
		}
		}
		return false;	
 }
 
 public boolean checkForFullInventory (List<Stone> list) 
 {
	 	List<Stone> stoneList = new ArrayList<Stone>();
		for(int i = 0; i<list.size(); i++) {
			stoneList.add(list.get(i));	
		}
		for(int i = 0; i<stoneList.size(); i++) 
		{
			if(stoneList.get(i).getValue() == -1) 
			{
				return false; //check if inventory is already full of stones, if yes than player can't draw new stone
			}
		}
		return true;
 }
 
 //used if Computer has Joker in INventory
 public int checkForMostOccurencyValue (List<Stone> list) 
 {
	 	List<Stone> stoneList = new ArrayList<Stone>();
		for(int i = 0; i<list.size(); i++) {
			stoneList.add(list.get(i));	
		}
	
		int j = 1;
		int[] value_comeup = new int[15];
		int BiggestOccurency = -1; //-1, because lowest is 0 in value_comeup - to have a starting point!
		for(int i = 0; i<=14; i++) 
		{
			value_comeup[i] = 0;
			if(i == 14) 
			{
				value_comeup[i] = 999; //just to know when array stop
			}
		}

		for(int i = 0; i<stoneList.size(); i++) {
		int value_of_stone = stoneList.get(i).getValue();
		if(value_of_stone != -1) {
		value_comeup[value_of_stone]++;
		   }
		}
		
		while(value_comeup[j] != 999) 
		{
			if(value_comeup[j] > BiggestOccurency && value_comeup[j] > 0) //check for > 0, because otherwise it can also return values which are not existent in the inventory
			{
				BiggestOccurency = j;
			}
			j++;
		}
		return BiggestOccurency;
 }
 
 public int checkForGreaterOneValueComeUp (int value, List<Stone> list) 
 {
	 	List<Stone> stoneList = new ArrayList<Stone>();
	 	int counter = 0;
		for(int i = 0; i<list.size(); i++) {
			stoneList.add(list.get(i));	
		}
		
		for( int i = 0; i < stoneList.size(); i++ ) 
		{
			if (stoneList.get(i).getValue() == value) 
			{
				counter++;
			}
		}
		return counter;
 }
 
}



