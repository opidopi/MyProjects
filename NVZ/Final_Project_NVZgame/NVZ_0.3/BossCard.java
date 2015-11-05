//********************************************************************
//  BossCard.java       Author: Jerry Shin
//
/*Description
	Informational class that includes details relevent to zombie cards
Method
	BossCard (String, int, int)
	getTempHP
	toString

Vars
	int

//********************************************************************/

public class BossCard extends ZombieCard
{
	int tempHP;

	//Constructor
//------------------------------------------------------------
	public BossCard (String name, int dmg, int hp)
	{
		super(name, dmg, hp, "boss");
		tempHP = hp;

	}
//---------------------------------------------------------------

	//Boss Temp HP
	public int getTempHP()
	{
		return tempHP;
	}
	// returns type, attack type, name, damage and multiplier
	public String toString()
	{
		return "Type: " + getZombieType() +  " Attack Type: " + zombieType + " Name: " + getCardName() + " Damage: " + zombieDMG ;
	}
}


