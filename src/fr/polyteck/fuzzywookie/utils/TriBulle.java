package fr.polyteck.fuzzywookie.utils;

import java.util.ArrayList;
import java.util.List;

import fr.polytech.fuzzywookie.metier.Print;

public class TriBulle {

	public static List<Print> sortByFitness(List<Print> prints){
		
		List<Print> toReturn = new ArrayList<Print>();
		toReturn.addAll(prints);
		
		int i = toReturn.size();
		boolean swap = true;
		while(i > 0 && swap){
			System.out.println("Bulle : " + i);
			swap = false;
			for(int j = 1; j < i-1; j++){
				System.out.println("j=" + j);
				if(toReturn.get(j).simplexSolution() > toReturn.get(j + 1).simplexSolution()){
					Print tmp = toReturn.get(j);
					toReturn.add(j, prints.get(j + 1));
					toReturn.add(j + 1, tmp);
					swap = true;
				}
			}
			i--;
		}
		return toReturn;
	}
}
