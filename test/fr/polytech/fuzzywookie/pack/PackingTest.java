package fr.polytech.fuzzywookie.pack;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.junit.Test;

import fr.polytech.fuzzywookie.metier.Image;
import fr.polytech.fuzzywookie.metier.Print;

public class PackingTest {

	@Test
	public void test(){
		
		Print print = new Print();
		ArrayList<Image> images = new ArrayList<Image>();
		images.add(new Image(20, 20, "Image1"));
		images.add(new Image(40, 50, "Image2"));
		images.add(new Image(15, 30, "Image3"));
		images.add(new Image(30, 10, "Image4"));
		
		print.setListImage(images);
		print.setPatternX(80);
		print.setPatternY(100);
		
		Packing packing = new Packing();
		packing.packing(print);
	}
}
