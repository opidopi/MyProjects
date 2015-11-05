//********************************************************************
//  Player.java       Author: Sean Lantry
//
//	Dare: 12/17/2011

/*
	Description: This Class represents the area in which the ninja plays
		his cards. It includs the ninja player, a stack for cards in the
		process of being played, as well as attack and defense values.
		It also includes methods for moving cards between the player's
		decks and the stack as well as calculating attack and defense
		values
Method
	NinjaPlayArea(NinjaPlayer
	addToStack(int
	returnStack
	clearStack
	attack
	defend(int)
	calcAttack
	calcDefense
	getStackSize
	getStack
	getAttack
	getDefense

Vars
	NinjaPlayer
	Deck
	int

//********************************************************************/

public class NinjaPlayArea
{
	//the Ninja
	private NinjaPlayer ninja;
	//the Stack, where cards being played are stored
	private Deck ninjaStack;
	//ninja's attack and defense values (based on cards played)
	private int ninjaAttack, ninjaDefense;

//Constructor
//------------------------------------------------------------
	public NinjaPlayArea(NinjaPlayer nPlayer)
	{
		ninja = nPlayer;
		ninjaStack = new Deck();
		ninjaAttack = ninjaDefense = 0;
	}
//------------------------------------------------------------

	//takes a card of given index and moves it from the player's
	//hand to the stack
	public void addToStack(int index)
	{
		ninjaStack.addCard(ninja.getHand().removeCard(index));
	}

	//returns all the cards from the stack and returns them
	//to the player's hand
	public void returnStack()
	{
		int endCount = ninjaStack.getDeckSize();
		for(int i = 0; i < endCount; i++)
			ninja.getHand().addCard(ninjaStack.removeCard(0));
	}

	//clears the stack to the discard pile
	public void clearStack()
	{
		int endCount = ninjaStack.getDeckSize();
		for(int i = 0; i < endCount; i++)
			ninja.getDiscard().addCard(ninjaStack.removeCard(0));
	}

	//runs the attack calculation and decides
	//whether to clear or return stack as a result
	public int attack()
	{
		if(calcAttack() > 0)
			clearStack();
		else
			returnStack();
		return ninjaAttack;
	}

	//calculates defense and decides whether
	// to lose life from the damage received
	public int defend(int damage)
	{
		if(calcDefense() >= 0)
		{
			clearStack();
			if ((damage - ninjaDefense) > 0)
				ninja.loseLife(damage - ninjaDefense);
		}
		else
			returnStack();
		return ninjaDefense;
	}

	//analyzes cards in the stack and decides calculates
	//damage for correct card combinations, returns -1 for
	//incorrect combinations
	public int calcAttack()
	{
		ninjaAttack = 0;
		int attCount = 0;
		String cardType;
		Card tempCard;
		AttCard attBonus;
		ComboCard comboBonus;
		for (int i = 0; i < ninjaStack.getDeckSize(); i++)
		{
			tempCard = ninjaStack.getCard(i);
			cardType = tempCard.getCardType();
			if (cardType.equals("Defense"))
			{	ninjaAttack = -1;
				return -1;
			}
			if (cardType.equals("Attack"))
				attCount++;
		}
		if (attCount > 0)
		{
			for(int i = 0; i < ninjaStack.getDeckSize(); i++)
			{
				tempCard = ninjaStack.getCard(i);
				cardType = tempCard.getCardType();
				if(cardType.equals("Attack"))
				{
					attBonus = (AttCard)tempCard;
					ninjaAttack += attBonus.getBaseDMG();
					ninjaAttack *= attBonus.getMultiplier();
				}
				else
				{
					comboBonus = (ComboCard)tempCard;
					ninjaAttack += comboBonus.getBonus();
					ninjaAttack *= comboBonus.getMultiplier();
				}
			}
		}
		return ninjaAttack;
	}

	//analyzes cards in the stack and decides calculates
	//defense for correct card combinations, returns -1 for
	//incorrect combinations
	public int calcDefense()
	{
		ninjaDefense = 0;
		int defCount = 0;
		String cardType;
		Card tempCard;
		DefCard defBonus;
		ComboCard comboBonus;
		for (int i = 0; i < ninjaStack.getDeckSize(); i++)
		{
			tempCard = ninjaStack.getCard(i);
			cardType = tempCard.getCardType();
			if (cardType.equals("Attack"))
			{
				ninjaDefense =  -1;
				return -1;
			}
			if (cardType.equals("Defense"))
				defCount++;
		}
		if (defCount > 0)
		{
			for(int i = 0; i < ninjaStack.getDeckSize(); i++)
			{
				tempCard = ninjaStack.getCard(i);
				cardType = tempCard.getCardType();
				if(cardType.equals("Defense"))
				{
					defBonus = (DefCard)tempCard;
					ninjaDefense += defBonus.getBaseDEF();
					ninjaDefense *= defBonus.getMultiplier();
				}
				else
				{
					comboBonus = (ComboCard)tempCard;
					ninjaDefense += comboBonus.getBonus();
					ninjaDefense *= comboBonus.getMultiplier();
				}
			}
		}
		else if (ninjaStack.getDeckSize() > 0)
			ninjaDefense = -1;
		return ninjaDefense;
	}

	//returns number of cards on stack
	public int getStackSize()
	{
		return ninjaStack.getDeckSize();
	}

	//returns the stack
	public Deck getStack()
	{
		return ninjaStack;
	}

	//returns current attack value
	public int getAttack()
	{
		return ninjaAttack;
	}

	//return current defens value
	public int getDefense()
	{
		return ninjaDefense;
	}


}