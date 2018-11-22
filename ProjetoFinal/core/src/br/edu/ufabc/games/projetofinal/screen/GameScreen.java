package br.edu.ufabc.games.projetofinal.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Matrix4;

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
		private ChasingCamera camera;
		private BitmapFont    bitmapFont;

		// elementos
		private GameObject cenario;
		private Nave nave;
		private Planeta planeta;

		public GameScreen(String id) {
			super(id);
			viewMatrix = new Matrix4();
			tranMatrix = new Matrix4();
			spriteBatch = new SpriteBatch();
			modelBatch = new ModelBatch();
			bitmapFont = new BitmapFont(Gdx.files.internal("fonts/space.fnt"));
			environment = new Environment();
			environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1, 1, 1, 1));
			camera = new ChasingCamera(67.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),-10,-10);
			camera.near = 0.01f;
			camera.far = 1000f;
			camera.setOffsetYIn(5);
			camera.setOffsetYOut(5);

			
			cenario = new GameObject(ModelFactory.getModelbyName("CENARIO"));
			cenario.transform.scale(50, 1, 50);
			nave = new Nave();
			nave.getCurrent().transform.translate(0, 10, 0);
			planeta = new Planeta();
			planeta.getCurrent().transform.translate(0, 5, 10);
			
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
			if (!Commands.comandos[Commands.FRENTE] && !Commands.comandos[Commands.TRAS]
					&& !Commands.comandos[Commands.ESQUERDA] && !Commands.comandos[Commands.DIREITA]) {
				nave.parar();
			}
			nave.update(delta);
		}

		@Override
		public void draw(float delta) {
			Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_COLOR_BUFFER_BIT);
			Gdx.gl.glClearColor(0, 0, 0, 0);
			if (nave != null) {
				modelBatch.begin(camera);
				modelBatch.render(cenario, environment);
				modelBatch.render(planeta.getCurrent(), environment);
				modelBatch.render(nave.getCurrent(), environment);
				modelBatch.end();
				camera.update();
			}

			viewMatrix.setToOrtho2D(0, 0, Utils.GAME_WIDTH, Utils.GAME_HEIGHT);
			spriteBatch.setProjectionMatrix(viewMatrix);
			spriteBatch.setTransformMatrix(tranMatrix);
			spriteBatch.begin();

			bitmapFont.draw(spriteBatch,  "Vel "+(int) nave.velocidade.z, 10,450);
			spriteBatch.end();

		}


	
}
