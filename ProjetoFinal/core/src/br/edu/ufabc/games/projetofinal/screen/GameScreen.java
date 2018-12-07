package br.edu.ufabc.games.projetofinal.screen;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.CollisionObjectWrapper;
import com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm;
import com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithmConstructionInfo;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDispatcherInfo;
import com.badlogic.gdx.physics.bullet.collision.btManifoldResult;
import com.badlogic.gdx.physics.bullet.collision.btSphereBoxCollisionAlgorithm;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;
import com.badlogic.gdx.utils.Array;

import br.edu.ufabc.games.projetofinal.model.Bodies;
import br.edu.ufabc.games.projetofinal.model.GameObject;
import br.edu.ufabc.games.projetofinal.model.MassiveBody;
import br.edu.ufabc.games.projetofinal.model.ModelFactory;
import br.edu.ufabc.games.projetofinal.model.Ship;
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
	private BitmapFont bitmapFont;

	// elementos
	private GameObject cenario;
	private GameObject objetivo;
	private Ship nave;
	private Array<GameObject> bodies;

	public static float ORBITAL_VELOCITY = 0.2f;

	// Bullet
	btCollisionConfiguration collisionConfig;
	btDispatcher dispatcher;
	private Music music;

	public GameScreen(String id) {
		super(id);
		Bullet.init();
		bodies = new Array<GameObject>();
		viewMatrix = new Matrix4();
		tranMatrix = new Matrix4();
		spriteBatch = new SpriteBatch();
		modelBatch = new ModelBatch();
		bitmapFont = new BitmapFont(Gdx.files.internal("fonts/space.fnt"));
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1, 1, 1, 1));

		cenario = new GameObject(ModelFactory.getModelbyName("CENARIO"), null);
		cenario.transform.scale(500, 500, 500);
		
		objetivo = new GameObject(ModelFactory.getModelbyName("OBJETIVO"), new btSphereShape(15f));
		objetivo.transform.translate(0,0,0);
		objetivo.transform.scale(3, 3, 3);
		
		nave = new Ship();
		nave.setPosition(0, 0, -300);
		nave.getCurrent().setMass(100);
		nave.getCurrent().corpo.setWorldTransform(nave.getCurrent().transform);
		
		bodies.add(nave.getCurrent());
		
		music = Gdx.audio.newMusic(Gdx.files.internal("colisao.mp3"));
		music.setLooping(false);
		
		MassiveBody sun1 = createStar(new Vector3(-50,0,-250));
		MassiveBody sun2 = createStar(new Vector3(0,0,-250));
		MassiveBody sun3 = createStar(new Vector3(10,-10,-100));
		
		//createSystem(sun1);
		createSystem(sun2);
		//createSystem(sun3);
		
		collisionConfig = new btDefaultCollisionConfiguration();
		dispatcher = new btCollisionDispatcher(collisionConfig);

	}
	
	void createSystem(MassiveBody sun) {
		Random rnd = new Random();
		int numPlanets = rnd.nextInt(10 + 1) + 5;
		for (int i = 0; i < numPlanets; i++) {
			createBody(rnd.nextInt(5), sun);
		}
	}
	
	MassiveBody createStar(Vector3 position) {
		MassiveBody star = new MassiveBody("SUN");
		star.getCurrent().setPosition(position);
		star.getCurrent().setVelocity(Bodies.SUN.getVel());
		star.getCurrent().setMass(Bodies.SUN.getMass());
		star.getCurrent().transform.scale(Bodies.SUN.getScale(), Bodies.SUN.getScale(), Bodies.SUN.getScale());
		
		bodies.add(star.getCurrent());
		
		return star;
	}
	
	boolean checkCollision(GameObject b) {
		CollisionObjectWrapper co0 = new CollisionObjectWrapper(nave.getCurrent().corpo);
		CollisionObjectWrapper co1 = new CollisionObjectWrapper(b.corpo);

		btCollisionAlgorithmConstructionInfo ci = new btCollisionAlgorithmConstructionInfo();
		ci.setDispatcher1(dispatcher);
		btCollisionAlgorithm algorithm = new btSphereBoxCollisionAlgorithm(null, ci, co0.wrapper, co1.wrapper, false);

		btDispatcherInfo info = new btDispatcherInfo();
		btManifoldResult result = new btManifoldResult(co0.wrapper, co1.wrapper);

		algorithm.processCollision(co0.wrapper, co1.wrapper, info, result);

		boolean r = result.getPersistentManifold().getNumContacts() > 0;

		result.dispose();
		info.dispose();
		algorithm.dispose();
		ci.dispose();
		co1.dispose();
		co0.dispose();

		return r;
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
		if (!Commands.comandos[Commands.FRENTE] && !Commands.comandos[Commands.TRAS]
				&& !Commands.comandos[Commands.ESQUERDA] && !Commands.comandos[Commands.DIREITA]
				&& !Commands.comandos[Commands.FRENTE] && !Commands.comandos[Commands.TRAS]) {
			nave.parar();
		}
		if (Commands.comandos[Commands.INCLINANDO_ESQUERDA]) {
			nave.inclinarParaEsquerda();
		}
		if (Commands.comandos[Commands.INCLINANDO_DIREITA]) {
			nave.inclinarParaDireita();
		}
		if(!Commands.comandos[Commands.INCLINANDO_ESQUERDA] && !Commands.comandos[Commands.INCLINANDO_DIREITA]) {
			nave.pararDeInclinar();
		}
		if (Commands.comandos[Commands.ACELERANDO]) {
			nave.acelerar();
		}
		if (!Commands.comandos[Commands.ACELERANDO]) {
			nave.desacelerar();
		}
		
		nave.setCloseUp(Commands.comandos[Commands.CLOSEUP]);
		
		for(GameObject b: bodies) {
			if(checkCollision(b) && b.bodyType.equals("SUN")) {
				nave.remainingFalls -= 1;
				music.play();
				break;

			}
		}
		
		updateBodies(delta);
		nave.update(delta);
		nave.getCurrent().corpo.setWorldTransform(nave.getCurrent().transform);
		
		/* Consicoes de fim de jogo */
		if (nave.remainingFalls < 0) {
			END_STATE = "LOST ON A GRAVITATIONAL WELL";
			setDone(true);
		}
		if(nave.fuel <=0) {
			END_STATE = "OUT OF FUEL";
			setDone(true);
		}
		if(checkCollision(objetivo)) {
			END_STATE = "ARRIVED SAFELY!";
			setDone(true);
		}
		
	}

	public void createBody(int id, MassiveBody sun) {
		MassiveBody mb = new MassiveBody(Bodies.getById(id).getModel());

		Vector3 position = Bodies.getById(id).getPos().cpy();
		Vector3 positionSun = sun.getCurrent().getPosition().cpy();
		mb.getCurrent().setPosition(position.add(positionSun));
		mb.getCurrent().setVelocity(Bodies.getById(id).getVel());
		mb.getCurrent().setMass(Bodies.getById(id).getMass());
		mb.getCurrent().transform.scale(Bodies.getById(id).getScale(), Bodies.getById(id).getScale(),
				Bodies.getById(id).getScale());

		bodies.add(mb.getCurrent());
		bodies.get(bodies.size-1).corpo.setWorldTransform(bodies.get(bodies.size-1).transform);
		
	}

	public void updateBodies(float delta) {
		for (int i = 0; i < bodies.size; i++) {
			GameObject a = bodies.get(i);
			for (int j = 0; j < bodies.size; j++) {
				if (i == j) {
					continue;
				}
				GameObject b = bodies.get(j);
				a.applyForce(a.forceFrom(b), delta);
				a.update(delta);
				b.update(delta);
				a.corpo.setWorldTransform(a.transform);
				b.corpo.setWorldTransform(b.transform);
			}
		}
	}

	@Override
	public void draw(float delta) {
		Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 0);
		if (nave != null) {
			modelBatch.begin(nave.camera);
			modelBatch.render(cenario, environment);
			modelBatch.render(objetivo, environment);
			for (GameObject mb : bodies) {
				modelBatch.render(mb, environment);
			}

			modelBatch.render(nave.getCurrent(), environment);
			modelBatch.end();
			nave.camera.update();
		}

		viewMatrix.setToOrtho2D(0, 0, Utils.GAME_WIDTH, Utils.GAME_HEIGHT);
		spriteBatch.setProjectionMatrix(viewMatrix);
		spriteBatch.setTransformMatrix(tranMatrix);
		spriteBatch.begin();
		bitmapFont.draw(spriteBatch, "Mission: reach the mothership", 550, 900);

		bitmapFont.draw(spriteBatch, "Energy " + (int) nave.fuel, 10, 850);
		spriteBatch.end();

	}

}
