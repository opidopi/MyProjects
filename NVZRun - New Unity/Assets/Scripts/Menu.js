#pragma strict

var ray : Ray;
var hit : RaycastHit;

function Start () {
	Destroy(GameObject.FindGameObjectWithTag("LvlController"));
}

function Update () {
	if(Input.GetMouseButtonDown(0)){
		ray = Camera.main.ScreenPointToRay(Input.mousePosition);
		if(Physics.Raycast(ray, hit)){
			if( hit.transform.name == "Starthit"){
				Application.LoadLevel("maingame");
			}
		}
	}
}