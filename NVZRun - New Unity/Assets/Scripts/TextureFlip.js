#pragma strict

private var dir :float;

function Start () {
	dir = transform.localScale.x;
}

function FixedUpdate () {
	var player = transform.root.gameObject.GetComponent(CharacterMotor);
	if(player.movement.velocity.x < 0) {
		transform.localScale.x = -dir;
	}
	else if(player.movement.velocity.x > 0) {
		transform.localScale.x = dir;
	}

}