package Rummikub;

public class Computer extends Player {
	private boolean countDownFlag;

	public Computer() {
		super();
		this.setTurn(false);
	}

	
	public void setCountDownFlag (boolean flag) 
	{
		this.countDownFlag = flag;
	}

	public boolean getCountDownFlag () 
	{
		return this.countDownFlag;
	}
	

}
