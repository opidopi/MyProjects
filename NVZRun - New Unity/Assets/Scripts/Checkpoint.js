#pragma strict

private var spawnPoint : Transform;
var offset :int;

function Start() {
	spawnPoint = GameObject.FindGameObjectWithTag("Spawnpoint").transform;
}

function OnTriggerEnter(other : Collider) {
	if(other.tag == "Player") {
		spawnPoint.position = Vector3(transform.position.x, spawnPoint.position.y, spawnPoint.position.z);
		spawnPoint.position.x -= offset;
		other.transform.position.x = transform.position.x;
		other.GetComponent(PlayerControl).stop = true;
		other.GetComponent(PlayerControl).bossFight = true;
		transform.root.GetComponentInChildren(ZombieGun).fire = true;
	}
}
