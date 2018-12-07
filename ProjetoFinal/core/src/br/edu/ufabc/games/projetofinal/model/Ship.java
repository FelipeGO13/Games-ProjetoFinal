package br.edu.ufabc.games.projetofinal.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;

import br.edu.ufabc.games.projetofinal.util.FirstPersonCamera;

public class Ship {
	private GameObject gameObject;	
	public FirstPersonCamera camera;
	

	private int direcao;
	private int inclinar = 0;
	private boolean acelerando = false;
	private boolean closeup = false;
	private float velocidade = NORMAL_SPEED;
	private Vector3 direction = new Vector3();

	private static final int PARADO = 0;
	private static final int FRENTE = 1;
	private static final int TRAS = 2;
	private static final int ESQUERDA = 3;
	private static final int DIREITA = 4;

	private static final float ACELERACAO = 2f;
	private static final float NORMAL_SPEED = 3f;
	private static final float MAX_SPEED = 8f;
	private static final float ROLL_SPEED = 30f;
	
	public btCollisionShape shipShape;
	
	public int remainingFalls = 1;
	public float fuel;
	private boolean stop = true;
	private Vector3 lastPosition;
	private Vector3 newPosition;
	private Music   music;
	
	
	
	public Ship() {
		shipShape = new btBoxShape(new Vector3(0.5f, 0.5f, 0.5f));
		gameObject = new GameObject(ModelFactory.getModelbyName("NAVE"), shipShape);
		gameObject.transform.scale(0.5f, 0.5f, 0.5f);
		gameObject.bodyType = "NAVE";
		
		camera = new FirstPersonCamera(35.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.near = 0.01f;
		camera.far = 1000f;
		camera.lookAt(Vector3.Z);
		camera.update();
		
		fuel = 1000f;
		newPosition = new Vector3();
		lastPosition = new Vector3();
	}
	
	public void setPosition(float x, float y, float z) {
		camera.position.set(x, y, z);
		gameObject.setPosition(camera.position.cpy());
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
		music = Gdx.audio.newMusic(Gdx.files.internal("somnave.mp3"));
		music.setLooping(false);
		music.play();
		
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
	
	public void acelerar() {
		acelerando = true;
	}
	
	public void desacelerar() {
		acelerando = false;
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
	
	public void setCloseUp(boolean value) {
		closeup = value;
	}

	public void update(float delta) {
		
		/* Atualizar orientacao da camera */
		setYaw(-Gdx.input.getDeltaX() * camera.SENSITIVITY);
		setPitch(Gdx.input.getDeltaY() * camera.SENSITIVITY);
		
		if (inclinar != 0) {
			setRoll(ROLL_SPEED*inclinar*camera.SENSITIVITY);
		}
		
		/* Atualizar direcao de translacao da camera */
		if (direcao == FRENTE) {
			gameObject.transform.getTranslation(newPosition);
			direction.set(camera.direction);
		}
		if (direcao == TRAS) {
			gameObject.transform.getTranslation(newPosition);
			direction.set(camera.direction).scl(-1);
		}
		if (direcao == ESQUERDA) {
			gameObject.transform.getTranslation(newPosition);
			direction.set(camera.PITCH_AXIS);
		}
		if (direcao == DIREITA) {
			gameObject.transform.getTranslation(newPosition);	
			direction.set(camera.PITCH_AXIS).scl(-1);
		}
		
		/* Translacao da nave */
		
		if (direcao == PARADO) {
			gameObject.transform.getTranslation(lastPosition);
			gameObject.transform.getTranslation(newPosition);
			
			if (!acelerando) {
				velocidade -= ACELERACAO * delta;
				if (velocidade <= 0f) {
					//direction.set(0,0,0);
					velocidade = NORMAL_SPEED;
				}
			}
			if (stop) {
				gameObject.velocity.set(0,0,0);
				stop = false;
			}
			
		} else {
			stop = true;
			
			if (acelerando) {
				velocidade += ACELERACAO * delta;
				if (velocidade >= MAX_SPEED)
					velocidade = MAX_SPEED;
			}
			
			//camera.translate(direction.cpy().scl(velocidade));
			gameObject.velocity.add(direction.cpy().scl(velocidade*delta));
		}
		
		Vector3 pos = gameObject.position.cpy();
		if (!closeup) {
			pos.sub(camera.direction);
		}
		camera.position.set(pos);
		
		
		/* Atualizar energia e massa capturados */
		
		float diff = lastPosition.dst(newPosition);
		if(diff > 10 && fuel > 0) {
			fuel -= 8*direction.len();
		}
		
		fuel += gameObject.getEnergy();
		
	}

	public GameObject getCurrent() {
		return gameObject;
	}
}
