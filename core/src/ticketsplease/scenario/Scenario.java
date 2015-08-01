package ticketsplease.scenario;

import ticketsplease.Main;
import ticketsplease.Settings;
import ticketsplease.Translator;
import ticketsplease.entity.Entity;
import ticketsplease.entity.EntityRobot;
import ticketsplease.renderer.Renderer;
import ticketsplease.traveller.Traveller;
import ticketsplease.util.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Scenario implements Disposable {

	public float chanceOfInvalidTicket = 0.34327f;
	public float chanceOfNotEnoughMoney = 0.5f;

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

	public Array<Conversation> conversations = new Array<>();

	public Traveller currentTraveller;

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
					dragOriginY = (((Gdx.graphics.getHeight() - Gdx.input.getY()) * 1f / Gdx.graphics
							.getHeight()) - e.y);
					break;
				}
			}

			if (currentDragging != null) {
				currentDragging.x = (Gdx.input.getX() * 1f / Gdx.graphics.getWidth()) - dragOriginX;
				currentDragging.y = ((Gdx.graphics.getHeight() - Gdx.input.getY()) * 1f / Gdx.graphics
						.getHeight()) - dragOriginY;
			}
		} else {
			currentDragging = null;
		}

		if (Utils.isButtonJustPressed(Buttons.RIGHT) && !Gdx.input.isButtonPressed(Buttons.LEFT)) {
			for (Entity e : entities) {
				if (currentDragging != null) break;
				if (Gdx.input.getX() >= e.x * Gdx.graphics.getWidth()
						&& Gdx.input.getX() <= (e.x + e.width) * Gdx.graphics.getWidth()
						&& (Gdx.graphics.getHeight() - Gdx.input.getY()) >= e.y
								* Gdx.graphics.getHeight()
						&& (Gdx.graphics.getHeight() - Gdx.input.getY()) <= (e.y + e.height)
								* Gdx.graphics.getHeight()) {
					e.onInteract(renderer,
							((Gdx.input.getX() * 1f / Gdx.graphics.getWidth()) - e.x),
							(((Gdx.graphics.getHeight() - Gdx.input.getY()) * 1f / Gdx.graphics
									.getHeight()) - e.y));
					break;
				}
			}
		}

		for (Entity e : entities) {
			e.renderUpdate();
		}

		for (int i = conversations.size - 1; i >= 0; i--) {
			conversations.get(i).timer -= Gdx.graphics.getDeltaTime();
			if (conversations.get(i).shouldRemove) conversations.removeIndex(i);
		}

		if (Gdx.input.isKeyJustPressed(Keys.N)) {
			if (currentTraveller == null || currentTraveller != null) {
				getNextTraveller();
			}
		}
	}

	public void tickUpdate() {
		if (currentTraveller != null) {
			currentTraveller.tickUpdate();
		}
	}

	public void getNextTraveller() {
		currentTraveller = new Traveller(this);
		nicelyClearConversations();
	}

	public void nicelyClearConversations() {
		for (Conversation c : conversations) {
			c.timer = 0;
		}
	}

	@Override
	public void dispose() {
		renderer.dispose();
	}

}
