/*
Title: Final Programming Assignment
Author: Sean Lantry 
Date: 5/21/2012
Course & Section: CSC 221-002W
Description: This is the OneBitmap from my last program, but modified to fix
*               logical errors that allowed the Onebitmap X and Y coordinates to
*               be different from those of the actual sprite. This was compensated
*               for in other parts of the program previously, but it made sense
*               to do it here as this controls all actions of the sprite

Data Requirements:
Formulas:
Refined Algorithm:

 */
import java.io.*;
import java.util.*;
import javax.microedition.lcdui.*; // for basic graphics, display, etc
import javax.microedition.lcdui.game.*;

public class OneBitmap extends Sprite
{
    // member variables
    private int mXspeed;        //Speed in X-direction
    private int mYspeed;        //Speed in Y-direction
    private int mXpos;          //Current X postion
    private int mYpos;          //current Y position
    private int mCellWidth;     //total width of the sprite on screen
    private int mCellHeight;    //total height of the sprite on screen

    /** Creates a new instance of a sprite */
    public OneBitmap(Image img, int cellWidth, int cellHeight, int x, int y, int Xspeed, int Yspeed)
    {
        // create an non-animated sprite
        super(img, cellWidth, cellHeight);

        // setup our inital values
        mXpos = x;
        mYpos = y;
        mCellWidth = cellWidth;
        mCellHeight = cellHeight;
        mXspeed = Xspeed;
        mYspeed = Yspeed;

        setPosition(mXpos, mYpos);  //place the bitmap on the screen *here*

    }//end constructor

   public void setYSpeed()
   //reverse Y direction
   {
        mYspeed = -(mYspeed);
   }//end setYSpeed

   public void setXSpeed()
   //reverse X direction
   {
        mXspeed = -(mXspeed);
   }//end setXSpeed
   
   public void setYSpeed(int speed)
   //manually set Y direction
   {
        mYspeed = speed;
   }//end setYSpeed

   public void setXSpeed(int speed)
   //manually set X direction
   {
        mXspeed = speed;
   }//end setXSpeed

   public int getXspeed()
   //return current x direction
   {
           return mXspeed;
   }//end getXspeed

   public int getYspeed()
   //return current y direction
   {
           return mYspeed;
   }//end getYspeed

   public void move()
   //move x position by delta x
   {
       mXpos = mXpos + mXspeed;
       mYpos = mYpos + mYspeed;
       setPosition(mXpos,mYpos);
   }//end moveX


   public void setXpos(int x)
   //change x position
   {
      mXpos = x;
      setPosition(mXpos,mYpos);
   }//end setX

   public void setYpos(int y)
   //change y position
   {
      mYpos = y;
      setPosition(mXpos,mYpos);
   }//end setY

   public int getXpos()
   //return current x position
   {
      return mXpos;
   }//end getX

   public int getYpos()
   //return current y position
   {
      return mYpos;
   }//end getY

   public void moveForward()
    {
           //move forward one frame in sequence
           nextFrame();
    }//end moveForward

    public void moveBackward()
    {
           //move backward one frame in sequence
           prevFrame ();
    }//end moveBackward

    public void drawBitmap(Graphics g)
    //draw the shape at current position
    {
            paint(g);
    }//end draw

} //end class