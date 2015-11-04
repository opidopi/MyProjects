#pragma strict

private var fall : boolean;
private var stomp : boolean;
private var xpos : float;
private var max : boolean;
private var pk :boolean;

var move :boolean;
var invert :boolean;
var killOnMax :boolean;
var speed :float;
var maxAmount :float;
var maxHP :int;
var currentHP :int;
var deathKeeper :float;
var hitsound :AudioClip;

function Start() {
	transform.rotation.z = 180;
	currentHP = maxHP;
	xpos = transform.position.x;
	transform.GetChild(transform.childCount - 1).GetComponent.<ParticleSystem>().enableEmission = false;
}

function FixedUpdate () {
	if(stomp) {
		deathKeeper = Time.time;
		transform.position.z = 5;
		fall = true;
		move = false;
		stomp = false;
		if(!transform.GetChild(transform.childCount - 1).GetComponent.<ParticleSystem>().IsAlive()){
			transform.GetChild(transform.childCount - 1).GetComponent.<ParticleSystem>().enableEmission = true;
			transform.GetChild(transform.childCount - 1).GetComponent.<ParticleSystem>().Clear();
			transform.GetChild(transform.childCount - 1).GetComponent.<ParticleSystem>().Play();
		} else {
			transform.GetChild(transform.childCount - 1).GetComponent.<ParticleSystem>().Emit(50);
		}
		if(transform.tag == "Boss") {
			GameObject.FindGameObjectWithTag("LvlController").GetComponent(LvlController).score += 100;
			GameObject.FindGameObjectWithTag("LvlController").GetComponent(LvlController).killStage("win");
		} else if(pk){
			GameObject.FindGameObjectWithTag("LvlController").GetComponent(LvlController).score += 10;
		}
		gameObject.GetComponent(AnimateZombie).rowNumber = 1;
		gameObject.GetComponent(AnimateZombie).colNumber = 0;
	}
	if(fall){
		transform.position.y -= 0.01;
		if(gameObject.GetComponent(AnimateZombie).index == 4){
			gameObject.GetComponent(AnimateZombie).colNumber = 4;
			gameObject.GetComponent(AnimateZombie).rowNumber = 1;
			gameObject.GetComponent(AnimateZombie).totalCells = 1;
			gameObject.GetComponent(AnimateZombie).fps = 0;
		}
	}
	if(transform.position.y < -25 || transform.position.x < 0) {
		Destroy(gameObject);
	}
	if(move) {
		if(!invert) {
			if( transform.position.x >= xpos + maxAmount) {
				max = true;
				if(killOnMax) {
					Destroy(gameObject);
				}
			} else if( transform.position.x <= xpos) {
				max = false;
			}
			if(!max) {
				transform.position.x += speed;
			} else {
				transform.position.x -= speed;
			}
		} else {
			if( transform.position.x <= xpos + maxAmount) {
				max = true;
				if(killOnMax) {
					stomp = true;
					currentHP = 0;
				}
			} else if( transform.position.x >= xpos) {
				max = false;
			}
			if(!max) {
				transform.position.x -= speed;
			} else {
				transform.position.x += speed;
			}
		}
	}

}

function OnTriggerEnter( other : Collider) {
	if(!stomp) {
		if(other.tag == "Bullet") {
			if(!transform.GetChild(transform.childCount - 1).GetComponent.<ParticleSystem>().IsAlive()){
				transform.GetChild(transform.childCount - 1).GetComponent.<ParticleSystem>().enableEmission = true;
				transform.GetChild(transform.childCount - 1).GetComponent.<ParticleSystem>().Clear();
				transform.GetChild(transform.childCount - 1).GetComponent.<ParticleSystem>().Play();
			} else {
				transform.GetChild(transform.childCount - 1).GetComponent.<ParticleSystem>().Emit(50);
			}
			gameObject.GetComponent.<AudioSource>().clip = hitsound;
			gameObject.GetComponent.<AudioSource>().Play();
			if(currentHP <= 1) {
				pk = true;
				stomp = true;
				if(!other.GetComponent(Weapon).pierce){
					other.GetComponent(MeshRenderer).enabled = false;
					Destroy(other);
				}
			} else {
				currentHP -= other.GetComponent(Weapon).damage;
				if(!other.GetComponent(Weapon).pierce){
					other.GetComponent(MeshRenderer).enabled = false;
					Destroy(other);
				}
			}
		}
	}
}