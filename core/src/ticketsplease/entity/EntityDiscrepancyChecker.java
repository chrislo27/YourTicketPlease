package ticketsplease.entity;

import com.badlogic.gdx.Gdx;

import ticketsplease.Settings;
import ticketsplease.registry.AssetRegistry;
import ticketsplease.renderer.Renderer;
import ticketsplease.scenario.Scenario;


public class EntityDiscrepancyChecker extends Entity{

	public EntityDiscrepancyChecker(Scenario s, float x, float y) {
		super(s, x, y, 128f / Settings.DEFAULT_WIDTH, 128f / Settings.DEFAULT_HEIGHT);
	}

	@Override
	public void render(Renderer renderer, boolean interacting) {
		renderer.batch.draw(AssetRegistry.getTexture("discrepancy_base"), x * Gdx.graphics.getWidth(), y
				* Gdx.graphics.getHeight(), width * Gdx.graphics.getWidth(),
				height * Gdx.graphics.getHeight());
		
		renderer.batch.draw(AssetRegistry.getTexture("discrepancy_button" + (scenario.discrepancyMode ? "_pushed" : "")), x * Gdx.graphics.getWidth(), y
				* Gdx.graphics.getHeight(), width * Gdx.graphics.getWidth(),
				height * Gdx.graphics.getHeight());
	}

	@Override
	public void onInteract(Renderer renderer, float partX, float partY) {
		
	}

	@Override
	public void onInteractStart(Renderer renderer, float partX, float partY) {
		scenario.toggleDiscrepancyMode();
	}

}
