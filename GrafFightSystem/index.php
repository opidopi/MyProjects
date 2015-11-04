<!DOCTYPE html>
<html>
<style type="text/css">
.clear
{
clear:both;
}
.left
{
float:left;	
padding:5px;
margin:5px;
}
.right
{
float:right;	
padding:5px;
margin:5px;
}
</style>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <?php
            include '_php/CardSource/Card.php';
            include '_php/CardSource/PlayableCard.php';
            include '_php/CardSource/Deck.php';
            include '_php/CardSource/DeckMaker.php';
            include '_php/Character.php';
            include '_php/TestInterface.php';
            include '_php/FightClass.php';
            include '_php/config.php';
            set_time_limit(0);
            //echo "this does work!<br>";
            //$playlist = array();
            /*
            for($i=1;$i<=10;$i++)
            {
                $getPlaylist = mysql_query("SELECT itemID FROM grafighters_dev.marketplace WHERE ItemID = ".$i) or die(mysql_error());
                $row = mysql_fetch_array ($getPlaylist, MYSQL_NUM);
                $playlist[] = $row[0];
            }
            $getCornelius = mysql_query("SELECT damage, strength, toughness, intelligence, speed FROM cornelius WHERE charID = '".'645'."'") or die(mysql_error());
            $row = mysql_fetch_array($getCornelius,MYSQL_NUM);
             */
            //echo $row[0]."<br>";
            /*
            $testPlayer1 = new Character();
            $testPlayer1->construct(943, 1, 'toughness');
            $testPlayer2 = new Character();
            $testPlayer2->construct(914, 2, 'intelligence');
             */
            //$testDeck = DeckMaker::makeDeck($playlist);
            /*
            TestInterface::printPlayer($testPlayer);
            TestInterface::printHand($testPlayer);
            TestInterface::printDeck($testPlayer);
            TestInterface::printCurrentAttack($testPlayer);
            TestInterface::printGraveyard($testPlayer);
             */
            $fight = new FightClass();
            $fight->construct('1', '1', '1');
            $fight->run();
            $temparray = $fight->output();
            $testPlayer1 = $fight->getPlayer1();
            $testPlayer2 = $fight->getPlayer2();
            
        ?>

<div class = "left">
         <div>
         <h3>player 1</h3>
         <div>
         <?php TestInterface::printPlayer($testPlayer1); ?>
         <?php echo 'Final HP: '.$testPlayer1->getCurrentHP(); ?>
         </div>
          <div>
         <?php TestInterface::printHand($testPlayer1); ?>
         <?php TestInterface::printDeck($testPlayer1); ?>
         </div>
         </div>
</div>


<div class = "left">
        <div>
          <h3>player 2</h3>
          <div>
              <?php TestInterface::printPlayer($testPlayer2); ?>
              <?php echo 'Final HP: '.$testPlayer2->getCurrentHP(); ?>
            </div>
          <div>
         <?php TestInterface::printHand($testPlayer2); ?>
         <?php TestInterface::printDeck($testPlayer2); ?>
         </div>
         </div>
</div>

<div class = "clear">
</div>



<div class = "left">
	<div>
        <b>
        ATTACK
        </b>
    </div>
    <div>
    </div>
    <div>
        <div class = "left">
        PLAYER1
        	<div>
            <?php TestInterface::printCurrentAttack($testPlayer1); ?><br>
            <?php TestInterface::printAttackList($testPlayer1); ?><br>
            <div><?php TestInterface::printGraveyard($testPlayer1); ?></div>
            </div>
        </div>
        <div class = "left">
        Win/Loss
        	<div>
                <br>
                <br>
                <br>
                <?php echo TestInterface::printWinList($fight); ?> <br>
            </div>
        </div>
        <div class = "left">
        PLAYER2
        	<div>
                <?php TestInterface::printCurrentAttack($testPlayer2); ?><br>
                <?php TestInterface::printAttackList($testPlayer2); ?><br>
                
        <div><?php TestInterface::printGraveyard($testPlayer2); ?></div>
            </div>
            
        </div>
    </div>
</div>

    </body>
</html>
