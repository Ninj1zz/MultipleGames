package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Items implements Runnable
{
	ArrayList<Point> food = new ArrayList<>();

	public void spawn()
	{
		new Thread(this).start();
	}
	public void run()
	{
		food.add(new Point((int) ((Math.random() * 20) + 1), (int) ((Math.random() * 20) + 1)));
	}

	public void paint(Graphics g)
	{
		for (int i = 0; i < food.size(); i++)
		{
			g.setColor(Color.RED);
			g.fillOval(food.get(i).getX() * 25, food.get(i).getY() * 25, 25, 25);
		}
	}

	public boolean removeFood(Point position)
	{
		for(int i = 0; i < food.size(); i++)
		{
			if(food.get(i).getX() == position.getX() && food.get(i).getY() == position.getY())
			{
				food.remove(i);
				return true;
			}
		}
		return false;
	}

	public void removeAll()
	{
		for(int i = 0; i < food.size(); i++)
		{
			food.remove(i);
		}
	}

	public boolean contains(Point position)
	{
		for(int i = 0; i < food.size(); i++)
		{
			if(food.get(i).getX() == position.getX() && food.get(i).getY() == position.getY())
			{
				return true;
			}
		}
		return false;
	}

	public String toString()
	{
		String bodyTemp = "";
		for (int i = 0; i < food.size(); i++)
		{
			bodyTemp += food.get(i).toString() + "\n";
		}
		return bodyTemp;
	}
}