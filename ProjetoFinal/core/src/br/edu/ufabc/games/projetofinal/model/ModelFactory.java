package br.edu.ufabc.games.projetofinal.model;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.assets.loaders.ModelLoader.ModelParameters;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.utils.UBJsonReader;

public class ModelFactory {

	private static HashMap<String, Model> modelos = new HashMap<String, Model>();

	static {
		ModelLoader<ModelParameters> loader;
		loader = new G3dModelLoader(new UBJsonReader());
		System.out.println("Carregando modelos...");
		modelos.put("CENARIO", loader.loadModel(Gdx.files.internal("cenario.g3db")));
		modelos.put("NAVE", loader.loadModel(Gdx.files.internal("xwing/xwing.g3db")));
		modelos.put("COCKPIT", loader.loadModel(Gdx.files.internal("xwing/cockpit.g3db")));
		modelos.put("PLANETA1", loader.loadModel(Gdx.files.internal("planets/planet.g3db")));
		System.out.println("Modelos carregados!");
	}

	public static Model getModelbyName(String name) {
		return modelos.get(name);
	}

}
