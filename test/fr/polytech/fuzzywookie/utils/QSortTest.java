package fr.polytech.fuzzywookie.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import fr.polytech.fuzzywookie.metier.Image;
import fr.polyteck.fuzzywookie.utils.*;

public class QSortTest {

	@Test
	public void test(){
		
		ArrayList<Image> images = new ArrayList<Image>();
		images.add(new Image(110, 110, "aa"));
		images.add(new Image(70, 70, "bb"));
		images.add(new Image(110, 110, "cc"));
		images.add(new Image(90, 90, "dd"));
		images.add(new Image(120, 120, "ee"));
		images.add(new Image(80, 80, "ff"));
		
		
		ArrayList<Image> sort = new ArrayList<Image>();
		sort.add(new Image(120, 120, "0"));
		sort.add(new Image(110, 110, "0"));
		sort.add(new Image(110, 110, "0"));
		sort.add(new Image(90, 90, "0"));
		sort.add(new Image(80, 80, "0"));
		sort.add(new Image(70, 70, "0"));
		
		//appel qsort
		QSort qs = new QSort();
		qs.sort(images);
		
		//parcourt la liste des images
		for(int i = 0; i < images.size(); i++)
			assertEquals(sort.get(i).getArea(), images.get(i).getArea());
		} 
	}
