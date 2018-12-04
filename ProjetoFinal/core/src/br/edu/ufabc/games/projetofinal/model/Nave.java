package br.edu.ufabc.games.projetofinal.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;

import br.edu.ufabc.games.projetofinal.util.FirstPersonCamera;

public class Nave {
	private GameObject gameObject;
	private Vector3 frente;
	private Vector3 esquerda;
	private Vector3 direita;
	private Vector3 tras;
	public  Vector3 velocidade;
	public Vector3 position = new Vector3();
	
	public FirstPersonCamera camera;

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

	public Nave () {
		shipShape = new btBoxShape(new Vector3(0.5f, 0.5f, 0.5f));
		gameObject = new GameObject(ModelFactory.getModelbyName("NAVE"), shipShape);
		gameObject.transform.scale(0.5f, 0.5f, 0.5f);
		gameObject.transform.rotate(Vector3.Y, 180);
		
		camera = new FirstPersonCamera(67.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.near = 0.01f;
		camera.far = 1000f;
		camera.translate(0, 2, 0);
		
		
		frente = new Vector3(0, 0, 20f);
		tras = new Vector3(0, 0, -50f);
		esquerda = new Vector3(20f, 0, 0);
		direita = new Vector3(-20f, 0, 0);
		velocidade = new Vector3();
		
		camera.update();
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
		
		setYaw(-Gdx.input.getDeltaX() * camera.SENSITIVITY);
		setPitch(Gdx.input.getDeltaY() * camera.SENSITIVITY);
		
		if (inclinar != 0) {
			setRoll(ROLL_SPEED*inclinar*camera.SENSITIVITY);
		}
		
		gameObject.transform.setTranslation(camera.position);
		
		
		if (direcao == FRENTE) {
			velocidade.z += ACELERACAO * delta;
			if (velocidade.z >= 8f)
				velocidade.z = 8;
			camera.translate(camera.direction);
			
		}
		if (direcao == PARADO) {
			velocidade.z -= ACELERACAO * delta;
			if (velocidade.z <= 0.0f) {
				velocidade.z = 0;
			}
			//gameObject.transform.translate(velocidade);
			
			
		}
		if (direcao == TRAS) {
			camera.translate(camera.direction.cpy().scl(-1));
		}
		if (direcao == ESQUERDA) {
			Vector3 esquerda = camera.PITCH_AXIS;
			camera.translate(esquerda);
		}
		if (direcao == DIREITA) {
			Vector3 direita = camera.PITCH_AXIS.cpy().scl(-1);
			camera.translate(direita);
		}
		
		//gameObject.transform.setToLookAt(camera.direction.cpy().scl(-1), camera.up.cpy().scl(-1));
		
	}

	public GameObject getCurrent() {
		return gameObject;
	}
}
