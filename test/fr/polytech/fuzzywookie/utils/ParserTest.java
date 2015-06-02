package fr.polytech.fuzzywookie.utils;

import org.junit.Test;

import fr.polytech.fuzzywookie.metier.Image;
import fr.polytech.fuzzywookie.metier.Print;
import fr.polytech.fuzzywookie.metier.Project;
import fr.polyteck.fuzzywookie.utils.Parser;

public class ParserTest {

	@Test
	public void ParserTest(){
		
		Project projectTest = new Project("/Users/jocelyn/Downloads/data/data_30Salpha.txt", false);
		projectTest.parseFileAndSortImages();
		
		System.out.println("Prix : " + projectTest.getPricePattern()+ " X : "+projectTest.getPatternX()+" Y : "+projectTest.getPatternY());
		for(Image i:projectTest.getListImage()){
			System.out.println("X : "+ i.getWidth()+" Y : "+i.getHeight()+" Nb : "+i.getNbItem()+" Nom :"+i.getName());
		}
		
	}
}
