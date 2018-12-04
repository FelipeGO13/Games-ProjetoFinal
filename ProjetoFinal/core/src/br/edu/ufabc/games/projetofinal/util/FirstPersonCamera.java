package br.edu.ufabc.games.projetofinal.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

import br.edu.ufabc.games.projetofinal.model.GameObject;

public class FirstPersonCamera extends PerspectiveCamera{
	
	public float SENSITIVITY = 0.02f;
	
	public Vector3 YAW_AXIS = new Vector3(0,1,0);
	public Vector3 PITCH_AXIS = new Vector3(1,0,0);
	public Vector3 ROLL_AXIS = new Vector3(0,0,1);
	
	public FirstPersonCamera(float fov, float width, float height) {
		super(fov, width, height);
		
	}
	
	public void setYaw(float angle) {
		YAW_AXIS = up;
		rotate(YAW_AXIS, angle);
	}
	
	public void setPitch(float angle) {
		PITCH_AXIS = up.cpy().crs(direction).nor();
		rotate(PITCH_AXIS, angle);
	}
	
	public void setRoll(float angle) {
		ROLL_AXIS = direction;
		rotate(ROLL_AXIS, angle);
	}
	
	public void update() {
		
		super.update();
	}

}
