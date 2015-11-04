var target : Transform;
var distance = 3.0;
var height = 3.0;
var maxHeight = 0.0;
var xOffset = 0;
var damping = 5.0;
var smoothRotation = true;
var rotationDamping = 10.0;
var lockRotation :boolean;

function Update () {
	var wantedPosition = target.TransformPoint(xOffset, height, -distance);
	transform.position = Vector3.Lerp (transform.position, wantedPosition, Time.deltaTime * damping);

	if (smoothRotation) {
		var wantedRotation = Quaternion.LookRotation(target.position - transform.position, target.up);
		transform.rotation = Quaternion.Slerp (transform.rotation, wantedRotation, Time.deltaTime * rotationDamping);
	}

	else transform.LookAt (target, target.up);
	
	if(lockRotation){
		transform.localRotation = Quaternion.Euler(0,0,0);
	}
	if(transform.position.y > maxHeight) {
		transform.position.y = maxHeight;
	}
}