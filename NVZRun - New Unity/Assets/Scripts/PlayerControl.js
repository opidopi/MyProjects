#pragma strict


var stop :boolean;
var bossFight :boolean;
var loadDelay :float;
var equipWeapon :int;
var maxHP :int = 5;
var currentHP :int;
var weapon :GameObject[];
var shooting :boolean;
var atSprite :GameObject;

private var ray :Ray;
private var hit :RaycastHit;
private var doublejump :boolean;
private var timeKeeper :float;
var shootKeeper :float;


function Start () {
	timeKeeper = Time.time;
	shootKeeper = timeKeeper;
	timeKeeper += loadDelay;
	equipWeapon = GameObject.FindGameObjectWithTag("LvlController").GetComponent(LvlController).currentWeapon;
	currentHP = maxHP;
	stop = true;
}

function FixedUpdate () {
	if(!gameObject.GetComponent(CharacterMotor).IsJumping()){
		doublejump = false;
	}
	if(Time.time >= timeKeeper && Time.time <= timeKeeper + 1.0 && !bossFight) {
			stop =  false;
		}
	if(!stop) {
		gameObject.GetComponent(CharacterMotor).movement.velocity.x = 15;
	}else {
		gameObject.GetComponent(CharacterMotor).movement.velocity.x = 0.000001;
		transform.position.x -= 0.000001;
	}
	var b :GameObject;
	if(Input.GetMouseButtonDown(0)){
		ray = Camera.main.ScreenPointToRay(Input.mousePosition);
		if(Physics.Raycast(ray, hit)){
			if( hit.transform.name == "Jump"){
				if(!gameObject.GetComponent(CharacterMotor).IsGrounded() && !doublejump){
					doublejump = true;
					gameObject.GetComponent(CharacterMotor).grounded = true;
					gameObject.GetComponent(CharacterMotor).inputJump = true;
					gameObject.GetComponent(PlayerAudio).inputJump = true;
					gameObject.GetComponentInChildren(AnimateTexture).rowNumber = 2;
					gameObject.GetComponentInChildren(AnimateTexture).colNumber = 3;
					gameObject.GetComponentInChildren(AnimateTexture).totalCells = 1;
					gameObject.GetComponentInChildren(AnimateTexture).fps = 0;
				}else {
					gameObject.GetComponent(CharacterMotor).inputJump = true;
					gameObject.GetComponent(PlayerAudio).inputJump = true;
				}
			}
			if( hit.transform.name == "Shoot"){
				if(!shooting) {
					shooting = true;
					shootKeeper = Time.time;
					b = Instantiate(weapon[equipWeapon], transform.position, Quaternion.identity);
					shootKeeper = Time.time;
					atSprite.GetComponent(AnimateShot).rowNumber = 0;
					atSprite.GetComponent(AnimateShot).fps = weapon[equipWeapon].GetComponent(Weapon).fireRate;
					atSprite.GetComponent(AnimateShot).colNumber = 0;
					atSprite.GetComponent(AnimateShot).totalCells = 4;
				}
			}
		}
	}else if(Input.GetButtonDown("Jump") || Input.GetButtonDown("Fire1")){
		if( Input.GetButtonDown("Jump")){
			if(!gameObject.GetComponent(CharacterMotor).IsGrounded() && !doublejump){
				doublejump = true;
				gameObject.GetComponent(CharacterMotor).grounded = true;
				gameObject.GetComponent(CharacterMotor).inputJump = true;
				gameObject.GetComponent(PlayerAudio).inputJump = true;
				gameObject.GetComponentInChildren(AnimateTexture).rowNumber = 2;
				gameObject.GetComponentInChildren(AnimateTexture).colNumber = 3;
				gameObject.GetComponentInChildren(AnimateTexture).totalCells = 1;
				gameObject.GetComponentInChildren(AnimateTexture).fps = 0;
			}else {
				gameObject.GetComponent(CharacterMotor).inputJump = true;
				gameObject.GetComponent(PlayerAudio).inputJump = true;
			}
		}
		if( Input.GetButtonDown("Fire1")){
			if(!shooting) {
			    shooting = true;
			    shootKeeper = Time.time;
				b = Instantiate(weapon[equipWeapon], transform.position, Quaternion.identity);
				shootKeeper = Time.time;
				atSprite.GetComponent(AnimateShot).rowNumber = 0;
				atSprite.GetComponent(AnimateShot).fps = weapon[equipWeapon].GetComponent(Weapon).fireRate;
				atSprite.GetComponent(AnimateShot).colNumber = 0;
				atSprite.GetComponent(AnimateShot).totalCells = 4;
			}
		}
	}
}