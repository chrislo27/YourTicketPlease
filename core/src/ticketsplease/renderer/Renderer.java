package ticketsplease.renderer;

import ticketsplease.Main;
import ticketsplease.entity.Entity;
import ticketsplease.registry.AssetRegistry;
import ticketsplease.scenario.Conversation;
import ticketsplease.scenario.Scenario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

public class Renderer implements Disposable {

	public Main main;
	public SpriteBatch batch;
	Scenario scenario;

	private GlyphLayout glyph = new GlyphLayout();

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
			if (!e.isBeingReturned) continue;
			e.render(this);
		}

		batch.draw(AssetRegistry.getTexture("desk"), 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

		// entities on the desk
		for (Entity e : scenario.entities) {
			if (scenario.currentDragging == e) continue;
			if (e.isBeingReturned) continue;
			e.render(this);
		}

		// the current one you're dragging
		if (scenario.currentDragging != null) {
			scenario.currentDragging.render(this);
		}

		batch.flush();

		// conversations scrolling
		float height = 0;
		for (int i = 0; i < scenario.conversations.size; i++) {
			Conversation conv = scenario.conversations.get(i);

			if (conv.timer <= 0) {
				height += (glyph.height + 12) * conv.timer;
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

			height += 12 + glyph.height;
		}
		
		if(height > Gdx.graphics.getHeight() && scenario.conversations.size >= 1){
			if(scenario.conversations.get(0).timer > 0) scenario.conversations.get(0).timer = 0;
		}

		batch.end();
	}

	@Override
	public void dispose() {
	}

}
