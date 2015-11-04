#pragma strict

var footsteps :AudioClip;
var jump :AudioClip;
var hitsound :AudioClip;
var deathsound :AudioClip;
var hit :boolean;
var dead :boolean;
var inputJump :boolean;

function Start () {

}

function FixedUpdate () {
	if(gameObject.GetComponent(CharacterMotor).movement.velocity.x > 0 && gameObject.GetComponent(CharacterMotor).IsGrounded()) {
		if(hit){
			GetComponent.<AudioSource>().clip = hitsound;
			GetComponent.<AudioSource>().Play();
			GetComponent.<AudioSource>().loop = false;
			hit = false;
		}else if(dead){
			GetComponent.<AudioSource>().clip = deathsound;
			GetComponent.<AudioSource>().Play();
			GetComponent.<AudioSource>().loop = false;
			dead = false;
		}
		if(!GetComponent.<AudioSource>().isPlaying) {
			GetComponent.<AudioSource>().clip = footsteps;
			GetComponent.<AudioSource>().loop = true;
			GetComponent.<AudioSource>().Play();
		}
	} else if(inputJump){
		if(hit){
			GetComponent.<AudioSource>().clip = hitsound;
			GetComponent.<AudioSource>().Play();
			GetComponent.<AudioSource>().loop = false;
			hit = false;
		}else if(dead){
			GetComponent.<AudioSource>().clip = deathsound;
			GetComponent.<AudioSource>().Play();
			GetComponent.<AudioSource>().loop = false;
			dead = false;
		}else {
			GetComponent.<AudioSource>().Stop();
			GetComponent.<AudioSource>().clip = jump;
			GetComponent.<AudioSource>().loop = false;
			GetComponent.<AudioSource>().Play();
			inputJump = false;
		}
	} else {
		if(GetComponent.<AudioSource>().clip == footsteps){
			GetComponent.<AudioSource>().Stop();
		}else if(hit){
			GetComponent.<AudioSource>().clip = hitsound;
			GetComponent.<AudioSource>().Play();
			GetComponent.<AudioSource>().loop = false;
			hit = false;
		}else if(dead){
			GetComponent.<AudioSource>().clip = deathsound;
			GetComponent.<AudioSource>().Play();
			GetComponent.<AudioSource>().loop = false;
			dead = false;
		} else{
			GetComponent.<AudioSource>().loop = false;
		}
	}
}