#pragma strict

function Start () {

}

function Update () {
	if(!gameObject.GetComponentInChildren(ParticleSystem).GetComponent.<ParticleSystem>().IsAlive()){
		Destroy(gameObject);
	}
}