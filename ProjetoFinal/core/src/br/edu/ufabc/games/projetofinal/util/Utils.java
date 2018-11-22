package br.edu.ufabc.games.projetofinal.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Utils {
	public static final int GAME_WIDTH  = 1024;
	public static final int GAME_HEIGHT = 768;
	
	
	public static Vector2 convertCoordinates(float x, float y) {
		Vector2 position = new Vector2();
		position.x = x / ((float)Gdx.graphics.getWidth() / GAME_WIDTH);
		position.y = GAME_HEIGHT - y / ((float)Gdx.graphics.getHeight() / GAME_HEIGHT);
		
		return position;
	}

}
