<?php

$charID1 = $_POST['charID1'];
$charID2 = $_POST['charID2'];
$chalID = $_POST['chalID'];

include 'CardSource/Card.php';
include 'CardSource/PlayableCard.php';
include 'CardSource/Deck.php';
include 'CardSource/DeckMaker.php';
include 'Character.php';
include 'FightClass.php';
function runFightMaster($charID1,$charID2,$chalID) {
	/*Here is where the magic happens
	do your thing and then at the end it should return something like this, check out interface.php to see what I mean/
	*/

	//this will be multidimensional and crazy, but this is just and example.
	$fight = new FightClass();
        $fight->construct($charID1, $charID2, $chalID);
        $fight->run();
        $fightArrayMaster = $fight->output();

	return $fightArrayMaster;
}

$finalOutput = runFightMaster($charID1,$charID2,$chalID); 
echo json_encode($finalOutput);

?>