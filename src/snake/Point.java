package snake;

public class Point
{
	private int x;
	private int y;

	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public Point(Point p)
	{
		this.x = p.getX();
		this.y = p.getY();
	}


	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public Point add(Point p)
	{
		this.x += p.getX();
		this.y += p.getY();

		return this;
	}

	public Point subtract(Point p)
	{
		this.x -= p.getX();
		this.y -= p.getY();

		return this;
	}

	public boolean equals(Point point)
	{
		return getX() == point.getX() && getY() == point.getY();
	}

	public String toString()
	{
		return "(" + getX() + "," + getY() + ")";
	}
}