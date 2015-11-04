/*
Title: Final Programming Assignment 
Author: Sean Lantry 
Date: 5/21/2012, 5/22/2012
Course & Section: CSC 221-002W
Description: This class defines the ninja and how it behaves. It also creates a 
*               Star manager to manage the throwing stars
Data Requirements:  Only the canvas and the layer manager are needed by this class
*                       all other data is pulled from one of those
Formulas:
Refined Algorithm:
*   update
*   MOVE the ninja
*   CHECK for collisions with walls and upper y limit
*   CHECK for collisions between stars and zombies
*   CHECK for collisions between ninja and zombies
*   UPDATE all animation values for the ninja
* 
*   Check Kills
*   CHECK for collision between stars and zombies
*   KILL all zombies that collided with stars
*   REMOVE all stars that collided with zombies
* 
*   Check Death
*   CHECK for collisions between ninja and zombies
*   IF(collision occured)
*       SET the ninja to dead
*       SET animation to death
*       UPDATE all speeds to 0 for all ainimations
*   END IF
* 
*   reset
*   RESET all ninja values to original except kills
 */
import java.io.IOException;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;


public class NinjaManager 
{
    private static final int IDLE    = 0;       //index for the idle animation
    private static final int RIGHT   = 1;       //index for moving right animation
    private static final int LEFT    = 2;       //index for moving left animation
    private static final int S_RIGHT = 3;       //index for firing right animation
    private static final int S_LEFT  = 4;       //index for firing left animation
    private static final int DEATH   = 5;       //index for death animation
    
    public final int bitmapWidth  = 27;         //width of one ninja
    public final int bitmapHeight = 34;         //height of one ninja
    public final int ANI_NUM      = 6;          //number of animations
    public final int DIR_NUM      = 5;          //number of possible directions
    public final int FIRE_DELAY   = 10;         //delay between firing stars
    
    private boolean dead;                       //tells if the ninja is dead
    
    private int x;                              //x position
    private int y;                              //y position
    private int speed;                          //speed of bitmap
    private int dir;                            //direction of bitmap
    private int animation;                      //current animation to be displayed
    private int fireDelayCount;                 //delay between firing stars
    private int killCount;                      //total zombies killed

    private int[] delaycount;                   //delay coung for each animation
    private int[] delay;                        //delay between frames for each animation
    
    private OneBitmap[] animateBitmap;          //instantiate class for one bitmap
    private StarManager myStars;                //manages throwing stars
    public Lantry_FinalCanvas parent;           //Enables us to access data in the Canvas
    // the parent canvas's layer manager
    private LayerManager manager;               //Enables us to be included in Layer management
    
    public NinjaManager(Lantry_FinalCanvas parent)
    {
        //make sure our ninja stars out alive
        dead = false;
        //create array of bitmaps, 1 for each animation
        animateBitmap = new OneBitmap[ANI_NUM];
        //initialize speed, direction, animation and counts
        speed = 3;
        dir = 0;
        animation = IDLE;
        fireDelayCount = 0;
        killCount = 0;
        //set up the CoupleCollisionCanvas as the parent to access data
        this.parent = parent;

        //set up Layer Manager in CoupleCollisionCanvas as parent Layer
        this.manager = parent.getManager();
        //starting y position
        y = parent.GAME_ORIGIN_Y + parent.GAME_HEIGHT - bitmapHeight;
        //starting x position
        x = parent.GAME_ORIGIN_X;
        //set up delays and delay counts
        int[] mDelay = {5,1,1,2,2,10};
        delay = mDelay;
        int[] mDelaycount = {0,0,0,0,0,0};
        delaycount = mDelaycount;
        try
        {
            // First load our tiled bitmap
            // Be sure to get your syntax correct here
            Image ninjaImg;
            //Initialize the bitmap with height & width of one tile
            //include where to start
            //point of view will be upper left - by default
            for (int i = 0; i < ANI_NUM; i++)
            {// initializes correct animation file for each direction
                ninjaImg = Image.createImage("/Ninja/NinjaAni" + i + ".png");
                animateBitmap[i] = new OneBitmap(ninjaImg, bitmapWidth, bitmapHeight, x, y, 0, 0);
                //define the collision rectangle so the ninja won't die from his feet touching a zombie
                animateBitmap[i].defineCollisionRectangle (0,  0,  bitmapWidth,  bitmapHeight/2);
            }
        }//end try
        catch(IOException ioe) {System.err.println("Bitmap art asset failed loading.");}
        //insert hero closest to user
        manager.insert(animateBitmap[IDLE],0);
        //initialize star manager
        myStars = new StarManager(this);
    }//end constructor
    
    public int getDirection()
    {//returns the ninja's current direction
        return dir;
    }//end get direction
    public int getX()
    {//returns the ninja's current X position
        return x;
    }//end get x
    
