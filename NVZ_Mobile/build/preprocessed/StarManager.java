/*
Title: Final Programming Assignment 
Author: Sean Lantry 
Date: 5/21/2012, 5/22/2012
Course & Section: CSC 221-002W
Description: This class defines how throwing stars work, and keeps track of all
*               Stars thrown by the ninja
Data Requirements: This class require info from the ninja and the canvas class,
*                       these are passed through the ninja class as a parent
Formulas:
Refined Algorithm:
*   throw star
*   IF(max stars not reached)
*       ADD a new star
*       IF(direction is not left)
*           shoot right
*       ELSE
*           shoot left
*       END IF
*       INC the horde layer
*   END IF
* 
*   update
*   FOR(all stars on screen
*       move all stars in appropriate direction
*   END FOR
* 
*   remove star
*   COPY all stars below removed star to new array
*   COPY all stars above removed star to new array
*   ADD a new blank star to the array
*   DEC the horde layer
* 
*   reset
*   INIT all stars to blank stars
 */
import java.io.IOException;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.LayerManager;

public class StarManager 
{
    private final int TOTAL_STARS = 25;     //max throwing stars that can be drawn
    private final int STAR_SPEED = 4;       //speed of a throwing star
    private final int X = 0;                //index of X values for stars
    private final int Y = 1;                //index of Y values for stars
    private final int S = 2;                //index of direction for stars
    private final int F = 3;                //index of current frame for each star
    
    public final int bitmapWidth  = 8;      //width of one star
    public final int bitmapHeight = 8;      //height of one star
    
    private int numStars;                   //current number of stars
    
    private int[][] starVector;             //vector that holds information for stars
    
    private Image starImg;                  //image for the throwing star
    private OneBitmap animateBitmap;        //bitmap for the throwing star
    private NinjaManager parent;            //ninja manager parent
    private LayerManager manager;           //layer manager from ninja's parent
    
    public StarManager(NinjaManager parent)
    {
        //initials the number of stars and the star vector
        numStars = 0;
        starVector = new int[TOTAL_STARS][4];
        //initialize the parent and manager
        this.parent = parent;
        this.manager = parent.getManager();
        
        for(int i = 0; i < TOTAL_STARS; i++)
        {//set initial values for all stars
            starVector[i][X] = 0;
            starVector[i][Y] = 0;
            starVector[i][S] = 0;
            starVector[i][F] = 0;
        }//end for
        //load the star image
        try{starImg = Image.createImage("/ThrowStar/ThrowStar.png");}
        catch(IOException ioe) {System.err.println("Bitmap art asset failed loading.");}
    }//end constructor
    
    public void throwStar()
    {//creates a new throwing star moving in the appropriate direction
        if(numStars < TOTAL_STARS)
        {//make sure we don't exceed the max stars
            if(parent.getDirection() != 3)
            {//thrown right by default, as long as ninja is not facing left
                starVector[numStars][X] = parent.getX() + parent.bitmapHeight - bitmapHeight;
                starVector[numStars][Y] = parent.getY() + 10;
                starVector[numStars][S] = 1;
            }//end if
            else
            {//throw left if ninja is facing left
                starVector[numStars][X] = parent.getX();
                starVector[numStars][Y] = parent.getY() + 10;
                starVector[numStars][S] = -1;
            }//end else
            //create a new bitmap for the star
            animateBitmap = new OneBitmap(starImg, bitmapWidth, bitmapHeight, starVector[numStars][X],
                    starVector[numStars][Y], STAR_SPEED*starVector[numStars][S], 0);
            //insert the star into the layer manager and increment the number of stars
            manager.insert(animateBitmap, numStars + 1 );
            numStars++;
        }//end if
    }//end thrown star
    
    public void update()
    {//updates info for the stars
        for(int i = 0; i < numStars; i++)
        {//update every star on the map
            //create a new bitmap with the information about the star
            animateBitmap = new OneBitmap(starImg, bitmapWidth, bitmapHeight, starVector[i][X],
                    starVector[i][Y], STAR_SPEED * starVector[i][S], 0);
            //move the bitmap and update the star informtation
            animateBitmap.move();
            starVector[i][X] = starVector[i][X] + starVector[i][S] * STAR_SPEED;
            starVector[i][F]++;
            //check right side of screen
            if ( (starVector[i][X] + bitmapWidth) > (parent.parent.GAME_ORIGIN_X + parent.parent.GAME_WIDTH ))
                removeStar(i);

            //check left side of screen
            else if ( starVector[i][X] < parent.parent.GAME_ORIGIN_X)
                removeStar(i);
            else
            {//update the star in the layer manager
                manager.remove(manager.getLayerAt(i + 1));
                if (starVector[i][F] == 2)
                {//reset the frame
                    animateBitmap.moveForward();
                    starVector[i][F] = 0;
                }//end if
                manager.insert(animateBitmap, i + 1 );
            }//end else
        }//end for
    }//end update
    
    public void removeStar(int index)
    {//removes a star from the map and resets the information
        //create temporary array to hold data
        int[][] temp = new int[TOTAL_STARS][4];
        for(int i = 0; i < index; i++)
        {//copy over all stars below the one to be removed to the new array
            temp[i][X] = starVector[i][X];
            temp[i][Y] = starVector[i][Y];
            temp[i][S] = starVector[i][S];
        }//end for
        for(int i = index + 1; i < TOTAL_STARS; i++)
        {//copy all of the stars after the one to be removed to the new array
            temp[i - 1][X] = starVector[i][X];
            temp[i - 1][Y] = starVector[i][Y];
            temp[i - 1][S] = starVector[i][S];
        }//end for
        //create a new blank star to be added at the end of the array
        temp[TOTAL_STARS - 1][X] = 0;
        temp[TOTAL_STARS - 1][Y] = 0;
        temp[TOTAL_STARS - 1][S] = 0;
        //remove the star from the manager
        manager.remove(manager.getLayerAt(index + 1));
        //copy the temp array to the star vector
        starVector = temp;
        //decrement the stars and the layers of the zombie horde
        numStars--;
        parent.parent.horde.decLayer();
    }//end remove
    
    public void reset()
    {//resets the stars when moving to a new board
        numStars = 0;
        for(int i = 0; i < TOTAL_STARS; i++)
        {//reset each star in the array
            starVector[i][X] = 0;
            starVector[i][Y] = 0;
            starVector[i][S] = 0;
            starVector[i][F] = 0;
        }//end for
    }//end reset
}//end class
