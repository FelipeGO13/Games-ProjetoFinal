package br.edu.ufabc.games.projetofinal.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Matrix4;
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

import br.edu.ufabc.games.projetofinal.model.GameObject;
import br.edu.ufabc.games.projetofinal.model.ModelFactory;
import br.edu.ufabc.games.projetofinal.model.Nave;
import br.edu.ufabc.games.projetofinal.model.Planeta;
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
	//private ChasingCamera camera;
	private BitmapFont bitmapFont;

	// elementos
	private GameObject cenario;
	private Nave nave;
	private Planeta planeta;

	// Bullet
	btCollisionConfiguration collisionConfig;
	btDispatcher dispatcher;

	public GameScreen(String id) {
		super(id);
		Bullet.init();
		viewMatrix = new Matrix4();
		tranMatrix = new Matrix4();
		spriteBatch = new SpriteBatch();
		modelBatch = new ModelBatch();
		bitmapFont = new BitmapFont(Gdx.files.internal("fonts/space.fnt"));
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1, 1, 1, 1));
		//camera = new ChasingCamera(67.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), -10, -10);
		//camera.near = 0.01f;
		//camera.far = 1000f;
		//camera.setOffsetYIn(5);
		//camera.setOffsetYOut(5);

		cenario = new GameObject(ModelFactory.getModelbyName("CENARIO"), null);
		cenario.transform.scale(50, 1, 50);
		nave = new Nave();
		nave.getCurrent().transform.translate(0, 10, 0);
		planeta = new Planeta();
		planeta.getCurrent().transform.translate(0, 5, 10);
		nave.getCurrent().corpo.setWorldTransform(nave.getCurrent().transform);
		planeta.getCurrent().corpo.setWorldTransform(planeta.getCurrent().transform);
		//camera.setObjectToFollow(nave.getCurrent());

		collisionConfig = new btDefaultCollisionConfiguration();
		dispatcher = new btCollisionDispatcher(collisionConfig);

		//camera.update();

	}

	boolean checkCollision() {
		CollisionObjectWrapper co0 = new CollisionObjectWrapper(nave.getCurrent().corpo);
		CollisionObjectWrapper co1 = new CollisionObjectWrapper(planeta.getCurrent().corpo);

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
				&& !Commands.comandos[Commands.ESQUERDA] && !Commands.comandos[Commands.DIREITA]) {
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
		
		nave.update(delta);
		nave.getCurrent().corpo.setWorldTransform(nave.getCurrent().transform);
		if(checkCollision()) {
			System.out.println("Colidiu");
		}
	}

	@Override
	public void draw(float delta) {
		Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 0);
		if (nave != null) {
			modelBatch.begin(nave.camera);
			modelBatch.render(cenario, environment);
			modelBatch.render(planeta.getCurrent(), environment);
			modelBatch.render(nave.getCurrent(), environment);
			modelBatch.end();
			nave.camera.update();
		}

		viewMatrix.setToOrtho2D(0, 0, Utils.GAME_WIDTH, Utils.GAME_HEIGHT);
		spriteBatch.setProjectionMatrix(viewMatrix);
		spriteBatch.setTransformMatrix(tranMatrix);
		spriteBatch.begin();

		bitmapFont.draw(spriteBatch, "Vel " + (int) nave.velocidade.z, 10, 450);
		spriteBatch.end();

	}

}
