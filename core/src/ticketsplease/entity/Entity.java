package ticketsplease.entity;

import ticketsplease.discrepancy.Discrepancy;
import ticketsplease.renderer.Renderer;
import ticketsplease.scenario.Scenario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;


public abstract class Entity {

	public float x, y, width, height;
	Scenario scenario;
	public boolean isBeingReturned = false;
	
	public Array<Discrepancy> discrepancies = new Array<>();
	
	public Entity(Scenario s, float x, float y, float w, float h){
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		scenario = s;
	}
	
	public abstract void render(Renderer renderer, boolean interacting);
	
	public abstract void onInteract(Renderer renderer, float partX, float partY);
	
	public abstract void onInteractStart(Renderer renderer, float partX, float partY);
	
	public boolean checkForDiscrepancies(float partX, float partY){
		for(Discrepancy d : discrepancies){
			if(partX > d.x && partX < d.x + d.width && partY > d.y && partY < d.y + d.height){
				if(d.discrepant) return true;
			}
		}
		
		return false;
	}
	
	public boolean isAnythingDiscrepant(){
		for(Discrepancy d : discrepancies){
			if(d.discrepant) return true;
		}
		
		return false;
	}
	
	public void renderUpdate(){
		if(x < scenario.xBoundary){
			x = scenario.xBoundary;
		}
		if(y < scenario.yBoundary){
			y = scenario.yBoundary;
		}
		if(x + width > scenario.xBoundary + scenario.sizex){
			x = scenario.xBoundary + scenario.sizex - width;
		}
		if(this != scenario.currentDragging){
			if(y + height > scenario.yBoundary + scenario.sizey){
				float returnSpeed = 1.25f;
				y -= returnSpeed * Gdx.graphics.getDeltaTime();
			}
		}
	}
	
}
