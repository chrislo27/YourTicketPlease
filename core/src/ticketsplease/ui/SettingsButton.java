package ticketsplease.ui;

import ticketsplease.Main;
import ticketsplease.Settings;
import ticketsplease.registry.ScreenRegistry;

public class SettingsButton extends Button {

	public SettingsButton(int x, int y) {
		super(x, y, 64f / Settings.DEFAULT_WIDTH, 64f / Settings.DEFAULT_HEIGHT, null);
	}

	@Override
	public void render(Main main) {
		imageRender(main, "guisettings");
		if (this.main == null) this.main = main;
	}

	private Main main = null;

	@Override
	public boolean onLeftClick() {
		if (main == null) {
			return false;
		}
		main.setScreen(ScreenRegistry.get("settings"));
		return true;
	}

}
