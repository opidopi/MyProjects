//********************************************************************
//  DefCard.java       Author: Jerry Shin
//								Sean Lantry
/*Description
	Informational class that includes details relevent to defense cards
Method
	DefCard(String, int, int, String)
	getBaseDEF
	getMultiplier
	getDefenseType
	toString

Vars
	int
	String

//********************************************************************/

public class DefCard extends Card
{

	int baseDEF, multiplier;
	String defenseType;

	//Constructor
//------------------------------------------------------------
	public DefCard (String name, int def, int inMultiplier, String indefenseType)
	{
		super(name, "Defense");
		baseDEF = def;
		defenseType = indefenseType;
		multiplier = inMultiplier;
	}
//------------------------------------------------------------

	//Once ceated, object is inmutable, you can only get data from it
	//return defensive bonus
	public int getBaseDEF()
	{
		return baseDEF;
	}
	//return defensive multiplier
	public int getMultiplier()
	{
		return multiplier;
	}
	//return defense type
	public String getDefenseType()
	{
		return defenseType;
	}
	//return type, defense type, name, defensive bonus and multiplier
	public String toString()
	{
		return "Type: " + getCardType() +  " Defense Type: " + defenseType + " Name: " + getCardName() + " Defense: " + baseDEF + " Mulitplier: " + multiplier;
	}
}