
//********************************************************************
//  NinjaPlayer.java       Author: Sean Lantry
//
//	Dare: 12/17/2011

/*
	Description: This Class repressents the Ninja Player. It includes
		extra data unique to the Ninja player suche as life, lvl, name,
		etc. It also includes methods for accessing and altering this
		data.
Method
	NinjaPlayer(Deck, Deck, String)
	getLife
	getMaxLife
	loseLife(int)
	gainLife(int)
	setLife(int)
	incLvl
	incHandSize

Vars
	int
	String
	Deck

//********************************************************************/


public class NinjaPlayer extends Player
{
	//hit points as integers, both max HP and current
	//current level of ninja(not currently used)
	private int life, maxlife, lvl;
	//name of ninja
	private String name;
	//Starting discard deck
	//inventory and active item cards
	//items not currently used
	private Deck inventory, activeItems;


//Constructor
//------------------------------------------------------------
	public NinjaPlayer(Deck inDeck, Deck inInv, String inName)
	{
		super(inDeck, 5);
		life = 30;
		maxlife = 30;
		lvl = 1;
		name = inName;
		inventory = inInv;
		activeItems = new Deck();
	}

//------------------------------------------------------------

	//returns current health of ninja
	public int getLife()
	{
		return life;
	}

	//returns max life of ninja
	public int getMaxLife()
	{
		return maxlife;
	}

	//decrements life and returns true or
	//false for whether you are alive or dead
	public boolean loseLife(int amount)
	{
		life -= amount;
		if(life <= 0)
			return false;
		else
			return true;
	}

	//increases life by a certain amount up
	//to maximum health
	public void gainLife(int amount)
	{
		life += amount;
		if(life > maxlife)
			life = maxlife;
	}

	//sets current and max life at a certain amount
	public void setLife(int amount)
	{
		life = amount;
		maxlife = amount;
	}

	//increases ninja level
	//not currently used
	public void incLvl()
	{
		lvl++;
		incHandSize();
	}

	//incureases hand size at certain lvls
	//not currently used
	public void incHandSize()
	{
		switch(lvl)
		{
			case 2:
				super.incHandSize();
				break;
			case 5:
				super.incHandSize();
				break;
			case 8:
				super.incHandSize();
				break;
		}
	}

}