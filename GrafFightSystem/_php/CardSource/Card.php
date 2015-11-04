<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Card
 *
 * @author Opie
 */
abstract class Card 
{
    private $ID;
    private $Type;
    private $name;

    function construct($setID, $setType, $name)
    {
        $this->ID = $setID;
        $this->Type = $setType;
        $this->name = $name;
    }
    
    function getID()
    {
        return $this->ID;
    }
    
    function getType()
    {
        return $this->Type;
    }
    
    function getName()
    {
        return $this->name;
    }
}

?>
