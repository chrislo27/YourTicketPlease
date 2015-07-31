package ticketsplease.ui;

import ticketsplease.Main;

public abstract class UiElement {

	protected float x;
	protected float y;
	protected float width;
	protected float height;

	public abstract void render(Main main);

	public abstract boolean visible();

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	/**
	 * 
	 * @return true if handled
	 */
	public abstract boolean onLeftClick();

	/**
	 * 
	 * @return true if handled
	 */
	public abstract boolean onRightClick();

	public boolean onKeyTyped(char key) {
		return false;
	}
}
