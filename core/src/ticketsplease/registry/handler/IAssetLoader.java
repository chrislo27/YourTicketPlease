package ticketsplease.registry.handler;

import java.util.HashMap;

import ticketsplease.animation.Animation;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public interface IAssetLoader {
	
	/**
	 * puts the assets to be loaded in the AssetManager
	 * @param manager
	 */
	public void addManagedAssets(AssetManager manager);
	
	/**
	 * Method for adding textures that will be loaded immediately
	 * @param textures
	 */
	public void addUnmanagedTextures(HashMap<String, Texture> textures);
	
	/**
	 * puts the animations to be loaded in the map
	 * @param animations
	 */
	public void addUnmanagedAnimations(HashMap<String, Animation> animations);
	
}
