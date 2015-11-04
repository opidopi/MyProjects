<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of testclass
 *
 * @author Opie
 */

include 'CardSource/Card.php';
include 'CardSource/PlayableCard.php';

class testclass
{
    private $testcard;
    
    function construct()
    {
        $this->testcard = new PlayableCard();
        $this->testcard->contsruct("ID","DMG","DEF","SKILL");
    }
    
    function getCard()
    {
        return $this->testcard;
    }
}

?>
