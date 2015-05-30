package fr.polytech.fuzzywookie.voisinage;

import java.util.ArrayList;
import java.util.List;

import fr.polytech.fuzzywookie.metier.Image;
import fr.polytech.fuzzywookie.metier.Pattern;
import fr.polytech.fuzzywookie.metier.Print;
import fr.polytech.fuzzywookie.pack.Packing;

public class Voisinnage {

	public static List<Print> generate(Print initialPrint) {
		List<Print> neighbors = new ArrayList<Print>();
		Print neighbor = initialPrint;
		neighbors.add(neighbor);

		while (neighbors.size() < 1000) {
			System.out.println(neighbors.size());
			int rng = (int) (Math.random() * 3);
			if (rng == 0)
				neighbor = addPattern(neighbor);
			//else if (rng == 1)
				//neighbor = changeImage(neighbor);
			//else if (rng == 2)
				//neighbor = removeImage(neighbor);
			if (neighbor.isValid())
				neighbors.add(neighbor);
		}
		return neighbors;
	}

	private static Print removeImage(Print print) {

		List<Pattern> patterns = print.getListPattern();
		
		int rng = (int) (Math.random() * patterns.size());
		
		Pattern neo = patterns.get(rng);
		
		List<Image> images = neo.getImageList();
		rng = (int) (Math.random() * images.size());
		Image removeImg = images.get(rng);
		if(print.getNbImage(removeImg)>1)
			images.remove(rng);
		
		return print;
	}

	private static Print changeImage(Print print) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Print addPattern(Print print) {

		Packing packing = new Packing();
		packing.packNewPattern(print);
		return print;
	}

}
