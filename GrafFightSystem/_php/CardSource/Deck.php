<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Deck
 *
 * @author Opie
 */
class Deck 
{
    private $cards = array();
    private $size;
    
    function construct()
    {
        $this->size = 0;
    }
    
    function getCard($index)
    {
        if($this->size == 0)
            return new PlayableCard();
        else
            return $this->cards[$index];
    }
    
    function getDeck()
    {
        return $this->cards;
    }
    
    function getDeckSize()
    {
        return $this->size;
    }
    
    function addCard($newCard)
    {
        $this->cards[] = $newCard;
        $this->size++;
    }
    
    function eraseCard($index)
    {
        $tempDeck = array();
        
        for($i = 0; $i < sizeof($this->cards); $i++)
        {
            if($i !== $index)
            {
                $tempDeck[] = $this->cards[$i];
            }
        }
        $this->cards = $tempDeck;
        $this->size--;
    }
    
    function removeCard($index)
    {
        if(sizeof($this->cards)==0)
           return 0;
        $tempDeck = array();
        $tempCard = $this->cards[$index];
        for($i = 0; $i < sizeof($this->cards); $i++)
        {
            if($i !== $index)
            {
                $tempDeck[] = $this->cards[$i];
            }
        }
        $this->cards = $tempDeck;
        $this->size--;
        return $tempCard;
    }
    
    function shuffle()
    {
        for($i = 1; $i < $this->size * 5; $i++)
        {
            $this->addCard($this->removeCard(rand(0, $this->size - 1)));
        }
    }
}

?>
