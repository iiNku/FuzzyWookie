package fr.polytech.fuzzywookie.metier;

import java.util.List;

public class Pattern extends Rectangle {

	private List<Image> imageList;
	private int nbPrint;
	private String name;
	
	public Pattern(int width, int height)
	{
		super(width, height);
	}

	public List<Image> getImageList() {
		return imageList;
	}

	public void setImageList(List<Image> imageList) {
		this.imageList = imageList;
	}

	public int getNbPrint() {
		return nbPrint;
	}

	public void setNbPrint(int nbPrint) {
		this.nbPrint = nbPrint;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
