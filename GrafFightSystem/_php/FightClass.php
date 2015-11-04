<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of FightClass
 *
 * @author Opie
 */
include'config.php';

class FightClass 
{
    private $player1;
    private $player2;
    private $fightID;
    private $p1ExList;
    private $p2ExList;
    private $p1CritList;
    private $p2CritList;
    private $p1Attack;
    private $p2Attack;
    private $p1AttList;
    private $p2AttList;
    private $p1Damage;
    private $p2Damage;
    private $p1DmgList;
    private $p2DmgList;
    private $p1Defense;
    private $p2Defense;
    private $p1DefList;
    private $p2DefList;
    private $p1Heals;
    private $p1HealList;
    private $p2Heals;
    private $p2HealList;
    private $p1Cardcount;
    private $p2Cardcount;
    private $roundWinner;
    private $winList;
    private $fightWinner;
    private $fightOver;
    private $roundNum;
    private $p1HPlist;
    private $p2HPlist;
    
    function construct($p1ID, $p2ID, $fightID)
    {
        $this->fightID = $fightID;
        $this->player1  = new Character();
        $this->player2 = new Character();
        $this->player1->construct($p1ID, 1, $fightID);
        $this->player2->construct($p2ID, 2, $fightID);
        $this->winList = array();
        $this->p1AttList = array();
        $this->p2AttList = array();
        $this->p1DefList = array();
        $this->p2DefList = array();
        $this->p1DmgList = array();
        $this->p2DmgList = array();
        $this->p1ExList = array();
        $this->p2ExList = array();
        $this->p1CritList = array();
        $this->p2CritList = array();
        $this->p1HealList = array();
        $this->p2HealList = array();
        $this->p1Cardcount = array();
        $this->p2Cardcount = array();
        $this->p1HPlist = array();
        $this->p2HPlist = array();
        $this->fightOver = false;
        $this->roundNum = 0;
    }
    
    function playCards()
    {
        $this->player1->playCard();
        $this->player2->playCard();
    }
    
    function calcEXattack()
    {
        $p1Roll = rand(0,99);
        $p2Roll = rand(0,99);
        $this->p1ExList[] = $p1Roll;
        $this->p2ExList[] = $p2Roll;
        if($p1Roll < $this->player1->geteXattackChance())
            $this->player1->playCard();   
        if($p2Roll< $this->player2->geteXattackChance())
            $this->player2->playCard();
    }
    
    function calcHit()
    {
        $this->p1Attack = 0;
        $this->p2Attack = 0;
        $this->p1Attack = rand(1,20) + ($this->player1->getStat($this->player1->getCurrentAttack()->getCard(0)->getSkill())- 3)/2;
        $this->p2Attack = rand(1,20) + ($this->player2->getStat($this->player2->getCurrentAttack()->getCard(0)->getSkill())- 3)/2;
        if($this->p1Attack > $this->p2Attack)
            $this->roundWinner = 'player1';
        else if($this->p2Attack > $this->p1Attack)
            $this->roundWinner = 'player2';
        else
            $this->roundWinner = 'tie';
        $this->winList[] = $this->roundWinner;
        $this->p1AttList[] = $this->p1Attack;
        $this->p2AttList[] = $this->p2Attack;
    }
    
