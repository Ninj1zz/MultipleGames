package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Player implements Runnable
{

	private ArrayList<Point> body = new ArrayList<>();
	private Point direction = new Point(0, 1);
	private Point nextDirection;
	private Items items;
	private int consumed;
	private Point fieldSize;
	private boolean wallEnabled;

	private final int LEFT, RIGHT, DOWN, UP;
	private int status = 0;
	private Manager manager;


	public Player(int left, int right, int down, int up, Items items, Point fieldSize, Manager manager, Point startPos, boolean wallEnabled)
	{
		this.LEFT = left;
		this.RIGHT = right;
		this.DOWN = down;
		this.UP = up;

		this.wallEnabled = wallEnabled;

		nextDirection = direction;

		this.manager = manager;

		// Copy of items-object
		this.items = items;

		this.fieldSize = fieldSize;

		createSnake(startPos);
	}

	public int getLEFT()
	{
		return LEFT;
	}
	public int getRIGHT()
	{
		return RIGHT;
	}
	public int getUP()
	{
		return UP;
	}
	public int getDOWN()
	{
		return DOWN;
	}
	public Point getDirection()
	{
		return direction;
	}
	public void setNextDirection(Point nextDirection)
	{
		this.nextDirection = nextDirection;
	}

	private void createSnake(Point headPosition)
	{
		body.add(headPosition);
		manager.addOccupiedPositions(headPosition);
	}

	public void startSnake()
	{
		status = 1;
		new Thread(this).start();
	}

	public void run()
	{
		while(status == 1)
		{
			if(collisionCheck())
			{
				killSnake();
				return;
			}
			move();
			try {
				Thread.sleep(150);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void move()
	{
		for (int i = body.size() - (1 + consumed); i > 0; i--)
		{
			body.get(i).setX(body.get(i - 1).getX());
			body.get(i).setY(body.get(i - 1).getY());
		}
		consumed = 0;

		// Update actual direction
		direction = nextDirection;

		// Moving head towards direction
		body.get(0).add(direction);

		// Snake moves into wall (if wallEnabled false)
		if(!wallEnabled)
		{
			if (body.get(0).getX() < 0)
			{
				body.get(0).setX(fieldSize.getX() - 1);
			}
			if (body.get(0).getY() < 0)
			{
				body.get(0).setY(fieldSize.getY() - 1);
			}
			if (body.get(0).getX() > fieldSize.getX() - 1)
			{
				body.get(0).setX(0);
			}
			if (body.get(0).getY() > fieldSize.getY() - 1)
			{
				body.get(0).setY(0);
			}
		}

		// Check if head is on item
		if (items.contains(getHeadPosition()))
		{
			items.removeFood(getHeadPosition());
			items.spawn();
			grow();
		}
	}

	private boolean collisionCheck()
	{
		Point nextHeadPosition = new Point(getHeadPosition());
		nextHeadPosition.add(nextDirection);
		ArrayList<Point> occupiedPositions = manager.getOccupiedPositions();

		//Check collision with own body parts
		for(int i = 0; i < occupiedPositions.size(); i++)
			if(nextHeadPosition.equals(occupiedPositions.get(i)))
				return true;

		if(wallEnabled)
		{
			if (body.get(0).getX() < 0)
			{
				return true;
			}
			if (body.get(0).getY() < 0)
			{
				return true;
			}
			if (body.get(0).getX() > fieldSize.getX() - 1)
			{
				return true;
			}
			if (body.get(0).getY() > fieldSize.getY() - 1)
			{
				return true;
			}
		}
		return false;
	}

	private void killSnake()
	{
		this.status = 0;
		while(!body.isEmpty())
		{
			try
			{
				Thread.sleep(50);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			manager.removeOccupiedPosition(body.get(body.size() - 1));
			body.remove(body.size() - 1);
		}
		manager.playerKilled(this);
	}

	public void paint(Graphics g)
	{
		for (int i = 0; i < body.size(); i++)
		{
			g.setColor(new Color(50, 80, 10));
			g.fillRect(body.get(i).getX() * 25, body.get(i).getY() * 25, 25, 25);
		}
	}

	public void grow()
	{
		consumed = 1;
		Point newBodyPart = new Point(body.get(body.size() - 1).getX(), body.get(body.size() - 1).getY());
		body.add(newBodyPart);
		manager.addOccupiedPositions(newBodyPart);
	}

	public ArrayList<Point> getBody()
	{
		return body;
	}

	public Point getHeadPosition()
	{
		return getBody().get(0);
	}

	public String toString()
	{
		String bodyTemp = "";
		for (int i = 0; i < body.size(); i++)
		{
			bodyTemp += body.get(i).toString() + "\n";
		}
		return bodyTemp;
	}
}