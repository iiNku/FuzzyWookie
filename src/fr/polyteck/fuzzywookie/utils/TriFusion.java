package fr.polyteck.fuzzywookie.utils;

import java.util.ArrayList;
import java.util.List;

import fr.polytech.fuzzywookie.metier.Print;

public class TriFusion {

	/*public static void triFusion(List<Print> tableau) {
		
		int longueur = tableau.size();
		if (longueur > 0) {
			triFusion(tableau, 0, longueur - 1);
		}
	}

	private static void triFusion(List<Print> tableau, int deb, int fin) {
		if (deb != fin) {
			int milieu = (fin + deb) / 2;
			triFusion(tableau, deb, milieu);
			triFusion(tableau, milieu + 1, fin);
			fusion(tableau, deb, milieu, fin);
		}
	}

	private static void fusion(List<Print> tableau, int deb1, int fin1, int fin2) {
		System.out.println("fusion : " + tableau.size());
		int deb2 = fin1 + 1;

		Print table1[] = new Print[fin1 - deb1 + 1];
		for (int i = deb1; i <= fin1; i++) {
			table1[i - deb1] = tableau.get(i);
		}

		int compt1 = deb1;
		int compt2 = deb2;

		for (int i = deb1; i <= fin2; i++) {
			if (compt1 == deb2) 
			{
				break; 
			} else if (compt2 == (fin2 + 1)) 
			{
				tableau.add(i, table1[compt1 - deb1]); 
				compt1++;
			} else if (table1[compt1 - deb1].simplexSolution() < tableau.get(compt2).simplexSolution()) {
				tableau.add(i, table1[compt1 - deb1]); 
				compt1++;
			} else {
				tableau.add(i, tableau.get(compt2));
				compt2++;
			}
		}
	}*/
}
