//********************************************************************
//  ZombiePanel.java       Author: Jerry Shin
//

/*
	Description: This Class includes information relevent to the area the
	 Zombie player plays in. It includes the stack of active zombies and
	 methods to calculate their attacks as well as methods to calculate
	 their deaths and move cards between the stack and the player's decks

 		methods
			ZombiePlayArea(ZombiePlayer)
			attack
			ZombieStack
			zombieKill(int)
			playZombie

		Variables
			ZombiePlayer
			Deck
			int
			bossTrigger

//********************************************************************/

public class ZombiePlayArea
{
	private ZombiePlayer zombie;
	private Deck zombieStack;
	private int attack;
	boolean bossTrigger;
	// sets parameters for zombieplayarea
	public ZombiePlayArea(ZombiePlayer zPlayer)
	{
		zombie = zPlayer;
		zombieStack = new Deck();
		attack =  0;
		bossTrigger = false;
	}
	//calcs zombie damage
	public int attack()
	{
		attack = 0;
		int attCount = 0;
		ZombieCard tempCard;
		for(int i = 0; i < zombieStack.getDeckSize(); i++)
			{
				tempCard = (ZombieCard)zombieStack.getCard(i);
				attack = attack + tempCard.getzombieDMG();
			}
		return attack;
	}
	public Deck ZombieStack()
	{
		return zombieStack;
	}
	//calcs damage taken by zombie and removes from play area
	public void zombieKill(int damage)
	{
		// calcs dmg dealth removes card

		while (damage > 0)
		{
			// if there is no zombies to attack, damage will = to 0
			if(zombieStack.getDeckSize() == 0)
				{
				damage = 0;
				}
			else
			{
				ZombieCard tempCard = (ZombieCard)zombieStack.getCard(0);
				//take card from hte stack and moves to the discard stack
				if (damage >= tempCard.getzombieHP())
					{
						zombie.getDiscard().addCard ((ZombieCard)zombieStack.removeCard(0));
					}
				damage = damage - tempCard.getzombieHP();
			}
		}
	}
	//while zombieplayer has card in hand add to zombie stack
	public void playZombie ()
	{
		while (zombie.getHand().getDeckSize()>0)
		{
			zombie.bossTrigger((ZombieCard)zombie.getHand().getCard(0));
			zombieStack.addCard(zombie.getHand().removeCard(0));
		}
	}
}