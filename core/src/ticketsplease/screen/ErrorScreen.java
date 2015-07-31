package ticketsplease.screen;

import ticketsplease.Main;
import ticketsplease.Settings;
import ticketsplease.registry.ScreenRegistry;
import ticketsplease.ui.Button;

/**
 * Same as MessageScreen except it has a button to return to the main menu
 * 
 *
 */
public class ErrorScreen extends MessageScreen {

	public ErrorScreen(Main m) {
		super(m);

		container.elements.add(new Button((Settings.DEFAULT_WIDTH / 2) - 80, 128, 160, 32,
				"menu.backmainmenu") {

			@Override
			public boolean onLeftClick() {
				main.setScreen(ScreenRegistry.get("mainmenu"));
				return true;
			}

			@Override
			public boolean visible() {
				return true;
			}
		});
	}

}
