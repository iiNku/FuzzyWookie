package fr.polytech.fuzzywookie.voisinage;

import java.util.ArrayList;
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
		Print neighbor = initialPrint;
		neighbors.add(neighbor);
		Print tmp = new Print(neighbor.getProject());
		
		tmp.setPrint(neighbor);
		
		while (neighbors.size() < 10000) {
			tmp = new Print(neighbor.getProject());
			tmp.setPrint(neighbor);
			neighbor = new Print(neighbor.getProject());
			neighbor.setPrint(tmp);
			int rng = (int)(Math.random()*100)%3;
			if (rng == 0)
				neighbor = addPattern(neighbor);
			else if (rng == 1)
			{
				Print tmpModif = addImage(neighbor);
				if(tmpModif != null) neighbor = tmpModif;
			}
			else if (rng == 2)
			{
//				Print tmpModif = removeImage(neighbor);
//				if(tmpModif != null) neighbor = tmpModif;
			}
				
			if (neighbor.isValid()){
				//neighbor = tmp;
				neighbors.add(neighbor);
			}
			System.out.println(neighbors.size());
		}
		return neighbors;
	}

	private Print removeImage(Print print) {
		System.out.println("removeImage");
		boolean remove = false;
		for(Image img : print.getProject().getListImage())
		{
			if(print.getNbImage(img)>1)
			{
				for(Pattern p : print.getListPattern())
				{
					if(p.getNbImage(img)>0)
					{
						List<Image> parcoursImage = new ArrayList<Image>();
						parcoursImage.addAll(p.getImageList());
						for(Image removeImage : parcoursImage)
						{
							if(removeImage.getName().equals(img.getName()))
							{
								p.getImageList().remove(removeImage);
								Pattern freeSpace = new Pattern(img.getWidth(), img.getHeight());
								freeSpace.setDecoupX(removeImage.getX());
								freeSpace.setDecoupY(removeImage.getY());
								p.addFreeSpace(freeSpace);
								remove = true;
								return print;
							}
						}
						
					}
				}
			}				
		}
		return print;
		
//		
//		Print removePrint = new Print(print.getProject());
//		removePrint.setPrint(print);
//		
//		List<Pattern> patterns = removePrint.getListPattern();
//		
//		int rng = (int) (Math.random() * patterns.size());
//		
//		Pattern neo = patterns.get(rng);
//		
//		List<Image> images = neo.getImageList();
//		rng = (int) (Math.random() * images.size());
//		
//		Image removeImg = images.get(rng);
//		if(removePrint.getNbImage(removeImg)>1)
//		{
//			images.remove(rng);
//			if(neo.getImageList().isEmpty())
//				removePrint.getListPattern().remove(neo);
//		}
//		if(removePrint.isValid())
//			return removePrint;
//		else
//			return null;
	}

	private Print changeImage(Print print) {
		List<Pattern> patterns = print.getListPattern();
		
		int rng = (int) (Math.random() * patterns.size());
		
		Pattern neo = patterns.get(rng);
		
		List<Image> images = neo.getImageList();
		if(!images.isEmpty())
		{
		rng = (int) (Math.random() * images.size());
		Image removeImg = images.get(rng);
		if(print.getNbImage(removeImg)>1)
		{
			Pattern freeSpace = new Pattern(removeImg.getWidth(), removeImg.getHeight());
			freeSpace.setDecoupX(removeImg.getX());
			freeSpace.setDecoupY(removeImg.getY());
			images.remove(rng);
			neo.addFreeSpace(freeSpace);
		}
		for(Image i : print.getProject().getListImage())
		{
			if(neo.addImageInFreeSpace(i))
				return print;
		}	
		}
		return print;
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
		Print print = new Print(neighbor.getProject());
		print.setPrint(neighbor);
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
						
						return print;
					}
				}
				
			}
		}
		return null;
	}
	
	private Print addPattern(Print print) {

		Packing packing = new Packing();
		packing.packNewPattern(print);
		return print;
	}

}
