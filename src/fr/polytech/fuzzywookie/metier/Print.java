package fr.polytech.fuzzywookie.metier;

import java.util.List;

public class Print {
	private List<Pattern> listPattern;
	private List<Image> listImage;
	private int patternX, patternY;
	
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

}
