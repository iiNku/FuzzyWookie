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

	public void packing(Print print) {

		List<Image> images = new ArrayList<Image>();
		images.addAll(print.getProject().getListImage());

		boolean allPlaced = false;

		Stack<Pattern> stack = new Stack<Pattern>();
		Pattern main = print.createPattern();
		stack.push(main);
		
		while(!stack.isEmpty() || !allPlaced){
			boolean ImagePlace = false;
			if(stack.isEmpty()){
				main = print.createPattern();
				stack.push(main);
			}

			Pattern pattern = stack.pop();
			for (Image image : images) {

				if (imageFits(pattern, image)) {

					images.remove(image);
					if (images.size() == 0) {
						allPlaced = true;
						images.addAll(print.getProject().getListImage());
					}
					placeImage(main, image, pattern);
					List<Pattern> splitPatterns = splitPattern(pattern, image);
					stack.push(splitPatterns.get(0));
					stack.push(splitPatterns.get(1));
					ImagePlace = true;
					break;
				}
			}
			if(!ImagePlace)
				main.addFreeSpace(pattern);
		}
	}

	public void packNewPattern(Print print) {

		List<Image> images = new ArrayList<Image>();
		images.addAll(print.getProject().getListImage());

		Stack<Pattern> stack = new Stack<Pattern>();
		Pattern main = print.createPattern();
		stack.push(main);

		boolean imageAdded = true;

		while (imageAdded && !stack.empty()) {
			imageAdded = false;
			Pattern pattern = stack.pop();
			int times = 0;
			while(times < images.size() / 4){
				int rng = (int) (Math.random() * images.size());
				Image image = images.get(rng);
				times++;
				if (imageFits(pattern, image)) {
					imageAdded = true;
					placeImage(main, image, pattern);
					List<Pattern> splitPatterns = splitPattern(pattern, image);
					stack.push(splitPatterns.get(0));
					stack.push(splitPatterns.get(1));
					break;
				}
			}
			if(imageAdded == false)
				main.addFreeSpace(pattern);
		}
	}

	 public List<Pattern> splitPattern(Pattern pattern, Image placed) {
		List<Pattern> splitPatterns = new ArrayList<Pattern>();
		Pattern p11 = new Pattern(pattern.getWidth(), pattern.getHeight()
				- placed.getHeight());
		p11.setDecoupX(pattern.getDecoupX());
		p11.setDecoupY(pattern.getDecoupY() + placed.getHeight());

		Pattern p12 = new Pattern(pattern.getWidth() - placed.getWidth(),
				placed.getHeight());
		p12.setDecoupX(pattern.getDecoupX() + placed.getWidth());
		p12.setDecoupY(pattern.getDecoupY());

		PatternCouple couple1 = new PatternCouple(p11, p12);

		Pattern p21 = new Pattern(placed.getWidth(), pattern.getHeight()
				- placed.getHeight());
		p21.setDecoupX(pattern.getDecoupX());
		p21.setDecoupY(pattern.getDecoupY() + placed.getHeight());

		Pattern p22 = new Pattern(pattern.getWidth() - placed.getWidth(),
				pattern.getHeight());
		p22.setDecoupX(pattern.getDecoupX() + placed.getWidth());
		p22.setDecoupY(pattern.getDecoupY());

		PatternCouple couple2 = new PatternCouple(p21, p22);

		PatternCouple selected = getSelectedCouple(couple1, couple2);
		splitPatterns.add(selected.getPattern1());
		splitPatterns.add(selected.getPattern2());
		
		return splitPatterns;
	}

	private PatternCouple getSelectedCouple(PatternCouple couple1,
			PatternCouple couple2) {

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

	public void placeImage(Pattern main, Image image, Pattern temp) {

		Image placed = new Image(image.getWidth(), image.getHeight(),
				image.getName());
		placed.setX(temp.getDecoupX());
		placed.setY(temp.getDecoupY());

		main.addImage(placed);
	}
}
