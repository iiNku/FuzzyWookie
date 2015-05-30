package fr.polytech.fuzzywookie.metier;

import static org.junit.Assert.*;

import org.junit.Test;

public class ImageTest {

	
	@Test
	public void testIntersect(){
		
		Image image1 = new Image(30, 30, "Image1");
		image1.setX(0);
		image1.setY(0);
		
		Image image2 = new Image(50, 50, "Image1");
		image2.setX(15);
		image2.setY(0);
		
		assertTrue(image1.intersect(image2));
		assertTrue(image2.intersect(image1));
		
		image2.setY(30);
		
		assertFalse(image1.intersect(image2));
		assertFalse(image2.intersect(image1));
	}
}
