#pragma strict

private var time :float;
var deathdelay :float;
var bulletSpeed :float;
var bulletOffset :float;
var bulletArc :float;
var fireRate :int;
var damage :float;
var pierce :boolean;


function Start () {
	time = Time.time;
	transform.rotation.z = 180;
	transform.position.x += bulletOffset;
	GetComponent.<Rigidbody>().velocity.x =  bulletSpeed;
	GetComponent.<Rigidbody>().velocity.y = bulletArc;
	gameObject.GetComponent(AnimateTexture).offset = Vector2(0,0);
}

function FixedUpdate () {
	if(Time.time >= time + deathdelay) {
		Destroy(gameObject);
	}
}