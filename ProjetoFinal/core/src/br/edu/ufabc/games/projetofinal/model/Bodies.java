package br.edu.ufabc.games.projetofinal.model;

import com.badlogic.gdx.math.Vector3;

public enum Bodies {
	
	SUN(-1, new Vector3(0, 0, 0), new Vector3(0, 0, 0), 2E11f, 1f, "SUN"),
	PLANET1(0, new Vector3(-10, 15, 2), new Vector3(-0.3f, 0, -0.2f), 5E8f, 0.4f, "PLANETA1"),
	PLANET2(1, new Vector3(10, 7, 1), new Vector3(-0.3f, 0, -0.5f), 1E9f, 0.4f, "PLANETA2"),
	PLANET3(2,  new Vector3(10.3f, 7, 1), new Vector3(-0.3f, 0, -0.7f), 1E7f, 0.08f, "PLANETA3"),
	PLANET4(3, new Vector3(20, -7, 0.5f), new Vector3(-0.1f, 0, -0.4f), 1E8f, 0.6f, "PLANETA4"),
	PLANET5(4, new Vector3(4, 1, 1), new Vector3(-0.1f, 0, -0.7f), 1E7f,  0.2f, "PLANETA5");
	
	private int id;
	private Vector3 pos;
	private Vector3 vel;
	private float mass;
	private float scale;
	private String model;
	
	private Bodies(int id, Vector3 pos, Vector3 vel, float mass, float scale, String model) {
		this.id = id;
		this.pos = pos;
		this.vel = vel;
		this.mass = mass;
		this.scale = scale;
		this.model = model;
	}
	
	public static Bodies getById(int id) {
		for(Bodies b : Bodies.values()) {
			if(b.getId() == id) {
				return b;
			}
				
		}
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Vector3 getPos() {
		return pos;
	}

	public void setPos(Vector3 pos) {
		this.pos = pos;
	}

	public Vector3 getVel() {
		return vel;
	}

	public void setVel(Vector3 vel) {
		this.vel = vel;
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	

}