    function dealDamage()
    {
        $this->p1Damage = 0;
        $this->p2Damage = 0;
        $this->p1Defense = $this->player1->getCurrentAttack()->getCard(0)->getDefense();
        $this->p2Defense = $this->player2->getCurrentAttack()->getCard(0)->getDefense();
        $this->p1Heals = 0;
        $this->p2Heals = 0;
        $this->p1HealList[] = $this->p1Heals;
        $this->p2HealList[] = $this->p2Heals;
        if($this->roundWinner == 'player1')
        {
            $this->p1Damage += $this->player1->getDamage();
            for($i=0; $i< $this->player1->getCurrentAttack()->getDeckSize();$i++)
                $this->p1Damage += $this->player1->getCurrentAttack()->getCard($i)->getDamage();
        }
        else if($this->roundWinner == 'player2')
        {
            $this->p2Damage += $this->player2->getDamage();
            for($i=0; $i<$this->player2->getCurrentAttack()->getDeckSize();$i++)
                $this->p2Damage += $this->player2->getCurrentAttack()->getCard($i)->getDamage();
        }
        $p1Roll = rand(0,99);
        $p2Roll = rand(0,99);
        $this->p1CritList[] = $p1Roll;
        $this->p2CritList[] = $p2Roll;
        if($p1Roll < $this->player1->getCritChance())
            $this->p1Damage += $this->p1Damage;   
        if($p2Roll< $this->player2->getCritChance())
            $this->p2Damage += $this->p2Damage;
        $this->p1DmgList[] = $this->p1Damage;
        $this->p2DmgList[] = $this->p2Damage;
        $this->p1Damage -= $this->p2Defense;
        $this->p2Damage -= $this->p1Defense;
        if($this->p1Damage < 0)
            $this->p1Damage = 0;
        if($this->p2Damage < 0)
            $this->p2Damage = 0;
        $this->p1Damage -= $this->p2Heals;
        $this->p2Damage -= $this->p1Heals;
        $this->player1->takeDamage($this->p2Damage);
        $this->player2->takeDamage($this->p1Damage);
        $this->p1DefList[] = $this->p1Defense;
        $this->p2DefList[] = $this->p2Defense;
        $this->p1HPlist[] = $this->player1->getCurrentHP();
        $this->p2HPlist[] = $this->player2->getCurrentHP();
    }
    
    function endRound()
    {
        $this->p1Cardcount[] = $this->player1->doAttack();
        $this->p2Cardcount[] = $this->player2->doAttack();
        $this->player1->fillHand();
        $this->player2->fillHand();
        if($this->player1->getCurrentHP() < 1 && $this->player2->getCurrentHP() < 1)
        {
            $this->fightOver = true;
            $this->fightWinner = 'tie';
        }
        else if($this->player1->getCurrentHP() < 1)
        {
            $this->fightOver = true;
            $this->fightWinner = 'player2';
        }
        else if($this->player2->getCurrentHP() < 1)
        {
            $this->fightOver = true;
            $this->fightWinner = 'player1';
        }
        $this->roundNum++;   
    }
    
    function run()
    {
        while(!$this->WinCheck())
            {
                $this->playCards();
                $this->calcEXattack();
                $this->calcHit();
                $this->dealDamage();
                $this->endRound();
            }
    }
    
