/*
Title: Final Programming Assignment 
Author: Sean Lantry 
Date: 5/19/2012
Course & Section: CSC 221-002W
Description:  This is the canvas for my game. This game is Ninjas vs. Zombies.
*               You will play as a ninja that throws Shurikens(throwing stars) at
*               zombies to kill them. If the zombe touches the ninja, the ninja
*               will die. The object of the game is to get the highest score possible.
*               Each zombie is worth 100pts. If you die, you can restart from the
*               Beginning by just pressing Enter. This game generates random "rooms"
*               in which everything take place. The first room starts with a horde
*               of 5 zombies. These zombies spawn at regular intervals and must all
*               be killed before advancing to the next room. To advance to the next
*               room, simply walk the ninja to the doorway on the right side of the
*               screen after killing all zombies in the Horde. Once a new room is
*               entered, a new horde will created, larger than the last horde. The
*               zombies will also spawn quicker as the game progresses. The delay
*               between spawns is based on the size of the zombie horde. Also, a
*               new background will be generated for each new room to help show that
*               the ninja is moving from room to room in this zombie ridden castle
*               or whatever it is. As I said, the object is to get high score.
*               Theoretically, the game will continue until it crashes the program,
*               but by that point I believe the game should be too dificult for
*               any person to still be playing it. Also, I modified the collision
*               rectangles to that the zombie must be hit in the torso, and collisions
*               for killing a ninja are ignored for the ninja's and zombies legs.
* 
*           Design flaws:
*               Since I rushed right into coding, without much planning, there are
*                   some design flaws I would like to point out. I have noticed these,
*                   but would have to redesign many of my logical processes to fix them.
*                   All of these flaws are animation based, as this is my first course
*                   (first course I finished anyway)that uses animation. Since the
*                   Zombies spawn from closest to the user down, dead zombies appear
*                   ontop of living ones in some cases. It is barely noticable, as
*                   the dead zombie is just a blob on the ground, but it is there.
*                   Also, since the ninja is always closest to the user, the ninja
*                   will appear on top of zombies that it should be behind. I should
*                   have based all layering on the Y positions of the animations,
*                   but did not think of that at the time. I noticed the flaw after
*                   most of the project was complete and I was debugging. If it is
*                   a major issue, I can rewrite the program. Also, the bitmaps I
*                   found from the same site as my other programs, unfortunately
*                   most of them are two or three frame animations only, so the
*                   animation could be smoother. This is the reason for the delays
*                   between frames, to smooth out the animations so they don't occur too fast.
* 
*           Playing the Game:
*               You move the ninja with the arrow keys. There are 5 possible directions,
*                   up, down, left, right and standing still. The last direction is
*                   only active if no keys are pressed
*               You shoot using the fire button, which I have found is the Enter Key
*               You shoot right by default. The only way to shoot left is to shoot
*                   while moving left.
*               You advance to the next room by moving the ninja to the doorway
*               You can restart a game by pressing the fire key after you die
*               Zombies cannot hurt you when spawning. If one stars spawning next
*                   to you, get out of the way Quick!
Data Requirements:
Formulas:
Refined Algorithm:
*   update
*   CHECK for Input
*   UPDATE Background
*   UPDATE Ninja
*   UPDATE Horde
*   CHECK for cleared room
*   CHECK for dead ninja
 */
import java.util.Random;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;

public class Lantry_FinalCanvas extends GameCanvas implements Runnable
{
    // Declare our screen size so we can reference it throughout the game
    public final int SCREEN_WIDTH  = 240;
    public final int SCREEN_HEIGHT = 320;
    
    // the game boundary
    public final int GAME_WIDTH = 165;
    public final int GAME_HEIGHT = 170;

    // the shifted x,y origin of the game
    public final int GAME_ORIGIN_X = (getWidth() - GAME_WIDTH)/2;
    public final int GAME_ORIGIN_Y = (getHeight() - GAME_HEIGHT)/2;
    
    //upper Y limit for ninja and zombies
    public final int UPPER_Y_LIMIT = GAME_ORIGIN_Y + 105;
    
    private boolean win;    //tells if a room is cleared
    private boolean lose;   //ends the game
    private boolean dead;   //tells if the ninja is dead
    
    private Display mDisplay; // hold the display object from the MIDlet
    private Font mGameFont; // hold the font that we will use for our game
    
    public LayerManager manager;            //create a layer for each bitmap image
    public BackgroundManager background;    //manages backgrounds and bg animations
    public NinjaManager ninja;              //manages the ninja
    public HordeManager horde;
    public Random random;                   //random number generator
    
    // This constructor recieves the display so we can set it later in our thread
    public Lantry_FinalCanvas(Display d)
    {
    super(true); // call base class constructor
    mDisplay = d; // store display object
    }//end constructor
    
    public LayerManager getManager()
    {//this allows any bitmap manager to create a layer
        return this.manager;
    }//end get manager
    
    public Random getRandom()
    //returns a random number
    //we put it in the canvas class so any bitmap manager can request a random number from it
    {
        return this.random;
    }//end get random
    
