package br.edu.ufabc.games.projetofinal.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;

import br.edu.ufabc.games.projetofinal.util.FirstPersonCamera;

public class Ship {
	private GameObject gameObject;	
	public FirstPersonCamera camera;
	
	public  Vector3 velocidade;

	private int direcao;
	private int inclinar = 0;

	private static final int PARADO = 0;
	private static final int FRENTE = 1;
	private static final int TRAS = 2;
	private static final int ESQUERDA = 3;
	private static final int DIREITA = 4;

	private static final float ACELERACAO = 5f;
	private static final float ROLL_SPEED = 30f;
	
	public btCollisionShape shipShape;
	
	public boolean onGravity;
	public float fuel;
	private Vector3 lastPosition;
	private Vector3 newPosition;
	
	public Ship () {
		shipShape = new btBoxShape(new Vector3(0.5f, 0.5f, 0.5f));
		gameObject = new GameObject(ModelFactory.getModelbyName("NAVE"), shipShape);
		gameObject.transform.scale(0.5f, 0.5f, 0.5f);
		
		camera = new FirstPersonCamera(35.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.near = 0.01f;
		camera.far = 1000f;
		camera.lookAt(Vector3.Z);
		camera.translate(0, 2, 0);
		camera.update();
		
		velocidade = new Vector3();
		onGravity = false;
		fuel = 1000f;
		newPosition = new Vector3(0,0,-450);
		lastPosition = new Vector3(0, 0, -450);
	}
	
	public void setYaw(float angle) {
		camera.setYaw(angle);
		gameObject.transform.rotate(Vector3.Y, angle);
	}
	
	public void setPitch(float angle) {
		camera.setPitch(angle);
		gameObject.transform.rotate(Vector3.X, angle);
	}
	
	public void setRoll(float angle) {
		camera.setRoll(angle);
		gameObject.transform.rotate(Vector3.Z, angle);
	}

	public void andarParaFrente() {
		direcao = FRENTE;
	}

	public void andarParaTras() {
		direcao = TRAS;
	}

	public void andarParaDireita() {
		direcao = DIREITA;
	}

	public void andarParaEsquerda() {
		direcao = ESQUERDA;
	}
	
	public void parar() {
		direcao = PARADO;
	}
	
	public void inclinarParaEsquerda() {
		inclinar = -1;
	}
	
	public void inclinarParaDireita() {
		inclinar = 1;
	}
	
	public void pararDeInclinar() {
		inclinar = 0;
	}

	public void update(float delta) {
		
		/* Atualizar orientacao da camera */
		setYaw(-Gdx.input.getDeltaX() * camera.SENSITIVITY);
		setPitch(Gdx.input.getDeltaY() * camera.SENSITIVITY);
		
		if(onGravity && fuel < 1000) {
			fuel += 1;
		}
		
		if (inclinar != 0) {
			setRoll(ROLL_SPEED*inclinar*camera.SENSITIVITY);
		}
		
		if (direcao == FRENTE) {
			gameObject.transform.setTranslation(camera.position);
			gameObject.transform.getTranslation(newPosition);
			
			velocidade.z += ACELERACAO * delta;
			if (velocidade.z >= 8f)
				velocidade.z = 8;
			camera.translate(camera.direction);
		}
		
		if (direcao == PARADO) {
			gameObject.transform.setTranslation(camera.position);
			
			velocidade.z -= ACELERACAO * delta;
			if (velocidade.z <= 0.0f) {
				velocidade.z = 0;
			}
			//camera.translate(velocidade);
			gameObject.transform.getTranslation(lastPosition);
			gameObject.transform.getTranslation(newPosition);
		}
		
		if (direcao == TRAS) {
			gameObject.transform.setTranslation(camera.position);
			gameObject.transform.getTranslation(newPosition);
			
			camera.translate(camera.direction.cpy().scl(-1));
		}
		
		if (direcao == ESQUERDA) {
			gameObject.transform.setTranslation(camera.position);
			gameObject.transform.getTranslation(newPosition);
			
			Vector3 esquerda = camera.PITCH_AXIS;
			camera.translate(esquerda);
		}
		
		if (direcao == DIREITA) {
			gameObject.transform.setTranslation(camera.position);
			gameObject.transform.getTranslation(newPosition);
			
			Vector3 direita = camera.PITCH_AXIS.cpy().scl(-1);
			camera.translate(direita);
		}

		float diff = lastPosition.dst(newPosition);
		if(diff > 10 && fuel > 0) {
			fuel -= 2;
		}
	}

	public GameObject getCurrent() {
		return gameObject;
	}
}
