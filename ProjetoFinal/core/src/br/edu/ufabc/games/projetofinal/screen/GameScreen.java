package br.edu.ufabc.games.projetofinal.screen;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.utils.Array;

import br.edu.ufabc.games.projetofinal.model.Bodies;
import br.edu.ufabc.games.projetofinal.model.GameObject;
import br.edu.ufabc.games.projetofinal.model.MassiveBody;
import br.edu.ufabc.games.projetofinal.model.ModelFactory;
import br.edu.ufabc.games.projetofinal.model.Nave;
import br.edu.ufabc.games.projetofinal.util.ChasingCamera;
import br.edu.ufabc.games.projetofinal.util.Commands;
import br.edu.ufabc.games.projetofinal.util.Utils;

public class GameScreen extends AbstractScreen {
	// parte 2D
	private Matrix4 viewMatrix;
	private Matrix4 tranMatrix;
	private SpriteBatch spriteBatch;

	// parte 3D
	private ModelBatch modelBatch;
	private Environment environment;
	private ChasingCamera camera;
	private BitmapFont bitmapFont;

	// elementos
	private GameObject cenario;
	private Nave nave;
	private Array<MassiveBody> bodies;
	
	public static float ORBITAL_VELOCITY = 0.2f;
	
	public GameScreen(String id) {
		super(id);
		Bullet.init();
		bodies =  new Array<MassiveBody>();
		viewMatrix = new Matrix4();
		tranMatrix = new Matrix4();
		spriteBatch = new SpriteBatch();
		modelBatch = new ModelBatch();
		bitmapFont = new BitmapFont(Gdx.files.internal("fonts/space.fnt"));
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1, 1, 1, 1));
		camera = new ChasingCamera(67.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), -10, -8);
		camera.near = 0.01f;
		camera.far = 1000f;
		camera.setOffsetYIn(5);
		camera.setOffsetYOut(5);

		cenario = new GameObject(ModelFactory.getModelbyName("CENARIO"));
		cenario.transform.scale(500, 500, 500);
		nave = new Nave();
		nave.getCurrent().transform.translate(0, 10, 0);
		nave.getCurrent().transform.scale(0.5f, 0.5f, 0.5f);
		
		
		
		Random rnd = new Random();
		
		MassiveBody sun = new MassiveBody("SUN");
		sun.setPosition(Bodies.SUN.getPos());
		sun.setVelocity(Bodies.SUN.getVel());
		sun.setMass(Bodies.SUN.getMass());
		sun.getCurrent().transform.scale(Bodies.SUN.getScale(), Bodies.SUN.getScale(), Bodies.SUN.getScale());
		
		bodies.add(sun);
		int numPlanets = rnd.nextInt((5 - 1) + 1);
		System.out.println("Numero satelites: " + (numPlanets + 1));
		for(int i = 0; i < numPlanets + 1; i++) {
			createBody(i);
		}
		
		camera.setObjectToFollow(nave.getCurrent());

		camera.update();

	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void update(float delta) {
	    
		if (Commands.comandos[Commands.FRENTE]) {
			nave.andarParaFrente();
		}
		if (Commands.comandos[Commands.TRAS]) {
			nave.andarParaTras();
		}
		if (Commands.comandos[Commands.ESQUERDA]) {
			nave.andarParaEsquerda();
		}
		if (Commands.comandos[Commands.DIREITA]) {
			nave.andarParaDireita();
		}
		if (Commands.comandos[Commands.CIMA]) {
			nave.andarParaCima();
		}
		if (Commands.comandos[Commands.BAIXO]) {
			nave.andarParaBaixo();
		}
		if (!Commands.comandos[Commands.FRENTE] && !Commands.comandos[Commands.TRAS]
				&& !Commands.comandos[Commands.ESQUERDA] && !Commands.comandos[Commands.DIREITA]
						&& !Commands.comandos[Commands.CIMA] && !Commands.comandos[Commands.BAIXO]) {
			nave.parar();
		}
		
		updateBodies(delta);
		nave.update(delta);
		
	}
	
	public void createBody(int id) {
		MassiveBody planeta = new MassiveBody(Bodies.getById(id).getModel());
		
		planeta.setPosition(Bodies.getById(id).getPos());
		planeta.setVelocity(Bodies.getById(id).getVel());
		planeta.setMass(Bodies.getById(id).getMass());
		planeta.getCurrent().transform.scale(Bodies.getById(id).getScale(), Bodies.getById(id).getScale(), Bodies.getById(id).getScale());
		bodies.add(planeta);
	}
	
	public void updateBodies(float delta) {
		for(int i = 0; i < bodies.size; i++) {
			MassiveBody a = bodies.get(i);
			for(int j =0; j < bodies.size; j++) {
				if(i == j) {
					continue;
				}
				MassiveBody b = bodies.get(j);
				a.applyForce(a.forceFrom(b), delta);
				a.update(delta);
				b.update(delta);
			}
		}
		
	}

	@Override
	public void draw(float delta) {
		Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 0);
		if (nave != null) {
			modelBatch.begin(camera);
			modelBatch.render(cenario, environment);
			for(MassiveBody mb : bodies) {
				modelBatch.render(mb.getCurrent(), environment);
			}
			
			modelBatch.render(nave.getCurrent(), environment);
			modelBatch.end();
			camera.update();
		}

		viewMatrix.setToOrtho2D(0, 0, Utils.GAME_WIDTH, Utils.GAME_HEIGHT);
		spriteBatch.setProjectionMatrix(viewMatrix);
		spriteBatch.setTransformMatrix(tranMatrix);
		spriteBatch.begin();

		bitmapFont.draw(spriteBatch, "Vel " + (int) nave.velocidade.z, 10, 450);
		spriteBatch.end();

	}

}
