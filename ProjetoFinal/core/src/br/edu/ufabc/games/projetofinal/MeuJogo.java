package br.edu.ufabc.games.projetofinal;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;

import br.edu.ufabc.games.projetofinal.screen.AbstractScreen;
import br.edu.ufabc.games.projetofinal.screen.CreditsScreen;
import br.edu.ufabc.games.projetofinal.screen.GameScreen;
import br.edu.ufabc.games.projetofinal.screen.StartScreen;
import br.edu.ufabc.games.projetofinal.util.Commands;
import br.edu.ufabc.games.projetofinal.util.Utils;

public class MeuJogo extends Game implements InputProcessor, ControllerListener  {
	private AbstractScreen currentScreen;
	public static AssetManager assetManager;
	
	@Override
	public void create () {
		Bullet.init();
		assetManager  = new AssetManager();
		assetManager.load("skybox.g3db",Model.class);
		assetManager.load("xwing/xwing.g3db", Model.class);
		assetManager.load("planets/sun.g3db", Model.class);
		assetManager.load("planets/planet_ako.g3db", Model.class);
		assetManager.load("planets/planet_dante.g3db", Model.class);
		assetManager.load("planets/planet_down.g3db", Model.class);
		assetManager.load("planets/planet_dust.g3db", Model.class);
		assetManager.load("planets/planet_reststop.g3db", Model.class);
		assetManager.load("mothership/mothership.g3db", Model.class);
		
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCursorCatched(true);
		
		currentScreen = new StartScreen("START");
	}

	@Override
	public void render () {
		currentScreen.render(Gdx.graphics.getDeltaTime());
		// logica minima do controller
		// quando uma tela termina sua fun��o, passa para a pr�xima
		if (currentScreen.isDone()) {
			if (currentScreen.getId().equals("START")) {
				currentScreen = new GameScreen("GAME");
			}
			else if (currentScreen.getId().equals("GAME")) {
				currentScreen = new CreditsScreen("CREDITS", currentScreen.END_STATE);
			}
			else {
				currentScreen = new StartScreen("START");
			}
			
		}
	}

	/*--- M�todos do InputProcessor - (touch + teclado)--------------------------------------*/
	@Override
	public boolean keyDown(int keycode) {
		
		if (keycode == Input.Keys.W) {
			Commands.comandos[Commands.FRENTE] = true;
			return true;
		}
		if (keycode == Input.Keys.S) {
			Commands.comandos[Commands.TRAS] = true;
			return true;
		}
		if (keycode == Input.Keys.A) {
			Commands.comandos[Commands.ESQUERDA] = true;
			return true;
		}
		if (keycode == Input.Keys.D) {
			Commands.comandos[Commands.DIREITA] = true;
			return true;
		}
		if (keycode == Input.Keys.Q) {
			Commands.comandos[Commands.INCLINANDO_ESQUERDA] = true;
			return true;
		}
		if (keycode == Input.Keys.E) {
			Commands.comandos[Commands.INCLINANDO_DIREITA] = true;
			return true;
		}
		if (keycode == Input.Keys.SHIFT_LEFT) {
			Commands.comandos[Commands.ACELERANDO] = true;
			return true;
		}
		if (keycode == Input.Keys.F) {
			Commands.comandos[Commands.CLOSEUP] = true;
			return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.W) {
			Commands.comandos[Commands.FRENTE] = false;
			return true;
		}
		if (keycode == Input.Keys.S) {
			Commands.comandos[Commands.TRAS] = false;
			return true;
		}
		if (keycode == Input.Keys.A) {
			Commands.comandos[Commands.ESQUERDA] = false;
			return true;
		}
		if (keycode == Input.Keys.D) {
			Commands.comandos[Commands.DIREITA] = false;
			return true;
		}
		if (keycode == Input.Keys.Q) {
			Commands.comandos[Commands.INCLINANDO_ESQUERDA] = false;
			return true;
		}
		if (keycode == Input.Keys.E) {
			Commands.comandos[Commands.INCLINANDO_DIREITA] = false;
			return true;
		}
		if (keycode == Input.Keys.SHIFT_LEFT) {
			Commands.comandos[Commands.ACELERANDO] = false;
			return true;
		}
		if (keycode == Input.Keys.F) {
			Commands.comandos[Commands.CLOSEUP] = false;
			return true;
		}
		if (keycode == Input.Keys.ESCAPE) {
			Gdx.app.exit();
			return true;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		
		Gdx.input.setCursorPosition(Utils.MID_SCREEN_X, Utils.MID_SCREEN_Y);
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/*--- M�todos do Controller - (touch + teclado)--------------------------------------*/

	@Override
	public void connected(Controller controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnected(Controller controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		// TODO Auto-generated method stub
        if (buttonCode == 0) {
			Commands.comandos[Commands.FRENTE] = true;
			return true;
		}
		if (buttonCode == 1) {
			Commands.comandos[Commands.TRAS] = true;
			return true;
		}
		if (buttonCode == 2) {
			Commands.comandos[Commands.ESQUERDA] = true;
			return true;
		}
		if (buttonCode == 3) {
			Commands.comandos[Commands.DIREITA] = true;
			return true;
		}
		if (buttonCode == 5) {
			Commands.comandos[Commands.INCLINANDO_ESQUERDA] = true;
			return true;
		}
		if (buttonCode == 6) {
			Commands.comandos[Commands.INCLINANDO_DIREITA] = true;
			return true;
		}
		if (buttonCode == 8) {
			Commands.comandos[Commands.ACELERANDO] = true;
			return true;
		}
		if (buttonCode == 9) {
			Commands.comandos[Commands.CLOSEUP] = true;
			return true;
		}
		return false;
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {
		if (buttonCode == 0) {
			Commands.comandos[Commands.FRENTE] = false;
			return true;
		}
		if (buttonCode == 1) {
			Commands.comandos[Commands.TRAS] = false;
			return true;
		}
		if (buttonCode == 2) {
			Commands.comandos[Commands.ESQUERDA] = false;
			return true;
		}
		if (buttonCode == 3) {
			Commands.comandos[Commands.DIREITA] = false;
			return true;
		}
		if (buttonCode == 5) {
			Commands.comandos[Commands.INCLINANDO_ESQUERDA] = false;
			return true;
		}
		if (buttonCode == 6) {
			Commands.comandos[Commands.INCLINANDO_DIREITA] = false;
			return true;
		}
		if (buttonCode == 8) {
			Commands.comandos[Commands.ACELERANDO] = false;
			return true;
		}
		if (buttonCode == 9) {
			Commands.comandos[Commands.CLOSEUP] = false;
			return true;
		}
		if (buttonCode == 11) {
			Gdx.app.exit();
			return true;
		}
		return false;
	}

	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		// TODO Auto-generated method stub
		if (axisCode == 1) {
			if (value >= 0.9f) {
				Commands.comandos[Commands.DIREITA] = true;
				return true;
			}
			else if (value <= -0.9f) {
				Commands.comandos[Commands.ESQUERDA] = true;
				return true;
			}
			else {
				Commands.comandos[Commands.DIREITA] = false;
				Commands.comandos[Commands.ESQUERDA] = false;
				return true;
			}
		}
		else if (axisCode == 0) {
			if (value >= 0.9f) {
				Commands.comandos[Commands.TRAS] = true;
				return true;
			}
			else if (value <= -0.9f) {
				Commands.comandos[Commands.FRENTE] = true;
				return true;
			}
			else {
				Commands.comandos[Commands.FRENTE] = false;
				Commands.comandos[Commands.TRAS] = false;
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
