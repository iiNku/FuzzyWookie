package fr.polytech.fuzzywookie.voisinage;

import java.util.ArrayList;
import java.util.List;

import fr.polytech.fuzzywookie.metier.Image;
import fr.polytech.fuzzywookie.metier.Pattern;
import fr.polytech.fuzzywookie.metier.Print;
import fr.polytech.fuzzywookie.metier.Project;
import fr.polytech.fuzzywookie.pack.Packing;

public class Voisinnage {

	public static List<Print> generate(Print initialPrint) {
		
		List<Print> neighbors = new ArrayList<Print>();
		Print neighbor = initialPrint;
		neighbors.add(neighbor);
		Print tmp = neighbor;
		
		while (neighbors.size() < 1000) {
			int rng = (int) ((Math.random() * 100)%4);
			if (rng == 0)
				tmp = addPattern(neighbor);
			else if (rng == 1)
				tmp = changeImage(neighbor);
			else if (rng == 2)
				tmp = removeImage(neighbor);
			else {
				Print tmp2 = addImage(neighbor);
				if(tmp2 != null) tmp = tmp2;
			}
			System.out.println(tmp);
			if (tmp.isValid()){
				neighbor = tmp;
				neighbors.add(neighbor);
			}
		}
		return neighbors;
	}

	private static Print removeImage(Print print) {

		List<Pattern> patterns = print.getListPattern();
		
		int rng = (int) (Math.random() * patterns.size());
		
		Pattern neo = patterns.get(rng);
		
		List<Image> images = neo.getImageList();
		rng = (int) (Math.random() * images.size());
		if(images.size()==0)
		{
			patterns.remove(neo);
		}
		else
		{
		Image removeImg = images.get(rng);
		if(print.getNbImage(removeImg)>1)
			images.remove(rng);
		}
		return print;
	}

	private static Print changeImage(Print print) {
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

	private static Print addImage(Print print)
	{
		for(Pattern p : print.getListPattern())
		{
			for(Pattern space : p.getFreeSpace())
			{
				for(Image i : print.getProject().getListImage())
				{
					if(i.getArea() < space.getArea() && i.getWidth() < space.getWidth() && i.getHeight() < space.getHeight())
					{
						p.addImage(i);
						List<Pattern> splitpatterns = Packing.splitPattern(space, i);
						i.setX(space.getDecoupX());
						i.setY(space.getDecoupY());
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
	
	private static Print addPattern(Print print) {

		Packing packing = new Packing();
		packing.packNewPattern(print);
		return print;
	}

}
