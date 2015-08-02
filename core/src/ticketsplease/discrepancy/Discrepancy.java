package ticketsplease.discrepancy;


public class Discrepancy {

	public float x, y, width, height;
	public boolean discrepant = false;
	
	public Discrepancy(float x, float y, float w, float h, boolean phony){
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		discrepant = true;
	}
	
}
