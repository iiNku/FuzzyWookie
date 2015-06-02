package fr.polytech.fuzzywookie.voisinage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
			
			int rng = (int)(Math.random()*100)%3;
			if (rng == 0 && neighbor.getListPattern().size()<neighbor.getProject().getListImage().size()){
				if(!neighbor.isValid()){
					System.out.println("hahahaha");
				}				
				System.out.println("addpattern");
				tmp = addPattern(neighbor);
			}
			else if (rng == 1)
			{
				if(!neighbor.isValid()){
					System.out.println("hahahaha");
				}
				System.out.println("addimage");
				tmp = addImage(neighbor);
				//if(tmpModif != null) neighbor = tmpModif;
			}
			else if (rng == 2)
			{
				if(!neighbor.isValid()){
					System.out.println("hahahaha");
				}
				System.out.println("removeimage");
				tmp = removeImage(neighbor);
				//if(tmpModif != null) neighbor = tmpModif;
			}
				
			if (tmp != null){
				neighbor = new Print(tmp);
				if(neighbor.isValid()){
					System.out.println("OUI");
				}
				neighbors.add(neighbor);
			}
			System.out.println(neighbors.size());
		}
		return neighbors;
	}

	private Print removeImage(Print print) {
		
		Print tmp = new Print(print);
		if(!tmp.isValid()){
			System.out.println("fuck shit");
		}
		System.out.println("removeImage");
		
		List<Image> imagesDouble = new ArrayList<Image>();
		for(Image image : tmp.getProject().getListImage()){
			if(tmp.getNbImage(image) > 1){
				imagesDouble.add(image);
			}
		}
		
		if(imagesDouble.size() == 0) return null;
		
		int rng = (int) (Math.random() * imagesDouble.size());
		Image imageToRemove = imagesDouble.get(rng);
		
		List<Pattern> patternDouble = new ArrayList<Pattern>();
		for(Pattern pattern : tmp.getListPattern()){
			if(pattern.getNbImage(imageToRemove) > 0){
				patternDouble.add(pattern);
			}
		}
		
		int rngPattern = (int) (Math.random() * patternDouble.size());
		Pattern patternToRemove = tmp.getListPattern().get(rngPattern);
		
		patternToRemove.remove(imageToRemove.getName());
		if(!tmp.isValid()){
			System.out.println("shit");
		}
		return tmp.isValid() ? tmp : null;
	}

	private Print changeImage(Print print) {
		
		Print tmp = new Print(print);
		List<Pattern> patterns = tmp.getListPattern();
		
		int rng = (int) (Math.random() * patterns.size());
		
		Pattern neo = patterns.get(rng);
		
		List<Image> images = neo.getImageList();
		if(!images.isEmpty())
		{
		rng = (int) (Math.random() * images.size());
		Image removeImg = images.get(rng);
		if(tmp.getNbImage(removeImg)>1)
		{
			Pattern freeSpace = new Pattern(removeImg.getWidth(), removeImg.getHeight());
			freeSpace.setDecoupX(removeImg.getX());
			freeSpace.setDecoupY(removeImg.getY());
			images.remove(rng);
			neo.addFreeSpace(freeSpace);
		}
		for(Image i : tmp.getProject().getListImage())
		{
			if(neo.addImageInFreeSpace(i))
				return tmp.isValid() ? tmp : null;
		}	
		}
		return tmp.isValid() ? tmp : null;
	}
	
//	private Print removePattern(Print neighbor)
//	{
//		for(Pattern p : neighbor.getListPattern())
//		{
//			for(Image)
//		}
//	}

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
						packing.placeImage(p, i, space);
						List<Pattern> splitpatterns = packing.splitPattern(space, i);
						p.getFreeSpace().remove(space);
						p.addFreeSpace(splitpatterns.get(0));
						p.addFreeSpace(splitpatterns.get(1));
						
						return print.isValid() ? print : null;
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
