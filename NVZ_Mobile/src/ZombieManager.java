/*
Title: Final Programming Assignment 
Author: Sean Lantry 
Date: 5/22/2012
Course & Section: CSC 221-002W
Description: This class manages an individual zombie as it moves around the map
Data Requirements:  only the canvas and manager are needed for drawing
Formulas:
Refined Algorithm:
*   kill
*   SET animation to death
*   SET speed to 0 for all animations
*   SET zombie to dead
* 
*   update
*   MOVE the zombie and all animations
*   CHECK for collisions with walls
*   IF(death animation is finished)
*       do nothing
*   ELSE(update
*   IF(Spawning && spawning is finished
*       SET to move left
*   MOVE animations forward
* 
 */
import java.io.IOException;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;


public class ZombieManager 
{
    private static final int SPAWN   = 0;       //Index of the spawn animation
    private static final int LEFT    = 1;       //Index of the move left animation
    private static final int RIGHT   = 2;       //Index of the move right animation
    private static final int DEATH   = 3;       //Index of the death animation
    
    public final int bitmapWidth  = 27;         //width of one ninja
    public final int bitmapHeight = 34;         //height of one ninja
    public final int ANI_NUM      = 4;          //number of animations
    public final int DIR_NUM      = 5;          //number of possible directions
    
    private int x;                              //x position
    private int y;                              //y position
    private int speed;                          //speed of bitmap
    private int dir;                            //direction of bitmap
    private int animation;                      //current animation to be displayed
    private int layer;                          //current layer of the animation

    private boolean dead;                       //tells whether this zombie is alive or dead
    private boolean spawning;                   //tells whether this zombie is spawning or alread on the map
    
    private int[] delaycount;                   //delay count for each animation
    private int[] delay;                        //delay between frames of animation
    
    private OneBitmap[] animateBitmap;          //instantiate class for one bitmap
    
    private Lantry_FinalCanvas parent;          //Enables us to access data in the Canvas
    // the parent canvas's layer manager
    private LayerManager manager;               //Enables us to be included in Layer management
    
    public ZombieManager(Lantry_FinalCanvas parent)
    {
        //create array of bitmaps, 1 for each animation
        animateBitmap = new OneBitmap[ANI_NUM];
        //initialize speed, direction, animation, death and spawning to starting values
        speed = 1;
        dir = 0;
        animation = SPAWN;
        dead = false;
        spawning = true;
        //set up the CoupleCollisionCanvas as the parent to access data
        this.parent = parent;

        //set up Layer Manager in CoupleCollisionCanvas as parent Layer
        this.manager = parent.getManager();
        do
        {//this loop ensures that the zombies do not spawn on top of the ninja
            //starting y position
            y = parent.UPPER_Y_LIMIT + parent.getRandom().nextInt(30);
            //starting x position
            x = parent.GAME_ORIGIN_X  + parent.getRandom().nextInt(parent.GAME_WIDTH - bitmapWidth);
            //set up the delay values for the different animations
            int[] mDelay = {20,2,2,3};
            delay = mDelay; //saves the array created in the global variable
            //set up the delay counts for the different animations
            int[] mDelaycount = {0,0,0,0};
            delaycount = mDelaycount; //saves the array created in the global variable
            try
            {
                // First load our tiled bitmap
                // Be sure to get your syntax correct here
                Image zombieImg;
                //Initialize the bitmap with height & width of one tile
                //include where to start
                //point of view will be upper left - by default
                for (int i = 0; i < ANI_NUM; i++)
                {// initializes correct animation file for each direction
                    zombieImg = Image.createImage("/Zombie/ZombieAni" + i + ".png");
                    animateBitmap[i] = new OneBitmap(zombieImg, bitmapWidth, bitmapHeight, x, y, 0, 0);
                    //set the collision rectangle so the zombie can't kill us and can't be killed by 
                    //collisions with the lower half of the bitmap - Shoot them in the head!
                    animateBitmap[i].defineCollisionRectangle (0,  0,  bitmapWidth,  bitmapHeight/2);
                }//end for
            }//end try
            catch(IOException ioe) {System.err.println("Bitmap art asset failed loading.");}
        }//end do
        //check for a collision with the ninja
        while(animateBitmap[animation].collidesWith( (Sprite) manager.getLayerAt(0), true ));
        //insert zombie at the farthes back postion
        layer = manager.getSize() - 1;
        //insert the zombie on the background layer, pushing the background back one spot
        manager.insert(animateBitmap[SPAWN], manager.getSize() - 1);
    }//end constructor
    
    public int getX()
    {//returns X value of zombie position
        return x;
    }//kend get X
    
    public int getY()
    {//returns Y value of zombie position
        return y;
    }//end get Y
    
    public void setX(int x)
    {//sets the X position of the zombie
        this.x = x;
    }//end set X
    
    public void setY(int y)
    {//sets the Y position of the zombie
        this.y = y;
    }//end set Y
    
    public LayerManager getManager()
    {//gets the layer manager
        return manager;
    }//end get manager
    
    public void incLayer()
    {//increments this zombie's layer by one
        layer++;
    }//end increment layer
    
    public void decLayer()
    {//decrements the zombie's layer by one
        layer--;
    }//end decrement layer
    
    public int getLayer()
    {//returns current layer of zombie
        return layer;
    }//end get layer
    
    public boolean checkDeath()
    {//checks if the zombie is alive or dead(or undead and dead dead?)
        return dead;
    }//returns check death
    
    public boolean checkSpawn()
    {//checks to see if the zombie is spawning
        return spawning;
    }//end check spawn
    
