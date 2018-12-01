package br.edu.ufabc.games.projetofinal.model;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public class MassiveBody {
	
	private Vector3 position;
	private Vector3 velocity;
	private float mass;
	private Vector3 acceleration;
	private Vector3 tmp;
	public Vector3 center;
	public Vector3 dimensions;
	
	private GameObject obj;
	private BoundingBox boundingBox;
	
	public MassiveBody() {
		obj = new GameObject(ModelFactory.getModelbyName("PLANETA1"));
		acceleration = new Vector3();
		tmp = new Vector3();
		center = new Vector3();
		dimensions = new Vector3();
		boundingBox = new BoundingBox();
		obj.calculateBoundingBox(boundingBox);
		boundingBox.getCenter(center);
		boundingBox.getDimensions(dimensions);
		
	}
	
	public Vector3 forceFrom(MassiveBody other) {
		float G = 6.674E-11f; // N*m^2/kg^2
		float m1 = this.mass;
		float m2 = other.mass;
		Vector3 dr = tmp.set(other.position).sub(this.position);
		float l = dr.len();
		return dr.scl(G * m1 * m2 / (l * l * l));
	}
	
	public void applyForce(Vector3 f, float dt) {
		acceleration.set(f).scl(1 / mass);
		velocity.add(tmp.set(acceleration).scl(dt));
		position.add(tmp.set(velocity).scl(dt));

		obj.transform.setTranslation(position);
		obj.calculateTransforms();
	}
	
	
	public void update(float delta) {
		obj.update(delta);	
	}
	  
	public GameObject getCurrent() {
		return obj;
	}

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position) {
		this.position = position;
	}

	public Vector3 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3 velocity) {
		this.velocity = velocity;
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public Vector3 getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector3 acceleration) {
		this.acceleration = acceleration;
	}

	public Vector3 getTmp() {
		return tmp;
	}

	public void setTmp(Vector3 tmp) {
		this.tmp = tmp;
	}

	public Vector3 getCenter() {
		return center;
	}

	public void setCenter(Vector3 center) {
		this.center = center;
	}

	public Vector3 getDimensions() {
		return dimensions;
	}

	public void setDimensions(Vector3 dimensions) {
		this.dimensions = dimensions;
	}

	public GameObject getObj() {
		return obj;
	}

	public void setObj(GameObject obj) {
		this.obj = obj;
	}
	
	
	
}
 