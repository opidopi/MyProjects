<?php
include'/../config.php';

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of CardMaker
 *
 * @author Opie
 */
class DeckMaker 
{
    
    
    public static function makeDeck($playlist)
    {
        $outDeck;
        $tempCard;
        $outDeck = new Deck();
        $outDeck->construct();
        for($i=0;$i<sizeof($playlist);$i++)
        {
            $temp = self::getSubType($playlist[$i]);
            switch($temp)
            {
                case 'move':
                    $tempCard = new PlayableCard();
                    $tempCard->contsruct($playlist[$i],self::getName($playlist[$i]),
                            self::getDamage($playlist[$i]),
                            self::getDefense($playlist[$i]),
                            self::getSkill($playlist[$i]),
                            'move');
                    $outDeck->addCard($tempCard);
                    break;
                case 'special':
                    $tempCard = new PlayableCard();
                    $tempCard->contsruct($playlist[$i],self::getName($playlist[$i]),
                            self::getDamage($playlist[$i]),
                            self::getDefense($playlist[$i]),
                            self::getSkill($playlist[$i]),
                            'special');
                    $outDeck->addCard($tempCard);
                    break;
                case 'block':
                    $tempCard = new PlayableCard();
                    $tempCard->contsruct($playlist[$i],self::getName($playlist[$i]),
                            self::getDamage($playlist[$i]),
                            self::getDefense($playlist[$i]),
                            self::getSkill($playlist[$i]),
                            'block');
                    $outDeck->addCard($tempCard);
                    break;
                default:
                    echo 'you messed up a card at '.$i;
                    break;
            }
        }
        return $outDeck;
    }
    
    private static function getSubType($ID)
    {
        $query = mysql_query("SELECT itemType FROM grafighters_dev.marketplace WHERE ItemID = ".$ID) or die(mysql_error());
        $row = mysql_fetch_array ($query, MYSQL_NUM);
        return $row[0];
    }
    
    private static function getName($ID)
    {
        $query = mysql_query("SELECT itemName FROM grafighters_dev.marketplace WHERE ItemID = ".$ID) or die(mysql_error());
        $row = mysql_fetch_array ($query, MYSQL_NUM);
        return $row[0];
    }
    
    private static function getDamage($ID)
    {
        $query = mysql_query("SELECT Damage FROM grafighters_dev.marketplace WHERE ItemID = ".$ID) or die(mysql_error());
        $row = mysql_fetch_array ($query, MYSQL_NUM);
        return $row[0];
    }
    
    private static function getDefense($ID)
    {
        $query = mysql_query("SELECT Defense FROM grafighters_dev.marketplace WHERE ItemID = ".$ID) or die(mysql_error());
        $row = mysql_fetch_array ($query, MYSQL_NUM);
        return $row[0];
    }
    
    private static function getSkill($ID)
    {
        $query = mysql_query("SELECT itemClass FROM grafighters_dev.marketplace WHERE ItemID = ".$ID) or die(mysql_error());
        $row = mysql_fetch_array ($query, MYSQL_NUM);
        return $row[0];
    }
}

?>