    public int getY()
    {//returns the ninja's current y position
        return y;
    }//end get y
    
    public void setX(int x)
    {//sets the X position of the ninja
        this.x = x;
    }//end set x
    
    public void setY(int y)
    {//sets the Y position of the ninja
        this.y = y;
    }//end set y
    
    public LayerManager getManager()
    {//returns the layer manager of the ninja
        return manager;
    }//end get manager
    
    //update method to update animations
    public void update()
    {//updates all ninja information
        if(fireDelayCount != 0)
        {//check for fire delay
            fireDelayCount++;
            if(fireDelayCount == FIRE_DELAY)
                fireDelayCount = 0;
        }//end if
        for(int i = 0; i < ANI_NUM; i++)
        {//keep all animations at the same place and frame
            animateBitmap[i].move();          //update to next x position
            x = animateBitmap[i].getXpos();    //get the new x position
            y = animateBitmap[i].getYpos();    //get the new y position

            // check right side of screen
            if ( (x + bitmapWidth) > (parent.GAME_ORIGIN_X + parent.GAME_WIDTH ))
            {//if the edge of bitmap is beyond the right edge of screen
                dir = 0;
                animation = IDLE;
                delaycount[i] = 0;
                animateBitmap[i].setXSpeed(0);                                  //stop
                x = parent.GAME_ORIGIN_X + parent.GAME_WIDTH - bitmapWidth;    //kiss the side
            }//end if
            //check left side of screen
            if ( x < parent.GAME_ORIGIN_X)
            {//if the bitmap is touching the left side of the screen
                dir = 0;
                animation = IDLE;
                delaycount[i] = 0;
                animateBitmap[i].setXSpeed(0);      //stop
                x = parent.GAME_ORIGIN_X;           //kiss the side
            }//end if
            // check bottom of screen
            if ( (y + bitmapHeight) > parent.GAME_ORIGIN_Y + parent.GAME_HEIGHT)
            {//if the edge of the bitmap is beyond the bottom of the screen
                dir = 0;
                animation = IDLE;
                delaycount[i] = 0;
                animateBitmap[i].setYSpeed(0);                                     //stop
                y = parent.GAME_ORIGIN_Y + parent.GAME_HEIGHT - bitmapHeight;     //kiss the bottom
            }//end if
            // check top of screen
            if (y < parent.UPPER_Y_LIMIT - bitmapHeight)
            {//if the bitmap is less than length of the radius
                dir = 0;
                animation = IDLE;
                delaycount[i] = 0;
                animateBitmap[i].setYSpeed(0);    //stop
                y = parent.UPPER_Y_LIMIT - bitmapHeight;        //kiss the top
            }//end if
            //update x and y coordinates
            animateBitmap[i].setYpos(y);        //set new x
            animateBitmap[i].setXpos(x);        //set new y
            if(delaycount[i] == delay[i])
            {//check for animation delays
                if(animation == DEATH && animateBitmap[animation].getFrame() == animateBitmap[animation].getFrameSequenceLength() - 1)
                {}//do nothing if the ninja is dead and the death animation is finished
                else
                    animateBitmap[animation].moveForward();     //move forward one frame
                //reset delay count
                delaycount[i] = 0;
            }
            else
                delaycount[i]++; //increment delay count
            //stop the ninja from moving
            animateBitmap[i].setXSpeed(0);
            animateBitmap[i].setYSpeed(0);
        }//end for
        //remove current direction and insert correct one
        manager.remove(manager.getLayerAt(0));
        manager.insert(animateBitmap[animation],0);
        myStars.update();
        //check for kills and deaths
        checkKill();
        checkDeath();
        //reset the direction
        dir = 0;
        //if the ninja is not dead, and the animation sequence is complete return to the idle animation
        if(animation != DEATH)
            if(animateBitmap[animation].getFrame() == animateBitmap[animation].getFrameSequenceLength() - 1)
                animation = IDLE;
    }//end update
    
    public void moveUp()
    {//move up method
        if(!dead)
        {//checks if the ninja is dead
            dir = 1;
            animation = RIGHT;
            for(int i = 0; i < ANI_NUM; i++)
            {//set speed for all animations
            animateBitmap[i].setYSpeed(-speed);
            animateBitmap[i].setXSpeed(0);
            }//end for
        }//end if
    }//end move up
    
    public void moveDown()
    {//move down method
        if(!dead)
        {//checks if the ninja is dead
            dir = 2;
            animation = RIGHT;
            for(int i = 0; i < ANI_NUM; i++)
            {//set speed for all animations
            animateBitmap[i].setYSpeed(speed);
            animateBitmap[i].setXSpeed(0);
            }//end for
        }//end if
    }//end move down
    
