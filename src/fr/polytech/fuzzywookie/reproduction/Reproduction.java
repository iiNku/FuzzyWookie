package fr.polytech.fuzzywookie.reproduction;

import java.util.ArrayList;
import java.util.List;

import fr.polytech.fuzzywookie.metier.*;
import fr.polytech.fuzzywookie.pack.PackingTest;

public class Reproduction {
    
    public Print ReproductionPattern(Print p1,Print p2){
    	
    	Print child = p1.clone();
    	
    	int meanPattern =0;
    	int offsetFather = 0;
    	int offsetMother = 0;
    	int occurence = 0;
		int mean = Math.round((p1.getListPattern().size()+p2.getListPattern().size())/2);
		
    	ArrayList<Pattern> father = (ArrayList<Pattern>) p1.getListPattern();
    	ArrayList<Pattern> mother = (ArrayList<Pattern>) p2.getListPattern();
    	int nbPattern =(int) (Math.random()*100)%3;
    	int val = (int)((Math.random()*100) % (mean-Math.min(mother.size(), father.size())));
    	if(nbPattern==0){
    		meanPattern = mean;
    	}else if(nbPattern==1){
    		meanPattern = mean- val;//(int)((Math.random()*100) % (mean-Math.min(mother.size() ,  father.size())));
    	}else if(nbPattern==2){
    		meanPattern = mean+val; //(int)((Math.random()*100) % (mean-Math.min(mother.size() ,  father.size())));
    	}
    	
    	int minSize = (father.size() /2) > (mother.size() / 2) ? mother.size() / 2 : father.size() /2;
    	int pat = 1 + (int) (Math.random() * ((minSize - 1) + 1));

    	while(child.getListPattern().size() < meanPattern){
    		if(child.getListPattern().size()>=mother.size() || child.getListPattern().size() >= father.size())
    		{
    			fusionImage(father, mother, child);
    		}
    		else
    		{
	    		System.out.println("Creation child : " + child.getListPattern().size());
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
	    				addPattern(child, meanPattern, mother.get(offsetMother + i));
	    			}
	    			offsetMother+=pat;
	    		}
	    		occurence++;
	    		}
	    	}
	    return child;
    }

	private void addPattern(Print child, int meanPattern, Pattern pattern) {
		
		if(child.getListPattern().size() < meanPattern)
			child.getListPattern().add(pattern);
	}
	
	private void fusionImage(ArrayList<Pattern> father,ArrayList<Pattern> mother, Print child)
	{
		
	}
    
}
