//********************************************************************
//  InfoPanel.java       Author: Sean Lantry
//

/*
	Description: This is the panel that displays extra game data such as
		the ninja's life, and the attack and defenses of the zombies

Method
	InfoPanel(GameArea)
	resetInfo

Vars
	GamesArea
	JLabel

//********************************************************************/


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class InfoPanel extends JPanel
{
	//game data and display labels
	private GameArea game;
	private JLabel hpLabel, phaseLabel, nextThreatLabel, nextZHPLabel, totalThreatLabel, totalHPLabel;

//constructor
//---------------------------------------------------------
	public InfoPanel(GameArea inGame)
	{
		//vars
		game = inGame;
		hpLabel = new JLabel("HP: " +Integer.toString(game.getNinja().getLife()));
		phaseLabel = new JLabel("Phase: Attack");
		nextThreatLabel = new JLabel("Next Threat: 0");
		nextZHPLabel = new JLabel("Next Zombie HP: 0");
		totalThreatLabel = new JLabel("Total Threat: 0");

		setLayout (new BoxLayout (this, BoxLayout.Y_AXIS));
		add(hpLabel);
		add(phaseLabel);
		add (Box.createRigidArea (new Dimension (0,50)));
		add (Box.createVerticalGlue());
		add(nextZHPLabel);
		add(nextThreatLabel);
		add(totalThreatLabel);
		setPreferredSize(new Dimension (150, 250));
		setBackground (Color.green);
	}

//---------------------------------------------------------

	//updates label text with current game info
	public void resetInfo()
	{
		hpLabel.setText("HP: " +Integer.toString(game.getNinja().getLife()));
		nextThreatLabel.setText("Next Threat: " + ((ZombieCard)game.getZombiePA().ZombieStack().getCard(0)).getzombieDMG());
		nextZHPLabel.setText("Next Zombie HP: " + ((ZombieCard)game.getZombiePA().ZombieStack().getCard(0)).getzombieHP());
		totalThreatLabel.setText("Total Threat: " + game.getZombiePA().attack());
		switch(game.getTurnStep())
		{
			case 1:
				phaseLabel.setText("Phase: Discard");
				break;
			case 2:
				phaseLabel.setText("Phase: Zombie Spawn");
				break;
			case 3:
				phaseLabel.setText("Phase: Attack");
				break;
			case 4:
				phaseLabel.setText("Phase: Defend");
				break;
		}
	}
}