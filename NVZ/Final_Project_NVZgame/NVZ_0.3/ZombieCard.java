//********************************************************************
//  ZombieCard.java       Author: Jerry Shin
//

/*Description
	Informational class that includes details relevent to zombie cards

	methods
		ZombieCard (String, int, int, String )
		getzombieDMG()
		getzombieHP()
		getZombieType()
		toString()

	variables
		int
		String
//********************************************************************/

public class ZombieCard extends Card
{
	// Attack Damge, multiplier and type of attack
	int zMultiplier, zombieHP, zombieDMG;
	String zombieType;

	//Constructor
//------------------------------------------------------------
	public ZombieCard (String name, int dmg, int hp, String inzombieType)
	{
		super(name, "Zombie");
		zombieHP = hp;
		zombieDMG = dmg;
		zombieType = inzombieType;
	}
//---------------------------------------------------------------

	//Once ceated, object is inmutable, you can only get data from it
	public int getzombieDMG()
	{
		return zombieDMG;
	}
	//Once ceated, object is inmutable, you can only get data from it
	public int getzombieHP()
	{
		return zombieHP;
	}
	//returns zombie type
	public String getZombieType()
	{
		return zombieType;
	}
	// returns type, zombie type, name, damage and multiplier
	public String toString()
	{
		return "Type: " + getZombieType() +  " Zombie Type: " + zombieType + " Name: " + getCardName() + " Damage: " + zombieDMG + " HP: " + zombieHP;
	}
}


