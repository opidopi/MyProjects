<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Character
 *
 * @author Opie
 */
include'config.php';

class Character 
{
    private $playerID;
    private $playerNum;
    private $playerType;
    private $lvl;
    
    private $bal;
    private $str;
    private $tgh;
    private $int;
    private $spd;
    
    private $handSize;
    private $dmg;
    private $baseHP;
    private $crit;
    private $eXatt;
    
    private $currentHP;
    
    private $mainDeck;
    private $hand;
    private $graveyard;
    private $currentAtt;
    private $attList;
    

    
   
    function construct($playerID, $playerNum, $fightID)
    {
        $this->playerID = $playerID;
        $this->lvl = 1;
        $this->playerNum = $playerNum;
        $this->playerType = self::returnType($playerID);

        $cornelius = self::returnCornelius($playerID);
        $this->bal = $cornelius[0];
        $this->str = $cornelius[1];
        $this->tgh = $cornelius[2];
        $this->int = $cornelius[3];
        $this->spd = $cornelius[4];
        
        switch($this->playerType)
        {
            case 'balance':
                $this->handSize = 2 + $this->lvl/2 + $this->bal/5;
                $this->dmg = $this->lvl/2 + $this->str/3;
                $this->baseHP = 5 + $this->lvl * 3 + $this->tgh;
                $this->crit = $this->lvl/5 + $this->int/3;
                $this->eXatt = $this->lvl/10 + $this->spd/3;
                break;
            case 'strength':
                $this->handSize = 2 + $this->lvl/2 + $this->bal/10;
                $this->dmg = $this->lvl/2 + $this->str/2;
                $this->baseHP = 5 + $this->lvl * 3 + $this->tgh;
                $this->crit = $this->lvl/5 + $this->int/3;
                $this->eXatt = $this->lvl/10 + $this->spd/3;
                break;
            case 'toughness':
                $this->handSize = 2 + $this->lvl/2 + $this->bal/10;
                $this->dmg = $this->lvl/2 + $this->str/3;
                $this->baseHP = 5 + $this->lvl * 3 + $this->tgh*2;
                $this->crit = $this->lvl/5 + $this->int/3;
                $this->eXatt = $this->lvl/10 + $this->spd/3;
                break;
            case 'intelligence':
                $this->handSize = 2 + $this->lvl/2 + $this->bal/10;
                $this->dmg = $this->lvl/2 + $this->str/3;
                $this->baseHP = 5 + $this->lvl * 3 + $this->tgh;
                $this->crit = $this->lvl/5 + $this->int;
                $this->eXatt = $this->lvl/10 + $this->spd/3;
                break;
            case 'speed':
                $this->handSize = 2 + $this->lvl/2 + $this->bal/10;
                $this->dmg = $this->lvl/2 + $this->str/3;
                $this->baseHP = 5 + $this->lvl * 3 + $this->tgh;
                $this->crit = $this->lvl/5 + $this->int/3;
                $this->eXatt = $this->lvl/10 + $this->spd;
                break;
            default:
                echo 'You messed up your character at '.$playerType."<br>";
                break;
        }
        $this->currentHP = $this->baseHP;
        $playlist = self::returnPlaylist($playerID, $fightID);
        $this->mainDeck = DeckMaker::makeDeck($playlist);
        $this->mainDeck->shuffle();
        $this->hand = new Deck();
        $this->hand->construct();
        $this->graveyard = new Deck();
        $this->graveyard->construct();
        $this->currentAtt = new Deck();
        $this->currentAtt->construct();
        $this->attList = new Deck();
        $this->attList->construct();
        $this->fillhand();
    }
    
    private static function returnPlaylist($charID,$chalID)
    {
        $getPlaylist = mysql_query("SELECT m1,m2,m3,m4,m5,m6,m7,m8,m9,m10 FROM chalPlaylists WHERE charID = '".$charID."' AND chalID = '".$chalID."'") or die(mysql_error());
        $row = mysql_fetch_array($getPlaylist,MYSQL_NUM);
        return $row;
    }

    private static function returnCornelius($charID)
    {
        $getCornelius = mysql_query("SELECT damage, strength, toughness, intelligence, speed FROM cornelius WHERE charID = '".$charID."'") or die(mysql_error());
        $row = mysql_fetch_array($getCornelius);
        return $row;
    }
    
    private static function returnType($charID)
    {
        $getCornelius = mysql_query("SELECT Type FROM cornelius WHERE charID = '".$charID."'") or die(mysql_error());
        $row = mysql_fetch_array($getCornelius);
        return $row[0];
    }
    
    function fillHand()
    {
        while($this->hand->getDeckSize()< floor($this->handSize))
        {
            if($this->mainDeck->getDeckSize() == 0)
            {
                $this->mainDeck = $this->graveyard;
                $this->graveyard = new Deck();
                $this->graveyard->construct();
                $this->mainDeck->shuffle();
                
            }
            $this->hand->addCard($this->mainDeck->removeCard(0));
        }
    }
    function playCard()
    {
        if($this->hand->getDeckSize() > 0)
            $this->currentAtt->addCard($this->hand->removeCard(0));
    }
    function doAttack()
    {
        $count = 0;
        while($this->currentAtt->getDeckSize()>0)
        {
            $this->attList->addCard($this->currentAtt->getCard(0));
            $this->graveyard->addCard($this->currentAtt->removeCard(0));
            $count++;
        }
        return $count;
    }
    
    function takeDamage($damage)
    {
        $this->currentHP -= $damage;
    }
    
    function getPlayerNum()
    {
        return $this->playerNum;
    }
    function getPlayerType()
    {
        return $this->playerType;
    }
    function getLvl()
    {
        return $this->lvl;
    }
    function getBalance()
    {
        return $this->bal;
    }
    function getStrength()
    {
        return $this->str;
    }
    function getToughness()
    {
        return $this->tgh;
    }
    function getIntelligence()
    {
        return $this->int;
    }
    function getSpeed()
    {
        return $this->spd;
    }
    function getHandSize()
    {
        return $this->handSize;
    }
    function getDamage()
    {
        return $this->dmg;
    }
    function getBaseHP()
    {
        return $this->baseHP;
    }
    function getCritChance()
    {
        return $this->crit;
    }
    function geteXattackChance()
    {
        return $this->eXatt;
    }
    function getCurrentHP()
    {
        return $this->currentHP;
    }
    function getDeck()
    {
        return $this->mainDeck;
    }
    function getHand()
    {
        return $this->hand;
    }
    function getCurrentAttack()
    {
        return $this->currentAtt;
    }
    function getAttackList()
    {
        return $this->attList;
    }
    function getGraveYard()
    {
        return $this->graveyard;
    }
    
    function getStat($statType)
    {
        switch($statType)
        {
            case 'balance':
                return $this->bal;
                break;
            case 'strength':
                return $this->str;
                break;
            case 'toughness':
                return $this->tgh;
                break;
            case 'intelligence':
                return $this->int;
                break;
            case 'speed':
                return $this->spd;
                break;
            default:
                return 0;
                break;
        }
    }
    
}

?>
