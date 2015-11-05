//********************************************************************
//  Player.java       Author: Sean Lantry
//

/*
	Description: This Class repressents the player, either the Zombie
		or the Ninja Player. It is never actually instantiated, only
		extended. This class includes 3 Decks to represent a player's
		Draw deck, their hand and their discard pile. It includes methods
		for moving cards between these decks.

Method
	Player(Deck, int)
	drawCards
	discard(int
	useCard(int
	getDiscard
	getHand
	getHandSize
	incHandSize
	getCurrentSize

Vars
	int
	Deck
	Card
//********************************************************************/

public class Player
{
	//Player's max hand size
	private int handSize;
	//Drawdeck, hand and discard pile
	protected Deck drawDeck, handDeck, discardDeck;


	//Constructor
//------------------------------------------------------------
	public Player(Deck inputDeck, int inSize)
	{
		drawDeck = inputDeck;
		handDeck = new Deck();
		discardDeck = new Deck();
		drawDeck.shuffle();
		handSize = inSize;
	}
//------------------------------------------------------------

	//draws cards from the draw deck and fills the player's
	//hand to the maximum hand size.
	//If the drawdeck is empty then reshuffle the discard pile
	//into the draw deck
	public void drawCards()
	{
		for (int i = handDeck.getDeckSize(); i < handSize; i++)
		{
			if (drawDeck.getDeckSize() > 0)
				handDeck.addCard(drawDeck.removeCard(0));
			else
			{
				drawDeck = discardDeck;
				discardDeck = new Deck();
				drawDeck.shuffle();
				handDeck.addCard(drawDeck.removeCard(0));
			}
		}
	}

	//discards a group of cards from the players
	//hand to the discard pile
	//not used in current version of game
	public void discard(int[] index)
	{
		if(handDeck.getDeckSize() > 0)
		{
			for(int i = 0; i < index.length; i++)
			{
				discardDeck.addCard(handDeck.removeCard(index[i]));
				for (int j = i; j < index.length; j++)
					index[j]--;
			}
		}
	}

	//Removes a card from the hand and places it
	//in the discard pile but returns it to be used
	//as an alias
	//not used in current version of game
	public Card useCard(int index)
	{
		Card tempCard = handDeck.getCard(index);
		discardDeck.addCard(handDeck.removeCard(index));
		return tempCard;
	}

	//returns the discard pile to be used
	public Deck getDiscard()
	{
		return discardDeck;
	}

	//returns the hand to be used
	public Deck getHand()
	{
		return handDeck;
	}

	//returns the hand to be used
	public int getHandSize()
	{
		return handSize;
	}

	//increases max hand size
	public void incHandSize()
	{
		handSize++;
	}

	//returns current hand size
	public int getCurrentSize()
	{
		return handDeck.getDeckSize();
	}

}