//********************************************************************
//  GameArea.java       Author: Sean Lantry
//

/*
	Description: This Class brings together the entire game. It includes
		play areas for both the ninja and zombie players and the players
		themselves. It controls the flow of the game and performs all game
		actions necessary through the turn sequence. This is the Class that
		is controlled by the interface and acts as the main control of the
		game.

Method
	GameArea(NinjaPlayer inNinja, ZombiePlayer inZombie)
	getTurnStep
	getWinLoss
	getNinja
	getZombie
	getNinjaPA
	getZombiePA
	endPhase

Vars
	ZombiePlayer
	ZombiePlayArea
	NinjaPlayer
	NinjaPlayArea


//********************************************************************/

public class GameArea
{
	//Zombie player and play area
	private ZombiePlayer zombie;
	private ZombiePlayArea zombiePA;
	//Ninja Player and play area
	private NinjaPlayer ninja;
	private NinjaPlayArea ninjaPA;
	//keep track of turn phase, number of turns, win loss and boss cards
	private int turnStep, turnNum, winLoss, bossFlag;

//constructor
//---------------------------------------------------------
	public GameArea(NinjaPlayer inNinja, ZombiePlayer inZombie)
	{
		ninja = inNinja;
		zombie = inZombie;
		ninjaPA = new NinjaPlayArea(ninja);
		zombiePA = new ZombiePlayArea(zombie);
		turnStep = 1;
		turnNum = 1;
		winLoss = 0;
		bossFlag = 0;
	}
//---------------------------------------------------------

	//returns the current turn phase
	public int getTurnStep()
	{
		return turnStep;
	}

	//returns the win loss value
	public int getWinLoss()
	{
		return winLoss;
	}

	//returns the ninja player
	public NinjaPlayer getNinja()
	{
		return ninja;
	}

	//returns the zombie player
	public ZombiePlayer getZombie()
	{
		return zombie;
	}

	//returns the ninja play area
	public NinjaPlayArea getNinjaPA()
	{
		return ninjaPA;
	}

	//returns the zombie play area
	public ZombiePlayArea getZombiePA()
	{
		return zombiePA;
	}

	//this method cycles through the turn phases
	//it calls on the methods of its helper objecst
	//to execute the functions of the game in the
	//appropriate order
	//returns -1 for errors
	public int endPhase()
	{
		int retNum = -1;
		switch (turnStep)
		{
			case 1:
				ninjaPA.clearStack();
				ninja.drawCards();
				turnStep++;
				return endPhase();
			case 2:
				if(turnNum % 10 == 0)
					zombie.incLvl();
				zombie.drawCards();
				zombiePA.playZombie();
				turnStep++;
				return 1;
			case 3:
				if(ninjaPA.getStackSize() == 0)
				{
					turnStep++;
					return 1;
				}
				retNum = ninjaPA.attack();
				if (retNum > 0 )
				{
					zombiePA.zombieKill(retNum);
					turnStep++;
				}
				return retNum;
			case 4:
				retNum = ninjaPA.defend(zombiePA.attack());
				if (retNum >= 0)
				{
					turnNum++;
					turnStep = 1;
					return 1;
				}
				else
					return retNum;
		}
		return retNum;
	}
}