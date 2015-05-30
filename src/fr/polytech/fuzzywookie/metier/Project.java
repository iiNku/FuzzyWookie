package fr.polytech.fuzzywookie.metier;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import fr.polytech.fuzzywookie.pack.Packing;
import fr.polytech.fuzzywookie.reproduction.Reproduction;
import fr.polytech.fuzzywookie.voisinage.Voisinnage;
import fr.polyteck.fuzzywookie.utils.Parser;
import fr.polyteck.fuzzywookie.utils.QSort;

public class Project {
	private List<Print> listPrint;
	private List<Image> listImage;
	private int patternX = 0, patternY = 0;
	private int pricePattern;
	private String file;
	private Print initialPrint;

	public Project(String file) {
		this.file = file;
		listPrint = new ArrayList<Print>();
		listImage = new ArrayList<Image>();
	}

	public Project() {
		listPrint = new ArrayList<Print>();
		listImage = new ArrayList<Image>();
	}

	public void init() {
		Parser.parserFile(file, this);
		QSort qs = new QSort();
		qs.sort(listImage);
	}

	public List<Print> getListPrint() {
		return listPrint;
	}

	public void setListPrint(List<Print> listPrint) {
		this.listPrint = listPrint;
	}

	public List<Image> getListImage() {
		return listImage;
	}

	public void setListImage(List<Image> listImage) {
		this.listImage = listImage;
	}

	public int getPatternX() {
		return patternX;
	}

	public void setPatternX(int patternX) {
		this.patternX = patternX;
	}

	public int getPatternY() {
		return patternY;
	}

	public void setPatternY(int patternY) {
		this.patternY = patternY;
	}

	public int getPricePattern() {
		return pricePattern;
	}

	public void setPricePattern(int pricePattern) {
		this.pricePattern = pricePattern;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Print getInitialPrint() {
		return initialPrint;
	}

	public void setInitialPrint(Print initialPrint) {
		this.initialPrint = initialPrint;
	}

	public List<Print> getReproduction() {
		List<Print> Solutions = getBestSolution();
		Reproduction repro = new Reproduction();

		int i = 1;
		int y;
		while (Solutions.get(i) != null) {
			y = 0;
			while (i != y && Solutions.get(y) != null) {
				Solutions.add(repro.ReproductionPattern(Solutions.get(i),
						Solutions.get(y)));
				y++;
			}
			i++;
		}
		return Solutions;
	}

	public List<Print> getBestSolution() {
		List<Print> Solutions = this.getListPrint();
		int i = 0;
		List<Print> fitness = null;
		List<Print> SolutionsFinal = null;
		while (Solutions.get(i) != null) {
			fitness.add(Solutions.get(i));
			i++;
		}
		fitness = triFitnessDecroissant(fitness);
		i = 0;
		int max = (int) Math.round((fitness.size() * 20 / 100));
		while (fitness.get(i) != null && max < i) {
			SolutionsFinal.add(fitness.get(i));
			i++;
		}
		return SolutionsFinal;
	}

	private static List<Print> triFitnessDecroissant(List<Print> tableau) {
		int longueur = tableau.size();
		Print tampon;
		boolean permut;
		do {
			// hypothèse : le tableau est trié
			permut = false;
			for (int i = 0; i < longueur - 1; i++) {
				// Teste si 2 éléments successifs sont dans le bon ordre ou non
				if (tableau.get(i).simplexSolution() > tableau.get(i + 1).simplexSolution()) {
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

	public void launch() {
		init();
		initialPrint = new Print(this);
		Packing packing = new Packing();
		packing.packing(initialPrint);

		this.listPrint.addAll(Voisinnage.generate(initialPrint));

		int beginMs = (int) System.currentTimeMillis();
		while (System.currentTimeMillis() < beginMs + 7200000) {

			List<Print> reproduction = this.getReproduction();
			System.out.println(BestPrint(reproduction));

		}
	}
	
	public Print BestPrint(List<Print> tableau){
		Print p = null;
		p = tableau.get(0);
		int i=1;
		while(tableau.get(i)!=null){
			if(tableau.get(i).simplexSolution()<p.simplexSolution()){
				p = tableau.get(i);
			}
			i++;
		}
		return p;
	}

}
