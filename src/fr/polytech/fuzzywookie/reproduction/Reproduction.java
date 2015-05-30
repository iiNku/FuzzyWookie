package fr.polytech.fuzzywookie.reproduction;

import java.util.ArrayList;
import java.util.List;

import fr.polytech.fuzzywookie.metier.*;
import fr.polytech.fuzzywookie.pack.PackingTest;

public class Reproduction {
    
    public Print ReproductionPattern(Print p1,Print p2){
    	
    	int pat = 3;
    	
    	Print child = new Print(p1.getProject());
    	int meanPattern = Math.round((p1.getListPattern().size()+p2.getListPattern().size())/2);
    	
    	int offsetFather = 0;
    	int offsetMother = 0;
    	int occurence = 0;
    	
    	ArrayList<Pattern> father = (ArrayList<Pattern>) p1.getListPattern();
    	ArrayList<Pattern> mother = (ArrayList<Pattern>) p2.getListPattern();
    	
    	while(child.getListPattern().size() < meanPattern){
    		
    		if(occurence % 2 == 0){
    			for(int i = 0; i < pat; i++){
    				addPattern(child, meanPattern, father.get(offsetFather + i));
    			}
    			offsetFather+=3;
    		}
    		else{
    			for(int i = 0; i < pat; i++){
    				addPattern(child, meanPattern, mother.get(offsetMother + i));
    			}
    			offsetMother+=3;
    		}
    		occurence++;
    	}
    	return child;
    }

	private void addPattern(Print child, int meanPattern, Pattern pattern) {
		
		if(child.getListPattern().size() < meanPattern)
			child.getListPattern().add(pattern);
		
	}
    
}
