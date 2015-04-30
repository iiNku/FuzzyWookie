package fr.polytech.fuzzywookie.metier;

import java.util.List;

public class Print {
	private List<Pattern> listPattern;
	private List<Image> listImage;
	private int patternX, patternY;
	private int pricePattern;
	
	public Print()
	{
		
	}
	
	public boolean init()
	{	
		return false;
	}
	
	public Pattern createPattern()
	{
		return new Pattern(patternX, patternY);
	}

	public List<Pattern> getListPattern() {
		return listPattern;
	}

	public void setListPattern(List<Pattern> listPattern) {
		this.listPattern = listPattern;
	}

	public List<Image> getListImage() {
		return listImage;
	}

	public void setListImage(List<Image> listImage) {
		this.listImage = listImage;
	}

	public int getPatternX() {
		return patternX;
	}

	public void setPatternX(int patternX) {
		this.patternX = patternX;
	}

	public int getPatternY() {
		return patternY;
	}

	public void setPatternY(int patternY) {
		this.patternY = patternY;
	}

	public int getPricePattern() {
		return pricePattern;
	}

	public void setPricePattern(int pricePattern) {
		this.pricePattern = pricePattern;
	}

}
