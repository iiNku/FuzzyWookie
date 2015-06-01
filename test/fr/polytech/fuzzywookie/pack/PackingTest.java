package fr.polytech.fuzzywookie.pack;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.junit.Test;

import fr.polytech.fuzzywookie.metier.Image;
import fr.polytech.fuzzywookie.metier.Pattern;
import fr.polytech.fuzzywookie.metier.Print;
import fr.polytech.fuzzywookie.metier.Project;
import fr.polyteck.fuzzywookie.utils.QSort;

public class PackingTest {

	@Test
	public void test(){
		
		Project p = new Project();
		Print print = new Print(p);
		ArrayList<Image> images = new ArrayList<Image>();
		images.add(new Image(20, 20, "Image1"));
		images.add(new Image(40, 50, "Image2"));
		images.add(new Image(20, 30, "Image3"));
		images.add(new Image(20, 10, "Image4"));
		images.add(new Image(60, 20, "Image5"));
		images.add(new Image(10, 30, "Image6"));
		images.add(new Image(50, 30, "Image7"));
		images.add(new Image(30, 40, "Image8"));
		images.add(new Image(10, 10, "Image9"));
		images.add(new Image(30, 40, "Image10"));
		
		QSort qsort = new QSort();
		qsort.sort(images);
		
		p.setListImage(images);
		p.setPatternX(80);
		p.setPatternY(100);
		
		
		
		Packing packing = new Packing();
		packing.packing(print);
		
		assertEquals(2, print.getListPattern().size());
		Pattern pattern1 = print.getListPattern().get(0);
		assertEquals(0, pattern1.getImageList().get(0).getX());
		assertEquals(0, pattern1.getImageList().get(0).getY());
		
		assertEquals(40, pattern1.getImageList().get(1).getX());
		assertEquals(0, pattern1.getImageList().get(1).getY());
		
		assertEquals(70, pattern1.getImageList().get(2).getX());
		assertEquals(0, pattern1.getImageList().get(2).getY());
		
		assertEquals(70, pattern1.getImageList().get(3).getX());
		assertEquals(30, pattern1.getImageList().get(3).getY());
		
		assertEquals(40, pattern1.getImageList().get(4).getX());
		assertEquals(40, pattern1.getImageList().get(4).getY());
		
		assertEquals(40, pattern1.getImageList().get(5).getX());
		assertEquals(80, pattern1.getImageList().get(5).getY());
		
		assertEquals(0, pattern1.getImageList().get(6).getX());
		assertEquals(50, pattern1.getImageList().get(6).getY());
		
		assertEquals(20, pattern1.getImageList().get(7).getX());
		assertEquals(50, pattern1.getImageList().get(7).getY());
		
		Pattern pattern2 = print.getListPattern().get(1);
		assertEquals(0, pattern2.getImageList().get(0).getX());
		assertEquals(0, pattern2.getImageList().get(0).getY());
		
		assertEquals(0, pattern2.getImageList().get(1).getX());
		assertEquals(30, pattern2.getImageList().get(1).getY());
		
		assertTrue(print.isValid());
		
		packing.packNewPattern(print);
		
		assertTrue(print.isValid());
	}
	
	//@Test
	public void testOnRealData(){
		
		Project project = new Project("/Users/iiNku/Documents/workspace/FuzzyWookie/test/data_50Salpha.txt");
		project.parseFileAndSortImages();
		
		Print print = new Print(project);
		
		Packing packing = new Packing();
		packing.packing(print);
		
		assertTrue(print.isValid());
	}
}
