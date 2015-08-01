package ticketsplease.renderer;

import ticketsplease.Main;
import ticketsplease.entity.Entity;
import ticketsplease.registry.AssetRegistry;
import ticketsplease.scenario.Scenario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class Renderer implements Disposable {

	public Main main;
	public SpriteBatch batch;
	Scenario scenario;

	public Renderer(Scenario s) {
		scenario = s;
		main = scenario.main;
		batch = main.batch;
	}

	public void render() {
		batch.begin();
		
		batch.draw(AssetRegistry.getTexture("desk"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		batch.flush();
		
		for (Entity e : scenario.entities) {
			if (scenario.currentDragging == e) continue;
			e.render(this);
		}
		
		if (scenario.currentDragging != null) {
			scenario.currentDragging.render(this);
		}
		
		batch.end();
	}

	@Override
	public void dispose() {
	}

}