    public void kill()
    {// kills the zombie
        dead = true;
        //set correct animation for death
        animation = DEATH;
        for(int i = 0; i < ANI_NUM; i++)
        {//set speeds for all animations to 0
            animateBitmap[i].setXSpeed(0);
            animateBitmap[i].setYSpeed(0);
        }//end for
    }//end kill
    
    public void changeDir()
    {//changes the zombie's direction to a random one
        switch(Math.abs(parent.getRandom().nextInt(DIR_NUM)))
        {//generate a random number between 0 and 3 to choose one
            //of the 4 moving directions
            case 0:
                moveUp();
                break;
            case 1:
                moveDown();
                break;
            case 2:
                moveLeft();
                break;
            default :
                moveRight();
                break;
        }//end switch
    }//end change direction
    
    //update method to update animations
    public void update()
    {//performs all updates necessary to keep zombie working
        for(int i = 0; i < ANI_NUM; i++)
        {//keep all animations at the same place and frame
            //move the bitmap
            animateBitmap[i].move();
            //get the new x position
            x = animateBitmap[i].getXpos();
            //get the new y position
            y = animateBitmap[i].getYpos();

            // check right side of screen
            if ( (x + bitmapWidth) > (parent.GAME_ORIGIN_X + parent.GAME_WIDTH ))
            {//if the edge of bitmap is beyond the right edge of screen
                //set correct direction and animation
                dir = 3;
                animation = LEFT;
                delaycount[i] = 0;
                animateBitmap[i].setXSpeed(-speed);                            //reverse
                x = parent.GAME_ORIGIN_X + parent.GAME_WIDTH - bitmapWidth;    //kiss the side
            }//end if
            //check left side of screen
            if ( x < parent.GAME_ORIGIN_X)
            {//if the bitmap is touching the left side of the screen
                //set correct direction and animation
                dir = 4;
                animation = RIGHT;
                delaycount[i] = 0;
                animateBitmap[i].setXSpeed(speed);   //reverse
                x = parent.GAME_ORIGIN_X;           //kiss the side
            }//end if

            // check bottom of screen
            if ( (y + bitmapHeight) > parent.GAME_ORIGIN_Y + parent.GAME_HEIGHT)
            {//if the edge of the bitmap is beyond the bottom of the screen
                //set correct direction and animation
                dir = 1;
                animation = LEFT;
                delaycount[i] = 0;
                animateBitmap[i].setYSpeed(-speed);                               //reverse
                y = parent.GAME_ORIGIN_Y + parent.GAME_HEIGHT - bitmapHeight;     //kiss the bottom
            }//end if
            // check top of screen
            if (y < parent.UPPER_Y_LIMIT - bitmapHeight)
            {//if the bitmap is less than length of the radius
                //set correct direction and animation
                dir = 2;
                animation = LEFT;
                delaycount[i] = 0;
                animateBitmap[i].setYSpeed(speed);              //reverse
                y = parent.UPPER_Y_LIMIT - bitmapHeight;        //kiss the top
            }//end if

            //update x and y coordinates
            animateBitmap[i].setYpos(y);        //set new x
            animateBitmap[i].setXpos(x);        //set new y
            if(delaycount[i] == delay[i])
            {//only update if the delaycount is equal to the delay value
                if(animation == DEATH && animateBitmap[animation].getFrame() == animateBitmap[animation].getFrameSequenceLength() - 1)
                {}//do nothing if zombie is dead and the death animation is finished
                else
                    //move forward one frame
                    animateBitmap[animation].moveForward();
                //reset delay count for this animation
                delaycount[i] = 0;
            }//end if
            else
                //inc delay count for this animation
                delaycount[i]++;
        }//end for
        //remove current direction and insert correct one
        manager.remove(manager.getLayerAt(layer));
        manager.insert(animateBitmap[animation],layer);
        
        if(animation == SPAWN && animateBitmap[animation].getFrame() == animateBitmap[animation].getFrameSequenceLength() - 1)
        {//checks to see if spawn animation is complete
            spawning = false;
            //default start direction is left
            animation = LEFT;
            for(int i = 0; i < ANI_NUM; i++)
                //start zombie moving for all animations
                animateBitmap[i].setXSpeed(-speed);
        }//end if
    }//end update
    
    public void moveUp()
    {//move up method
        dir = 1;
        animation = LEFT;
        for(int i = 0; i < ANI_NUM; i++)
        {//set speed for all animations
            animateBitmap[i].setYSpeed(-speed);
            animateBitmap[i].setXSpeed(0);
        }//end if
    }//end move up
    
    public void moveDown()
    {//move down method
        dir = 2;
        animation = LEFT;
        for(int i = 0; i < ANI_NUM; i++)
        {//set speed for all animations
            animateBitmap[i].setYSpeed(speed);
            animateBitmap[i].setXSpeed(0);
        }//end if
    }//end move down
    
    public void moveLeft()
    {//move left method
        dir = 3;
        animation = LEFT;
        for(int i = 0; i < ANI_NUM; i++)
        {//set speed for all animations
            animateBitmap[i].setYSpeed(0);
            animateBitmap[i].setXSpeed(-speed);
        }//end if
    }//end move left
    
    public void moveRight()
    {//move right method
        dir = 4;
        animation = RIGHT;
        for(int i = 0; i < ANI_NUM; i++)
        {//set speed for all animations
            animateBitmap[i].setYSpeed(0);
            animateBitmap[i].setXSpeed(speed);
        }//end if
    }//end move right
}//end class