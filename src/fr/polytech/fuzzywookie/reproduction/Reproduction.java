package fr.polytech.fuzzywookie.reproduction;

import java.util.ArrayList;
import java.util.List;

import fr.polytech.fuzzywookie.metier.*;
import fr.polytech.fuzzywookie.pack.PackingTest;

public class Reproduction {
    
    public Print ReproductionPattern(Print p1,Print p2){
    	
    	Print child = new Print(p1.getProject());
    	int ajout = 0;
    	
    	int meanPattern = Math.round((p1.getListPattern().size()+p2.getListPattern().size())/2);
    	
    	int offsetFather = 0;
    	int offsetMother = 0;
    	int occurence = 0;
    	
    	List<Pattern> father = p1.getListPattern();
    	List<Pattern> mother = p2.getListPattern();
    	
    	int minSize = (father.size() /2) > (mother.size() / 2) ? mother.size() / 2 : father.size() /2;
    	int pat = 1 + (int) ((Math.random() *100)%(minSize));

    	while(child.getListPattern().size() < meanPattern){
    		//System.out.println("Creation child : " + child.getListPattern().size());
    		if(occurence % 2 == 0){
    			for(int i = 0; i < pat; i++){
    				if(father.size() <= offsetFather + i){
    					occurence++;
    					break;
    				}
    				addPattern(child, meanPattern, father.get(offsetFather + i));
    			}
    			offsetFather+=pat;
    		}
    		else{
    			for(int i = 0; i < pat; i++){
    				if(mother.size() <= offsetMother + i){
    					occurence++;
    					break;
    				}
    				System.out.println("lel el bug : "+ i+offsetMother);
    				addPattern(child, meanPattern, mother.get(offsetMother + i));
    			}
    			offsetMother+=pat;
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
