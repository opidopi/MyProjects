//********************************************************************
//  nvzTester.java       Author: Sean Lantry
//

/*
	Description: This is the test driver for the current version of this
		game. It generates some basic test data and creates a frame and
		panel to hold the interface components.It then runs a loop constantly
		updating the display panels with the latest game info and used the
		buttons in the various interface panels to drive the game function.

Method
	main(String

Vars
	Deck
	AttCard
	DefCard
	ComboCard
	ZombieCard
//********************************************************************/

import java.io.*;
import java.util.Scanner;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class nvzTester
{
	//Main method
		//Generate game data
		//create interface panels
		//create frame and add panels
		//run game updates
	public static void main(String [] args)
	{
		//the next very long chunck of code is just the generation of
		//enough game data to make the game somewhat playable

		//card variable declarations
		AttCard c1, c2, c3, c7, c11, c12, c15, c16, c18;
		DefCard c4, c5, c14;
		ComboCard c6, c8, c9, c10, c13, c17;
		ZombieCard z1, z2, z3, z4, z5, z6;

		//list of all card types in the game
//---------------------------------------------------------------------------------------
		c1 = new AttCard("straight_punch.jpg", 1, 1,"Punch");
		c2 = new AttCard("straight_kick.jpg", 2, 1, "Kick");
		c3 = new AttCard("trip.jpg", 1, 1, "Grapple");
		c4 = new DefCard("light_block.jpg", 1, 1, "Block");
		c5 = new DefCard("sidestep.jpg", 2, 1, "Dodge");
		c6 = new ComboCard("flying.jpg", 1, 1, "Ariel");
		c7 = new AttCard("body_toss.jpg", 2, 1,"Grapple");
		c8 = new ComboCard("brutal.jpg", 4, 1, "Power");
		c9 = new ComboCard ("dark_hadou.jpg", 30, 1, "Special");
		c10 = new ComboCard ("dash.jpg", 2, 1, "Speed");
		c11 = new AttCard("dragon_kick.jpg", 9, 1,"Kick");
		c12 = new AttCard("falcon_punch.jpg", 40, 1,"Special");
		c13 = new ComboCard ("Flipping.jpg", 2, 1, "Ariel");
		c14 = new DefCard("heavy_block.jpg", 2, 1, "Block");
		c15 = new AttCard("metsu_shoryuken.jpg", 50, 1,"Special");
		c16 = new AttCard("raging_demon.jpg", 100, 1,"Special");
		c17 = new ComboCard ("Unstoppable.jpg", 5, 1, "Power");
		c18 = new AttCard("Uppercut.jpg", 3, 1,"Punch");
		z1 = new ZombieCard("drooler.jpg", 1, 2, "weak");
		z2 = new ZombieCard("crawler.jpg", 2, 4, "weak");
		z3 = new ZombieCard("thriller.jpg", 4, 8, "medium");
		z4 = new ZombieCard("runner.jpg", 5, 10, "medium");
		z5 = new ZombieCard("pirate.jpg", 10, 15, "Strong");
		z6 = new ZombieCard("zombiejesus.jpg", 100, 100, "Strong");
//---------------------------------------------------------------------------------------
		Deck d1 = new Deck();
		Deck d2 = new Deck();
		Deck zd1 = new Deck();

		//deck generation
//---------------------------------------------------------------------------------------
		for(int i = 0; i < 10; i++)
		{
			zd1.addCard(new ZombieCard(z1.getCardName(), z1.getzombieDMG(), z1.getzombieHP(), z1.getZombieType()));
			zd1.addCard(new ZombieCard(z2.getCardName(), z2.getzombieDMG(), z2.getzombieHP(), z2.getZombieType()));
		}
		for(int i = 0; i < 5; i++)
		{
			zd1.addCard(new ZombieCard(z3.getCardName(), z3.getzombieDMG(), z3.getzombieHP(), z3.getZombieType()));
			zd1.addCard(new ZombieCard(z4.getCardName(), z4.getzombieDMG(), z4.getzombieHP(), z4.getZombieType()));
		}
		zd1.addCard(new ZombieCard(z5.getCardName(), z5.getzombieDMG(), z5.getzombieHP(), z5.getZombieType()));
		zd1.addCard(new ZombieCard(z6.getCardName(), z6.getzombieDMG(), z6.getzombieHP(), z6.getZombieType()));

		for(int i = 0; i < 1; i++)
		{
			d1.addCard(new AttCard(c1.getCardName(), c1.getBaseDMG(), c1.getMultiplier(), c1.getAttackType()));
			d2.addCard(new AttCard(c1.getCardName(), c1.getBaseDMG(), c1.getMultiplier(), c1.getAttackType()));
		}
		for(int i = 0; i < 1; i++)
		{
			d1.addCard(new AttCard(c2.getCardName(), c2.getBaseDMG(), c2.getMultiplier(), c2.getAttackType()));
			d2.addCard(new AttCard(c2.getCardName(), c2.getBaseDMG(), c2.getMultiplier(), c2.getAttackType()));
		}
		for(int i = 0; i < 1; i++)
		{
			d1.addCard(new AttCard(c3.getCardName(), c3.getBaseDMG(), c3.getMultiplier(), c3.getAttackType()));
			d2.addCard(new AttCard(c3.getCardName(), c3.getBaseDMG(), c3.getMultiplier(), c3.getAttackType()));
		}
		for(int i = 0; i < 1; i++)
		{
			d1.addCard(new DefCard(c4.getCardName(), c4.getBaseDEF(), c4.getMultiplier(), c4.getDefenseType()));
			d2.addCard(new DefCard(c4.getCardName(), c4.getBaseDEF(), c4.getMultiplier(), c4.getDefenseType()));
		}
		for(int i = 0; i < 1; i++)
		{
			d1.addCard(new DefCard(c5.getCardName(), c5.getBaseDEF(), c5.getMultiplier(), c5.getDefenseType()));
			d2.addCard(new DefCard(c5.getCardName(), c5.getBaseDEF(), c5.getMultiplier(), c5.getDefenseType()));
		}
		for(int i = 0; i < 1; i++)
		{
			d1.addCard(new ComboCard(c6.getCardName(), c6.getBonus(), c6.getMultiplier(), c6.getComboType()));
			d2.addCard(new ComboCard(c6.getCardName(), c6.getBonus(), c6.getMultiplier(), c6.getComboType()));
		}
		for(int i = 0; i < 1; i++)
		{
			d1.addCard(new AttCard(c7.getCardName(), c7.getBaseDMG(), c7.getMultiplier(), c7.getAttackType()));
			d2.addCard(new AttCard(c7.getCardName(), c7.getBaseDMG(), c7.getMultiplier(), c7.getAttackType()));
		}
		for(int i = 0; i < 1; i++)
		{
			d1.addCard(new ComboCard(c8.getCardName(), c8.getBonus(), c8.getMultiplier(), c8.getComboType()));
			d2.addCard(new ComboCard(c8.getCardName(), c8.getBonus(), c8.getMultiplier(), c8.getComboType()));
		}
		for(int i = 0; i < 1; i++)
		{
			d1.addCard(new ComboCard(c9.getCardName(), c9.getBonus(), c9.getMultiplier(), c9.getComboType()));
			d2.addCard(new ComboCard(c9.getCardName(), c9.getBonus(), c9.getMultiplier(), c9.getComboType()));
		}
		for(int i = 0; i < 1; i++)
		{
			d1.addCard(new ComboCard(c10.getCardName(), c10.getBonus(), c10.getMultiplier(), c10.getComboType()));
			d2.addCard(new ComboCard(c10.getCardName(), c10.getBonus(), c10.getMultiplier(), c10.getComboType()));
		}
		for(int i = 0; i < 1; i++)
		{
			d1.addCard(new AttCard(c11.getCardName(), c11.getBaseDMG(), c11.getMultiplier(), c11.getAttackType()));
			d2.addCard(new AttCard(c11.getCardName(), c11.getBaseDMG(), c11.getMultiplier(), c11.getAttackType()));
		}
		for(int i = 0; i < 1; i++)
		{
			d1.addCard(new AttCard(c12.getCardName(), c12.getBaseDMG(), c12.getMultiplier(), c12.getAttackType()));
			d2.addCard(new AttCard(c12.getCardName(), c12.getBaseDMG(), c12.getMultiplier(), c12.getAttackType()));
		}
		for(int i = 0; i < 1; i++)
		{
			d1.addCard(new ComboCard(c13.getCardName(), c13.getBonus(), c13.getMultiplier(), c13.getComboType()));
			d2.addCard(new ComboCard(c13.getCardName(), c13.getBonus(), c13.getMultiplier(), c13.getComboType()));
		}
		for(int i = 0; i < 1; i++)
		{
			d1.addCard(new DefCard(c14.getCardName(), c14.getBaseDEF(), c14.getMultiplier(), c14.getDefenseType()));
			d2.addCard(new DefCard(c14.getCardName(), c14.getBaseDEF(), c14.getMultiplier(), c14.getDefenseType()));
		}
		for(int i = 0; i < 1; i++)
		{
			d1.addCard(new AttCard(c15.getCardName(), c15.getBaseDMG(), c15.getMultiplier(), c15.getAttackType()));
			d2.addCard(new AttCard(c15.getCardName(), c15.getBaseDMG(), c15.getMultiplier(), c15.getAttackType()));
		}
		for(int i = 0; i < 1; i++)
		{
			d1.addCard(new AttCard(c16.getCardName(), c16.getBaseDMG(), c16.getMultiplier(), c16.getAttackType()));
			d2.addCard(new AttCard(c16.getCardName(), c16.getBaseDMG(), c16.getMultiplier(), c16.getAttackType()));
		}
		for(int i = 0; i < 1; i++)
		{
			d1.addCard(new ComboCard(c17.getCardName(), c17.getBonus(), c17.getMultiplier(), c17.getComboType()));
			d2.addCard(new ComboCard(c17.getCardName(), c17.getBonus(), c17.getMultiplier(), c17.getComboType()));
		}
		for(int i = 0; i < 1; i++)
		{
			d1.addCard(new AttCard(c18.getCardName(), c18.getBaseDMG(), c18.getMultiplier(), c18.getAttackType()));
			d2.addCard(new AttCard(c18.getCardName(), c18.getBaseDMG(), c18.getMultiplier(), c18.getAttackType()));
		}
//---------------------------------------------------------------------------------------
	//end of game data generation

		//use new game data to create players and the game area
		NinjaPlayer ninja = new NinjaPlayer(d1, new Deck(), "Neenja");
		ZombiePlayer zombie = new ZombiePlayer(zd1, new Deck());
		GameArea game = new GameArea(ninja, zombie);

		//create interface objects
		JFrame frame = new JFrame ("NINJA");
      	frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
      	int input = game.endPhase();
      	HandPanel hand = new HandPanel(game);
      	StackPanel stack = new StackPanel(game);
      	ZombiePanel zPane = new ZombiePanel(game);
      	InfoPanel iPanel = new InfoPanel(game);
      	InfoPanel filler = new InfoPanel(game);
      	JPanel panel = new JPanel();
      	panel.setLayout (new BorderLayout());
      	panel.add(zPane, BorderLayout.NORTH);
      	panel.add(stack, BorderLayout.CENTER);
      	panel.add(hand, BorderLayout.SOUTH);
      	panel.add(iPanel, BorderLayout.WEST);
      	panel.add(filler, BorderLayout.EAST);
      	panel.setBackground (Color.green);
		frame.getContentPane().add (panel);

		frame.pack();
     	frame.setVisible(true);

     	//constantly update interface with newest game data
     	while(true)
     	{
			stack.resetStack();
			hand.resetHand();
			zPane.resetZombie();
			iPanel.resetInfo();
			filler.resetInfo();
		}

//this is the non-gui interface that was used for initial debugging of game data
/*
		System.out.println(game.endPhase());
		System.out.println("Zombies:\n" + game.getZombiePA().ZombieStack().toString());
		System.out.println("Stack:\n" + game.getNinjaPA().getStack().toString());
		System.out.println("Hand:\n" + ninja.getHand().toString());
		Scanner in = new Scanner(System.in);
		int input = 1;
		while(input != 0)
		{
			System.out.print("Choose a card number to  add to stack or 0 to stop:");
			input = in.nextInt();
			if (input > 0)
			{
				game.getNinjaPA().addToStack(input - 1);
				System.out.println("Zombies:\n" + game.getZombiePA().ZombieStack().toString());
				System.out.println("Stack:\n" + game.getNinjaPA().getStack().toString());
				System.out.println("Hand:\n" + ninja.getHand().toString());
			}
			else if (input == -1)

			{
				game.getNinjaPA().returnStack();
				System.out.println("Zombies:\n" + game.getZombiePA().ZombieStack().toString());
				System.out.println("Stack:\n" + game.getNinjaPA().getStack().toString());
				System.out.println("Hand:\n" + ninja.getHand().toString());
			}
			else
			{
				input = game.endPhase();
				System.out.println(input);
				if(input > 0)
					input = 0;
				else
				{
					System.out.println("Why U No Use Attack Cards?");
					System.out.println("Zombies:\n" + game.getZombiePA().ZombieStack().toString());
					System.out.println("Stack:\n" + game.getNinjaPA().getStack().toString());
					System.out.println("Hand:\n" + ninja.getHand().toString());
					input = 1;
				}
			}
		}
		input = 1;
		while(input != 0)
		{
			System.out.println("Zombies Attack for " + game.getZombiePA().attack() + "!\n Choose Your Cards to Defend With!");
			System.out.print("Choose a card number to  add to stack or 0 to stop:");
			input = in.nextInt();
			if (input > 0)
			{
				game.getNinjaPA().addToStack(input - 1);
				System.out.println("Zombies Attack for " + game.getZombiePA().attack() + "!\n Choose Your Cards to Defend With!");
				System.out.println("Zombies:\n" + game.getZombiePA().ZombieStack().toString());
				System.out.println("Stack:\n" + game.getNinjaPA().getStack().toString());
				System.out.println("Hand:\n" + ninja.getHand().toString());
			}
			else
			{
				input = game.endPhase();
				System.out.println(input);
				if(input > 0)
					input = 0;
				else
				{
					System.out.println("Why U No Use Defense Cards?");
					System.out.println("Zombies Attack for " + game.getZombiePA().attack() + "!\n Choose Your Cards to Defend With!");
					System.out.println("Zombies:\n" + game.getZombiePA().ZombieStack().toString());
					System.out.println("Stack:\n" + game.getNinjaPA().getStack().toString());
					System.out.println("Hand:\n" + ninja.getHand().toString());
					input = 1;
				}
			}
		}
*/
	}

}