package ticketsplease.screen;

import ticketsplease.Main;
import ticketsplease.scenario.Scenario;


public class ScenarioScreen extends Updateable{

	public Scenario scenario;
	
	public ScenarioScreen(Main m) {
		super(m);
		scenario = new Scenario(m);
	}

	@Override
	public void render(float delta) {
		if(scenario != null) scenario.render();
	}

	@Override
	public void renderUpdate() {
		if(scenario != null) scenario.renderUpdate();
	}

	@Override
	public void tickUpdate() {
	}

	@Override
	public void renderDebug(int starting) {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		scenario.dispose();
	}

}
