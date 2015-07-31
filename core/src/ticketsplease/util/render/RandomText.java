package ticketsplease.util.render;

import ticketsplease.Main;
import ticketsplease.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;

public class RandomText {

	private static final GlyphLayout glyphLayout = new GlyphLayout();

	public static void render(Main main, String text, int chance, int maxrender) {
		if (text != null) {
			glyphLayout.setText(main.font, text);

			float height = glyphLayout.height;
			for (int i = 0; i < maxrender; i++) {
				if (MathUtils.random(1, chance) != 1) continue;
				main.font.draw(
						main.batch,
						text,
						MathUtils.random(1, Settings.DEFAULT_WIDTH - 1),
						MathUtils.random(Math.round(height),
								Math.round(Gdx.graphics.getHeight() - height)), 1, Align.center,
						false);
			}
		}
	}

	/**
	 * synthetic method, turns chance into (10 + fps + (fps / 2))
	 * 
	 * @param main
	 * @param text
	 * @param maxrender
	 */
	public static void render(Main main, String text, int maxrender) {
		render(main, text,
				10 + Gdx.graphics.getFramesPerSecond() + (Gdx.graphics.getFramesPerSecond() / 2),
				maxrender);
	}

}
