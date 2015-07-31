package ticketsplease.ui;

import ticketsplease.Main;

public abstract class BackButton extends Button {

	public BackButton(int x, int y) {
		super(x, y, 32, 32, null);
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
