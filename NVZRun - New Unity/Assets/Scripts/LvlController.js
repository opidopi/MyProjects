#pragma strict

var paused :boolean;
var startLives :int;
var maxLives :int;
var currentLives :int;
var score :int;
var lvlCount :int;
var currentWeapon :int;

private var timeScale :float;


function Start () {
	currentLives = startLives;
	timeScale =  Time.timeScale;
	score = 0;
	currentWeapon = 0;
	DontDestroyOnLoad(gameObject);
}

function Update () {
	if(Input.GetKeyUp("p")){
		if(!paused) {
			paused = true;
		}	else {
			paused = false;
		}
	}
	
	if(paused) {
		if(Time.timeScale > 0.0) {
			Time.timeScale = 0.0;
			GameObject.FindGameObjectWithTag("Player").GetComponent.<AudioSource>().Pause();
		}
	} else {
		if(Time.timeScale < timeScale) {
			Time.timeScale = timeScale;
			GameObject.FindGameObjectWithTag("Player").GetComponent.<AudioSource>().Play();
		}
		GameObject.FindGameObjectWithTag("SDisplay").GetComponent(GUIText).text = "Score: " + score;
		GameObject.FindGameObjectWithTag("LDisplay").GetComponent(GUIText).text = "x " + currentLives;
	}
}

function killStage(winloss :String) {
	print("killentered: " + winloss);
	if(winloss.Equals("win")){
		lvlCount++;
		score += lvlCount *100;
		Application.LoadLevel("maingame");
	}
	else {
		Application.LoadLevel("mainmenu");
	}
}