    function output()
    {
        $outArray = array();
        $cardNum = array();
        $cardNum[0] = $this->player1->getAttackList()->getDeckSize();
        $cardNum[1] = $this->player2->getAttackList()->getDeckSize();;
        
        for($i=0;$i<$this->roundNum;$i++)
        {
            for($k=1;$k<=2;$k++)
            {
                $tempPlayer = new Character();
                if($k == 1)
                {
                    $tempPlayer = $this->player1;
                    $cardcount = $this->p1Cardcount;
                }
                else
                {
                    $tempPlayer = $this->player2;
                    $cardcount = $this->p2Cardcount;
                }

                $outArray['player'.$k]['stats'][$i]['number'] = $tempPlayer->getPlayerNum();
                $outArray['player'.$k]['stats'][$i]['type'] = $tempPlayer->getPlayerType();
                $outArray['player'.$k]['stats'][$i]['level'] = $tempPlayer->getLvl();
                $outArray['player'.$k]['stats'][$i]['balance'] = $tempPlayer->getBalance();
                $outArray['player'.$k]['stats'][$i]['strength'] = $tempPlayer->getStrength();
                $outArray['player'.$k]['stats'][$i]['toughness'] = $tempPlayer->getToughness();
                $outArray['player'.$k]['stats'][$i]['intelligence'] = $tempPlayer->getIntelligence();
                $outArray['player'.$k]['stats'][$i]['speed'] = $tempPlayer->getSpeed();
                $outArray['player'.$k]['stats'][$i]['handsize'] = $tempPlayer->getHandSize();
                $outArray['player'.$k]['stats'][$i]['damage'] = $tempPlayer->getDamage();
                $outArray['player'.$k]['stats'][$i]['hp'] = $tempPlayer->getBaseHP();
                $outArray['player'.$k]['stats'][$i]['crit'] = $tempPlayer->getCritChance();
                $outArray['player'.$k]['stats'][$i]['extrahit'] = $tempPlayer->geteXattackChance();
                $outArray['player'.$k]['stats'][$i]['winner'] = $this->fightWinner;

                $offset = 0;
                for($j=0;$j<$this->roundNum;$j++)
                {
                    $outArray['player'.$k]['cardinfo'][$j+$offset] = array();
                    for($o=0;$o<$cardcount[$j];$o++)
                    {
                        if($o > 0)
                        {
                            $offset++;
                        }
                        $outArray['player'.$k]['cardinfo'][$j+$offset]['round'] = $j + 1;
                        $outArray['player'.$k]['cardinfo'][$j+$offset]['ID'] = $tempPlayer->getAttackList()->getCard($j+$offset)->getID();
                        $outArray['player'.$k]['cardinfo'][$j+$offset]['damage'] = $tempPlayer->getAttackList()->getCard($j+$offset)->getDamage();
                        $outArray['player'.$k]['cardinfo'][$j+$offset]['defense'] = $tempPlayer->getAttackList()->getCard($j+$offset)->getDefense();
                        $outArray['player'.$k]['cardinfo'][$j+$offset]['skill'] = $tempPlayer->getAttackList()->getCard($j+$offset)->getSkill();
                        $outArray['player'.$k]['cardinfo'][$j+$offset]['type'] = $tempPlayer->getAttackList()->getCard($j+$offset)->getSubType();
                        $outArray['player'.$k]['cardinfo'][$j+$offset]['name'] = $tempPlayer->getAttackList()->getCard($j+$offset)->getName();
                    }
                }
            }
            $outArray['player1']['roundstats'][$i] = array();
            $outArray['player1']['roundstats'][$i]['round'] = $i + 1;
            $outArray['player1']['roundstats'][$i]['hp'] = $this->p1HPlist[$i];
            $outArray['player1']['roundstats'][$i]['extrahit'] = $this->p1ExList[$i];
            $outArray['player1']['roundstats'][$i]['hitroll'] = $this->p1AttList[$i];
            $outArray['player1']['roundstats'][$i]['crit'] = $this->p1CritList[$i];
            $outArray['player1']['roundstats'][$i]['damage'] = $this->p1DmgList[$i];
            $outArray['player1']['roundstats'][$i]['defense'] = $this->p1DefList[$i];
            $outArray['player1']['roundstats'][$i]['heals'] = $this->p1HealList[$i];
            $outArray['player1']['roundstats'][$i]['winner'] = $this->winList[$i];
            
            $outArray['player2']['roundstats'][$i] = array();
            $outArray['player2']['roundstats'][$i]['round'] = $i + 1;
            $outArray['player2']['roundstats'][$i]['hp'] = $this->p2HPlist[$i];
            $outArray['player2']['roundstats'][$i]['extrahit'] = $this->p2ExList[$i];
            $outArray['player2']['roundstats'][$i]['hitroll'] = $this->p2AttList[$i];
            $outArray['player2']['roundstats'][$i]['crit'] = $this->p2CritList[$i];
            $outArray['player2']['roundstats'][$i]['damage'] = $this->p2DmgList[$i];
            $outArray['player2']['roundstats'][$i]['defense'] = $this->p2DefList[$i];
            $outArray['player2']['roundstats'][$i]['heals'] = $this->p2HealList[$i];
            $outArray['player2']['roundstats'][$i]['winner'] = $this->winList[$i];
        }
        $outArray['beforecard'] = true;
        while($cardNum[0] != $cardNum[1])
        {
            $outArray['addcard'] = true;
            if($cardNum[0] < $cardNum[1])
            {
                $outArray['player1']['cardinfo'][$cardNum[0]]['round'] = 0;
                $outArray['player1']['cardinfo'][$cardNum[0]]['ID'] = 0;
                $outArray['player1']['cardinfo'][$cardNum[0]]['damage'] = 0;
                $outArray['player1']['cardinfo'][$cardNum[0]]['defense'] = 0;
                $outArray['player1']['cardinfo'][$cardNum[0]]['skill'] = 0;
                $outArray['player1']['cardinfo'][$cardNum[0]]['type'] = 0;
                $outArray['player1']['cardinfo'][$cardNum[0]]['name'] = 0;
                $cardNum[0]++;
            }
            else if($cardNum[0] > $cardNum[1])
            {
                $outArray['player2']['cardinfo'][$cardNum[1]]['round'] = 0;
                $outArray['player2']['cardinfo'][$cardNum[1]]['ID'] = 0;
                $outArray['player2']['cardinfo'][$cardNum[1]]['damage'] = 0;
                $outArray['player2']['cardinfo'][$cardNum[1]]['defense'] = 0;
                $outArray['player2']['cardinfo'][$cardNum[1]]['skill'] = 0;
                $outArray['player2']['cardinfo'][$cardNum[1]]['type'] = 0;
                $outArray['player2']['cardinfo'][$cardNum[1]]['name'] = 0;
                $cardNum[1]++;
            }
        }
        $outArray['player1']['cardinfo'][$cardNum[0]]['round'] = 0;
        $outArray['player1']['cardinfo'][$cardNum[0]]['ID'] = 0;
        $outArray['player1']['cardinfo'][$cardNum[0]]['damage'] = 0;
        $outArray['player1']['cardinfo'][$cardNum[0]]['defense'] = 0;
        $outArray['player1']['cardinfo'][$cardNum[0]]['skill'] = 0;
        $outArray['player1']['cardinfo'][$cardNum[0]]['type'] = 0;
        $outArray['player1']['cardinfo'][$cardNum[0]]['name'] = 0;
        $outArray['player2']['cardinfo'][$cardNum[1]]['round'] = 0;
        $outArray['player2']['cardinfo'][$cardNum[1]]['ID'] = 0;
        $outArray['player2']['cardinfo'][$cardNum[1]]['damage'] = 0;
        $outArray['player2']['cardinfo'][$cardNum[1]]['defense'] = 0;
        $outArray['player2']['cardinfo'][$cardNum[1]]['skill'] = 0;
        $outArray['player2']['cardinfo'][$cardNum[1]]['type'] = 0;
        $outArray['player2']['cardinfo'][$cardNum[1]]['name'] = 0;
        return $outArray;
    }
    
    function getPlayer1()
    {
        return  $this->player1;
    }
    
    function getPlayer2()
    {
        return  $this->player2;
    }
    
    function getP1Attack()
    {
        return $this->p1Attack;
    }
    
    function getP2Attack()
    {
        return $this->p2Attack;
    }
    
    function getP1Damage()
    {
        return $this->p1Damage;
    }
    
    function getP2Damage()
    {
        return $this->p2Damage;
    }
    
    function getP1Defense()
    {
        return $this->p1Defense;
    }
    
    function getP2Defense()
    {
        return $this->p2Defense;
    }
    
    function getP1Heals()
    {
        return $this->p1Heals;
    }
    
    function getP2Heals()
    {
        return $this->p2Heals;
    }
    
    function getRoundWinner()
    {
        return $this->roundWinner;
    }
    
    function getWinList()
    {
        return $this->winList;
    }
    
    function WinCheck()
    {
        return $this->fightOver;
    }
    
    function getWinner()
    {
        return $this->fightWinner;
    }
    
}

?>
