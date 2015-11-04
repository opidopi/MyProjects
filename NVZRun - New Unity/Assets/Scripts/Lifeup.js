#pragma strict
var HealAmount :int;
function OnTriggerEnter(other :Collider) {
	if(other.tag == "Player") {
		other.GetComponent(PlayerControl).currentHP += HealAmount;
		if(other.GetComponent(PlayerControl).currentHP > other.GetComponent(PlayerControl).maxHP) {
			other.GetComponent(PlayerControl).currentHP = other.GetComponent(PlayerControl).maxHP;
		}
		Destroy(gameObject);
	}
}