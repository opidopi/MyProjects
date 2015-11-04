#pragma strict

private var x :float;
var offset :float;
var followCamera :boolean;
var followZ :boolean;
var xlag :float;

function Start () {
	x = Camera.main.transform.position.x;
}

function Update () {
	if(followCamera) {
		if(followZ){
			transform.position.x = ((Camera.main.transform.position.x - x) * transform.position.z/offset) + xlag;
		}
		else {
			transform.position.x = ((Camera.main.transform.position.x - x)/offset) + xlag;
			}
	} else {
		if(followZ){
			transform.position.x = ((x - Camera.main.transform.position.x) * transform.position.z/offset) + xlag;
		}
		else {
			transform.position.x = ((x - Camera.main.transform.position.x)/offset) + xlag;
		}
	}
}