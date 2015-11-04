#pragma strict

var zombie :GameObject;
var zombieAni :GameObject;
var fireRate :float;
var fireSpeed :float;
var fireAmount :int;
var spreadDelay :float;
var distance :float;
var xOffset :float;
var yOffset :float;
var fire :boolean;

private var volleyKeeper :float;
private var shotKeeper :float;
private var rate :float;
private var shotCount :int;
private var z:GameObject;
private var zA:GameObject;

function Start () {
	volleyKeeper = Time.time;
	shotKeeper = volleyKeeper;
	rate = spreadDelay * fireAmount + fireRate;
	shotCount = 0;
}

function FixedUpdate () {
	if(fire)
	{
		if(Time.time <= volleyKeeper + rate) {
			if(Time.time >= shotKeeper + spreadDelay && shotCount < fireAmount ) {
				zA = Instantiate(zombieAni, transform.position, Quaternion.identity);
				zA.transform.position.x += xOffset;
				zA.transform.position.y += yOffset - zombie.transform.localScale.y/2;
				zA.transform.position.z -= 0.1
				;
				z = Instantiate(zombie, transform.position, Quaternion.identity);
				z.transform.position.x += xOffset;
				z.transform.position.y += yOffset;
				z.transform.position.z -= 0.01;
				z.GetComponent(Enemy).move = true;
				z.GetComponent(Enemy).speed = fireSpeed;
				z.GetComponent(Enemy).maxAmount = -distance;
				z.GetComponent(Enemy).killOnMax = true;
				z.GetComponent(Enemy).invert = true;
				shotKeeper = Time.time;
				shotCount++;
			}
		} else {
			volleyKeeper = Time.time;
			shotKeeper = volleyKeeper;
			shotCount = 0;
		}
	}
}