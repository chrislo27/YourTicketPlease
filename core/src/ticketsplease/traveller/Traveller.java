package ticketsplease.traveller;

import ticketsplease.Main;
import ticketsplease.Translator;
import ticketsplease.scenario.Conversation;
import ticketsplease.scenario.Scenario;

import com.badlogic.gdx.Gdx;


public class Traveller {

	Scenario scenario;
	int timeTaken = (int) (-1.5f * Main.TICKS);
	
	public Traveller(Scenario s){
		scenario = s;
	}
	
	public void tickUpdate(){
		timeTaken++;
		
		if(timeTaken == 0){
			// player saying your ticket please
			scenario.conversations.add(new Conversation(Translator.getMsg("conv.ticketplease"), true));
		}else if(timeTaken == Main.TICKS){
			// ticket spawn
			scenario.spawnTicket();
		}
	}
	
}
