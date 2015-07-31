package ticketsplease.ui;

import ticketsplease.Main;
import ticketsplease.Settings;

public abstract class BackButton extends Button {

	public BackButton(float x, float y) {
		super(x, y, 64f / Settings.DEFAULT_WIDTH, 64f / Settings.DEFAULT_HEIGHT, null);
	}

	@Override
	public void render(Main main) {
		imageRender(main, (usesExitTex ? exitpath : backpath));
	}

	private static final String exitpath = "guiexit";
	private static final String backpath = "guiback";

	private boolean usesExitTex = false;

	public BackButton useExitTexture() {
		usesExitTex = true;
		return this;
	}

	@Override
	public abstract boolean onLeftClick();
}
