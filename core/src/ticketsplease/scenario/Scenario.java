package ticketsplease.scenario;

import java.util.Date;
import java.util.GregorianCalendar;

import ticketsplease.Main;
import ticketsplease.entity.Entity;
import ticketsplease.entity.EntityDiscrepancyChecker;
import ticketsplease.entity.EntityTicket;
import ticketsplease.entity.EntityTrainList;
import ticketsplease.renderer.Renderer;
import ticketsplease.traveller.Traveller;
import ticketsplease.util.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Scenario implements Disposable {

	public float chanceOfInvalidTicket = 0.35f;

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

	public boolean discrepancyMode = false;
	
	public Date currentDate = new GregorianCalendar(0, 1, 1).getTime();
	
	public Array<Color> todayTrains = new Array<>(3);

	public Scenario(Main main) {
		this.main = main;

		renderer = new Renderer(this);

		entities.clear();
		entities.add(new EntityDiscrepancyChecker(this, 0.1f, 0.1f));
		entities.add(new EntityTrainList(this, 0.2f, 0.1f));
		
		generateTodaysTrains();
	}

	public void render() {
		renderer.render();
	}

	public void renderUpdate() {
		renderer.renderUpdate();
		
		// calc currentDragging
		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			for (int i = entities.size - 1; i >= 0; i--) {
				Entity e = entities.get(i);
				if (currentDragging != null) break;
				if (isMouseOverEntity(e)) {
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
			if (currentDragging != null) {
				entities.removeValue(currentDragging, true);
				entities.insert(entities.size, currentDragging);

				currentDragging = null;
			}
		}

		if (Utils.isButtonJustPressed(Buttons.RIGHT) && !Gdx.input.isButtonPressed(Buttons.LEFT)) {
			for (Entity e : entities) {
				if (currentDragging != null) break;
				if (isMouseOverEntity(e)) {
					if (discrepancyMode && e.discrepancies.size > 0) {
						boolean foundSomething = e.checkForDiscrepancies(
								((Gdx.input.getX() * 1f / Gdx.graphics.getWidth()) - e.x),
								(((Gdx.graphics.getHeight() - Gdx.input.getY()) * 1f / Gdx.graphics
										.getHeight()) - e.y));
					} else {
						e.onInteractStart(renderer, ((Gdx.input.getX() * 1f / Gdx.graphics
								.getWidth()) - e.x),
								(((Gdx.graphics.getHeight() - Gdx.input.getY()) * 1f / Gdx.graphics
										.getHeight()) - e.y));
					}
					break;
				}
			}
		}

		if (Gdx.input.isButtonPressed(Buttons.RIGHT) && !Gdx.input.isButtonPressed(Buttons.LEFT)) {
			for (Entity e : entities) {
				if (currentDragging != null) break;
				if (isMouseOverEntity(e)) {
					e.onInteract(renderer,
							((Gdx.input.getX() * 1f / Gdx.graphics.getWidth()) - e.x),
							(((Gdx.graphics.getHeight() - Gdx.input.getY()) * 1f / Gdx.graphics
									.getHeight()) - e.y));
					break;
				}
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.SPACE)){
			toggleDiscrepancyMode();
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

	/**
	 * Inverts discrepancyMode but with fancy animations
	 */
	public void toggleDiscrepancyMode() {
		discrepancyMode = !discrepancyMode;
		if(discrepancyMode == true) renderer.discrepancySweep = 1f;
	}
	
	public void generateTodaysTrains(){
		for(int i = 0; i < 3; i++){
			if(todayTrains.size >= i){
				todayTrains.insert(i, new Color());
			}
			
			todayTrains.get(i).set(Utils.HSBtoRGBA8888(Math.abs(MathUtils.random(-1f, 1f)), 0.75f, 0.75f));
		}
		
		if(todayTrains.size > 3) todayTrains.removeRange(3, todayTrains.size - 1);
	}

	public boolean isMouseOverEntity(Entity e) {
		if (Gdx.input.getX() >= e.x * Gdx.graphics.getWidth()
				&& Gdx.input.getX() <= (e.x + e.width) * Gdx.graphics.getWidth()
				&& (Gdx.graphics.getHeight() - Gdx.input.getY()) >= e.y * Gdx.graphics.getHeight()
				&& (Gdx.graphics.getHeight() - Gdx.input.getY()) <= (e.y + e.height)
						* Gdx.graphics.getHeight()) {
			return true;
		}

		return false;
	}

	public void tickUpdate() {
		if (currentTraveller != null) {
			currentTraveller.tickUpdate();
		}
	}

	public void spawnTicket() {
		EntityTicket ticket = new EntityTicket(this, 0.5f, 0.5f);
		ticket.x = (xBoundary + sizex / 2f) - ticket.width / 2f;
		ticket.y = (yBoundary + sizey);

		entities.add(ticket);
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
