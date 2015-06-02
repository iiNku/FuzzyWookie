package fr.polytech.fuzzywookie.voisinage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.polytech.fuzzywookie.metier.Image;
import fr.polytech.fuzzywookie.metier.Pattern;
import fr.polytech.fuzzywookie.metier.Print;
import fr.polytech.fuzzywookie.metier.Project;
import fr.polytech.fuzzywookie.pack.Packing;

public class Voisinnage {

	private Packing packing;
	
	public List<Print> generate(Print initialPrint) {
		

		packing = new Packing();
		
		List<Print> neighbors = new ArrayList<Print>();
		neighbors.add(initialPrint);
		
		Print neighbor = new Print(initialPrint);
		
		while (neighbors.size() < 1000) {
			
			Print tmp = null;
			
			int rng = (int)(Math.random()*100)%4;
			if (rng == 0 && neighbor.getListPattern().size()<neighbor.getProject().getListImage().size()){
				System.out.println("addipattern");
				tmp = addPattern(neighbor);
				if(!neighbor.isValid()){
					System.out.println("stop");
				}
			}
			else 
			if (rng == 1)
			{
				System.out.println("addimage");
				tmp = addImage(neighbor);
				if(tmp!=null && !tmp.isValid()){
					System.out.println("ueueueu");
				}
				if(!neighbor.isValid()){
					if(tmp == null)
						System.out.println("addimagerienrenvoyé");
					System.out.println("stop");
				}
				//if(tmpModif != null) neighbor = tmpModif;
			}
			else if (rng == 2)
			{
				if(!neighbor.isValid()){
					System.out.println("pas normal du tout");
				}
				Print old_neighbor = new Print(neighbor);
				System.out.println("removeimg");
				tmp = removeImage(neighbor);
				if(!neighbor.isValid()){
					System.out.println("stop");
				}
				//if(tmpModif != null) neighbor = tmpModif;
			}
//			else if (rng == 3)
//			{
//				System.out.println("removepattern");
//				tmp = removePattern(neighbor);
//			}
				
			if (tmp != null && tmp.isValid()){
				neighbor = new Print(tmp);
				neighbors.add(neighbor);
				if(!neighbor.isValid()){
					System.out.println("nat fais gaffe!");
				}
			}
			System.out.println(neighbors.size());
		}
		return neighbors;
	}

	private Print removeImage(Print print) {
		
		Print tmp_removeImage = new Print(print);
		System.out.println("removeImage");
		
		List<Image> imagesDouble = new ArrayList<Image>();
		for(Image image : tmp_removeImage.getProject().getListImage()){
			if(tmp_removeImage.getNbImage(image) > 1){
				imagesDouble.add(image);
			}
		}
		
		if(imagesDouble.size() == 0) return null;
		
		int rng = (int) (Math.random() * imagesDouble.size());
		Image imageToRemove = imagesDouble.get(rng);
		
		List<Pattern> patternDouble = new ArrayList<Pattern>();
		for(Pattern pattern : tmp_removeImage.getListPattern()){
			if(pattern.getNbImage(imageToRemove) > 0){
				patternDouble.add(pattern);
			}
		}
		
		int rngPattern = (int) (Math.random() * patternDouble.size());
		Pattern patternToRemove = tmp_removeImage.getListPattern().get(rngPattern);
		
		Pattern save = new Pattern(patternToRemove);
		
		tmp_removeImage.getListPattern().get(rngPattern).remove(imageToRemove.getName());
		if(tmp_removeImage.getListPattern().get(rngPattern).getImageList().size()==0){
			tmp_removeImage.getListPattern().remove(rngPattern);
		}
		
		if(tmp_removeImage.isValid()){
			return tmp_removeImage;
		}
		else{
			patternToRemove = new Pattern(save);
			return null;
		}
	}

	private Print changeImage(Print print) {
		
		Print tmp_changeImage = new Print(print);
		List<Pattern> patterns = tmp_changeImage.getListPattern();
		
		int rng = (int) (Math.random() * patterns.size());
		
		Pattern neo = patterns.get(rng);
		
		List<Image> images = neo.getImageList();
		if(!images.isEmpty())
		{
		rng = (int) (Math.random() * images.size());
		Image removeImg = images.get(rng);
		if(tmp_changeImage.getNbImage(removeImg)>1)
		{
			Pattern freeSpace = new Pattern(removeImg.getWidth(), removeImg.getHeight());
			freeSpace.setDecoupX(removeImg.getX());
			freeSpace.setDecoupY(removeImg.getY());
			images.remove(rng);
			neo.addFreeSpace(freeSpace);
		}
		for(Image i : tmp_changeImage.getProject().getListImage())
		{
			if(neo.addImageInFreeSpace(i))
				return tmp_changeImage.isValid() ? tmp_changeImage : null;
		}	
		}
		return tmp_changeImage.isValid() ? tmp_changeImage : null;
	}
	
	private Print removePattern(Print neighbor)
	{
		Print print = new Print(neighbor);
		Map<String, Integer> nbImageMap = new HashMap<String, Integer>();
		List<Pattern> deletePatterns = new ArrayList<Pattern>();
		for(Image i : print.getProject().getListImage())
		{
				nbImageMap.put(i.getName(), Integer.valueOf(print.getNbImage(i)));
		}
		for(Pattern p : print.getListPattern())
		{
			boolean retirePattern = false;
			for(Image i : p.getImageList())
			{
				if(nbImageMap.get(i.getName())-p.getNbImage(i)<=0)
				{
					break;
				}
				retirePattern = true;
			}
			if(retirePattern = true)
				deletePatterns.add(p);	
		}
		if(!deletePatterns.isEmpty())
		{
			int rdn = (int)(Math.random()*100)%deletePatterns.size();
			print.getListPattern().remove(rdn);
			if(print.isValid())
				return print;
		}
		return null;
	}

	private Print addImage(Print neighbor)
	{
		Print print = new Print(neighbor);
		
		for(Pattern p : print.getListPattern())
		{
			for(Pattern space : p.getFreeSpace())
			{
				for(Image i : print.getProject().getListImage())
				{
					if(i.getArea() < space.getArea() && i.getWidth() < space.getWidth() && i.getHeight() < space.getHeight())
					{
						Pattern save = new Pattern(p);
						packing.placeImage(p, i, space);
						List<Pattern> splitpatterns = packing.splitPattern(space, i);
						p.getFreeSpace().remove(space);
						p.addFreeSpace(splitpatterns.get(0));
						p.addFreeSpace(splitpatterns.get(1));
						
						if(print.isValid()){
							return print;
						}
						else{
							p = new Pattern(save);
							return null;
						}
					}
				}
				
			}
		}
		return null;
	}
	
	private Print addPattern(Print print) {

		Print tmp = new Print(print);
		
		Packing packing = new Packing();
		packing.packNewPattern(tmp);
		return tmp.isValid() ? tmp : null;
	}

}
