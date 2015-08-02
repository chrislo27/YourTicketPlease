package ticketsplease.renderer;

import ticketsplease.Main;
import ticketsplease.Settings;
import ticketsplease.discrepancy.Discrepancy;
import ticketsplease.entity.Entity;
import ticketsplease.registry.AssetRegistry;
import ticketsplease.scenario.Conversation;
import ticketsplease.scenario.Scenario;
import ticketsplease.util.render.StencilMaskUtil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

public class Renderer implements Disposable {

	public Main main;
	public SpriteBatch batch;
	Scenario scenario;

	private GlyphLayout glyph = new GlyphLayout();

	public float discrepancySweep = 0;
	public float sweepWidth = 0.15f;

	public Renderer(Scenario s) {
		scenario = s;
		main = scenario.main;
		batch = main.batch;
	}

	public void renderUpdate() {
		if (discrepancySweep > 0) {
			discrepancySweep -= Gdx.graphics.getDeltaTime() * 1.5f;
		}
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
			if (!e.isBeingReturned) continue;
			e.render(this,
					scenario.isMouseOverEntity(e) && Gdx.input.isButtonPressed(Buttons.RIGHT));
		}

		batch.draw(AssetRegistry.getTexture("desk"), 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

		// entities on the desk
		for (Entity e : scenario.entities) {
			if (scenario.currentDragging == e) continue;
			if (e.isBeingReturned) continue;
			e.render(this,
					scenario.isMouseOverEntity(e) && Gdx.input.isButtonPressed(Buttons.RIGHT));
		}

		// the current one you're dragging
		if (scenario.currentDragging != null) {
			scenario.currentDragging.render(
					this,
					scenario.isMouseOverEntity(scenario.currentDragging)
							&& Gdx.input.isButtonPressed(Buttons.RIGHT));
		}

		batch.flush();

		// conversations scrolling
		float combinedHeight = 0;
		for (int i = 0; i < scenario.conversations.size; i++) {
			Conversation conv = scenario.conversations.get(i);

			if (conv.timer <= 0 && conv.timer >= -1) {
				combinedHeight += (glyph.height + 12) * conv.timer;
			}

			float height = combinedHeight;

			if (conv.timer <= -1) {
				conv.shouldRemove = true;
				continue;
			}

			glyph.setText(main.font, conv.text, Color.WHITE, 256, Align.left, true);

			batch.setColor(0.7f, 0.7f, 0.7f, 1);
			Main.fillRect(batch, Gdx.graphics.getWidth() - 16 - glyph.width - 4 - 4,
					Gdx.graphics.getHeight() - (height + main.font.getCapHeight()) + 4,
					glyph.width + 8, -glyph.height - 8);
			batch.setColor(0.8f, 0.8f, 0.8f, 1);
			Main.fillRect(batch, Gdx.graphics.getWidth() - 14 - glyph.width - 4 - 4,
					Gdx.graphics.getHeight() - (height + main.font.getCapHeight()) + 2,
					glyph.width + 4, -glyph.height - 4);
			batch.setColor(1, 1, 1, 1);

			main.font.draw(batch, glyph, Gdx.graphics.getWidth() - 16 - glyph.width - 4,
					Gdx.graphics.getHeight() - (height + main.font.getCapHeight()));

			if (conv.wasPlayer) {
				batch.draw(AssetRegistry.getTexture("conversation-tick"), Gdx.graphics.getWidth()
						- 16 - glyph.width - 4 - 4 + glyph.width + 8 - 4, Gdx.graphics.getHeight()
						- (height + main.font.getCapHeight()) + 4 - glyph.height - 4 - 4, 18, 16);
			} else {
				batch.draw(AssetRegistry.getTexture("conversation-tick"), Gdx.graphics.getWidth()
						- 16 - glyph.width - 4,
						Gdx.graphics.getHeight() - (height + main.font.getCapHeight()) + 4
								- glyph.height - 4 - 4, -18, 16);
			}

			combinedHeight += 12 + glyph.height;
		}

		if (combinedHeight > Gdx.graphics.getHeight() && scenario.conversations.size >= 1) {
			if (scenario.conversations.get(0).timer > 0) scenario.conversations.get(0).timer = 0;
		}

		batch.end();

		if (!scenario.discrepancyMode && !Settings.debug) return;

		batch.begin();
		batch.setColor(0, 0, 0, 0.25f);
		Main.fillRect(batch, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.setColor(1, 1, 1, 1);
		batch.end();

		Gdx.gl.glLineWidth(2f);

		if (!Settings.debug) {
			StencilMaskUtil.prepareMask();
			main.shapes.begin(ShapeType.Filled);
			main.shapes.rect((discrepancySweep) * Gdx.graphics.getWidth(), 0, (-sweepWidth)
					* Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			main.shapes.end();
			StencilMaskUtil.useMask();
		}

		main.shapes.begin(ShapeType.Line);
		for (Entity e : scenario.entities) {
			main.shapes.rect(e.x * Gdx.graphics.getWidth(), e.y * Gdx.graphics.getHeight(),
					Gdx.graphics.getWidth() * e.width, Gdx.graphics.getHeight() * e.height);
			for (Discrepancy d : e.discrepancies) {
				main.shapes.rect(
						(e.width * Gdx.graphics.getWidth()) * d.x + (e.x * Gdx.graphics.getWidth()),
						(e.height * Gdx.graphics.getHeight()) * d.y + (e.y * Gdx.graphics.getHeight()),
						(Gdx.graphics.getWidth() * e.width) * d.width,
						(Gdx.graphics.getHeight() * e.height) * d.height);
			}
		}
		main.shapes.end();

		StencilMaskUtil.resetMask();

		Gdx.gl.glLineWidth(1f);
	}

	@Override
	public void dispose() {
	}

}
