<?php
$connect = @mysql_connect("localhost", "root", "");
mysql_select_db("grafighters_dev", $connect) or die (mysql_errno().":<b> ".mysql_error()."</b>");

/*$connect=mysql_connect("localhost","grafighter",
	"pencilwarrior1");
mysql_select_db("grafighters_LIVE",$connect) or 
   die (mysql_errno().":<b> ".mysql_error()."</b>");*/

?>