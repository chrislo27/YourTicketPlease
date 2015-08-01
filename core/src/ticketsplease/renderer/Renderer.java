package ticketsplease.renderer;

import ticketsplease.Main;
import ticketsplease.entity.Entity;
import ticketsplease.scenario.Scenario;

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
		
		for (Entity e : scenario.entities) {
			if (scenario.currentDragging == e) continue;
			e.render(this);
		}

		batch.flush();
		
		if (scenario.currentDragging != null) {
			scenario.currentDragging.render(this);
		}
		
		batch.end();
	}

	@Override
	public void dispose() {
	}

}
