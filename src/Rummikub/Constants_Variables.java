package Rummikub;




//better using enum class!
//advantage: better for oop
//
public class Constants_Variables {
	
private final int MAX_STONES;
private final int MAX_CAP_INV;
private final int MAX_SECONDS_PER_TURN;
private final int MAX_SECONDS_GAME;
private final int MAX_STONES_PER_PLAYER;

public Constants_Variables() {
	MAX_STONES = 56;
	MAX_CAP_INV = 20;
	MAX_SECONDS_PER_TURN = 10;
	MAX_SECONDS_GAME  = 1800;
	MAX_STONES_PER_PLAYER = 13;
}


public int getMaxStones() {
	return this.MAX_STONES;
}
public int getMaxInvSpace() {
	return this.MAX_CAP_INV;
}
public int getSecondsPerTurn() {
	return this.MAX_SECONDS_PER_TURN;
}

public int getMaxSecondsGame() 
{
	return this.MAX_SECONDS_GAME;
}
public int getMaxStonesPerPlayer() 
{
	return this.MAX_STONES_PER_PLAYER;
}
}
