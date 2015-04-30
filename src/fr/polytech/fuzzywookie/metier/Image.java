package fr.polytech.fuzzywookie.metier;

public class Image extends Rectangle {

	private int x,y;
	private String name;
	int nbItem;
	
	public Image(int width, int height, int nbItem)
	{
		super(width, height);
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
