package fr.polytech.fuzzywookie.metier;

import java.util.ArrayList;
import java.util.List;

import fr.polyteck.fuzzywookie.utils.Parser;
import fr.polyteck.fuzzywookie.utils.QSort;

public class Print {
	private List<Pattern> listPattern;
	private List<Image> listImage;
	private int patternX=0, patternY=0;
	private int pricePattern;
	private String file;
	
	public Print(String file)
	{
		listPattern = new ArrayList<Pattern>();
		listImage = new ArrayList<Image>();
		this.file = file;
	}
	
	public void init()
	{	
		Parser.parserFile(file, this);
		QSort qs = new QSort();
		qs.sort(listImage);	
	}
	
	public Pattern createPattern()
	{
		if(!(patternX == 0) && !(patternY==0))
		{
			Pattern pattern = new Pattern(patternX, patternY);
			listPattern.add(pattern);
			return new Pattern(patternX, patternY);
		}
		return null;	
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
