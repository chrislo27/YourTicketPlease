package ticketsplease.entity;

import ticketsplease.Settings;
import ticketsplease.Translator;
import ticketsplease.registry.AssetRegistry;
import ticketsplease.renderer.Renderer;
import ticketsplease.scenario.Scenario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class EntityTrainList extends Entity {

	public EntityTrainList(Scenario s, float x, float y) {
		super(s, x, y, 192f / Settings.DEFAULT_WIDTH, 256f / Settings.DEFAULT_HEIGHT);
	}

	@Override
	public void render(Renderer renderer, boolean interacting) {
		renderer.batch.draw(AssetRegistry.getTexture("trainlist"), x * Gdx.graphics.getWidth(), y
				* Gdx.graphics.getHeight(), width * Gdx.graphics.getWidth(),
				height * Gdx.graphics.getHeight());

		for (int i = 0; i < 3; i++) {
			renderer.batch.draw(AssetRegistry.getTexture("trainIcon"), (x + 0.05f * width)
					* Gdx.graphics.getWidth(), (y + 0.025f * height + ((i) * 0.075f))
					* Gdx.graphics.getHeight(),
					64f / Settings.DEFAULT_WIDTH * Gdx.graphics.getWidth(), 64f
							/ Settings.DEFAULT_HEIGHT * Gdx.graphics.getHeight());

			renderer.batch.setColor(scenario.todayTrains.get(i));
			renderer.batch.draw(AssetRegistry.getTexture("trainIconLine"), (x + 0.05f * width)
					* Gdx.graphics.getWidth(),
					(y + 0.05f * height + ((i) * 0.075f)) * Gdx.graphics.getHeight(), 64f
							/ Settings.DEFAULT_WIDTH * Gdx.graphics.getWidth(), 64f
							/ Settings.DEFAULT_HEIGHT * Gdx.graphics.getHeight());
			renderer.batch.setColor(1, 1, 1, 1);

			renderer.main.font.setColor(0, 0, 0, 1);
			renderer.main.font.draw(renderer.batch,
					Integer.toHexString(Color.rgb888(scenario.todayTrains.get(i))),
					(x + 0.4f * width) * Gdx.graphics.getWidth(),
					(y + 0.225f * height + ((i) * 0.075f)) * Gdx.graphics.getHeight());
			renderer.main.font.setColor(1, 1, 1, 1);
		}

		renderer.main.font.getData().setScale(0.5f);
		renderer.main.font.setColor(0, 0, 0, 1);
		renderer.main.font.draw(renderer.batch, "> " + Translator.getMsg("trainlist.today"),
				(x + 0.05f * width) * Gdx.graphics.getWidth(),
				(y + 0.98f * height) * Gdx.graphics.getHeight());
		renderer.main.font.setColor(1, 1, 1, 1);
		renderer.main.font.getData().setScale(1);
	}

	@Override
	public void onInteract(Renderer renderer, float partX, float partY) {
	}

	@Override
	public void onInteractStart(Renderer renderer, float partX, float partY) {
	}

}
