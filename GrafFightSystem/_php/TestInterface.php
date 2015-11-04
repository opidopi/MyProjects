<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of TestInterface
 *
 * @author Opie
 */
class TestInterface 
{
    static function printPlayer($testPlayer)
    {
        echo 'Player Number: '.$testPlayer->getPlayerNum()."<br>"
                .'Player Type: '.$testPlayer->getPlayerType()."<br>"
                .'Player Level: '.$testPlayer->getLvl()."<br>";
        echo 'Balance: '.$testPlayer->getBalance()."<br>"
                .'Strength: '.$testPlayer->getStrength()."<br>"
                .'Toughness: '.$testPlayer->getToughness()."<br>"
                .'Intelligence: '.$testPlayer->getIntelligence()."<br>"
                .'Speed: '.$testPlayer->getSpeed()."<br>";
        echo 'Hand Size: '.$testPlayer->getHandSize()."<br>"
                .'Damage: '.$testPlayer->getDamage()."<br>"
                .'Max HP: '.$testPlayer->getBaseHP()."<br>"
                .'Current HP: '.$testPlayer->getCurrentHP()."<br>"
                .'Crit Chance: '.$testPlayer->getCritChance()."<br>"
                .'Extra Attack Chance: '.$testPlayer->geteXattackChance()."<br>";
    }
    
    static function printHand($testPlayer)
    {
        echo "Hand: <br>";
        for($i = 0; $i < $testPlayer->getHand()->getDeckSize(); $i++)
        {
            echo $testPlayer->getHand()->getCard($i)->getID().' '
                    .$testPlayer->getHand()->getCard($i)->getDamage().' '
                    .$testPlayer->getHand()->getCard($i)->getDefense().' '
                    .$testPlayer->getHand()->getCard($i)->getSkill().' '
                    .$testPlayer->getHand()->getCard($i)->getSubType().' '
                    .$testPlayer->getHand()->getCard($i)->getName().'<br>';
        }
    }
    
    static function printDeck($testPlayer)
    {
        echo "Main Deck: <br>";
        for($i = 0; $i < $testPlayer->getDeck()->getDeckSize(); $i++)
        {
            echo $testPlayer->getDeck()->getCard($i)->getID().' '
                    .$testPlayer->getDeck()->getCard($i)->getDamage().' '
                    .$testPlayer->getDeck()->getCard($i)->getDefense().' '
                    .$testPlayer->getDeck()->getCard($i)->getSkill().' '
                    .$testPlayer->getDeck()->getCard($i)->getSubType().' '
                    .$testPlayer->getDeck()->getCard($i)->getName().'<br>';
        }
    }
    
    static function printGraveyard($testPlayer)
    {
        echo "GraveYard: <br>";
        for($i = 0; $i < $testPlayer->getGraveYard()->getDeckSize(); $i++)
        {
            echo $testPlayer->getGraveYard()->getCard($i)->getID().' '
                    .$testPlayer->getGraveYard()->getCard($i)->getDamage().' '
                    .$testPlayer->getGraveYard()->getCard($i)->getDefense().' '
                    .$testPlayer->getGraveYard()->getCard($i)->getSkill().' '
                    .$testPlayer->getGraveYard()->getCard($i)->getSubType().' '
                    .$testPlayer->getGraveYard()->getCard($i)->getName().'<br>';
        }
    }
    
    static function printCurrentAttack($testPlayer)
    {
        echo "Current Attack: <br>";
        for($i = 0; $i < $testPlayer->getCurrentAttack()->getDeckSize(); $i++)
        {
            echo $testPlayer->getCurrentAttack()->getCard($i)->getID().' '
                    .$testPlayer->getCurrentAttack()->getCard($i)->getDamage().' '
                    .$testPlayer->getCurrentAttack()->getCard($i)->getDefense().' '
                    .$testPlayer->getCurrentAttack()->getCard($i)->getSkill().' '
                    .$testPlayer->getCurrentAttack()->getCard($i)->getSubType().' '
                    .$testPlayer->getCurrentAttack()->getCard($i)->getName().'<br>';
        }
    }
    static function printAttackList($testPlayer)
    {
        echo "Attack List: <br>";
        for($i = 0; $i < $testPlayer->getAttackList()->getDeckSize(); $i++)
        {
            echo $testPlayer->getAttackList()->getCard($i)->getID().' '
                    .$testPlayer->getAttackList()->getCard($i)->getDamage().' '
                    .$testPlayer->getAttackList()->getCard($i)->getDefense().' '
                    .$testPlayer->getAttackList()->getCard($i)->getSkill().' '
                    .$testPlayer->getAttackList()->getCard($i)->getSubType().' '
                    .$testPlayer->getAttackList()->getCard($i)->getName().'<br>';
        }
    }
    
    static function printWinList($testfight)
    {
        $retArray = array();
        $retArray = $testfight->getWinList();
        for($i = 0; $i < sizeof($retArray); $i++)
            echo $retArray[$i].'<br>';
    }
}

?>
