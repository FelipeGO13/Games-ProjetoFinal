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

	private static final float ACELERACAO = 5f;
	
	public btCollisionShape shipShape;
	public boolean onGravity;

	public Nave () {
		shipShape = new btBoxShape(new Vector3(0.5f, 0.5f, 0.5f));
		gameObject = new GameObject(ModelFactory.getModelbyName("NAVE"));
		gameObject.transform.scale(0.5f, 0.5f, 0.5f);
		gameObject.transform.translate(0, 2, 0);
		frente = new Vector3(0, 0, 20f);
		tras = new Vector3(0, 0, -50f);
		esquerda = new Vector3(20f, 0, 0);
		direita = new Vector3(-20f, 0, 0);
		velocidade = new Vector3();
		onGravity = false;
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

	public void update(float delta) {
		if (direcao == FRENTE) {
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
			
		}
		if (direcao == TRAS) {
			gameObject.transform.translate(tras.cpy().scl(delta));
		}
		if (direcao == ESQUERDA) {
			gameObject.transform.translate(esquerda.cpy().scl(delta));
		}
		if (direcao == DIREITA) {
			gameObject.transform.translate(direita.cpy().scl(delta));
		}
	}

	public GameObject getCurrent() {
		return gameObject;
	}
}
