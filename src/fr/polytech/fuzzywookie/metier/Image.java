package fr.polytech.fuzzywookie.metier;

public class Image {

	private int width;
	private int height;
	private int x,y;
	private String name;
	int nbItem;
	
	public Image(int width, int height, String name)
	{
		this.width = width;
		this.height = height;
		this.name = name;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
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
