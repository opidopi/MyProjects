/*
Title: Final Programming Assignment 
Author: Sean Lantry 
Date: 5/22/2012
Course & Section: CSC 221-002W
Description: This class manages the horde of zombies, all behavior is controlled here
Data Requirements:
Formulas:
Refined Algorithm:
*   addMonser
*   WHILE(no collision with ninja)
*       MAKE new staring position
*   END WHILE
*   ADD a zombie to the board
* 
*   new horde
*   CREATE new zombie array larger than previous
*   RE-initializ all values
* 
*   update
*   IF(time to spawn)
*       addMonser
*   END IF
*   UPDATE all zombies
*   IF(time to change direction)
*       change a zombie direction
*   END IF
 */

import javax.microedition.lcdui.game.LayerManager;

public class HordeManager 
{
    private final int MAX_CHANGE_COUNT = 75;        //time between direction changes
    private final int MAX_SPAWN_COUNT = 50;         //time between spawns
    
    private int spawnCount;                         //total zombies spawned
    private int hordeSize;                          //current horde size
    private int hordeSpawned;                       //number of horde spawned so far
    private int changeIndex;                        //current monster set to change direction
    private int changeCount;                        //count between direction changes
    private int offset;                             //layer offset to the first zombie
    
    private ZombieManager mHorde[];                 //array of zombies i.e. the horde
    private Lantry_FinalCanvas parent;              //canvas for game screen info
    private LayerManager manager;                   //manager from the canvas
    
    public HordeManager(Lantry_FinalCanvas parent)
    {
        //set the starting size of the horde
        hordeSize = 5;
        //initialize all staring values
        spawnCount = hordeSpawned = changeIndex = changeCount = 0;
        offset = 1;
        //create a new array of zombies
        mHorde = new ZombieManager[hordeSize];
        
        //set up the Canvas as the parent to access data
        this.parent = parent;
        this.manager = parent.getManager();
        //add the first monster to the horde
        addMonster();
    }//end constructor
    
    public void addMonster()
    {//adds a monster to the horde
        if(hordeSpawned < hordeSize)
        {//add a new monster only if space is available and increments the number spawned
            mHorde[hordeSpawned] = new ZombieManager(parent);
            hordeSpawned++;
        }//end if
    }//end add monster
    
    public void newHorde()
    {//creates a whole new horde for the next room, based on the previous horde size
        hordeSize = hordeSize * 2 - hordeSize/2;
        mHorde = new ZombieManager[hordeSize];
        spawnCount = hordeSpawned = changeIndex = changeCount = 0;
        offset = 1;
    }//end new horde
    
    public void incLayer()
    {//increments the layer offset if a star is added to the manager
        offset++;
        for(int i = 0; i < hordeSpawned; i++)
            //increment the layer for each zombie
            mHorde[i].incLayer();
    }//end increment layer
    
    public void decLayer()
    {//decrements the layer offset if the 
        offset--;
        for(int i = 0; i < hordeSpawned; i++)
            //decrement the layer for each zombie
            mHorde[i].decLayer();
    }//end decrementn layer
    
    public void kill(int layer)
    {//kills the zombie at the given layer
        mHorde[layer - offset].kill();
    }//end kill
    
    public boolean checkDeath(int layer)
    {//checks if the zombie at a given layer is alive or dead
        return mHorde[layer - offset].checkDeath();
    }//end check death
    
    public boolean checkSpawn(int layer)
    {//checks to see if the zombie is spawning
        return mHorde[layer - offset].checkSpawn();
    }//end check spawn
    
    public int getOffset()
    {//returns the current layer offset of the horde
        return offset;
    }//end get offset
    
    public int getSize()
    {//returns the current size of this horde
        return hordeSize;
    }//end get size
    
    public int getCurrentSize()
    {//returns the number of zombies the horde has spawned so far
        return hordeSpawned;
    }//end get current size
    
    public void update()
    {//updates all the zombies in the horde
        spawnCount++;
        if(spawnCount >= MAX_SPAWN_COUNT - hordeSize/10)
        {//spawn a new monster when appropriate
            spawnCount = 0;
            addMonster();
        }//end if
        //update all monsters on the game screen
        for(int i = 0; i < hordeSpawned; i++)
            //update all zombies spawned so far
            mHorde[i].update();
        //randomly change direction
        changeCount++;
        if(changeCount == MAX_CHANGE_COUNT)
        {//change direction when necessary
            changeCount = 0;
            if(!mHorde[changeIndex].checkDeath())
                //only change direction of living zombies
                mHorde[changeIndex].changeDir();
            changeIndex++;
            if(changeIndex == hordeSpawned)
                //reset the index if it reaches the number of horde spawned so far
                changeIndex = 0;
        }//end if
    }//end update
}//end class
