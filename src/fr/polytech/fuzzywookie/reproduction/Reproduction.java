package fr.polytech.fuzzywookie.reproduction;

import java.util.ArrayList;

import fr.polytech.fuzzywookie.metier.*;
import fr.polytech.fuzzywookie.voisinage.Voisinnage;

public class Reproduction {
    
    public Print ReproductionPattern(Print p1,Print p2){
    	
    	Print child = p1.clone();
    	child.getListPattern().clear();
    	
    	int meanPattern =0;
    	int offsetFather = 0;
    	int offsetMother = 0;
    	int occurence = 0;
		int mean = Math.round((p1.getListPattern().size()+p2.getListPattern().size())/2);
		
    	ArrayList<Pattern> father = (ArrayList<Pattern>) p1.getListPattern();
    	ArrayList<Pattern> mother = (ArrayList<Pattern>) p2.getListPattern();
    	int nbPattern =(int) (Math.random()*100)%3;
    	int max = Math.max(1,Math.min(Math.abs(mean-mother.size()), Math.abs(mean-father.size())));
    	int val = (int)((Math.random()*100) % (mean-max));
    	if(nbPattern==0){
    		meanPattern = mean;
    	}else if(nbPattern==1){
    		meanPattern = mean- val;//(int)((Math.random()*100) % (mean-Math.min(mother.size() ,  father.size())));
    	}else if(nbPattern==2){
    		meanPattern = mean+val; //(int)((Math.random()*100) % (mean-Math.min(mother.size() ,  father.size())));
    	}
    	
    	int minSize = (father.size() /2) > (mother.size() / 2) ? mother.size() / 2 : father.size() /2;
    	int pat = 1 + (int) (Math.random() * ((minSize - 1) + 1));

    	boolean bFather = true;
    	boolean bMother = true;
    	
    	while(child.getListPattern().size() < meanPattern){
    		if(child.getListPattern().size()>=mother.size() || child.getListPattern().size()>=father.size())
    		{
    			fusionImage(father, mother, child);
    		}
    		else
    		{
	    		if(occurence % 2 == 0){
	    			for(int i = 0; i < pat; i++){
	    				if(father.size() <= offsetFather + i){
	    					bFather = false;
	    					break;
	    				}
	    				addPattern(child, meanPattern, father.get(offsetFather + i));
	    			}
	    			if(bFather) offsetFather+=pat;
	    		}
	    		else{
	    			for(int i = 0; i < pat; i++){
	    				if(mother.size() <= offsetMother + i){
	    					bMother = false;
	    					break;
	    				}
	    				addPattern(child, meanPattern, mother.get(offsetMother + i));
	    			}
	    			if(bMother) offsetMother+=pat;
	    		}
	    		occurence++;
	    		}
    	}
    	if(child.isValid())
    	{
    	int randomMutation = (int)(Math.random()*100);
    	if(randomMutation>75)
    	{
    		Voisinnage v= new Voisinnage();
    		boolean MutaOk = false;
    		int cptMuta = 0;
    		while(!MutaOk)
    		{
	    		Print tmp = null;
	    		int rng = (int)(Math.random()*100)%4;
	    		if (rng == 0){
					tmp = v.addPattern(child);
				}
				else if (rng == 1)
				{
					tmp = v.addImage(child);
				}
				else if (rng == 2)
				{
					tmp = v.removeImage(child);
				}
				else if (rng == 3 && cptMuta>5)
				{
					tmp = v.removePattern(child);
				}
	    		if(tmp != null)
	    		{
	    			cptMuta++;
	    			if(cptMuta==35)
	    				MutaOk = true;
	    			child = tmp.clone();
	    		}
    		}
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
		Pattern p = child.createPattern();
		p.addFreeSpace(new Pattern(child.getProject().getPatternX(), child.getProject().getPatternY()));
		Pattern fatherFus = father.get((int)(Math.random()*100)%father.size());
		Pattern motherFus = mother.get((int)(Math.random()*100)%mother.size());
		for(int i = 0; i<fatherFus.getImageList().size()&& i < motherFus.getImageList().size() ; i++)
		{		
			if(i%2==0)
			{
				Pattern addPattern = p.addImageInFreeSpace(child.getProject().getImageByName(fatherFus.getImageList().get(i).getName()));
				if(addPattern != null)
					p = addPattern.clone();
			}
			else
			{
				Pattern addPattern = p.addImageInFreeSpace(child.getProject().getImageByName(motherFus.getImageList().get(i).getName()));
				if(addPattern!=null)
					p = addPattern.clone();
			}		
		}
		if(!p.isValid() || p.getImageList().size() == 0)
			child.getListPattern().remove(p);
	}
}