    private void checkInput()
    {//check to see which key is pressed
        int keyState = getKeyStates();
        if ((keyState & UP_PRESSED) != 0)
        {
            //move the shape north
            ninja.moveUp();

        }//end if

        if ((keyState & DOWN_PRESSED) != 0)
        {
            //move the shape south
            ninja.moveDown();
        }//end if
        if ((keyState & LEFT_PRESSED) != 0)
        {
            //move the shape west
            ninja.moveLeft();
        }//end if
        if ((keyState & RIGHT_PRESSED) != 0)
        {
            //move the shape east
            ninja.moveRight();
        }//end if
        if ((keyState & FIRE_PRESSED) != 0)
        {
            if(!dead)
                //fire the shot
                ninja.fire();
            else
                GameInitialize();
        }//end if
    }//end check Input
    
    public void checkWin()
    {//check if the room is cleared
        if(win)
            //checks if the ninja has exited the room if the room is cleared
            checkExit();
        if(horde.getCurrentSize() == horde.getSize() && horde.getCurrentSize() != 0)
        {//only check for a win if all zombies in the horde have spawned
            int i = 0;
            boolean dead = true;
            while(i < horde.getSize() && dead)
            {//check each zombe to see if it is dead
                dead = horde.checkDeath(i + horde.getOffset());
                i++;
            }//end while
            if(dead)
            {//if all zombies are dead, set win to true and check for an exit
                win = true;
                checkExit();
            }//end if
        }//end if
    }//end check win
    
    public void checkExit()
    {//checks if the ninja is in the doorway to move to the next room
        if(ninja.getX() > GAME_ORIGIN_X + GAME_WIDTH - ninja.bitmapWidth - 10)
        {//checks if the ninja's x is pas the x value of the door
            if(ninja.getY() < UPPER_Y_LIMIT - ninja.bitmapHeight/2)
            {//checks if the ninja's Y value is inside the range of the door
                while(manager.getSize() > 1)
                {//remove all layers but the background
                    manager.remove(manager.getLayerAt(0));
                }//end while
                //reset all managers for a new room
                background.newBackground();
                ninja.resetNinja();
                horde.newHorde();
                win = false;
            }//end if
        }//end if
    }//end check exit
    
    private void GameInitialize()
    {
        // set to fullscreen to use the entire display or false to only use
        //  the area inbetween the two bars on the display
        setFullScreenMode(true);

        //instantiate the random number generator
        random = new Random();
        
        // create a game font
        mGameFont = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
        
        // Initialize the Layer Manager
        manager = new LayerManager();
        
        // Initialize the Background Manager
        background = new BackgroundManager(this);
        background.newBackground();
        
        // Initialize the Ninja Manager
        ninja = new NinjaManager(this);
        // Initializes the Horde Manager
        horde = new HordeManager(this);
        // Initialize the win and lose variables
        win = lose = false;
    }//end GameInitialize
    
    private void gameUpdate()
    {
        //check for input
        checkInput();
        //update the background
        background.update();
        //update the ninja
        ninja.update();
        //update the zombie horde
        horde.update();
        //check for win
        checkWin();
        dead = ninja.ninjaDeath();
    }//end gameUpdate
    
    private void gameDraw(Graphics g)
    {
        // the next two lines clear the background
        g.setColor(0xffffff);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        manager.paint(g, 0, 0);
        g.setColor(0xffffff);
        g.drawString("Score: " + ninja.getKills()*100, GAME_ORIGIN_X,
                GAME_ORIGIN_Y, Graphics.TOP | Graphics.LEFT);
        //draw a message on the screen telling the player to enter the door
        //if the room is cleared
        if(win)
            g.drawString("Enter the Door", GAME_ORIGIN_X + GAME_WIDTH/10,
                GAME_ORIGIN_Y + GAME_HEIGHT/10, Graphics.TOP | Graphics.LEFT);
        //displays game over message if ninja is dead
        if(dead)
        {
            g.drawString("GAME OVER", GAME_ORIGIN_X + GAME_WIDTH/5,
                GAME_ORIGIN_Y + GAME_HEIGHT/4, Graphics.TOP | Graphics.LEFT);
            g.drawString("Fire to Restart", GAME_ORIGIN_X + GAME_WIDTH/6,
                GAME_ORIGIN_Y + GAME_HEIGHT/3, Graphics.TOP | Graphics.LEFT);
        }
        // Flush the offscreen graphics buffer
        flushGraphics();
    }//end gameDraw
    
    public void start()
    {
        // Set the canvas as the current phone's screen
        mDisplay.setCurrent(this);

        // we call our own initialize function to setup all game objects
        GameInitialize();

        // Here we setup the thread and start it
        Thread thread = new Thread(this);
        thread.start();
    }//end start

    public void run()
    {
        // get the instance of the graphics object
        Graphics g = getGraphics();

        // The main game loop
        while(!lose)
        {
                gameUpdate(); // update game
                gameDraw(g); // draw game
                try
                {
                    Thread.sleep(33); // sleep to sync the framerate on all devices
                }
                catch (InterruptedException ie)
                {}
        }//end while
    }//end run
}// end class
