package fr.polyteck.fuzzywookie.utils;

import java.util.List;

import fr.polytech.fuzzywookie.metier.Image;
import fr.polytech.fuzzywookie.metier.Print;

public class QSortSimplex {

	private List<Print> list;

	public void sort(List<Print> list) {

		if (list == null || list.size() == 0)
			return;

		this.list = list;
		quicksort(0, list.size() - 1);
	}

	private void quicksort(int low, int high) {

		//System.out.println("low="+low+" high=" + high);
		int i = low, j = high;
		Print pivot = list.get(low + (high - low) / 2);

		while (i <= j) {
			//System.out.println("i="+i+" j=" + j);
			while (list.get(i).simplexSolution() > pivot.simplexSolution())
				i++;

			while (list.get(j).simplexSolution() < pivot.simplexSolution())
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
	    Print temp = list.get(i);
	    list.set(i, list.get(j));
	    list.set(j, temp);
	  }
}
