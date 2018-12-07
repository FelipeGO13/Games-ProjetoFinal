package br.edu.ufabc.games.projetofinal.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

import br.edu.ufabc.games.projetofinal.MeuJogo;
import br.edu.ufabc.games.projetofinal.util.Utils;

public class StartScreen extends AbstractScreen {
	private Texture texture;
	private SpriteBatch spriteBatch;
	private Matrix4 viewMatrix;
	private Matrix4 tranMatrix;
	private BitmapFont font;
	private boolean loaded = false;
	private int progress;
	private float time = 0;
	private boolean visible = false;
	private Music   music;

	public StartScreen(String id) {
		super(id);
		spriteBatch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("solarsystem.jpg"));
		viewMatrix = new Matrix4();
		tranMatrix = new Matrix4();
		font = new BitmapFont(Gdx.files.internal("fonts/space.fnt"));
		progress = 0;

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		spriteBatch.dispose();
		texture.dispose();
		music.dispose();
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		if (loaded) {
			if (Gdx.input.justTouched()) {
				setDone(true);
			}
		}

		progress = (int)(MeuJogo.assetManager.getProgress()*100);
		MeuJogo.assetManager.update();
		if (progress == 100) {
			loaded = true;
		}
		time += delta;
		if (time >= 0.5f) {
			visible = !visible;
			time=0;
		}
				
	}

	@Override
	public void draw(float delta) {
		
		// TODO Auto-generated method stub
		// tornando meu plano cartesiano can�nico 2D
		viewMatrix.setToOrtho2D(0, 0, Utils.GAME_WIDTH, Utils.GAME_HEIGHT);
		// spritebatch, siga esse padr�o de plano cartesiano
		spriteBatch.setProjectionMatrix(viewMatrix);
		// qualquer transforma��o linear (escala, rota��o), ficara armazenada na
		// tranMatrix
		spriteBatch.setTransformMatrix(tranMatrix);
		spriteBatch.begin();
		spriteBatch.draw(texture, 0, 0, Utils.GAME_WIDTH, Utils.GAME_HEIGHT, 0, 0, texture.getWidth(),
				texture.getHeight(), false, false);
		if (!loaded)
		    font.draw(spriteBatch, "Loading..."+progress+"%", 100, 400);
		else {
			if (visible) {
				font.draw(spriteBatch,"Touch to Start!",100,70); 
			}
		}

		spriteBatch.end();
	}

}
