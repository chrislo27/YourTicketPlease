package ticketsplease.renderer;

import ticketsplease.Main;
import ticketsplease.entity.Entity;
import ticketsplease.registry.AssetRegistry;
import ticketsplease.scenario.Scenario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		batch.setColor(0.9f, 0.9f, 0.9f, 1);
		Main.fillRect(batch, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.setColor(1, 1, 1, 1);
		
		// entities being returned to the customer
		for (Entity e : scenario.entities) {
			if (scenario.currentDragging == e) continue;
			if(!e.isBeingReturned) continue;
			e.render(this);
		}
		
		batch.draw(AssetRegistry.getTexture("desk"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		// entities on the desk
		for (Entity e : scenario.entities) {
			if (scenario.currentDragging == e) continue;
			if(e.isBeingReturned) continue;
			e.render(this);
		}
		
		// the current one you're dragging
		if (scenario.currentDragging != null) {
			scenario.currentDragging.render(this);
		}
		
		batch.end();
	}

	@Override
	public void dispose() {
	}

}
