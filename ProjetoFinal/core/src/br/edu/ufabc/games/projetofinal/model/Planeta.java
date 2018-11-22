package br.edu.ufabc.games.projetofinal.model;

public class Planeta {
	private GameObject obj;
	
    public Planeta() {
    	obj = new GameObject(ModelFactory.getModelbyName("PLANETA1"));
    }
    
    public void update(float delta) {
       obj.update(delta);	
    }
    
    public GameObject getCurrent() {
    	return obj;
    }
}
