#pragma strict

var player : GameObject;
var spawnPoint : Transform;


function OnTriggerEnter(other : Collider) {
	if(other.tag == "Player") {
		if(other.GetComponent(PlayerControl).currentHP == 1 || transform.tag == "Instakill") {
			if(GameObject.FindGameObjectWithTag("LvlController").GetComponent(LvlController).currentLives >= 1) {
				spawnPoint = GameObject.FindGameObjectWithTag("Spawnpoint").transform;
				GameObject.FindGameObjectWithTag("LvlController").GetComponent(LvlController).currentWeapon = 0;
				var p :GameObject = Instantiate(player, spawnPoint.position, Quaternion.identity);
				var sf = Camera.main.GetComponent(SmoothFollow2);
				sf.target = p.transform;
				Destroy(other.gameObject);
				GameObject.FindGameObjectWithTag("LvlController").GetComponent(LvlController).currentLives--;
				//p.GetComponent(PlayerAudio).dead = true;
				
			} else {
				GameObject.FindGameObjectWithTag("LvlController").GetComponent(LvlController).killStage("lose");
			}
		} else {
			other.GetComponent(PlayerControl).currentHP--;
			other.GetComponent(PlayerAudio).hit = true;
			if(!GameObject.FindGameObjectWithTag("Playerblood").GetComponent.<ParticleSystem>().IsAlive()){
				GameObject.FindGameObjectWithTag("Playerblood").GetComponent.<ParticleSystem>().enableEmission = true;
				GameObject.FindGameObjectWithTag("Playerblood").GetComponent.<ParticleSystem>().Clear();
				GameObject.FindGameObjectWithTag("Playerblood").GetComponent.<ParticleSystem>().Play();
			} else {
				GameObject.FindGameObjectWithTag("Playerblood").GetComponent.<ParticleSystem>().Emit(50);
			}
		}
	}
}