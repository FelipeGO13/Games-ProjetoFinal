package br.edu.ufabc.games.projetofinal.model;

import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;

public class Planeta {
	private GameObject obj;
	btCollisionShape planetShape;
	
    public Planeta() {
    	planetShape = new btSphereShape(5.0f);
    	obj = new GameObject(ModelFactory.getModelbyName("PLANETA1"), planetShape);
    }
    
    public void update(float delta) {
       obj.update(delta);	
    }
    
    public GameObject getCurrent() {
    	return obj;
    }
}
