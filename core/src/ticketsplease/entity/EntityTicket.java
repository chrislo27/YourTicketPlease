package ticketsplease.entity;

import ticketsplease.Settings;
import ticketsplease.Translator;
import ticketsplease.discrepancy.Discrepancy;
import ticketsplease.registry.AssetRegistry;
import ticketsplease.renderer.Renderer;
import ticketsplease.scenario.Scenario;
import ticketsplease.util.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

public class EntityTicket extends Entity {

	/**
	 * Line colour
	 */
	public Color lineColor;

	public String origin = "";
	public String destination = "";

	public EntityTicket(Scenario s, float x, float y) {
		super(s, x, y, 512f / Settings.DEFAULT_WIDTH, 256f / Settings.DEFAULT_HEIGHT);

		boolean shouldBeError = MathUtils.randomBoolean(scenario.chanceOfInvalidTicket);
		
		// line colour discrepancy
		discrepancies.add(new Discrepancy(0, 0, 1f, 0.1796875f, false));
		lineColor = new Color(
				Utils.HSBtoRGBA8888(Math.abs(MathUtils.random(-1f, 1f)), 0.75f, 0.75f));

		// origin
		discrepancies.add(new Discrepancy(0.025f, 0.925f - 0.11f, 0.5f - 0.025f, 0.125f, false));

		// destination
		discrepancies.add(new Discrepancy(0.025f, 0.925f - 0.11f - 0.125f, 0.5f - 0.025f, 0.125f,
				false));
		
		// date valid
		discrepancies.add(new Discrepancy(0.025f, 0.925f - 0.11f - 0.25f, 0.5f - 0.025f, 0.125f,
				false));
	}

	@Override
	public void render(Renderer renderer, boolean interacting) {
		renderer.batch.draw(AssetRegistry.getTexture("ticket_base"), x * Gdx.graphics.getWidth(), y
				* Gdx.graphics.getHeight(), width * Gdx.graphics.getWidth(),
				height * Gdx.graphics.getHeight());

		renderer.batch.setColor(lineColor);
		renderer.batch.draw(AssetRegistry.getTexture("ticket_line"), x * Gdx.graphics.getWidth(), y
				* Gdx.graphics.getHeight(), width * Gdx.graphics.getWidth(),
				height * Gdx.graphics.getHeight());
		renderer.batch.setColor(1, 1, 1, 1);

		renderer.main.font.setColor(0, 0, 0, 1);
		renderer.main.font.draw(renderer.batch, Translator.getMsg("ticket.origin") + ": " + origin, (x + (width * 0.025f))
				* Gdx.graphics.getWidth(), (y + (height * 0.925f)) * Gdx.graphics.getHeight());
		renderer.main.font.draw(renderer.batch, Translator.getMsg("ticket.destination") + ": " + destination, (x + (width * 0.025f))
				* Gdx.graphics.getWidth(),
				(y + (height * (0.925f - 0.125f))) * Gdx.graphics.getHeight());
		renderer.main.font.draw(renderer.batch, Translator.getMsg("ticket.date") + ": ", (x + (width * 0.025f))
				* Gdx.graphics.getWidth(),
				(y + (height * (0.925f - 0.25f))) * Gdx.graphics.getHeight());
		renderer.main.font.draw(renderer.batch, Translator.getMsg("ticket.trainid") + ": " + Integer.toHexString(Color.rgb888(lineColor)), (x + (width * 0.025f))
				* Gdx.graphics.getWidth(),
				(y + (height * (0.925f - 0.25f))) * Gdx.graphics.getHeight());
		renderer.main.font.setColor(1, 1, 1, 1);
	}

	@Override
	public void onInteract(Renderer renderer, float partX, float partY) {
	}

	@Override
	public void onInteractStart(Renderer renderer, float partX, float partY) {
	}

}
