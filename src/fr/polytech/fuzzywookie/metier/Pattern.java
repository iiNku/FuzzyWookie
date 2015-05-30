package fr.polytech.fuzzywookie.metier;

import java.util.ArrayList;
import java.util.List;

public class Pattern extends Rectangle {

	private List<Image> imageList;
	private int nbPrint;
	private String name;
	private int decoupX, decoupY;
	
	public Pattern(int width, int height)
	{
		super(width, height);
		imageList = new ArrayList<Image>();
		this.decoupX = 0;
		this.decoupY = height;
	}

	public int getDecoupX() {
		return decoupX;
	}

	public void setDecoupX(int decoupX) {
		this.decoupX = decoupX;
	}

	public int getDecoupY() {
		return decoupY;
	}

	public void setDecoupY(int decoupY) {
		this.decoupY = decoupY;
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
	
	public void addImage(Image image){
		
		imageList.add(image);
	}
	
	public int getHigherSide(){
		
		return width > height ? width : height;
	}

	
}
