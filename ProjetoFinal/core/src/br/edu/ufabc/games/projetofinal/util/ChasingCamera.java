package br.edu.ufabc.games.projetofinal.util;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

import br.edu.ufabc.games.projetofinal.model.GameObject;

public class ChasingCamera extends PerspectiveCamera {

	private GameObject objectToFollow;
	private int mode;
	public static final int ZOOM_IN = 0;
	public static final int ZOOM_OUT = 1;
	private float offsetZoomIn;
	private float offsetZoomOut;
	private float currentOffset;
	private float currentOffsetY;
	private Vector3 objectPosition;
	private float offsetYOut=0.0f;
	private float offsetYIn=0.0f;

	public ChasingCamera(float fov, float width, float height, float offsetZoomIn, float offsetZoomOut) {
		super(fov, width, height);
		this.offsetZoomIn = offsetZoomIn;
		this.offsetZoomOut = offsetZoomOut;
		mode = ZOOM_OUT;
		objectPosition = new Vector3();
	}

	public void setObjectToFollow(GameObject obj) {
		objectToFollow = obj;
	}
	public void setOffsetYIn(float offsetY) {
		this.offsetYIn = offsetY;
	}
	
	public void setOffsetYOut(float offsetY) {
		this.offsetYOut = offsetY;
	}

	public void update() {
		
		// defino meu offset de acordo com o modo
		currentOffset = (mode == ZOOM_IN) ? offsetZoomIn : offsetZoomOut;
		currentOffsetY = (mode == ZOOM_IN)? offsetYIn: offsetYOut;
		// vou pegar a posicao do objeto a ser seguido
		if (objectToFollow != null) {
			objectToFollow.transform.getTranslation(objectPosition);
			// defino a nova posicao
			float angulo = 0;// objectToFollow.getAngle();
			
			
			float newX, newY, newZ;
			newX = objectPosition.x + (float)(currentOffset*Math.sin(Math.toRadians(angulo))) ;
			newY = objectPosition.y + currentOffsetY;
			newZ = objectPosition.z + (float)(currentOffset*Math.cos(Math.toRadians(angulo)));
			this.position.set(newX, newY, newZ);
			// defino para onde a camera irá olhar
			this.lookAt(objectPosition.x, objectPosition.y+currentOffsetY, objectPosition.z);
			// chamo o update da classe pai
		}
		super.update();
	}
	
	public void setMode(int mode) {
		this.mode = mode;
	}
}
