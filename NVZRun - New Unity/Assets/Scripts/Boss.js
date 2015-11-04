#pragma strict

private var fall : boolean;
private var stomp : boolean;

function Start() {
	transform.rotation.z = 180;
}

function FixedUpdate () {
	if(stomp) {
		transform.position.z = 5;
		transform.localScale.y /= 2;
		fall = true;
		stomp = false;
		GameObject.FindGameObjectWithTag("LvlController").GetComponent(LvlController).killStage("win");
	}
}

function OnTriggerEnter( other : Collider) {
	if(!stomp) {
		if(other.tag == "Bullet") {
			stomp = true;
		}
	}
}