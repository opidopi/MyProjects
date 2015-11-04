#pragma strict

var atSprite :GameObject;

function Start () {
	
}

function FixedUpdate () {
	var player = transform.root.gameObject.GetComponent(CharacterMotor);
	var at = gameObject.GetComponent(AnimateTexture);
	if(player.IsGrounded()){
		if(!transform.root.GetComponent(PlayerControl).stop) {
			at.rowNumber = 0;
			at.totalCells = 6;
			at.fps = 20;
		}  else {
			at.rowNumber = 2;
			at.totalCells = 2;
			at.fps = 2;
		}
	} else {
		if(transform.root.GetComponent(CharacterMotor).movement.velocity.y >= 0) {
			if(at.rowNumber != 1){
				at.rowNumber = 1;
				at.colNumber = 0;
				at.totalCells = 3;
				at.fps = 20;
			} else if (at.index == 2) {
				at.fps = 0;
				at.totalCells = 1;
				at.colNumber = 2;
			}
		} else {
			if(at.rowNumber != 1){
				at.rowNumber = 1;
				at.colNumber = 2;
				at.totalCells = 3;
				at.fps = -20;
			} else if (at.index == 0) {
				at.fps = 0;
				at.totalCells = 1;
				at.colNumber = 0;
			}
		}
	}
	if(transform.root.GetComponent(PlayerControl).shooting) {
		if(atSprite.GetComponent(AnimateShot).index == 3 && atSprite.GetComponent(AnimateShot).rowNumber == 0){
			atSprite.GetComponent(AnimateShot).rowNumber = 1;
			atSprite.GetComponent(AnimateShot).fps = 0;
			atSprite.GetComponent(AnimateShot).colNumber = 0;
			atSprite.GetComponent(AnimateShot).totalCells = 1;
			transform.root.GetComponent(PlayerControl).shooting = false;
		} else {
		}
	} else {
		atSprite.GetComponent(AnimateShot).rowNumber = 1;
		atSprite.GetComponent(AnimateShot).fps = 0;
		atSprite.GetComponent(AnimateShot).colNumber = 0;
	}
}