package ticketsplease.ui;

import ticketsplease.Main;
import ticketsplease.Settings;

import com.badlogic.gdx.utils.Array;

public class UiContainer {

	public Array<UiElement> elements = new Array<UiElement>();

	public void render(Main main) {
		for (UiElement e : elements) {
			if (!e.visible()) continue;
			e.render(main);
		}
	}

	public boolean onLeftClick() {
		for (UiElement e : elements) {
			if (!e.visible()) continue;
			if (mouseIn(e)) if (e.onLeftClick()) return true;
		}

		return false;
	}

	public boolean onRightClick() {
		for (UiElement e : elements) {
			if (!e.visible()) continue;
			if (mouseIn(e)) if (e.onRightClick()) return true;
		}

		return false;
	}

	public boolean onKeyTyped(char c) {
		for (UiElement e : elements) {
			if (!e.visible()) continue;
			if (e.onKeyTyped(c)) return true;
		}

		return false;
	}

	protected static boolean mouseIn(UiElement e) {
		if (!e.visible()) return false;
		if (Main.getInputX() >= e.getX() && Main.getInputX() <= (e.getX()) + (e.getWidth())) {
			if (Settings.DEFAULT_HEIGHT - Main.getInputY() >= e.getY()
					&& Settings.DEFAULT_HEIGHT - Main.getInputY() <= (e.getY()) + (e.getHeight())) {
				return true;
			}
		}

		return false;
	}
}
