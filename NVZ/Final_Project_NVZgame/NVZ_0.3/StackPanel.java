//********************************************************************
//  ZombiePanel.java       Author: Sean Lantry
//

/*
	Description: This is the panel that displays the stack of active
		cards waiting to be played.


Method
	StackPanel(GameArea
	resetStack
	ButtonListener
	paintComponent

Vars
	JLabel
	ImageIcon
	JPanel
	ImageIcon
	Card
	GameArea
	JButton
	ButtonListener



//********************************************************************/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StackPanel extends JPanel
{

	//these are the labels, images and the panel for storing the cards
	private JLabel[] card;
	private ImageIcon[] cardPic;
	private JPanel cardPanel;
	private ImageIcon bg;

	//objects for interacting with the game, buttons, the game object
	private Card tempCard;
	private GameArea game;
	private JButton clear, done;
	private ButtonListener listener;

//constructor
//---------------------------------------------------------
	public StackPanel(GameArea inGame)
	{
		game = inGame;
		card = new JLabel[game.getNinja().getHandSize()];
		cardPic = new ImageIcon[game.getNinja().getHandSize()];
		bg = new ImageIcon("HandBG.jpg");
		cardPanel = new JPanel();
		clear = new JButton("Clear");
		done = new JButton("Done");
		listener = new ButtonListener();
		clear.addActionListener(listener);
		done.addActionListener(listener);

		for(int i = 0; i < game.getNinja().getHandSize(); i++)
		{
			tempCard = game.getNinja().getHand().getCard(i);
			cardPic[i] = new ImageIcon(tempCard.getCardName());
			card[i] = new JLabel(cardPic[i]);
			cardPanel.add(card[i]);
		}
		add(cardPanel);
		add(clear);
		add(done);
		setPreferredSize(new Dimension (900, 250));
		setBackground (Color.green);
	}
//---------------------------------------------------------

	//resets panel with current game data
	public void resetStack()
	{
		for (int i = 0; i < game.getNinjaPA().getStackSize(); i++)
		{
			tempCard = game.getNinjaPA().getStack().getCard(i);
			cardPic[i] = new ImageIcon(tempCard.getCardName());
			card[i].setIcon(cardPic[i]);
			card[i].setVisible(true);
			card[i].setEnabled(true);
		}
		for (int i = game.getNinjaPA().getStackSize(); i < game.getNinja().getHandSize(); i++)
		{
			card[i].setVisible(false);
			card[i].setEnabled(false);
		}
		repaint();
	}

	//clears the stack or returns it to the players hand when
	//appropriate button is pushed
	private class ButtonListener implements ActionListener
	{

		public void actionPerformed (ActionEvent event)
		{
			if (clear == event.getSource())
				game.getNinjaPA().returnStack();
			else
			{
				int errorcode = game.endPhase();
			}
			resetStack();
		}
	}

	//paints backround over old data
	public void paintComponent(Graphics g)
	{
		g.drawImage(bg.getImage(), 0, 0, null);
	}

}
