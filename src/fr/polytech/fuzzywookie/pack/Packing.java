package fr.polytech.fuzzywookie.pack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import fr.polytech.fuzzywookie.metier.Image;
import fr.polytech.fuzzywookie.metier.Pattern;
import fr.polytech.fuzzywookie.metier.Print;
import fr.polytech.fuzzywookie.metier.Rectangle;

public class Packing {

	public void packing(Print print){
		
		Pattern pattern = print.createPattern();
		
		Stack<Pattern> stack = new Stack<Pattern>();
		stack.add(pattern);
		
		while(!stack.empty()){
			Pattern p = stack.pop();
			for(Image i : print.getListImage()){
				if(p.getArea() > i.getArea()){
					int x = 0;
					int y = p.getHeight() - i.getHeight();
					p.addImage(new Image(x, y, i.getName()));
				}
			}
		}
	}
}
