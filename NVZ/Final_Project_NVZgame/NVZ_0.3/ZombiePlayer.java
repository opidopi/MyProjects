//********************************************************************
//  ZombiePanel.java       Author: Jerry Shin
//

/*
	Description: This Class includes information relevent to the Zombie
		Player including the level and decks. It also determines whether
		the boss card is in play (not used in current version)
/*
methods
	ZombiePlayer (Deck, Deck)
	incLvl
	incHandSize
	bossTrigger(ZombieCard)

Variables
	int
	Deck

*/
//********************************************************************/


public class ZombiePlayer extends Player
{
	//current level of zombie, affects hand size
	private int lvl;
	//boss deck to be used once boss is in play
	//not currently used
	private Deck bossDeck;

	//constructor
//---------------------------------------------------------
	public ZombiePlayer (Deck inDeck, Deck inBossDeck)
	{
		super(inDeck, 1);
		lvl = 1;
		bossDeck = discardDeck = inBossDeck;
	}
//---------------------------------------------------------

	//increases lvl
	public void incLvl()
	{
		lvl++;
		incHandSize();
	}

	//increases hand size based on lvl
	public void incHandSize()
	{
		switch (lvl)
		{
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				super.incHandSize();
				break;

		}
	}

	//checks for the boss card and switches decks if boss is in play
	//not currently used
	public void bossTrigger(ZombieCard boss)
	{
		if(boss.getZombieType().equalsIgnoreCase("boss"))
		{
			drawDeck = bossDeck;
		}

	}
}