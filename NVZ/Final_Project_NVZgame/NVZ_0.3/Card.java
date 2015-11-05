//********************************************************************
//  Card.java       Author: Jerry Shin
//							Sean Lantry

/*Description
	This class is an informational class, it stores information
	important to all cards, regardless of the type of card
Method
	Card
	Card (String, String)
	getCardType
	getCardName
	toString

Vars
	String

//********************************************************************/

public class Card
{

	//Card Type and Name
	private String cardName, cardType;

//Constructors
//---------------------------------------------
	 //Create Blank Card
	public Card ()
	{
		cardName = "blank";
		cardType = "blank";
	}
	// Create Card with inputted Data
	public Card (String name, String type)
	{
		cardName = name;
		cardType = type;
	}
//------------------------------------------------

	// gets card type
	public String getCardType()
	{
		return cardType;
	}
	// gets card name
	public String getCardName()
	{
		return cardName;
	}
	//card toString method, returns name and type
	public String toString()
	{
		return "Type: " + cardType + " Name: " + cardName;
	}
}