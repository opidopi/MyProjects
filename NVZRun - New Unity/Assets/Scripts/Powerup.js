#pragma strict
var ID :int;

function Start(){
	transform.rotation.z = 180;
}

function OnTriggerEnter(other :Collider) {
	if(other.tag == "Player") {
		other.GetComponent(PlayerControl).equipWeapon = ID;
		Destroy(gameObject);
	}
}