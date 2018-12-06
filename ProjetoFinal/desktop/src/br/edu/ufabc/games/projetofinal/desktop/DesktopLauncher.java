package br.edu.ufabc.games.projetofinal.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import br.edu.ufabc.games.projetofinal.MeuJogo;
import br.edu.ufabc.games.projetofinal.util.Utils;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = Utils.GAME_HEIGHT;
		config.width = Utils.GAME_WIDTH;
		config.fullscreen = true;
		new LwjglApplication(new MeuJogo(), config);
	}
}
