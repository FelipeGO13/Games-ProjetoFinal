package br.edu.ufabc.games.projetofinal.model;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;

public class Nave {
	private GameObject gameObject;
	private Vector3 frente;
	private Vector3 esquerda;
	private Vector3 direita;
	private Vector3 tras;
	public  Vector3 velocidade;

	private int direcao;

	private static final int PARADO = 0;
	private static final int FRENTE = 1;
	private static final int TRAS = 2;
	private static final int ESQUERDA = 3;
	private static final int DIREITA = 4;
	public static final int CIMA = 5;
	public static final int BAIXO  = 6;

	private static final float ACELERACAO = 5f;
	public btCollisionShape shipShape;
	
	public boolean onGravity;
	public float fuel;
	private Vector3 lastPosition;
	private Vector3 newPosition;
	
	public Nave () {
		shipShape = new btBoxShape(new Vector3(0.5f, 0.5f, 0.5f));
		gameObject = new GameObject(ModelFactory.getModelbyName("NAVE"), shipShape);
		gameObject.transform.scale(0.5f, 0.5f, 0.5f);
		gameObject.transform.translate(0, 2, 0);
		frente = new Vector3(0, 0, 20f);
		tras = new Vector3(0, 0, -50f);
		esquerda = new Vector3(20f, 0, 0);
		direita = new Vector3(-20f, 0, 0);
		velocidade = new Vector3();
		onGravity = false;
		fuel = 100f;
		newPosition = new Vector3(0,0,-450);
		lastPosition = new Vector3(0, 0, -450);
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
	public void andarParaCima() {
		direcao = CIMA;
	}
	public void andarParaBaixo() {
		direcao = BAIXO;
	}
	public void parar() {
		direcao = PARADO;
	}

	public void update(float delta) {
		
		if(onGravity && fuel < 100) {
			System.out.println("Gravidade");
			fuel += 1;
		}
		
		
		if (direcao == FRENTE) {
			gameObject.transform.getTranslation(newPosition);
			velocidade.z += ACELERACAO * delta;
			if (velocidade.z >= 8f)
				velocidade.z = 8;
			gameObject.transform.translate(velocidade);
		}
		if (direcao == PARADO) {
			velocidade.z -= ACELERACAO * delta;
			if (velocidade.z <= 0.0f) {
				velocidade.z = 0;
			}
			gameObject.transform.translate(velocidade);
			gameObject.transform.getTranslation(lastPosition);
			gameObject.transform.getTranslation(newPosition);
		}
		if (direcao == TRAS) {
			gameObject.transform.translate(tras.cpy().scl(delta));
		}
		if (direcao == ESQUERDA) {
			
			gameObject.transform.rotate(Vector3.Y, 1);
			velocidade.z -= ACELERACAO * delta;
			if (velocidade.z <= 0.0f) {
				velocidade.z = 0;
			}
			gameObject.transform.translate(velocidade);
			gameObject.setAngle(1);
		}
		if (direcao == DIREITA) {
			gameObject.transform.rotate(Vector3.Y, -1);
			velocidade.z -= ACELERACAO * delta;
			if (velocidade.z <= 0.0f) {
				velocidade.z = 0;
			}
			gameObject.transform.translate(velocidade);
			gameObject.setAngle(-1);
		}
		if (direcao == CIMA) {
			gameObject.transform.rotate(Vector3.X, -1);
			velocidade.z -= ACELERACAO * delta;
			if (velocidade.z <= 0.0f) {
				velocidade.z = 0;
			}
			gameObject.transform.translate(velocidade);
		}
		if (direcao == BAIXO) {
			gameObject.transform.rotate(Vector3.X, 1);
			velocidade.z -= ACELERACAO * delta;
			if (velocidade.z <= 0.0f) {
				velocidade.z = 0;
			}
			gameObject.transform.translate(velocidade);
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
