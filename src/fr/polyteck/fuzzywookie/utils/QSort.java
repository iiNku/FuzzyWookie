package fr.polyteck.fuzzywookie.utils;

import java.util.ArrayList;
import java.util.List;

import fr.polytech.fuzzywookie.metier.Image;
import fr.polytech.fuzzywookie.metier.Print;

public class QSort {

	private List<Image> list;

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
