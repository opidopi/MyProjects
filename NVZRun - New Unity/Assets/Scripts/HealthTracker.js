#pragma strict

var index :int;

function Start () {

}

function FixedUpdate () {
	if(GameObject.FindGameObjectWithTag("Player").GetComponent(PlayerControl).currentHP >= index) {
		gameObject.GetComponent(MeshRenderer).enabled = true;
	} else {
		gameObject.GetComponent(MeshRenderer).enabled = false;
	}
}