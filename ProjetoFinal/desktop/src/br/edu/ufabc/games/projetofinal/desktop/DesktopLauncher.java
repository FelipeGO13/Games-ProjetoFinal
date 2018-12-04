package br.edu.ufabc.games.projetofinal.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import br.edu.ufabc.games.projetofinal.MeuJogo;
import br.edu.ufabc.games.projetofinal.util.Utils;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Principia Mathematica";
		config.width = Utils.GAME_WIDTH;
		config.height = Utils.GAME_HEIGHT;
		config.fullscreen = true;
		new LwjglApplication(new MeuJogo(), config);
	}
}