    public void moveLeft()
    {//move left method
        if(!dead)
        {//checks if the ninja is dead
            dir = 3;
            animation = LEFT;
            for(int i = 0; i < ANI_NUM; i++)
            {//set speed for all animations
            animateBitmap[i].setYSpeed(0);
            animateBitmap[i].setXSpeed(-speed);
            }//end for
        }//end if
    }//end move left
    
    public void moveRight()
    {//move right method
        if(!dead)
        {//checks if the ninja is dead
            dir = 4;
            animation = RIGHT;
            for(int i = 0; i < ANI_NUM; i++)
            {//set speed for all animations
            animateBitmap[i].setYSpeed(0);
            animateBitmap[i].setXSpeed(speed);
            }//end for
        }//end if
    }//end move right
    
    public void fire()
    {//fire method
        if(!dead)
        {//checks if ninja is dead
            if(fireDelayCount == 0)
            {//checks for the fire delay
                fireDelayCount++;
                //set correct animation depending on direction
                if(dir != 3)
                    animation = S_RIGHT;
                else
                    animation = S_LEFT;
                //reset the throw animation
                animateBitmap[animation].setFrame(0);
                //throw a star and increment the zombie hord layers
                myStars.throwStar();
                parent.horde.incLayer();
            }//end if
        }
    }//end fire
    
    public void checkKill()
    {//checks for a zombie kill
        int i = 1;
        int j = parent.horde.getOffset();
        boolean collision = false;
        while(i < parent.horde.getOffset())
        {//run loop as long as there are stars in the manager
            j = parent.horde.getOffset();
            while(j < manager.getSize() - 1 && collision == false)
            {//check for collisions of each star with each zombie
                if(!parent.horde.checkDeath(j))
                {//only check for a collision if the zombie is alive
                    Sprite temp = (Sprite)manager.getLayerAt(i);
                    if( temp.collidesWith( (Sprite) manager.getLayerAt(j), true ) )
                    {//do this if the star collides with a zombie
                        //kill the zombie, remove the star and inc killcount
                        parent.horde.kill(j);
                        myStars.removeStar(i - 1);
                        collision = true;
                        killCount++;
                    }//end if
                    else
                        j++;
                }//end if
                else
                    j++;
            }//end while
            if(collision == false)
                //increment i only if no collision occured, otherwise the layer
                //moves up so i does not need to be changed
                i++;
            else
                //reset collision to check the next star if there is one
                collision = false;            
        }//end while
    }//end check kill
    
    public void checkDeath()
    {//check to see if the ninja died
        if(!dead)
        {//only check if the ninja is not dead
            int i = parent.horde.getOffset();
            boolean collision = false;
            while(i < manager.getSize() - 1 && collision == false)
            {//continu checking for every zombie layer or until there is a collision
                if(!parent.horde.checkDeath(i) && !parent.horde.checkSpawn(i))
                {//make sure the zombie is not spawning or dead
                    Sprite temp = (Sprite)manager.getLayerAt(0);
                    if( temp.collidesWith( (Sprite) manager.getLayerAt(i), true ) )
                    {//if there is a collision, kill the ninja, and set the correct animation
                        collision = true;
                        dead = true;
                        animation = DEATH;
                        for(int j = 0; j < ANI_NUM; j++)
                        {//set speeds to 0 for all animations
                            animateBitmap[j].setXSpeed(0);
                            animateBitmap[j].setYSpeed(0);
                        }//end for

                    }//end if
                    else
                        //increment i if there is no collision
                        i++;
                }//end if
                else
                    //increment i since zombe does not need to be checked
                    i++;
            }//end while
        }//end if
    }//end check death
    
    public boolean ninjaDeath()
    {//returns whether the ninja is alive or dead
        return dead;
    }//end ninja death
    
    public int getKills()
    {//returns the number of kills so far
        return killCount;
    }//end get Kills
    
    public void resetNinja()
    {//resets the ninja back to the staring position when entering a new room
        //starting y position
        y = parent.GAME_ORIGIN_Y + parent.GAME_HEIGHT - bitmapHeight;
        //starting x position
        x = parent.GAME_ORIGIN_X;
        //reset all original values
        dead = false;
        dir = 0;
        animation = IDLE;
        fireDelayCount = 1;   
        for(int i = 0; i < ANI_NUM; i++)
        {//reset speed and position for all animations
            animateBitmap[i].setXSpeed(0);
            animateBitmap[i].setYSpeed(0);
            animateBitmap[i].setYpos(y);        //set new x
            animateBitmap[i].setXpos(x);        //set new y
        }
        //insert the reset ninja into the manager
        manager.insert(animateBitmap[IDLE],0);
        //reset the throwing stars
        myStars.reset();
    }//end reset ninja
}//end class
