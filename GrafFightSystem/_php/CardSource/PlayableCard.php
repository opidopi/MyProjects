<?php
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of PlayableCard
 *
 * @author Opie
 */
class PlayableCard extends Card
{
    private $damage;
    private $defense;
    private $skillbase;
    private $moveType;
    private $subType;
    
    function contsruct($setID, $name, $setDamage, $setDefense, $setSkill, $setSub)
    {
        $this->construct($setID, "Playable", $name);
        $this->damage = $setDamage;
        $this->defense = $setDefense;
        $this->skillbase = $setSkill;
        $this->subType = $setSub;
    }
    
    function setMoveType($type)
    {
        $this->moveType = $type;
    }
    
    function getMoveType()
    {
        return $this->moveType;
    }
    
    function getSubType()
    {
        return $this->subType;
    }
    
    function getDamage()
    {
        return $this->damage;
    }
    
    function getDefense()
    {
        return $this->defense;
    }
    
    function getSkill()
    {
        return $this->skillbase;
    }
}

?>
