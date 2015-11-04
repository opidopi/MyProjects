<?php 

############################################ START AWARD DEFINITIONS ###############################

	###### Flawless victory = win a fight without losing a health point	
	function checkFlawless($charID,$pStartingHealth,$phealth) {
		if ($pStartingHealth == $phealth) {
			$awardType = "flawless";
			giveAward($awardType,$charID);
		}
	}
	
	###### Rally Cap = win a fight, with being at 1 health for 5 or more moves
	function checkRally($charID,$numberOfTurns) {
		if ($numberOfTurns >= 5) {
			$awardType = "rally";
			giveAward($awardType,$charID);
		}
	}

	####### Ace - take an oppoenent down in one hit
	function checkAce($charID,$numberOfTurns) {
		if ($numberOfTurns == 1) {
			$awardType = "ace";
			giveAward($awardType,$charID);
		}
	}
	
	
	
	
	
	
	
	
	
	##### ConnectFive - create 5 original charcaters that max out all 5 different stats
	function checkConnectFive($charID,$userID) {
		//check to see if they have all 5 of characters maxed at different stats
		giveAward($awardType,$charID);
	}
	
	
	##### Well rounded - get a character with a perfect well rounded score
	function checkWellRounded($charID,$userID) {
		//check to see if they have all 5 of characters maxed at different stats
		giveAward($awardType,$charID);
	}
	
	
	##### Streak - win 10 matches in arow
	function checkStreak($charID) {
		//check to if streak is > 10
		giveAward($awardType,$charID);
	}
	
	
	#####*/


############################################ END AWARD DEFINITIONS ###############################


//------------- THE BELOW COMPLETS THE SAVE OF AWARDS ---------//

//call this function to give awards
function giveAward($awardType,$charID) {
	$userID = getAwardUser($charID);
	$awardID = getAwardID($awardType);
    addAward($awardID, $charID, $userID);
}


// this function gets the corresponding award ID based on the name
function getAwardUser($charID) {
	$userSQL = mysql_query("SELECT userID FROM CHARACTERS WHERE characterID='".$charID."'") or die(mysql_error()."Couldn't get the USERID");
	$row = mysql_fetch_array($userSQL);
	return $row['userID'];
}


// this function gets the corresponding award ID based on the name
function getAwardID($awardName) {
	$awardSQL = mysql_query("SELECT awardID FROM awards WHERE name='".$awardName."'") or die(mysql_error()."Couldn't get the awardID");
	$row = mysql_fetch_array($awardSQL);
	return $row['awardID'];
}


//puts the award in the DB
function addAward($awardID, $charID, $userID) {
	//add a check for if exsists?
	$sql = mysql_query("INSERT INTO awardsEarned (awardID, charID, userID, awardTime) VALUES ('".$awardID."', '".$charID."','".$userID."', NOW() )") or die(mysql_error()."Couldn't put the award in the DB");
}


?>
