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
		modelos.put("CENARIO", loader.loadModel(Gdx.files.internal("skybox.g3db")));
		modelos.put("NAVE", loader.loadModel(Gdx.files.internal("xwing/xwing.g3db")));
		modelos.put("SUN", loader.loadModel(Gdx.files.internal("planets/sun.g3db")));
		modelos.put("PLANETA1", loader.loadModel(Gdx.files.internal("planets/planet_ako.g3db")));
		modelos.put("PLANETA2", loader.loadModel(Gdx.files.internal("planets/planet_dante.g3db")));
		modelos.put("PLANETA3", loader.loadModel(Gdx.files.internal("planets/planet_down.g3db")));
		modelos.put("PLANETA4", loader.loadModel(Gdx.files.internal("planets/planet_dust.g3db")));
		modelos.put("PLANETA5", loader.loadModel(Gdx.files.internal("planets/planet_reststop.g3db")));
		modelos.put("OBJETIVO", loader.loadModel(Gdx.files.internal("mothership/mothership.g3db")));
		System.out.println("Modelos carregados!");
	}

	public static Model getModelbyName(String name) {
		return modelos.get(name);
	}

}
