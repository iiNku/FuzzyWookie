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

		List<Image> images = new ArrayList<Image>();
		images.addAll(print.getProject().getListImage());
		
		boolean allPlaced = false;
		
		QSort qsort = new QSort();
		qsort.sort(images);
		
		stack = new Stack<Pattern>();
		main = print.createPattern();
		stack.push(main);
		
		while(!stack.isEmpty() || !allPlaced){
			if(stack.isEmpty()){
				
				main = print.createPattern();
				stack.push(main);
			}
			
			Pattern pattern = stack.pop();
			for(Image image : images){
				
				if(imageFits(pattern, image)){
					
					images.remove(image);
					if(images.size() == 0) {
						allPlaced = true;
						images.addAll(print.getProject().getListImage());
					}
					placeImage(main, image, pattern);
					splitPattern(pattern, image);
					break;
				}
			}
			main.addFreeSpace(pattern);
		}
	}
	
	public static boolean isValid(List<Pattern> patterns){
		
		for(Pattern pattern : patterns){
			List<Image> images = pattern.getImageList();
			for(int i = 0; i < images.size(); i++){
				for(int j = i + 1; j < images.size(); j++){
					if(images.get(i).intersect(images.get(j)) || !images.get(i).isInside(pattern)) return false;
				}
			}
		}
		return true;
	}

	private void splitPattern(Pattern pattern, Image placed) {
		
		Pattern p11 = new Pattern(pattern.getWidth(), pattern.getHeight() - placed.getHeight());
		p11.setDecoupX(pattern.getDecoupX());
		p11.setDecoupY(pattern.getDecoupY() + placed.getHeight());
		
		Pattern p12 = new Pattern(pattern.getWidth() - placed.getWidth() , placed.getHeight());
		p12.setDecoupX(pattern.getDecoupX() + placed.getWidth());
		p12.setDecoupY(pattern.getDecoupY());
		
		PatternCouple couple1 = new PatternCouple(p11, p12);
		
		Pattern p21 = new Pattern(placed.getWidth(), pattern.getHeight() - placed.getHeight());
		p21.setDecoupX(pattern.getDecoupX());
		p21.setDecoupY(pattern.getDecoupY() + placed.getHeight());
		
		Pattern p22 = new Pattern(pattern.getWidth() - placed.getWidth(), pattern.getHeight());
		p22.setDecoupX(pattern.getDecoupX() + placed.getWidth());
		p22.setDecoupY(pattern.getDecoupY());
		
		PatternCouple couple2 = new PatternCouple(p21, p22);
		
		PatternCouple selected = getSelectedCouple(couple1, couple2);
		stack.push(selected.getPattern1());
		stack.push(selected.getPattern2());
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
				&& image.getWidth() <= pattern.getWidth()
				&& image.getHeight() <= pattern.getHeight();
	}
	
	private void placeImage(Pattern main, Image image, Pattern temp) {
		
		Image placed = new Image(image.getWidth(), image.getHeight(), image.getName());
		placed.setX(temp.getDecoupX());
		placed.setY(temp.getDecoupY());

		main.addImage(placed);
	}
}
