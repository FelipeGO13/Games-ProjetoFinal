package br.edu.ufabc.games.projetofinal.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

import br.edu.ufabc.games.projetofinal.util.Utils;

public class CreditsScreen extends AbstractScreen{
	private SpriteBatch spriteBatch;
	private Texture     texture;
	private Matrix4 viewMatrix;
	private Matrix4 tranMatrix;
	private BitmapFont font;
	private float time = 0f;
	private boolean visible = false;
	
	public String blinking_text;

	public CreditsScreen(String id) {
		super(id);
		texture = new Texture(Gdx.files.internal("credits.jpg"));
		spriteBatch = new SpriteBatch();
		viewMatrix  = new Matrix4();
		tranMatrix  = new Matrix4();
		font = new BitmapFont(Gdx.files.internal("fonts/space.fnt"));
		font.setColor(Color.BLACK);
		blinking_text = "Touch to start again";
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		spriteBatch.dispose();
		texture.dispose();
	}

	@Override
	public void update(float delta) {
		if (Gdx.input.justTouched()) {
			setDone(true);
		}
		
		time += delta;
		if (time >= 0.5f) {
			visible = !visible;
			time=0;
		}
	}

	@Override
	public void draw(float delta) {
		
		viewMatrix.setToOrtho2D(0, 0, Utils.GAME_WIDTH, Utils.GAME_HEIGHT);
		spriteBatch.setProjectionMatrix(viewMatrix);
		spriteBatch.setTransformMatrix(tranMatrix);
		spriteBatch.begin();
		spriteBatch.draw(texture, 0, 0, Utils.GAME_WIDTH, Utils.GAME_HEIGHT, 0, 0, texture.getWidth(),
				texture.getHeight(), false, false);
		
		if (visible) {
			font.draw(spriteBatch, blinking_text,100,70); 
		}
		
		spriteBatch.end();
	}

}
