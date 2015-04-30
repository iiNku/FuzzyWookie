package fr.polytech.fuzzywookie.utils;

import org.junit.Test;

import fr.polytech.fuzzywookie.metier.Image;
import fr.polytech.fuzzywookie.metier.Print;
import fr.polyteck.fuzzywookie.utils.Parser;

public class ParserTest {

	@Test
	public void ParserTest(){
		Parser p = new Parser();
		Print printTest = new Print();
		p.parserFile("/Users/jocelyn/Downloads/data/data_20Lalpha.txt",printTest);

		
		System.out.println(printTest.getPricePattern());
		System.out.println(printTest.getPatternX());
		System.out.println(printTest.getPatternY());

		for(Image i:printTest.getListImage()){
			System.out.println(i.getNbItem());
			System.out.println(i.getWidth());
			System.out.println(i.getHeight());
		}
		
	}
}
