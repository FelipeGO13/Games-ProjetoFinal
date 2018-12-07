package br.edu.ufabc.games.projetofinal.model;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationDesc;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;

import br.edu.ufabc.games.projetofinal.util.FirstPersonCamera;

public class GameObject extends ModelInstance {

	private AnimationController animationController;
	private boolean done;
	private float angle=0f;
	public final btCollisionObject corpo;
	public float mass = 1;
	public Vector3 position = new Vector3();
	public Vector3 velocity = new Vector3();
	public Vector3 acceleration = new Vector3();
	public Vector3 tmp;
	public String bodyType;
	
	public float ENERGY_ABS_RATE = 10f;
	public float energy = 0;

	public GameObject(Model model,  btCollisionShape shape) {
		super(model);
		done = false;
		corpo = new btCollisionObject();
		corpo.setCollisionShape(shape);
		corpo.setWorldTransform(this.transform);
		acceleration = new Vector3();
		tmp = new Vector3();
		/* modo debug */
		System.out.println("Animacoes = " + animations.size);
		for (Animation a : animations) {
			System.out.println(" --- Animacao " + a.id);
		}

		animationController = new AnimationController(this);
		if (animations.size > 0) {
			animationController.setAnimation(this.animations.get(0).id, -1,
					new AnimationController.AnimationListener() {

						@Override
						public void onLoop(AnimationDesc animation) {
							// TODO Auto-generated method stub
							done = true;
						}

						@Override
						public void onEnd(AnimationDesc animation) {
							// TODO Auto-generated method stub
							done = true;
						}
					});
		}
	}
	

	public Vector3 forceFrom(GameObject other) {
		float G = 6.674E-11f; // N*m^2/kg^2
		float m1 = this.mass;
		float m2 = other.mass;
		Vector3 dr = tmp.set(other.position).sub(this.position);
		float l = dr.len();
		if (other.bodyType.equals("SUN")) {
			energy += ENERGY_ABS_RATE/(l*l); // Absorb Radiation energy
		}
		return dr.scl(G * m1 * m2 / (l * l * l));
	}
	
	public void applyForce(Vector3 f, float dt) {
		acceleration.set(f).scl(2 / mass); // define tamanho da orbita e aceleracao dos corpos
		velocity.add(tmp.set(acceleration).scl(dt));
		position.add(tmp.set(velocity).scl(dt));

		this.transform.setTranslation(position);
		this.calculateTransforms();
	}
	
	public float getEnergy() {
		float current_energy = energy;
		energy = 0;
		return current_energy;
	}
	
	public void setAngle(float angle) {
		this.angle += angle;
	}
	public float getAngle() {
		return this.angle;
	}

	public void update(float delta) {
		animationController.update(delta);
		
	}

	public boolean isDone() {
		return done;
	}

	public void reset() {
		done = false;
	}
	
	public void setDone(boolean done) {
		this.done = done;
	}
	
	public btCollisionObject getCorpo() {
		return corpo;
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

}
