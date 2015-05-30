package fr.polytech.fuzzywookie.metier;

import java.util.ArrayList;
import java.util.List;

import fr.polytech.fuzzywookie.pack.Packing;
import fr.polyteck.fuzzywookie.utils.Parser;
import fr.polyteck.fuzzywookie.utils.QSort;

public class Project {
	private List<Print> listPrint;
	private List<Image> listImage;
	private int patternX=0, patternY=0;
	private int pricePattern;
	private String file;
	private Print initialPrint;
	
	public Project(String file)
	{
		this.file = file;
		listPrint = new ArrayList<Print>();
		listImage = new ArrayList<Image>();
	}
	
	public Project()
	{
		listPrint = new ArrayList<Print>();
		listImage = new ArrayList<Image>();
	}
	
	public void init()
	{	
		Parser.parserFile(file, this);
		QSort qs = new QSort();
		qs.sort(listImage);	
	}

	public List<Print> getListPrint() {
		return listPrint;
	}

	public void setListPrint(List<Print> listPrint) {
		this.listPrint = listPrint;
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

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Print getInitialPrint() {
		return initialPrint;
	}

	public void setInitialPrint(Print initialPrint) {
		this.initialPrint = initialPrint;
	}

	public void launch(){
		
		init();
		int beginMs = (int) System.currentTimeMillis();
		while(System.currentTimeMillis() < beginMs + 7200000){
			
			Packing packing = new Packing();
			
			
		}
	}
	
}
