//********************************************************************
//  AttCard.java       Author: Jerry Shin
//							Sean Lantry

/*Description
	Informational class that includes details relevent to attack cards

Method
	AttCard (String, int, int, String)
	getBaseDMG
	getMultiplier
	getAttackType
	toString

Vars
	int
	String

//********************************************************************/

public class AttCard extends Card
{
	// Attack Damge, multiplier and type of attack
	int baseDMG, multiplier;
	String attackType;

	//Constructor
//------------------------------------------------------------
	public AttCard (String name, int dmg, int inMultiplier, String inattackType)
	{
		super(name, "Attack");
		baseDMG = dmg;
		multiplier = inMultiplier;
		attackType = inattackType;
	}
//---------------------------------------------------------------

	//Once ceated, object is inmutable, you can only get data from it
	public int getBaseDMG()
	{
		return baseDMG;
	}
	// returns the multipier
	public int getMultiplier()
	{
		return multiplier;
	}
	//returns attack type
	public String getAttackType()
	{
		return attackType;
	}
	// returns type, attack type, name, damage and multiplier
	public String toString()
	{
		return "Type: " + getCardType() +  " Attack Type: " + attackType + " Name: " + getCardName() + " Damage: " + baseDMG + " Multiplier: " + multiplier;
	}
}