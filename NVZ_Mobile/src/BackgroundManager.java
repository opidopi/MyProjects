/*
Title: Final Programming Assignment 
Author: Sean Lantry 
Date: 5/19/2012
Course & Section: CSC 221-002W
Description: This class manages the varius background used for the game
*                   It randomly generates a new background every time the ninja
*                   clears and then exits the room
Data Requirements: The only data requirement is the layer manager from the canvas
*                   class, all other data is hard coded in
Formulas:
*   -Generate random wall tiles, including animated and non animated wall tiles
*       for the first two rows
*   -Generate random wall to floor border tiles for all but the last spot of the 3rd row
*   -Insert the door tile in the last column of the 3rd row
*   -Fill the last two rows with floor tiles
Refined Algorithm:
* 
*   new background
*   FOR first two rows of tiled layer
*       generate random wall tiles
*   END FOR
*   FOR all but last row of 3rd layer
*       generate random border tiles
*   END FOR
*   FOR all remaining tiles
*       generate floor tiles
*   END FOR
* 
*   update
*       FOR all animated tiles
*           move to next frame of animation
*       END FOR
 */


import java.io.IOException;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.TiledLayer;

public class BackgroundManager 
{
    //constanst:
    //these indicate the value associated with the given
    //characteristic of the animated tiles for use with the
    //2-D array that holds these values
    private static final int FRAME_INDEX    = 0;    //index of animated frame genrated by method call
    private static final int FRAME_COUNTER  = 1;    //current offset from start frame
    private static final int START_FRAME    = 2;    //starting frame of animation
    private static final int STOP_FRAME     = 3;    //last frame of animation
    private static final int DELAY          = 4;    //delay between animation
    private static final int DELAY_COUNT    = 5;    //count for delay
    private static final int BAL_DELAY      = 6;    //delay between frames
    private static final int BAL_COUNT      = 7;    //count for balancing frames delay
    
    private int x;                      //x and y coordinate of background on screen
    private int y;
    private int tileHeight;             //height and width of a tile
    private int tileWidth;
    private int rows;                   //number of rows and columns
    private int cols;
    private int animateNum;             //number of animated tiles available
    
    private int[][] animateFrames;      //information for animated tiles needed to make them display properly
    private int[][] cells;              //holds values of the background tiles
    
    private TiledLayer         background;      //the background that is added to the layer manager
    private Image              backgroundImg;   //tiled bitmap used to make background
    private Lantry_FinalCanvas parent;          //parent canvas class
    private LayerManager       manager;         //layer manager from parent
    
    public BackgroundManager(Lantry_FinalCanvas parent)
    {
        //get a copy of the parent and the layermanager
        this.parent = parent;
        manager = parent.getManager();
        
        //set background coordinates and size, initialize other integers
        x = parent.GAME_ORIGIN_X;
        y = parent.GAME_ORIGIN_Y;
        tileHeight = 34;
        tileWidth = 33;
        rows = 5;
        cols = 5;
        animateNum = 2;
        
        try
        {
            // load the image
            backgroundImg = Image.createImage("/Background/TiledBackground.png");
        }//end try
        catch (IOException e) {System.err.println("Bitmap art asset failed loading.");}
        
        // create the tiledlayer background
        background = new TiledLayer(cols, rows, backgroundImg, tileWidth, tileHeight);
        
        //initialize animation and cell arrays
        int[][] cells =
        {
            {1, 1, 1, 1, 1},
            {1, -1, 1, -2, 1},
            {5, 5, 5, 5, 7},
            {8, 8, 8, 8, 8},
            {8, 8, 8, 8, 8},
        };//end cell array
        this.cells = cells;
        //set the values for the animated frames
        int[][] animateFrames =
        {
            //index values
            {background.createAnimatedTile(9), background.createAnimatedTile(17)},
            //frame cound
            { 0, 0},
            //starting frame
            { 9, 17},
            //ending frame
            {16, 22},
            //delay between animations
            {100,25},
            //delay count
            { 0, 0},
            //delay between frames to smooth out animation
            { 4, 2},
            //delay count for between frames
            { 0, 0}
        };//end animate frames array
        this.animateFrames = animateFrames;
        
        // set the background with the images
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            background.setCell(j, i, cells[i][j]);
        }//end for
        
        //initialize animated tiles
        for(int i = 0; i < animateNum; i++)
        {
            background.setAnimatedTile(animateFrames[FRAME_INDEX][i], animateFrames[START_FRAME][i]);
        }//end for
        //set background position on screen
        background.setPosition(x, y);
        
        //Now add background as a layer - append will add background as layer 1
        manager.append(background);
    }//end constructor
    
    public void update()
    {
        //update for each animation
        for(int i = 0; i < animateNum; i++)
        {
            //check for delay, if so then advance to the next frame
            //only check for delay on first frame
            if(animateFrames[FRAME_COUNTER][i] == 0)
                if(animateFrames[DELAY_COUNT][i] >= animateFrames[DELAY][i])
                {
                    //increment the framecounter for each animation
                    animateFrames[FRAME_COUNTER][i]++;
                    animateFrames[DELAY_COUNT][i] = 0;
                }
                else
                    animateFrames[DELAY_COUNT][i]++;
            else
                //check for balancing delay, to make animations run smoothly
                if(animateFrames[BAL_COUNT][i] >= animateFrames[BAL_DELAY][i])
                {
                    //increment the framecounter for each animation
                    animateFrames[FRAME_COUNTER][i]++;
                    animateFrames[BAL_COUNT][i] = 0;
                }
                else
                    animateFrames[BAL_COUNT][i]++;
            //if the start frame plus the counter for that particular animation
            //is greater than the stop value, reset that couner
            if(animateFrames[START_FRAME][i] + animateFrames[FRAME_COUNTER][i] > animateFrames[STOP_FRAME][i])
                animateFrames[FRAME_COUNTER][i] = 0;
            //set the animated tile equal to the start frame plus the count for that animation
            background.setAnimatedTile(animateFrames[FRAME_INDEX][i], animateFrames[START_FRAME][i] +
                                                                        animateFrames[FRAME_COUNTER][i]);
        }//end for
    }//end update
    
    //generates a new random background
    public void newBackground()
    {
        //generates random panels for the walls
        for(int i = 0; i < 2; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                //select a random number from -2 to 4
                int temp = parent.getRandom().nextInt(7) - 2;
                //make all 0's 1's because we don't want see through walls
                if (temp == 0)
                    temp = 1;
                //set the tile equal to the randomly generated index
                cells[i][j] = temp;
            }
        }
        //generate new wall to floor border tiles for all except
        //the door tile as we will use that to move to the next level
        for(int i = 0; i < 4; i++)
            cells[2][i] = parent.getRandom().nextInt(2) + 5;
        
        // set the background with the images
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            background.setCell(j, i, cells[i][j]);
        }//end for
        
        //initialize animated tiles
        for(int i = 0; i < animateNum; i++)
        {
            background.setAnimatedTile(animateFrames[FRAME_INDEX][i], animateFrames[START_FRAME][i]);
        }//end for
        
        //remove the old background and insert a new one farthest from the user
        manager.remove(background);
        manager.insert(background, manager.getSize());
    }//end new background
}//end BackgroundManager
