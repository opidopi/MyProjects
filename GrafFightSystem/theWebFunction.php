<?php
include($_SERVER['DOCUMENT_ROOT'].'/_php/config.php');
include($_SERVER['DOCUMENT_ROOT'].'/_php/addAward.php');

/*
if (empty($charID1)) {$charID1 = 915;}
if (empty($charID2)) {$charID2 = 847;}

$charID_player = 1023;
$charID_opponent = 995;
$stageID = 'ghosttown';
$chalID = 5674;
*/


$createdFightArray = masterWeb($charID_player, $charID_opponent, $stageID, $chalID);

//echo "<pre>" . print_r($createdFightArray, true) . "</pre>";
///////////////////////////////// FUNCTION PARTY!/////////////////////////////////////////
	function returnPlaylist($charID,$chalID){
		$getPlaylist = mysql_query("SELECT m1,m2,m3,m4,m5,m6,m7 FROM chalPlaylists WHERE charID = '".$charID."' AND chalID = '".$chalID."'") or die(mysql_error());
		$row = mysql_fetch_array($getPlaylist,MYSQL_NUM);
		return $row;
	}
	
	function returnCornelius($charID){
		$getCornelius = mysql_query("SELECT damage, strength, toughness, intelligence, speed FROM cornelius WHERE charID = '".$charID."'") or die(mysql_error());
		$row = mysql_fetch_assoc($getCornelius);
		return $row;
	}
	
	function checkTie($p1MoveClass, $p2MoveClass) {
		if ($p1MoveClass == 'intelligence') {
			if ($p2MoveClass == 'strength' || $p2MoveClass == 'speed') { $winner = 'p1'; } else { $winner = 'p2';}
		}
		if ($p1MoveClass == 'strength') {
			if ($p2MoveClass == 'speed' || $p2MoveClass == 'toughness') { $winner = 'p1'; } else { $winner = 'p2';}
		}
		if ($p1MoveClass == 'speed') {
			if ($p2MoveClass == 'toughness' || $p2MoveClass == 'damage') { $winner = 'p1'; } else { $winner = 'p2';}
		}
		if ($p1MoveClass == 'toughness') {
			if ($p2MoveClass == 'damage' || $p2MoveClass == 'intelligence') { $winner = 'p1'; } else { $winner = 'p2';}
		}
		if ($p1MoveClass == 'damage') {
			if ($p2MoveClass == 'intelligence' || $p2MoveClass == 'strength') { $winner = 'p1'; } else { $winner = 'p2';}
		}
		return $winner;
	}
		
	function getMoveInfo($itemID) {
			
		$masterSQL = mysql_query("SELECT * FROM marketplace WHERE itemID = $itemID") or die(mysql_error()."Getting move information problem");
		$row = mysql_fetch_array($masterSQL,MYSQL_ASSOC);
		return $row;
	}
	
	function getMoveAnimation($itemID) {
			
		$masterSQL = mysql_query("SELECT actionNameNum FROM moves_actionName WHERE itemID = $itemID") or die(mysql_error()."Move animation problem");
		$row = mysql_fetch_array($masterSQL,MYSQL_ASSOC);
		return $row;
	}
	
	function checkTriggerCriteria($persp,$outcome, $bonusTriggerType) {
	// this checks if the bonus is elidgible for happening
		//check if the bonus has triggerd
		//make sure we have the right perspective
		$trigger = false;
		if ($bonusTriggerType == 'win') {
			if($persp == 'p1' && $outcome == 1) {
				$trigger = true;	
			}
			if($persp == 'p1' && $outcome == 2) {
				$trigger = false;	
			}
			if($persp == 'p2' && $outcome == 1) {
				$trigger = false;	
			}
			if($persp == 'p2' && $outcome == 2) {
				$trigger = true;	
			}
		}
		if ($bonusTriggerType == 'loss') {
			if($persp == 'p1' && $outcome == 1) {
				$trigger = false;	
			}
			if($persp == 'p1' && $outcome == 2) {
				$trigger = true;	
			}
			if($persp == 'p2' && $outcome == 1) {
				$trigger = true;	
			}
			if($persp == 'p2' && $outcome == 2) {
				$trigger = false;	
			}
		}
		if ($bonusTriggerType == 'either') {
			$trigger = true;	
		}
		return $trigger;
	}
	
	
	
	function executeBonus($playerItemID, $opponentItemID, $playerActionNameNum, $opponentActionNameNum, $playerMoveNum, $playerMoveClass, $opponentMoveNum,$bonusType, $bonusValue, $bonusTurnCount,$diff,$playerMoveListArray, $playerMoveType, $opponentMoveType, $moveStreakCount, $slotNumber) {
		$bonusOutcomeArray = array();
		$bonusOutcomeArray['turns'] = 0;
		$bonusOutcomeArray['value'] = 0;
		$bonusOutcomeArray['display'] = 0;
		$bonusOutcomeArray['positive'] = 0;
		$bonusOutcomeArray['subValue'] = 0;
		$bonusOutcomeArray['subTurn'] = false;
		
		//$subTurnBoolean = false;
		$bonusOutcomeArray['mathModifier'] = 0;//may be used in the futre to degrade bonuses over time
		if ($playerMoveType == 'special') {
			$bonusOutcomeArray['special'] = true;
			////////////////////////////////////////////SPECIAL MOVES ONLY////////////////////////////////////
			if ($playerMoveClass == 'intelligence') {
				//Calculus = 10% chance of a critcal strike
				$chanceOutcome = testYourLuck(.1);
				if($chanceOutcome == true) {
					$bonusOutcomeArray['value'] = 10;
					$bonusOutcomeArray['display'] = "+ 10";
				} else {
					$bonusOutcomeArray['value'] = 0;
					$bonusOutcomeArray['display'] = "+ 0";
				}
			}
			if ($playerMoveClass == 'strength') {
				//Timebomb = 2x the number thrown
				$bonusOutcomeArray['value'] = $slotNumber*2;
				$bonusOutcomeArray['display'] = "+".$slotNumber*2;
			}
			if ($playerMoveClass  == 'speed') {
				echo "moves streak = $moveStreakCount";
				//Hat Trick = Complete 3 moves in arow with this being the third, adds up all 3, does that much damage
				if ($moveStreakCount == 2) {
					if($i >= 2) {
						$combo1 = getMoveInfo($playerMoveListArray[$i-2]);
						$combo2 = getMoveInfo($playerMoveListArray[$i-1]);
						$finalCombo = $combo1['itemLevel'] + $combo2['itemLevel'] + 3;
					} else if ($i == 1) {
						$finalCombo = 3;
					
					} else if($i == 0) {
						$finalCombo = 3;
					}
					$bonusOutcomeArray['value'] = $finalCombo;
					$bonusOutcomeArray['display'] = $finalCombo;
				} else {
					$bonusOutcomeArray['value'] = 0;
					$bonusOutcomeArray['display'] = "+ 0";	
				}
			}
			if ($playerMoveClass == 'toughness') {
				//set the turn count to move num, maybe check for multiple lingering moves?
				//this should probably do damage eh?
				$bonusOutcomeArray['value'] = $opponentMoveNum;
				$bonusOutcomeArray['display'] = "+ ".$opponentMoveNum;
			}
			if ($playerMoveClass == 'damage') {
				
				$bonusOutcomeArray['value'] = 4;
				$bonusOutcomeArray['subValue'] = 2;
				$bonusOutcomeArray['display'] = "5x";
			}
				
			////////////////////////////////////////////REGULAR MOVES ONLY////////////////////////////////////
		} else {
			if ($bonusType == 'counter') {
				//intel
				if ($diff == 0) { $diff=1;}
				$bonusOutcomeArray['value'] = $opponentMoveNum;
				$bonusOutcomeArray['display'] = "+ ".$opponentMoveNum;
				
				$bonusOutcomeArray['subTurn'] = true;
				$bonusOutcomeArray['subTurnMove'] = $opponentActionNameNum; //do the opponents move against them, this is the ID
				$bonusOutcomeArray['subTurnMoveMeta'] = $opponentItemID; //do the opponents move against them
			}
			if ($bonusType == 'mult') {
				//strength
				if ($diff ==0) { $diff=1;}
				$diffMult = $diff*$bonusValue; //only 1 because we redid it
				$bonusOutcomeArray['value'] = $diffMult - $diff;
				$bonusOutcomeArray['display'] = "x".$bonusValue;
				$bonusOutcomeArray['subTurn'] = false;
			}
			if ($bonusType  == 'combo') {
				//speed
				$salt = rand(1,7); //eventually this will check a real move from your deck
				
				$moveInfo = getMoveInfo($playerMoveListArray[$salt-1]);
				$moveInfo['itemLevel'];
				$bonusOutcomeArray['value'] = $moveInfo['itemLevel'];
				$bonusOutcomeArray['display'] = "+ ".$moveInfo['itemLevel'];
				
				$bonusOutcomeArray['subTurn'] = true;
				$bonusOutcomeArray['subTurnMove'] = $moveInfo['actionNameNum'];
				$bonusOutcomeArray['subTurnMoveMeta'] = $moveInfo['itemID']; //randomly pick a move from your deck and do it
			}
			if ($bonusType == 'regen') {
				//set the turn count to move num, maybe check for multiple lingering moves?
				$bonusOutcomeArray['turns'] = $bonusTurnCount;
				$bonusOutcomeArray['value'] = $bonusValue;
				$bonusOutcomeArray['display'] = $bonusTurnCount."x";
				
				$bonusOutcomeArray['subTurn'] = true;
				$bonusOutcomeArray['subTurnMove'] = 51; //put whatever move will become "regen"
				$bonusOutcomeArray['subTurnMoveMeta'] = NULL;
			}
			if ($bonusType == 'bleed') {
				//damage
				$bonusOutcomeArray['turns'] = $bonusTurnCount;
				$bonusOutcomeArray['value'] = $bonusValue;
				$bonusOutcomeArray['display'] = $bonusTurnCount."x";
				
				$bonusOutcomeArray['subTurn'] = true;
				$bonusOutcomeArray['subTurnMove'] =51; //put whatever move will become "regen"
				$bonusOutcomeArray['subTurnMoveMeta'] = NULL;
			}
		}
		return $bonusOutcomeArray;
	}
	
	//check for a chance value
	function testYourLuck($bonusTriggerVal) {
		$chance = $bonusTriggerVal*10;
		$randKey = mt_rand(1, 10);
		if ($randKey < $chance) {
			return true;
		} else {
			//no bonus
			return false;
		}
	}	
		
	function writeDB($fightID,$turnNumber,$playerNum, $uniqueMoveID, $moveType, $scoringMoveType, $bonusEvent, $health, $reaction) {
		$sqlInsert = mysql_query("INSERT INTO fightDesc (fightID,orderNum, playerNum, actionNameNum, moveType, scoringMoveType, bonusEvent, health, moveMeta)
					VALUES ('".$fightID."','".$turnNumber."','".$playerNum."','".$uniqueMoveID."','".$moveType."', '".$scoringMoveType."','".$bonusEvent."','".$health."','".$reaction."')")or die(mysql_error());
		//$insertReturn = mysql_query($sqlInsert) or die(mysql_error());
		return $sqlInsert;
	}
	
		
	
	
	function updateSummary($fightID, $charID_winner, $charID_loser,$finalHealthList,$finalOutput,$charID_player,$charID_opponent, $p1StartingHealth, $p2StartingHealth) {
		//echo "i made it";
		$addWinner = mysql_query("UPDATE fight SET charID_winner ='".$charID_winner."', charID_loser ='".$charID_loser."', fightOutput ='".$finalOutput."',
									healthList ='".$finalHealthList."', player_start_health = '".$p1StartingHealth."', opponent_start_health = '".$p2StartingHealth."'
									WHERE fightID=".$fightID."") or die(mysql_error());
		//$summaryArray = array();						
						
		include($_SERVER['DOCUMENT_ROOT'].'/_php/fightsave.php');	
		
		
		//$summaryArray['fightID'] = $fightID;
		//$summaryArray['notifyLevelUp']= $notifyLevelUp;
		return $fightSaveReturnArray;
	}
	
	
	function generateDance() {
		$salt2 = rand(101,113);	
		$getDance = mysql_query("SELECT fightwebname FROM moves_actionName WHERE actionNameNum = '".$salt2."'") or die(mysql_error());
		$row = mysql_fetch_array($getDance,MYSQL_ASSOC);
		$fightWebName = $row['fightwebname'];
		$danceArray = array("fightWebName" => $fightWebName, "danceNum" => $salt2);
		return $danceArray;
		
	
	}
	function getReactionRef($reactionMoveNew) {
			
		return $reactionMoveOld;
	}
	

////////////////////////////// FUNCTION PARTY END ///////////////////////////////////////////////









function masterWeb($charID_player, $charID_opponent, $stageID, $chalID) {
	//we should prob put some logic in that says, if these things arent present, then do nothing. Ya know, basic error handling.
	//$charID1 = $_POST['charID_player'];
	//$charID2 = $_POST['charID_opponent'];
	//$stageID = $_POST['stageID'];
	//$chalID = $_POST['chalID'];
	
	//comment these out for real
	//$charID1 = 1204;
	//$charID2 = 1000;
	
	//fallback for testing

	$charID1 = $charID_player;
	$charID2 = $charID_opponent; 
	
	
	
	
	$p1_cornelius= array();
	$p2_cornelius= array();
	$p1_playlist= array();
	$p2_playlist= array();
		
	
	
	$p1_playlist =returnPlaylist($charID1,$chalID);
	$p2_playlist =returnPlaylist($charID2,$chalID);
	
	$p1_cornelius = returnCornelius($charID1);
	$p2_cornelius = returnCornelius($charID2);
	
	//print($p1_playlist_raw);
		//echo"<br />";
		//print($p2_playlist_raw);
	
	////////////////////// Calculate Health ////////////////////////////
	$p1health = (array_sum($p1_cornelius)-3);// Base is 10
	$p2health = (array_sum($p2_cornelius)-3);// Base is 10
	
	$p1Streak = 0;
	$p2Streak = 0;
	
	$p1StartingHealth = $p1health;
	$p2StartingHealth = $p2health;
	
	//missing the winner and loser, stay tuned to find out who wins.
	$idInsert = mysql_query("INSERT INTO fight (charID_player, charID_opponent, stageID, fightTime,chalID) VALUES ('".$charID1."','".$charID2."','".$stageID."', NOW(),'".$chalID."')") or die(mysql_error());
	$fightID= mysql_insert_id();	
	
	
	//get a random dance from this function
	$danceArray = array();
	$danceArray = generateDance();
	
	$globalTurnCount = 0;

	////////////////////////////////////////////////////////////////////////////////////////////MAINN LOOP START ////////////////////////////////////////////////////////////
		
		//function that loops through playlist 1 and 2 and compares the numbers
		$mainExtra= array();
		$tgArray = array();
		for ($i=0; $i<7; $i++) {
	
			$p1ItemVal = $p1_playlist[$i];	
			$p2ItemVal = $p2_playlist[$i];
			//DO THE MAIN DB CALL HERE TO GET EVERYTHING WE NEED? MAKE EXTERNAL FUNCTION
			$p1MoveMasterArray = getMoveInfo($p1ItemVal);
			$p2MoveMasterArray = getMoveInfo($p2ItemVal);
			
	
			//print_r($p1MoveMasterArray);
			//echo "</br>";
			//print_r($p2MoveMasterArray);
			
			$p1BonusTriggerType = $p1MoveMasterArray['bonusTriggerType'];
			$p1BonusTriggerVal = $p1MoveMasterArray['bonusTriggerVal'];
			$p1BonusType = $p1MoveMasterArray['bonusType'];
			$p1BonusValue = $p1MoveMasterArray['bonusValue'];
			$p1BonusTurnCount = $p1MoveMasterArray['bonusTurnCount'];
			
			
			$p2BonusTriggerType = $p2MoveMasterArray['bonusTriggerType'];
			$p2BonusTriggerVal = $p2MoveMasterArray['bonusTriggerVal'];
			$p2BonusType = $p2MoveMasterArray['bonusType'];
			$p2BonusValue = $p2MoveMasterArray['bonusValue'];
			$p2BonusTurnCount = $p2MoveMasterArray['bonusTurnCount'];
				
				
			//$bonusTriggerType;
			//--------win, loss, special
			
			//$bonusTriggerVal;
			//---------1 = 100% chance of happening, other values could be other percents
			
			//$bonusType;
			//--------regen, bleed, combo, counter, mult
			
			//$bonusValue;
			//--------the value for that particular bonus, used for differnt calculations depending on the type
			
			//$bonusTurnCount;
			//--------if the bonus last for more that 1 turn
			$p1ActionNameNum = $p1MoveMasterArray['actionNameNum'];
			$p2ActionNameNum = $p2MoveMasterArray['actionNameNum'];
	
			$p1MoveClass = $p1MoveMasterArray['itemClass'];
			$p2MoveClass = $p2MoveMasterArray['itemClass'];
			
			$p1MoveType = $p1MoveMasterArray['itemType'];
			$p2MoveType = $p2MoveMasterArray['itemType'];
			
			$p1MoveNum = $p1MoveMasterArray['itemLevel'];
			$p2MoveNum = $p2MoveMasterArray['itemLevel'];
			
			$p1OldRef = $p1MoveMasterArray['itemRef'];
			$p2OldRef = $p2MoveMasterArray['itemRef'];
			
			$p1Reaction = $p1MoveMasterArray['reactionMove'];
			$p2Reaction = $p2MoveMasterArray['reactionMove'];
			
			$subTurnBoolean = false;
			
			$p2health = (int) $p2health;
			$p1health = (int) $p1health;
			$p1MoveNum = (int) $p1MoveNum;
			$p2MoveNum = (int) $p2MoveNum;	
			
			//echo "p1 is throwing: $p1MoveClass $p1MoveNum";
			//echo "p2 is throwing: $p2MoveClass $p2MoveNum";
		
			//echo "p1 reaction is $p1Reaction";
			//echo "p2 reaction is $p2Reaction";
			$diff = 0;
			
			//echo "<div class='moveEntry'>";
	
			////////////////////for every move check the winner/////////////////////
			
			//RULES
			//if its a special, it automatically wins against other moves (except block)
			//if specials tie, consult checkTie
			//if its a block it trumps all
			if ($p1MoveType == "block" || $p2MoveType == "block") {
				//move is blocked, on to the next one
				$outcome = 0;
				echo"things are blocked";
			} else if ($p1MoveType == "special" || $p2MoveType == "special") {
				//there is a special move happening
				echo"specials!";
				if($p1MoveType == "special" && $p2MoveType == "special") {
					$tiebreaker = "";
					$tiebreaker = checkTie($p1MoveClass,$p2MoveClass);
					if ($tiebreaker == 'p1') {
						$outcome = 1;
					} if ($tiebreaker == 'p2') {
						$outcome = 2;
					}
				} else if ($p1MoveType == "special") {
					$outcome = 1;
				} else if($p2MoveType == "special") {
					$outcome = 2;
				}
				
			} else {
				echo"regs!";
				if ($p1MoveNum > $p2MoveNum) {
					$diff = $p1MoveNum - $p2MoveNum; 
					$p2health = $p2health - $diff;
					$outcome = 1;
				}
				if ($p2MoveNum > $p1MoveNum) {
					$diff = $p2MoveNum - $p1MoveNum; 
					$p1health = $p1health - $diff;
					$outcome = 2;
				}
				if ($p1MoveNum == $p2MoveNum) {
					$outcome = 0;// three?
					$diff = $p1MoveNum;
					//check for exact tie first?
					//this deal with ties right here
					
					$tiebreaker = "";
					if ($p1MoveClass == $p2MoveClass) { $outcome=0;} else { $tiebreaker = checkTie($p1MoveClass,$p2MoveClass);}
					if ($tiebreaker == 'p1') {
						$outcome = 1;
						$p2health = $p2health - $p1MoveNum;
					} if ($tiebreaker == 'p2') {
						$outcome = 2;
						$p1health = $p1health - $p2MoveNum;
					}
				}
			}
			
			if ($outcome == 1) {
				$p1Streak++;
				$p2Streak = 0;
			}
			if ($outcome == 2) {
				$p1Streak = 0;
				$p2Streak++;
			}
			if ($outcome == 0) {
				$p1Streak = 0;
				$p2Streak = 0;
			}
			////echo "</br> oh wow, the outcome of this move is ".$outcome." because the winner is ".$tiebreaker."</br>";
			
			/////////////////////Bonus Logic Start//////////////////////
			//if the outcome isnt a DEAD TIE aka outcome = 0 then we can have bonuses, yay!
			$p1TriggerOK = false;
			$p2TriggerOK = false;
			if ($outcome != 0) {//customize the trigger type
				$p1TriggerOK = checkTriggerCriteria('p1',$outcome,$p1BonusTriggerType);
				$p2TriggerOK = checkTriggerCriteria('p2',$outcome,$p2BonusTriggerType);
			}
			
			////echo $p1TriggerOK;
			////echo $p2TriggerOK;
			
			$p1MoveBonusResult = "";
			$p2MoveBonusResult = "";
			$p1BonusHappened = false;
			$p2BonusHappened = false;
			////hmmmmmmmmmm how do we order this by bonus type? make counter come after?
			if($p1TriggerOK == true) {
				
				if ($p1BonusTriggerVal !=1) {//that means this is a chance bonus
					$p1Chance = testYourLuck($p1BonusTriggerVal);
					if($p1Chance == true) {
						$p1BonusHappened = true;
						//echo "p1 bonus has been executed";
						$p1MoveBonusResult = executeBonus($p1ItemVal, $p2ItemVal,$p1ActionNameNum, $p2ActionNameNum, $p1MoveNum, $p1MoveClass, $p2MoveNum, $p1BonusType, $p1BonusValue, $p1BonusTurnCount, $diff, $p1_playlist, $p1MoveType, $p2MoveType,$p1Streak,$i);
					}
				} else {
					$p1BonusHappened = true;
					//echo "p1 bonus has been executed";
					$p1MoveBonusResult = executeBonus($p1ItemVal, $p2ItemVal, $p1ActionNameNum, $p2ActionNameNum, $p1MoveNum, $p1MoveClass, $p2MoveNum, $p1BonusType, $p1BonusValue, $p1BonusTurnCount, $diff, $p1_playlist, $p1MoveType, $p2MoveType,$p1Streak,$i);
				}
			}
			if($p2TriggerOK == true) {
				if ($p2BonusTriggerVal !=1) {//that means this is a chance bonus
					$p2Chance = testYourLuck($p2BonusTriggerVal);
					if($p2Chance == true) {
						$p2BonusHappened = true;
						//echo "p2 bonus has been executed";
						$p2MoveBonusResult = executeBonus($p2ItemVal, $p1ItemVal, $p2ActionNameNum, $p1ActionNameNum, $p2MoveNum, $p2MoveClass, $p1MoveNum, $p2BonusType, $p2BonusValue, $p2BonusTurnCount, $diff, $p2_playlist, $p2MoveType, $p1MoveType,$p2Streak,$i);
					}
				} else {
					$p2BonusHappened = true;
					//echo "p2 bonus has been executed";
					$p2MoveBonusResult = executeBonus($p2ItemVal, $p1ItemVal, $p2ActionNameNum, $p1ActionNameNum, $p2MoveNum, $p2MoveClass, $p1MoveNum, $p2BonusType, $p2BonusValue, $p2BonusTurnCount, $diff, $p2_playlist, $p2MoveType, $p1MoveType,$p2Streak,$i);	
				}
			}
			//print_r($p2MoveBonusResult);
			//print_r($p1MoveBonusResult);
			
			

			///////////////////Apply the Bonuses////////////////////////////
			
			//p1
			if($p1BonusHappened == true) {
				echo "bonus type = $p1BonusType";
				if ($p1BonusType == "special") {
						if ($p1MoveClass == "strength") { 
							$p2health -= $p1MoveBonusResult['value'];
						}
						if ($p1MoveClass == "damage") { 
							$p2health -= $p1MoveBonusResult['value'];
						}
						if ($p1MoveClass == "speed") { 
							$p2health -= $p1MoveBonusResult['value'];
						}
						if ($p1MoveClass == "intelligence") { 
							$p2health -= $p1MoveBonusResult['value'];
						}
						if ($p1MoveClass == "toughness") { 
						 	$p1health += $p1MoveBonusResult['value'];
						}
				}
				
				if ($p1BonusType == "mult" || $p1BonusType == "combo" || $p1BonusType == "counter") { //these hurt the other person
					$p2health -= $p1MoveBonusResult['value']; //aka just do it right now
				} 
				if ($p1BonusType == "regen") { 
						$tgArray['count'] = $p1BonusTurnCount;
						$tgArray['type'] = "regen";
						$tgArray['victim'] = "p1";
						$tgArray['val'] = $p1MoveBonusResult['value'];
						//echo "<div class='damage rightz'>".$p1BonusResult['class']."regen bonus</div>";
						$mainExtra[] = $tgArray;
				}
				if ($p1BonusType == "bleed") { 
						$tgArray['count'] = $p1BonusTurnCount;
						$tgArray['type'] = "bleed";
						$tgArray['victim'] = "p2";
						$tgArray['val'] = $p1MoveBonusResult['value'];
						//echo "<div class='damage rightz'>".$p1BonusResult['class']."damage bonus</div>";
						$mainExtra[] = $tgArray;
				}
			}
				
			
			//p2
			if($p2BonusHappened == true) {
				echo "bonus type = $p2BonusType";
				if ($p2BonusType == "special") {
						if ($p2MoveClass == "strength") { 
							$p1health -= $p2MoveBonusResult['value'];
						}
						if ($p2MoveClass == "damage") { 
							$p1health -= $p2MoveBonusResult['value'];
						}
						if ($p2MoveClass == "speed") { 
							$p1health -= $p2MoveBonusResult['value'];
						}
						if ($p2MoveClass == "intelligence") { 
							$p1health -= $p2MoveBonusResult['value'];
						}
						if ($p2MoveClass == "toughness") { 
						 	$p2health += $p2MoveBonusResult['value'];
						}
				}
				if ($p2BonusType == "mult" || $p2BonusType == "combo" || $p2BonusType == "counter") { //these hurt the other person
					$p1health -= $p2MoveBonusResult['value']; //aka just do it right now
					
				} 
				if ($p2BonusType == "regen") { 
						$tgArray['count'] = $p2BonusTurnCount;
						$tgArray['type'] = "regen";
						$tgArray['victim'] = "p2";
						$tgArray['val'] = $p2MoveBonusResult['value'];
						//echo "<div class='damage rightz'>".$p2BonusResult['class']." regen bonus</div>";
						$mainExtra[] = $tgArray;
						
				}
				if ($p2BonusType == "bleed") { 
						$tgArray['count'] = $p2BonusTurnCount;
						$tgArray['type'] = "bleed";
						$tgArray['victim'] = "p1";
						$tgArray['val'] = $p2MoveBonusResult['value'];
						//echo "<div class='damage rightz'>".$p2BonusResult['class']."damage bonus</div>";
						$mainExtra[] = $tgArray;
						
				}
			}
			
	

			
			
			
				
			////////////////////////// for bonuses that happen over time///////////////////////////////
			$p1BuildString = "";
			$p2BuildString = "";
			
			foreach ($mainExtra as  $linger) {
				if($linger['count'] >= 1) {
					if($linger['victim'] == "p1") {
						if($linger['type'] == "regen") {
							$p1health += $linger['val'];
							$p1BuildString .= $linger['type'].",+".$linger['val'].",";
							$subTurnBoolean = true;
						}
						if($linger['type'] == "bleed") {
							$p1health -= $linger['val'];
							//echo "haaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
							//echo "<div class='extra_bleed left'>p1-1</div>";
							$p1BuildString .= $linger['type'].",-".$linger['val'].",";
							$subTurnBoolean = true;
						}
					}
					if($linger['victim'] == "p2") {
						if($linger['type'] == "regen") {
							$p2health += $linger['val'];
							//echo "<div class='extra_heal right'>p2+1</div>";
							$p2BuildString .= $linger['type'].",+".$linger['val'].",";
							$subTurnBoolean = true;
						}
						if($linger['type'] == "bleed") {
							$p2health -= $linger['val'];
							//echo "hoooooooooooooooooooooooooooooooooooooooooooooooooo";
							//echo "<div class='extra_bleed right'>p2-1</div>";
							$p2BuildString .= $linger['type'].",-".$linger['val'].",";
							$subTurnBoolean = true;
						}
					}
				}
			}
			
			
			for ($k = 0; $k < count($mainExtra); $k++) {
				$mainExtra[$k]['count']--;
			}
			
			
			
			///need to think about what consitiutes a bouns, this only accounts for the winner bonus, not anything lingering
			
			$subTurnMove = 0;
			$subTurnMoveMeta = 0;
			$subTurnValue = 0;
			if ($p2MoveBonusResult['subTurn'] == true) {
				$subTurnBoolean = true;

				$p1SubTurnValue = $p2MoveBonusResult['value'];
				$p1SubTurnMove = $p2MoveBonusResult['subTurn'];
				$p1SubTurnMoveMeta = $p2MoveBonusResult['subTurnMoveMeta'];
				$p1BonusSubArray = 
				
				$p2SubTurnValue = $p2MoveBonusResult['value'];
				$p2SubTurnMove = $p2MoveBonusResult['subTurn'];
				$p2SubTurnMoveMeta = $p2MoveBonusResult['subTurnMoveMeta'];
			}
			
			
			
			
			
			
			
		
			///// just logic for displaying turn health
			if ($outcome == 1) {
				//echo "<div class='rslt'>p1:$p1health p1 wins p2:$p2health</div>";
			}
			if ($outcome == 2) {
				//echo "<div class='rslt'>p1:$p1health p2 wins p2:$p2health</div>";
			}
			if ($outcome == 3) {
				//echo "<div class='rslt'>p1:$p1health its a tie p2:$p2health</div>";
			}
			//echo "</div>";
			
			$getMeOuttaHere = false;
			//////////////// FIGHT END CRITERIA//////////////////
				if ($p2health <= 0 && $p1health > 0 ) {
					//$i = 11;
					$fightResult = 1;
					//echo "</br>player1 wins the fight</br>";
					$getMeOuttaHere = true;
				} else if ($p1health <= 0 && $p2health > 0) {
					//$i = 11;
					$fightResult = 2;
					//echo "</br>player2 wins the fight</br>";
					$getMeOuttaHere = true;
				} else if ($p2health <= 0 && $p1health <= 0 ) {
					//$i = 11;
					$fightResult = 3;
					//echo "</br>its a tie, both below 0</br>";
					$getMeOuttaHere = true;
				} else if ($i >= 6 && $p2health >= 0 && $p1health >= 0 ) {
					//$i = 11;
					// echo "still here";
					$fightResult = 3;
					//echo "</br>its a tie, both below 0</br>";
					$getMeOuttaHere = true;
				} 
			
			
			//for the DB

			if ($outcome == 1) { 
				$p1MoveType = 'offensive';
				$p2MoveType = 'defensive';
				$p1ScoringMoveType = 'win';
				$p2ScoringMoveType = 'loss';
				
			} if ($outcome == 2) { 
				$p1MoveType = "defensive";
				$p2MoveType = "offensive";
				$p1ScoringMoveType = 'loss';
				$p2ScoringMoveType = 'win';
			}
			if ($outcome == 0) { 
				$p1MoveType = 'tie';
				$p2MoveType = 'tie';
				$p1ScoringMoveType = 'tie';
				$p2ScoringMoveType = 'tie';
			} 
			
			//build the bonus event string
			$p1BonusEvent = "";
			$p2BonusEvent = "";
			
			$p1BonusEvent = array();
			$p2BonusEvent = array();
			
			//This is seperate duplicate logic for DB DISPLAY ONLY
			if($p1BonusHappened == true) {
				//echo "here we are1";
				if ($p1BonusType == "mult") {// x up
					//$p1BonusEvent = $p1BonusType.",x".$p1MoveBonusResult['value'];
					$p1BonusEvent[] = array($p1BonusType, $p1MoveBonusResult['display']);
				}
				if ($p1BonusType == "combo") { //these hurt the other person
					//$p1BonusEvent = $p1BonusType.",+".$p1MoveBonusResult['value'];
					$p1BonusEvent[] = array($p1BonusType, $p1MoveBonusResult['display']);
				} 
				if ($p1BonusType == "regen") { 
					//$p1BonusEvent = $p1BonusType.",+".$p1MoveBonusResult['value'];
					$p1BonusEvent[] = array($p1BonusType, $p1MoveBonusResult['display']);
				}
				if ($p1BonusType == "bleed") {
					//$p2BonusEvent = $p1BonusType.",-".$p1MoveBonusResult['value'];
					$p1BonusEvent[] = array($p1BonusType, $p1MoveBonusResult['display']);
				}
				if ($p1BonusType == "counter") {
					//$p2BonusEvent = $p1BonusType.",-".$p1MoveBonusResult['value'];
					$p1BonusEvent[] = array($p1BonusType, $p1MoveBonusResult['display']);
				}
			}
			
			if($p2BonusHappened == true) {
				//echo "here we are2";
				if ($p2BonusType == "mult") {// x up
					//$p2BonusEvent = $p2BonusType.",x".$p2MoveBonusResult['value'];
					$p2BonusEvent[] = array($p2BonusType,$p2MoveBonusResult['display']);
				}
				if ($p2BonusType == "combo") { //these hurt the other person
					//$p2BonusEvent = $p2BonusType.",+".$p2MoveBonusResult['value'];
					$p2BonusEvent[] = array($p2BonusType, $p2MoveBonusResult['display']);
				} 
				if ($p2BonusType == "regen") { 
					//$p2BonusEvent = $p2BonusType.",+".$p2MoveBonusResult['value'];
					$p2BonusEvent[] = array($p2BonusType, $p2MoveBonusResult['display']);
				}
				if ($p2BonusType == "bleed") {
					//$p1BonusEvent = $p2BonusType.",-".$p2MoveBonusResult['value'];	
					$p2BonusEvent[] = array($p2BonusType, $p2MoveBonusResult['display']);
				}
				if ($p2BonusType == "counter") {
					//$p2BonusEvent = $p1BonusType.",-".$p1MoveBonusResult['value'];
					$p2BonusEvent[] = array($p2BonusType, $p2MoveBonusResult['display']);
				}
			}
			/*
			//make this not always show up twice
			if (!empty($p1BonusEvent)) {
				$p1BonusEvent .= ",".$p1BuildString;
			} else {
				$p1BonusEvent .= $p1BuildString;
			}
			
			
			if (!empty($p2BonusEvent)) {
				$p2BonusEvent .= ",".$p2BuildString;
			} else {
				$p2BonusEvent .= $p2BuildString;
			}
			
			//echo "</br></br>";
	
			// remove trailing comma
			if(substr($p2BonusEvent, -1)== ",") {
				$p2BonusEvent = substr($p2BonusEvent, 0, -1);
			}
			if(substr($p1BonusEvent, -1)== ",") {
				$p1BonusEvent = substr($p1BonusEvent, 0, -1);
			}
		*/
		
			//echo "outcome =   $outcome";
			//////////////////////////////// FIGHT OUTPUT LEGACY SUPPORT/////////////////////////////////////
			///The below compiles the final output
			if ($i == 0) {
				$healthListP1='';
				$healthListP2='';
				$moveListP1='';
				$moveListP2='';
				$comma = "";
			} else {
				$comma = ",";
			}
				$healthListP1 .= $comma.$p1health;
				$healthListP2 .= $comma.$p2health;
				
			if($outcome==1) {
				//$theReaction == $p2Reaction;
				$moveListP1 .= $comma.$p1OldRef;
				$moveListP2 .= $comma.$p1OldRef."X";
				
				$p1DoThisMove = $p1ActionNameNum;
				$p2DoThisMove = $p1Reaction;
			}
			if($outcome==2) {
				//$theReaction == $p1Reaction;
				getReactionRef();
				$moveListP1 .= $comma.$p2OldRef."X";
				$moveListP2 .= $comma.$p2OldRef;
				
				$p1DoThisMove = $p2Reaction;
				$p2DoThisMove = $p2ActionNameNum;
			}
			if($outcome==0) {
				echo "the outcome is 0";
				//double breathloop
				$p1Reaction = 98;
				$p2Reaction = 98;
				$moveListP1 .= $comma.'XX';
				$moveListP2 .= $comma.'XX';
				
				$p1DoThisMove = $p1Reaction;
				$p2DoThisMove = $p2Reaction;
			}
			/*if ($i < 6) {
				$healthListP1 .= ',';
				$healthListP2 .= ',';
				$moveListP1 .= ',';
				$moveListP2 .= ',';
			}*/

			
			
			if ($getMeOuttaHere == true) {
				echo "at the end";
				//add the final animations
				if($fightResult==1) {
					//$salt = rand(1,12);		
					$moveListP1 .= ",XX,".$danceArray['fightWebName'];
				 	$moveListP2 .= ',1X,2X';
				}
				if($fightResult==2) {
					$salt = rand(1,12);
					$moveListP2 .= ",XX,".$danceArray['fightWebName'];
				 	$moveListP1 .= ',1X,2X';
				}
				if($fightResult==3) {
				 	echo "its a tie!";
					$moveListP1 .= ',1X,2X';
					$moveListP2 .= ',1X,2X';
					
				}
				
			}
			
			//for rallycap award
			if ($p1health <= 1) { $p1CountTurnsUnder1HP++;}
			if ($p2health <= 1) { $p2CountTurnsUnder1HP++;}
			
			//global turn total
			$globalTurnCount++;

			//if end trigger, in this case 10 moves is reached, create final output string
			if ($getMeOuttaHere == true) {
				//comma removal process
				/*if(substr($healthListP1, -1)== ",") { 
					$healthListP1 = substr($healthListP1, 0, -1);
				}
				if(substr($healthListP2, -1)== ",") { 
					$healthListP2 = substr($healthListP2, 0, -1);
				}
				if(substr($moveListP1, -1)== ",") { 
					$moveListP1 = substr($moveListP1, 0, -1);
				}
				if(substr($moveListP2, -1)== ",") { 
					$moveListP2 = substr($moveListP2, 0, -1);
				}*/
				
				$finalOutput = $moveListP1."|".$moveListP2;
				$finalHealthList = $healthListP1."|".$healthListP2;
				
				

				////echo "Output: ".$finalOutput;
				////echo "</br>HealthList: ".$finalHealthList;
				$finalResponse = array();
				$finalResponse['output']= $finalOutput;
				$finalResponse['healthlist']= $finalHealthList;
				//print_r($finalResponse);
			}
			//////////////////////////////// FIGHT OUTPUT LEGACY SUPPORT END /////////////////////////////////////
			if (!empty($p1BonusEvent)) {
				$p1BonusEventDB = json_encode($p1BonusEvent);
			} else { $p1BonusEventDB="";}
			if (!empty($p2BonusEvent)) {
				$p2BonusEventDB = json_encode($p2BonusEvent);
			} else { $p2BonusEventDB="";}
 
			 writeDB($fightID,$i,"1",$p1DoThisMove,$p1MoveType,$p1ScoringMoveType,$p1BonusEventDB,$p1health,$p1ItemVal);
			 writeDB($fightID,$i,"2",$p2DoThisMove,$p2MoveType,$p2ScoringMoveType,$p2BonusEventDB,$p2health,$p2ItemVal);
			 
			 
			 ///if a speed(combo), damage(cut), toughness(regen), or intelligence(counter) 
			 //strength doesnt have a lingering bonus
			 /*if ($subTurnBoolean == true) {
				 $p1ScoringMoveType = NULL;
				 $p2ScoringMoveType = NULL;
				 $p1BonusEventDB = 
				 $p2BonusEventDB = 
				 
				 				$subTurnBoolean = true;
				$subTurnValue = $p2MoveBonusResult['value'];
				$subTurnMove = $p2MoveBonusResult['subTurn'];
				$subTurnMoveMeta = $p2MoveBonusResult['subTurnMoveMeta'];
				
				
					writeDB($fightID,$i,"1",$p1DoThisMove,$p1MoveType,$p1ScoringMoveType,$p1BonusEventDB,$p1health,$p1ItemVal);
					writeDB($fightID,$i,"2",$p2DoThisMove,$p2MoveType,$p2ScoringMoveType,$p2BonusEventDB,$p2health,$p2ItemVal);	
			 }*/
			 			 

			if ($getMeOuttaHere == true) {
				break;
			}
		}
	////////////////////////////////////////////////////////////////////////////////////////END OF MAIN LOOP///////////////////////////////////////////////////////	
	
			$danceNum = $danceArray['danceNum'];
		
			if ($p2health > 0 && $p1health > 0 ) {
	
				$fightResult = 3;
				//tie both still alive
			}
			
			
			/////Nowwww we update the DB with the winner and the loser, thanks for waiting
			if ($fightResult == 1) {
				//echo 'win'; p1 is the winner
				//add the dance and fall
				//$salt2 = rand(101,113);
				
				writeDB($fightID,$i+1,"1",98,'offensive','win',NULL,$p1health,0);
				writeDB($fightID,$i+1,"2",114,'defensive','loss',NULL,$p2health,0);
				
				writeDB($fightID,$i+2,"1",$danceNum,'offensive','win',NULL,$p1health,0);
				writeDB($fightID,$i+2,"2",115,'defensive','loss',NULL,$p2health,0);
				
				//award check time
				checkFlawless($charID_player,$p1StartingHealth,$p1health);
				checkRally($charID_player,$p1CountTurnsUnder1HP);
				checkAce($charID_player,$globalTurnCount);
				

				
				$finalSummaryArray = updateSummary($fightID,$charID1,$charID2,$finalHealthList,$finalOutput,$charID_player,$charID_opponent,$p1StartingHealth, $p2StartingHealth);

			}
			if ($fightResult == 2) {
				//echo 'loss';
				//add the dance and fall
				//$salt2 = rand(101,113);
				
				writeDB($fightID,$i+1,"1",114,'defensive','loss',NULL,$p1health,0);
				writeDB($fightID,$i+1,"2",98,'offensive','win',NULL,$p2health,0);
				
				writeDB($fightID,$i+2,"1",115,'defensive','loss',NULL,$p1health,0);
				writeDB($fightID,$i+2,"2",$danceNum,'offensive','win',NULL,$p2health,0);
				
				//award check time
				checkFlawless($charID_opponent,$p2StartingHealth,$p2health);
				checkRally($charID_opponent,$p2CountTurnsUnder1HP);
				checkAce($charID_opponent,$globalTurnCount);
				
				
				$finalSummaryArray = updateSummary($fightID,$charID2,$charID1,$finalHealthList,$finalOutput,$charID_player,$charID_opponent,$p1StartingHealth, $p2StartingHealth);
			}
			if ($fightResult == 3) {
				
				//echo 'tie';
				//double rainbow fall
				writeDB($fightID,$i+1,"1",114,'offensive','win',NULL,$p1health,0);
				writeDB($fightID,$i+1,"2",114,'defensive','loss',NULL,$p2health,0);
				
				writeDB($fightID,$i+2,"1",115,'offensive','win',NULL,$p1health,0);
				writeDB($fightID,$i+2,"2",115,'defensive','loss',NULL,$p2health,0);
				
				$finalSummaryArray = updateSummary($fightID,0,0,$finalHealthList,$finalOutput,$charID_player,$charID_opponent,$p1StartingHealth, $p2StartingHealth);
			}
			
			
			

	

			//fight end
			//echo "</br>player 1 health = ".$p1health."</br>";
			//echo "</br>player 2 health = ".$p2health."</br>";
		
	/////BALANCING MODE HELPER////////////
	/*
	if ($p1health > $p2health) {
		echo "</br>player1 wins the fight</br>";
	}if ($p2health > $p1health) {
		echo "</br>player2 wins the fight</br>";
	}*/
		
		
	/*
		/// STILLL NEEED TO FIGURE OUT
		
		- Realationships for what beats what in a tie (tie on the 7 go to attribute lose 7 health)
		- How health should work 
		 Should the system be able to repeat
		- How do ties work?
		- How do we make bonus effects modular
		
		new Questions
		- How do we decide cornelius? (10 points for cornelius) 
		- are intial moves just random? 
		- same moves
		- figure out end criteria
	*/
		
	
	///////////////////////////////////////////////////// BONUS FUNCTIONS /////////////////////////////////////////////////////////////////////
	
	
	
	
	//need to echo alot of stuff, get fight save out of this function
	return $finalSummaryArray;
}


?>