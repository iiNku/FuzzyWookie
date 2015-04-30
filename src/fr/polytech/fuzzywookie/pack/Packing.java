package fr.polytech.fuzzywookie.pack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import fr.polytech.fuzzywookie.metier.Image;
import fr.polytech.fuzzywookie.metier.Pattern;
import fr.polytech.fuzzywookie.metier.Print;
import fr.polytech.fuzzywookie.metier.Rectangle;

public class Packing {
	
	private Stack<Pattern> stack;

	public void packing(Print print) {

		Pattern pattern = null;
		stack = new Stack<Pattern>();

		ArrayList<Image> list = new ArrayList<Image>();
		list.addAll(print.getListImage());
		
		boolean allPresent = false;
		
		while (!allPresent || !stack.empty()) {

			if(stack.empty()){
				System.out.println("Création nouveau pattern");
				pattern = print.createPattern();
				stack.add(pattern);
			}
			
			while (!stack.empty()) {
				Pattern p = stack.pop();
				System.out.println("Pattern utilisé : " + p.getWidth() + "*" + p.getHeight());
				for (Image i : list) {
					if (p.getArea() > i.getArea() && p.getWidth() >= i.getWidth() && p.getHeight() >= i.getHeight()) { // Testez la largeur et hauteur
						list.remove(i);
						if(list.isEmpty()){
							allPresent = true;
							list.addAll(print.getListImage());
						}
						
						insertImage(p, i);
						cutPattern(p, i);
						
						break;
					}
				}
			}
		}
	}

	private void cutPattern(Pattern p, Image i) {
		
		Pattern p11 = new Pattern(p.getWidth(), p.getHeight() - i.getHeight());
		Pattern p12 = new Pattern(p.getWidth() - i.getWidth(), i.getHeight());
		
		Pattern p21 = new Pattern(i.getWidth(), p.getHeight() - i.getHeight());
		Pattern p22 = new Pattern(p.getWidth() - i.getWidth(), p.getHeight());
		
		Pattern pMax1 = p11.getArea() > p12.getArea() ? p11 : p12;
		Pattern pMax2 = p21.getArea() > p22.getArea() ? p21 : p22;
		
		int max = pMax1.getWidth() > pMax1.getHeight() ? pMax1.getWidth() : pMax1.getHeight();
		int max2 = pMax1.getWidth() > pMax1.getHeight() ? pMax1.getWidth() : pMax1.getHeight();
		
		if(max > max2){
			p11.setDecoupX(0);
			p11.setDecoupY(p.getHeight() - i.getHeight());

			p12.setDecoupX(i.getWidth());
			p12.setDecoupY(p.getHeight());
			
			System.out.println("New pattern : " + p12.getWidth() + "*" + p12.getHeight());
			System.out.println("DecoupX : " + p12.getDecoupX() + " DecoupY" + p12.getDecoupY());
			
			System.out.println("New pattern : " + p11.getWidth() + "*" + p11.getHeight());
			System.out.println("DecoupX : " + p11.getDecoupX() + " DecoupY" + p11.getDecoupY());
			
			System.out.println("________");
			
			stack.add(p11);
			stack.add(p12);
		}
		else{
			
			p21.setDecoupX(0);
			p21.setDecoupY(p.getHeight() - i.getHeight());

			p22.setDecoupX(i.getWidth());
			p22.setDecoupY(p.getHeight());
			
			System.out.println("New pattern : " + p21.getWidth() + "*" + p21.getHeight());
			System.out.println("DecoupX : " + p21.getDecoupX() + " DecoupY" + p21.getDecoupY());
			
			System.out.println("New pattern : " + p22.getWidth() + "*" + p22.getHeight());
			System.out.println("DecoupX : " + p22.getDecoupX() + " DecoupY" + p22.getDecoupY());
			
			System.out.println("________");
			
			stack.add(p21);
			stack.add(p22);
		}
	}

	private void insertImage(Pattern pattern, Image image) {
		
		Image i = new Image(pattern.getDecoupX(), pattern.getDecoupY(), image.getName());
		pattern.addImage(i);
		pattern.setDecoupX(i.getWidth());
		
		System.out.println(i.getName() + "x=" + i.getX() + " et y=" + i.getY());
	}
}
