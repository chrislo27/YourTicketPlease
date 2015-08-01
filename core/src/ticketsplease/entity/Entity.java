package ticketsplease.entity;

import ticketsplease.renderer.Renderer;
import ticketsplease.scenario.Scenario;

import com.badlogic.gdx.Gdx;


public abstract class Entity {

	public float x, y, width, height;
	Scenario scenario;
	
	public Entity(Scenario s, float x, float y, float w, float h){
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		scenario = s;
	}
	
	public abstract void render(Renderer renderer);
	
	public void renderUpdate(){
		if(this != scenario.currentDragging){
			if(x < scenario.xBoundary){
				x = scenario.xBoundary;
			}
			if(y < scenario.yBoundary){
				y = scenario.yBoundary;
			}
			if(x + width > scenario.xBoundary + scenario.sizex){
				x = scenario.xBoundary + scenario.sizex - width;
			}
			if(y + height > scenario.yBoundary + scenario.sizey){
				float returnSpeed = 1.25f;
				y -= returnSpeed * Gdx.graphics.getDeltaTime();
			}
		}
	}
	
}
