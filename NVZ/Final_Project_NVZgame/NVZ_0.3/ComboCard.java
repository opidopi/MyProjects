//********************************************************************
//  ComboCard.java			Author: Jerry Shin
//									Sean Lantry
/*Description
	Informational class that includes details relevent to combo cards
Method
	ComboCard (String, int, int, String)
	getBonus
	getMultiplier
 	getComboType
 	toString

Vars
	int
	String

//********************************************************************/

public class ComboCard extends Card
{

	int bonus, multiplier;
	String comboType;

	//Constructor
//------------------------------------------------------------
	public ComboCard (String name, int inbonus, int inMultiplier, String incomboType)
	{
		super(name, "Combo");
		bonus = inbonus;
		comboType = incomboType;
		multiplier = inMultiplier;

	}
//------------------------------------------------------------

	//Once ceated, object is inmutable, you can only get data from it

	//returns the flat bonus of the combo
	public int getBonus()
	{
		return bonus;
	}
	//returns the multiplier of the combo
	public int getMultiplier()
	{
		return multiplier;
	}
	//returns the type of combo
	public String getComboType()
	{
		return comboType;
	}
	// returns type, combo type, name, bonus and multiplier
	public String toString()
	{
		return "Type: " + getCardType() +  " Combo Type: " + comboType + " Name: " + getCardName() + " Combo Bonus: " + bonus + " Multiplier: " + multiplier;
	}

}