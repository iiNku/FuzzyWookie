package fr.polytech.fuzzywookie.pack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import fr.polytech.fuzzywookie.metier.Image;
import fr.polytech.fuzzywookie.metier.Pattern;
import fr.polytech.fuzzywookie.metier.PatternCouple;
import fr.polytech.fuzzywookie.metier.Print;
import fr.polytech.fuzzywookie.metier.Project;
import fr.polytech.fuzzywookie.metier.Rectangle;
import fr.polyteck.fuzzywookie.utils.QSort;

public class Packing {
	
	private Stack<Pattern> stack;
	private Pattern main;

	public void packing(Print print) {

		List<Image> images = print.getProject().getListImage();
		QSort qsort = new QSort();
		qsort.sort(images);
		
		stack = new Stack<Pattern>();
		
		while(!stack.isEmpty() || !images.isEmpty()){
			
			if(stack.isEmpty() && !images.isEmpty()){
				
				main = print.createPattern();
				stack.push(main);
			}
			
			Pattern pattern = stack.pop();
			for(Image image : images){
				
				if(imageFits(main, image)){
					placeImage(main, image);
					splitPattern(pattern, image);
				}
			}
			
		}
		
	}

	private void splitPattern(Pattern pattern, Image placed) {
		
		Pattern p11 = new Pattern(pattern.getWidth(), pattern.getHeight() - placed.getHeight());
		Pattern p12 = new Pattern(pattern.getWidth() - placed.getWidth() ,pattern.getHeight() - placed.getHeight());

		PatternCouple couple1 = new PatternCouple(p11, p12);
		
		Pattern p21 = new Pattern(placed.getWidth(), pattern.getHeight() - placed.getHeight());
		Pattern p22 = new Pattern(pattern.getWidth() - placed.getWidth(), pattern.getHeight());
		
		PatternCouple couple2 = new PatternCouple(p21, p22);
		
		PatternCouple selected = getSelectedCouple(couple1, couple2);
	}

	private PatternCouple getSelectedCouple(PatternCouple couple1, PatternCouple couple2) {
		

		Pattern max1 = couple1.getLarger();
		Pattern max2 = couple2.getLarger();
		
		int side1 = max1.getHigherSide();
		int side2 = max2.getHigherSide();
		
		return side1 > side2 ? couple1 : couple2;
	}


	private boolean imageFits(Pattern pattern, Image image) {
		
		return pattern.getArea() >= image.getArea() 
				&& pattern.getWidth() >= image.getWidth()
				&& pattern.getHeight() >= image.getHeight();
	}
	
	private void placeImage(Pattern main, Image image) {
		
		Image placed = new Image(image.getWidth(), image.getHeight(), image.getName());
		placed.setX(main.getDecoupX());
		placed.setY(main.getDecoupY());
		
		main.addImage(placed);
	}
}
