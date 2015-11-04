<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of SpecialCard
 *
 * @author Opie
 */
abstract class SpecialCard extends PlayableCard
{
    function construct($setID, $setDamage, $setDefense, $setSkill)
    {
        $this->contsruct($setID, $setDamage, $setDefense, $setSkill);
    }
    
    function specialMechanic();
}

?>
