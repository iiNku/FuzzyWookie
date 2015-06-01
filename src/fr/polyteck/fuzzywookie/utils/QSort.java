package fr.polyteck.fuzzywookie.utils;

import java.util.ArrayList;
import java.util.List;

import fr.polytech.fuzzywookie.metier.Image;
import fr.polytech.fuzzywookie.metier.Print;

public class QSort {

	private List<Image> list;
	
	public static List<Print> triFitnessDecroissant(List<Print> tableau) {
		int longueur = tableau.size();
		Print tampon;
		boolean permut;
		do {
			// hypothèse : le tableau est trié
			permut = false;
			for (int i = 0; i < longueur - 1; i++) {
				// Teste si 2 éléments successifs sont dans le bon ordre ou non
				if (tableau.get(i).simplexSolution() > tableau.get(i + 1)
						.simplexSolution()) {
					// s'ils ne le sont pas, on échange leurs positions
					tampon = tableau.get(i);
					tableau.add((i), tableau.get(i + 1));
					tableau.add((i + 1), tampon);
					permut = true;
				}
			}
		} while (permut);
		return tableau;
	}

	public void sort(List<Image> list) {

		if (list == null || list.size() == 0)
			return;

		this.list = list;
		quicksort(0, list.size() - 1);
	}

	private void quicksort(int low, int high) {

		int i = low, j = high;
		Image pivot = list.get(low + (high - low) / 2);

		while (i <= j) {

			while (list.get(i).getArea() > pivot.getArea())
				i++;

			while (list.get(j).getArea() < pivot.getArea())
				j--;

			if (i <= j) {
				exchange(i, j);
				i++;
				j--;
			}
		}
		if (low < j)
			quicksort(low, j);
		if (i < high)
			quicksort(i, high);
	}
	
	private void exchange(int i, int j) {
	    Image temp = list.get(i);
	    list.set(i, list.get(j));
	    list.set(j, temp);
	  }
}
