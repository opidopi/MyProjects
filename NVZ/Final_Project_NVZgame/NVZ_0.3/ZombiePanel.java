//********************************************************************
//  ZombiePanel.java       Author: Jerry Shin
//

/*
	Description: This is the panel that displays the stack of active
	zombies.

		Method
			ZombiePanel(GameArea)
			resetHand



		Variables
			JLabel[]
			ImageIcon[]
			JPanel
			ImageIcon
			Card
			GameArea
//********************************************************************/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ZombiePanel extends JPanel
{

	//Panels, images and labels for display
	private JLabel[] card;
	private ImageIcon[] cardPic;
	private JPanel cardPanel;
	private ImageIcon bg;
	private Card tempCard;
	private GameArea game;

//constructor
//---------------------------------------------------------
	public ZombiePanel(GameArea inGame)
	{
		game = inGame;
		card = new JLabel[5];
		cardPic = new ImageIcon[5];
		bg = new ImageIcon("HandBG.jpg");
		cardPanel = new JPanel();

		for(int i = 0; i < 5; i++)
		{
			cardPic[i] = new ImageIcon();
			card[i] = new JLabel();
			cardPanel.add(card[i]);
		}
		add(cardPanel);
		setPreferredSize(new Dimension (900, 250));
		setBackground (Color.green);
	}
//---------------------------------------------------------

	//resets display based on current game data
	public void resetZombie()
	{
		for (int i = 0; i < game.getZombiePA().ZombieStack().getDeckSize() && i < 5; i++)
		{
			tempCard = game.getZombiePA().ZombieStack().getCard(i);
			cardPic[i] = new ImageIcon(tempCard.getCardName());
			card[i].setIcon(cardPic[i]);
			card[i].setVisible(true);
			card[i].setEnabled(true);
		}
		for (int i = game.getZombiePA().ZombieStack().getDeckSize(); i < 5; i++)
		{
			card[i].setVisible(false);
			card[i].setEnabled(false);
		}
		repaint();
	}

	//paints background over old data
	public void paintComponent(Graphics g)
	{
		g.drawImage(bg.getImage(), 150, 0, null);
		g.setColor(Color.green);
		g.fillRect(1050, 0, 150, 250);
	}

}
