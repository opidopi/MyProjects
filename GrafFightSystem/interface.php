
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Dev Interface</title>
        <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
        <style>
            body {
                font-size: 14px;
            }
            #player1 {
                width: 200px;
                float:left;
            }
            #player2 {
                width: 200px;
                float:right;
            }
            #container {
                margin:auto;
                width:1000px;
            }

            #card1, #card2 {
                display: inline-block;
                padding-top: 20px;
                width: 200px;
                font-size: 14px;
            }
            #card2 {
                text-align: right;
            }
            #card1 {
                text-align: left;
            }
            #currentTurn {
                width: 410px;
                border:1px solid #CCC;
                padding: 20px;
                margin: auto;

            }
            #turnList {

                width: 480px;
                padding: 20px;
                margin: auto;
                margin-top:15px;
                text-align: center;
            }
            #round {
                text-align: center;
                margin-bottom: 10px;
            }

            #winner {
                text-align: center;
            }
            #winnerTitle {
                text-align: center;
            }
            #nextBtn {
                margin: auto;
            }

        </style>


    </head>

    <body>
        <script type='text/javascript'>
            var charID1 = 3;
            var charID2 = 4;
            var chalID = 11;
            var counter = 0;
            var offset1 = 0;
            var offset2 = 0;
            var player1;
            var player2;
            var movelist1 = "";
            var movelist2 = "";
            $.post("_php/returnFightArray.php", { "charID1": charID1, "charID2": charID2, "chalID": chalID},
            function(data)
            {

                console.log(data);

                //console.log(data.player1);	

                //$('#player1').html();

                jQuery.each(data.player1.stats[0], function(i, val) {
                    $("#player1").append(i + " => " + val + "<br/>");
                });
                jQuery.each(data.player2.stats[0], function(i, val) {
                    $("#player2").append(i + " => " + val + "<br/>");
                });
                /*jQuery.each(data.player1.roundstats[0], function(i, val) {
                      $("#card1").append(val + " = " + i + "<br/>");
                    });
                    jQuery.each(data.player2.roundstats[0], function(i, val) {
                      $("#card2").append(i + " = " + val + "<br/>");
                    });*/
                counter = 0;
                offset1 = 0;
                offset2 = 0;
                movelist1 = "";
                movelist2 = "";
                player1 = data.player1;
                player2 = data.player2;
                clickme();
            }, "json");
            
            function clickme()
            {
                createCurrentTurn(player1.roundstats[counter],player2.roundstats[counter]);
                createMoveList(player1.cardinfo, player2.cardinfo);
                counter++;
            }

            function createCurrentTurn(p1Array, p2Array) {


                $('#winner').html(p1Array.winner);
                $('#rndNum').html(p1Array.round);

                $('#rndNum').html(p1Array.round);
                $('#rndNum').html(p2Array.round);
                
                $('#card1>#att>#attack').html(p1Array.hitroll);
                $('#card1>#def>#defense').html(p1Array.defense);
                $('#card1>#dmg>#damage').html(p1Array.damage);
                $('#card1>#exAtt>#extra').html(p1Array.extrahit);
                $('#card1>#crit>#crithit').html(p1Array.crit);

                $('#card2>#att>#attack').html(p2Array.hitroll);
                $('#card2>#def>#defense').html(p2Array.defense);
                $('#card2>#dmg>#damage').html(p2Array.damage);
                $('#card2>#exAtt>#extra').html(p2Array.extrahit);
                $('#card2>#crit>#crithit').html(p2Array.crit);

                $('#p1HPnum').html(p1Array.hp);
                $('#p2HPnum').html(p2Array.hp);
            }
            
            function createMoveList(p1Array, p2Array)
            {
                console.log('move list entered');
                //$('#turnList>#player1').html("this works");
                //movelist1 = "this works";
                    if(counter + offset1 < p1Array.length)
                    {
                        movelist1 += p1Array[counter + offset1].name;
                        if(counter + offset1 + 1 < p1Array.length)
                            while(p1Array[counter + offset1 + 1].round == counter + 1)
                            {
                                movelist1 += ", " + p1Array[counter + offset1 + 1].name;
                                offset1++;
                            }
                        movelist1 += "</br>";
                    }
                    $('#turnList>#player1').html(movelist1 + p1Array.length + ' ' + player1.roundstats.length);
                    if(counter + offset2 < p2Array.length)
                    {
                        movelist2 += p2Array[counter + offset2].name;
                        if(counter + offset2 + 1 < p2Array.length)
                            while(p2Array[counter + offset2 + 1].round == counter + 1)
                            {
                                movelist2 += ", " + p2Array[counter + offset2 + 1].name;
                                offset2++;
                            }
                        movelist2 += "</br>";
                    }
                    $('#turnList>#player2').html(movelist2 + p2Array.length + ' ' + player2.roundstats.length);
            }
        </script>


        <div id="contain">
            <div id="player1">Player 1 Data</br></div>
            <div id="player2">Player 2 Data</br></div>
            <div id="currentTurn">
                <div id="round">Round <span id="rndNum"> </span></div>

                
                

                <div id="card1">
                    <div id="p1HP"><span id='p1HPnum'></span> = P1 Health</div>
                    <div id="att">Attack Roll = <span id ="attack"></span></div>
                    <div id='def'> Defense =  <span id='defense'></span> </div>
                    <div id='dmg'> Damage =  <span id='damage'></span> </div>
                    <div id='exAtt'> Extra Attack =  <span id='extra'></span> </div>
                    <div id='crit'> Critical Hit =  <span id='crithit'></span> </div>

                </div>
                <div id="card2">
                    <div id="p2HP">P2 Health = <span id='p2HPnum'></span></div>
                    <div id="att">Attack Roll = <span id ="attack"></span></div>
                    <div id='def'> Defense = <span id='defense'></span> </div>
                    <div id='dmg'> Damage = <span id='damage'></span> </div>
                    <div id='exAtt'> Extra Attack =  <span id='extra'></span> </div>
                    <div id='crit'> Critical Hit =  <span id='crithit'></span> </div>
                </div>

                <div id="winnerTitle">Winner</div>
                <div id="winner">Winner here</div>
                <button id='nextBtn' type="button" onclick ="clickme()">Next Turn</button>
            </div>
            <div id="turnList">
                <div id="player1"></div>
                <div id="player2"></div>
            </div>
        </div>


    </body>
</html>

