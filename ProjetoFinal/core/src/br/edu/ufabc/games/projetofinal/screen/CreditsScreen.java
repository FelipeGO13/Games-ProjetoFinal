package br.edu.ufabc.games.projetofinal.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

public class CreditsScreen extends AbstractScreen{
	private SpriteBatch spriteBatch;
	private Texture     texture;
	private Matrix4 viewMatrix;
	private Matrix4 tranMatrix;

	public CreditsScreen(String id) {
		super(id);
		texture = new Texture(Gdx.files.internal("credits.jpg"));
		spriteBatch = new SpriteBatch();
		viewMatrix  = new Matrix4();
		tranMatrix  = new Matrix4();
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		spriteBatch.dispose();
		texture.dispose();
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		if (Gdx.input.justTouched()) {
			setDone(true);
		}
	}

	@Override
	public void draw(float delta) {
	
	}

}
