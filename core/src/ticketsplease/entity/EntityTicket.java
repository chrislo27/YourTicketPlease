package ticketsplease.entity;

import com.badlogic.gdx.Gdx;

import ticketsplease.Settings;
import ticketsplease.registry.AssetRegistry;
import ticketsplease.renderer.Renderer;
import ticketsplease.scenario.Scenario;


public class EntityTicket extends Entity{

	public EntityTicket(Scenario s, float x, float y) {
		super(s, x, y, 512f / Settings.DEFAULT_WIDTH, 256f / Settings.DEFAULT_HEIGHT);
	}

	@Override
	public void render(Renderer renderer) {
		renderer.batch.draw(AssetRegistry.getTexture("ticket_base"), x * Gdx.graphics.getWidth(), y
				* Gdx.graphics.getHeight(), width * Gdx.graphics.getWidth(),
				height * Gdx.graphics.getHeight());
		
		renderer.batch.setColor(1, 1, 1, 1);
		renderer.batch.draw(AssetRegistry.getTexture("ticket_line"), x * Gdx.graphics.getWidth(), y
				* Gdx.graphics.getHeight(), width * Gdx.graphics.getWidth(),
				height * Gdx.graphics.getHeight());
		renderer.batch.setColor(1, 1, 1, 1);
	}

	@Override
	public void onInteract(Renderer renderer, float partX, float partY) {
	}

}
