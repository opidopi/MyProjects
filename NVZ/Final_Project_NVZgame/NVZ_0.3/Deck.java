//********************************************************************
//  Deck.java       Author: Jerry Shin
//							Sean Lantry
//  Dare: 12/17/2011
/*
	Desctiption: Helper class that includes data relevent to a deck
		of cards. Includes methods for adding and removing cards from
		the deck as well as shuffling.
Method
	Deck
	getCard(int)
	getDeck
	Card[] getDeck()
	getDeckSize
	addCard(Card)
	eraseCard(int)
	removeCard(int)
	shuffle
	increaseSize
 	toString

Vars
	int
	String
	Card

//********************************************************************/

public class Deck
{
	//Max number of cards before expansion
	private int maxDeckSize = 10;
	//Array of cards that represent the deck
	private Card[] playerDeck;
	//current number of cards in the deck
	private int decksize;

	//Constructor
//------------------------------------------------------------
	public Deck()
	{
		playerDeck = new Card[maxDeckSize];
		decksize = 0;
	}
//------------------------------------------------------------

	//methods for getting object data
	public Card getCard(int cardIndex)
	{
		return playerDeck[cardIndex];
	}

	public Card[] getDeck()
	{
		return playerDeck;
	}

	public int getDeckSize()
	{
		return decksize;
	}

	//adds passed card to end of Deck
	public void addCard(Card newCard)
	{
		if (decksize < maxDeckSize)
		{
			playerDeck[decksize] = newCard;
			decksize++;
		}
		else
		{
			increaseSize();
			addCard(newCard);
		}
	}

	//removes specified card from deck and moves up remaing
	//cards to fill in missing place
	public void eraseCard(int cardIndex)
	{
		if(decksize > 0)
		{
			if(cardIndex < decksize - 1)
			{
				Card[] tempDeck = new Card[decksize - cardIndex];
				for (int i = 0; i < (decksize - cardIndex); i++)
				{
					tempDeck[i] = playerDeck[i + cardIndex];
				}
				for (int i = 1; i <= (decksize - cardIndex); i++)
				{
					playerDeck[(i - 1) + cardIndex] = tempDeck[i];
				}
			}
			decksize--;
		}
	}

	//performs same functions as erase, but returns removed card
	//and returns it for use elsewhere
	public Card removeCard(int cardIndex)
	{
		if (decksize > 0)
		{
			Card tempCard = playerDeck[cardIndex];
			if(cardIndex < decksize - 1)
			{
				Card[] tempDeck = new Card[decksize - cardIndex];
				for (int i = 0; i < (decksize - cardIndex); i++)
				{
					tempDeck[i] = playerDeck[i + cardIndex];
				}
				for (int i = 1; i < (decksize - cardIndex); i++)
				{
					playerDeck[(i - 1) + cardIndex] = tempDeck[i];
				}
			}
			decksize--;
			return tempCard;
		}
		else
			return new Card();
	}

	//shuffles deck by removing a random card and placing it on bottom of deck
	public void shuffle()
	{
		for (int i = 1; i < (decksize * 5); i++)
		{
			addCard(removeCard((int)(Math.random() * decksize)));
		}
	}

	//allows the deck of cards to grow with the
	//addition of cards.
	private void increaseSize()
	{
		Card[] tempDeck = playerDeck;
		maxDeckSize = maxDeckSize + 10;
		playerDeck = new Card[maxDeckSize];
		for( int i = 0; i < decksize; i++)
			playerDeck[i] = tempDeck[i];
	}

	public String toString()
	{
		String retValue = "";
		for (int i = 0; i < decksize; i++)
			retValue += playerDeck[i].getCardName() + " ";
		return retValue;

	}


}