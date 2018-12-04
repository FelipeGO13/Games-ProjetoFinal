package br.edu.ufabc.games.projetofinal.model;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;

public class MassiveBody {
	
	
	private GameObject obj;
	btCollisionShape sphereShape;
	
	public MassiveBody(String planetType) {
		sphereShape = new btSphereShape(3.0f);
		obj = new GameObject(ModelFactory.getModelbyName(planetType), sphereShape);
		
	}
	
	public void update(float delta) {
		obj.update(delta);	
	}
	  
	public GameObject getCurrent() {
		return obj;
	}

	public GameObject getObj() {
		return obj;
	}

	public void setObj(GameObject obj) {
		this.obj = obj;
	}
	
	
	
}
 