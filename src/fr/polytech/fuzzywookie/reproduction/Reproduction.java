package fr.polytech.fuzzywookie.reproduction;

import java.util.ArrayList;
import java.util.List;

import fr.polytech.fuzzywookie.metier.*;

public class Reproduction {
		private ArrayList<Pattern> fils = new ArrayList<Pattern>();
	
	public ArrayList<Pattern> ReproductionPattern(Print p1,Print p2){
		 List<Pattern> patterns1 = p1.getListPattern();
		 List<Pattern> patterns2 = p2.getListPattern();
		 int maxPattern1 = patterns1.size();
		 int maxPattern2 = patterns2.size();
		 int meanPattern = Math.round((maxPattern1+maxPattern2)/2); 
		
		 while(fils.size()<meanPattern){
			 int rand = (int) Math.random();
			 int randPattern1 = (int) Math.round(rand*maxPattern1)-1;
			 int randPattern2 = (int) Math.round(rand*maxPattern2)-1;
			 if(fils.contains(patterns1.get(randPattern1))){
				 fils.add(patterns1.get(randPattern1));
			 }
			 if(!fils.contains(patterns2.get(randPattern2))){
				 fils.add(patterns2.get(randPattern2));
			 }
			 
		 }
		// test fils
		return fils;
	}
		
}
