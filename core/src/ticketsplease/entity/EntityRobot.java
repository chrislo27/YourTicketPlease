package ticketsplease.entity;

import ticketsplease.Settings;
import ticketsplease.registry.AssetRegistry;
import ticketsplease.renderer.Renderer;
import ticketsplease.scenario.Scenario;

import com.badlogic.gdx.Gdx;

public class EntityRobot extends Entity {

	public EntityRobot(Scenario s, float x, float y) {
		super(s, x, y, 64f / Settings.DEFAULT_WIDTH, 64f / Settings.DEFAULT_HEIGHT);
	}

	@Override
	public void render(Renderer renderer) {
		renderer.batch.draw(AssetRegistry.getTexture("robot"), x * Gdx.graphics.getWidth(), y
				* Gdx.graphics.getHeight(), width * Gdx.graphics.getWidth(),
				height * Gdx.graphics.getHeight());
	}

}
