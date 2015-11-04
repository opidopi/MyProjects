#pragma strict
function OnTriggerEnter(other :Collider) {
	if(other.tag == "Player") {
		GameObject.FindGameObjectWithTag("LvlController").GetComponent(LvlController).currentLives++;
		if(GameObject.FindGameObjectWithTag("LvlController").GetComponent(LvlController).currentLives > GameObject.FindGameObjectWithTag("LvlController").GetComponent(LvlController).maxLives) {
			GameObject.FindGameObjectWithTag("LvlController").GetComponent(LvlController).currentLives = GameObject.FindGameObjectWithTag("LvlController").GetComponent(LvlController).maxLives;
		}
		Destroy(gameObject);
	}
}