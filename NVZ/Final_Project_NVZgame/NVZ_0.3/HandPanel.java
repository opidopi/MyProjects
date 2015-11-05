//********************************************************************
//  HandPanel.java       Author: Sean Lantry
//

/*
	Description: This is the panel that displays the ninja's hand
Method
	HandPanel(GameArea)
	resetHand
	ButtonListener
	paintComponent(Graphics)

Vars
	JButton
	ImageIcon
	JPanel
	ImageIcon
	Card
	GameArea
	ButtonListener


//********************************************************************/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HandPanel extends JPanel
{

	//these are the labels, images and the panel for storing the cards
	private JButton[] card;
	private ImageIcon[] cardPic;
	private JPanel cardPanel;
	private ImageIcon bg;
	//objects for interacting with the game, buttons, the game object
	private Card tempCard;
	private GameArea game;
	private ButtonListener listener;

//constructor
//---------------------------------------------------------
	public HandPanel(GameArea inGame)
	{
		game = inGame;
		card = new JButton[game.getNinja().getHandSize()];
		cardPic = new ImageIcon[game.getNinja().getHandSize()];
		bg = new ImageIcon("HandBG.jpg");
		cardPanel = new JPanel();
		listener = new ButtonListener();

		for(int i = 0; i < game.getNinja().getHandSize(); i++)
		{
			tempCard = game.getNinja().getHand().getCard(i);
			cardPic[i] = new ImageIcon(tempCard.getCardName());
			card[i] = new JButton(cardPic[i]);
			card[i].addActionListener(listener);
			cardPanel.add(card[i]);
		}
		add(cardPanel);
		setPreferredSize(new Dimension (900, 250));
		setBackground (Color.green);
		repaint();
	}
//---------------------------------------------------------

	//resets panel with current game data
	public void resetHand()
	{
		for (int i = 0; i < game.getNinja().getHand().getDeckSize(); i++)
		{
			tempCard = game.getNinja().getHand().getCard(i);
			cardPic[i] = new ImageIcon(tempCard.getCardName());
			card[i].setIcon(cardPic[i]);
			card[i].setVisible(true);
			card[i].setEnabled(true);
		}
		for (int i = game.getNinja().getHand().getDeckSize(); i < game.getNinja().getHandSize(); i++)
		{
			card[i].setVisible(false);
			card[i].setEnabled(false);
		}
		repaint();
	}

	//places cards clicked on into the stack
	private class ButtonListener implements ActionListener
	{

		public void actionPerformed (ActionEvent event)
		{
			for (int i = 0; i < card.length; i++)
			{
				if (card[i] == event.getSource())
				{
					game.getNinjaPA().addToStack(i);
					i = card.length;
				}
			}
			resetHand();
		}
	}

	//paints backround over old data
	public void paintComponent(Graphics g)
	{
		g.drawImage(bg.getImage(), 150, 0, null);
		g.setColor(Color.green);
		g.fillRect(1050, 0, 150, 250);
	}

}
