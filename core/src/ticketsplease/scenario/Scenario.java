package ticketsplease.scenario;

import ticketsplease.Main;
import ticketsplease.entity.Entity;
import ticketsplease.entity.EntityRobot;
import ticketsplease.renderer.Renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Scenario implements Disposable {

	public Array<Entity> entities = new Array<>();

	public Main main;
	
	public float sizex = 1f;
	public float sizey = 0.5f;
	public float xBoundary = 0f;
	public float yBoundary = 0f;

	Renderer renderer;

	public Entity currentDragging = null;
	float draggingX, draggingY;
	float dragOriginX, dragOriginY;

	public Scenario(Main main) {
		this.main = main;
		
		renderer = new Renderer(this);
		
		entities.clear();
		entities.add(new EntityRobot(this, 0, 0));
	}

	public void render() {
		renderer.render();
	}

	public void renderUpdate() {
		// calc currentDragging
		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			for (Entity e : entities) {
				if (currentDragging != null) break;
				if (Gdx.input.getX() >= e.x * Gdx.graphics.getWidth()
						&& Gdx.input.getX() <= (e.x + e.width) * Gdx.graphics.getWidth()
						&& (Gdx.graphics.getHeight() - Gdx.input.getY()) >= e.y
								* Gdx.graphics.getHeight()
						&& (Gdx.graphics.getHeight() - Gdx.input.getY()) <= (e.y + e.height)
								* Gdx.graphics.getHeight()) {
					currentDragging = e;
					dragOriginX = ((Gdx.input.getX() * 1f / Gdx.graphics.getWidth()) - e.x);
					dragOriginY = (((Gdx.graphics.getHeight() - Gdx.input.getY()) * 1f / Gdx.graphics.getHeight()) - e.y);
					break;
				}
			}
			
			if(currentDragging != null){
				currentDragging.x = (Gdx.input.getX() * 1f / Gdx.graphics.getWidth()) - dragOriginX;
				currentDragging.y = ((Gdx.graphics.getHeight() - Gdx.input.getY()) * 1f / Gdx.graphics.getHeight()) - dragOriginY;
				Main.logger.debug("pos: " + currentDragging.x + ", " + currentDragging.y);
			}
		} else {
			currentDragging = null;
		}

		for (Entity e : entities) {
			e.renderUpdate();
		}
	}

	@Override
	public void dispose() {
		renderer.dispose();
	}

}
