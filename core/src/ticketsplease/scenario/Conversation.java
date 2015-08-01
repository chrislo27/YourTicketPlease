package ticketsplease.scenario;


public class Conversation {

	public String text = "";
	public boolean wasPlayer = false;
	public float timer = 3f;
	public boolean shouldRemove = false;
	
	public Conversation(String text, boolean player){
		this.text = text;
		wasPlayer = player;
	}
	
}
