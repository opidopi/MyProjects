#pragma strict

var lvlController :Transform;
var spawnPoint :Transform;
var player :GameObject;
var scoreDisplay :GUIText;
var livesDisplay :GUIText;
var lvlSize :int = 1;
var startSize :int = 1;
var xOffset :int;
var yOffset :int;
var zOffset :int;
var gameTiles :GameObject[];
var bossTiles :GameObject[];

function Start () {
	var lcon : GameObject = GameObject.FindGameObjectWithTag("LvlController");
	if(!lcon) {
		Instantiate(lvlController, Vector3.zero, Quaternion.identity);
	}
	Instantiate(scoreDisplay, scoreDisplay.transform.position, Quaternion.identity);
	Instantiate(livesDisplay, livesDisplay.transform.position, Quaternion.identity);
	GameObject.FindGameObjectWithTag("SDisplay").GetComponent(GUIText).text = "Score: " + GameObject.FindGameObjectWithTag("LvlController").GetComponent(LvlController).score;
	spawnPoint.transform.position.x = xOffset;
	spawnPoint.transform.position.y = yOffset;
	spawnPoint.transform.position.z = 0;
	var d = new Date();
	Random.seed = d.Now.Second;
	var p = Instantiate( player, spawnPoint.position, Quaternion.identity);
	spawnPoint.transform.position.x -= 3;
	gameObject.GetComponent(SmoothFollow2).target = p.transform;
	var location = xOffset;
	var t :GameObject;
	for(var i = 0; i < startSize; i++){
	t = Instantiate( gameTiles[0], spawnPoint.position, Quaternion.identity);
	t.transform.position.x = location;
	t.transform.position.y += yOffset;
	t.transform.position.z += zOffset;
	location += t.transform.localScale.x * t.GetComponent(MapTileInfo).size;
	t.GetComponentInChildren(PlayerRespawn).spawnPoint = spawnPoint;
	}
	var last = t;
	var temp :int;
	for(i = 0; i < lvlSize - startSize; i++) {
		t = Instantiate( gameTiles[Random.value * gameTiles.Length], spawnPoint.position, Quaternion.identity);
		if(last.tag == "Gap") {
			while(t.tag == "Gap") {
				Destroy(t);
				t = Instantiate( gameTiles[Random.value * gameTiles.Length], spawnPoint.position, Quaternion.identity);
			}
		}
		if(t.tag == "Cave" && last.transform.position.y != t.transform.position.y + yOffset) {
			while(t.tag == "Cave") {
				Destroy(t);
				t = Instantiate( gameTiles[Random.value * gameTiles.Length], spawnPoint.position, Quaternion.identity);
			}
		}
		if(t.transform.localScale.x > last.transform.localScale.x) {
			location -= last.transform.localScale.x/t.transform.localScale.x*last.transform.localScale.x;
			location += t.transform.localScale.x*last.transform.localScale.x/t.transform.localScale.x;
		} else if(t.transform.localScale.x < last.transform.localScale.x) {
			location -= t.transform.localScale.x/last.transform.localScale.x*last.transform.localScale.x;
			location += t.transform.localScale.x*t.transform.localScale.x/last.transform.localScale.x;
		}
		t.transform.position.x = location;
		t.transform.position.y += yOffset;
		t.transform.position.z += zOffset;
		location += t.transform.localScale.x;
		if(t.GetComponent(MapTileInfo).offsetable && last.tag != "Cave"){
			temp = Random.value * t.GetComponent(MapTileInfo).steps;
			t.transform.position.y += temp * 4;
		}
		if(t.tag == "Terrain" || t.tag == "Gap" || t.tag == "Cave") {
			t.GetComponentInChildren(PlayerRespawn).spawnPoint = spawnPoint;
		}
		last = t;
	}
	t = Instantiate( bossTiles[Random.value * bossTiles.Length], spawnPoint.position, Quaternion.identity);
	if(t.transform.localScale.x > last.transform.localScale.x) {
		location -= last.transform.localScale.x/t.transform.localScale.x*last.transform.localScale.x;
		location += t.transform.localScale.x*last.transform.localScale.x/t.transform.localScale.x;
	} else if(t.transform.localScale.x < last.transform.localScale.x) {
		location -= t.transform.localScale.x/last.transform.localScale.x*last.transform.localScale.x;
		location += t.transform.localScale.x*t.transform.localScale.x/last.transform.localScale.x;
	}

	t.transform.position.x = location;
	t.transform.position.y += yOffset;
	t.transform.position.z += zOffset;
	location += t.transform.localScale.x;
	t.GetComponentInChildren(PlayerRespawn).spawnPoint = spawnPoint;
	last = t;
}

