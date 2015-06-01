package fr.polytech.fuzzywookie.metier;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
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

	public void parseFileAndSortImages() {
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

		List<Print> solutions = getBestSolution();
		Reproduction repro = new Reproduction();
		List<Print> toReturn = new ArrayList<Print>();

		toReturn.addAll(solutions);
		while (toReturn.size() < this.listPrint.size()) {

			for (Print print : solutions) {
				int rng = (int) Math.random() * solutions.size();
				Print child = repro.ReproductionPattern(print,solutions.get(rng));
				if (child.isValid())
					toReturn.add(child);
			}
		}
		return toReturn;
	}

	public List<Print> getBestSolution() {
		
		int max = (int) Math.round(this.getListPrint().size() * 20 / 100);
		
		List<Print> toReturn = new ArrayList<Print>();
		List<Print> sorted = QSort.triFitnessDecroissant(this.getListPrint());
		
		for(int i =0; i < max; i++){
			if(sorted.get(i) != null) toReturn.add(sorted.get(i));
		}
		
		return toReturn;
	}

	public void launch() {
		
		parseFileAndSortImages();
		
		initialPrint = new Print(this);
		Packing packing = new Packing();
		packing.packing(initialPrint);
		
		Voisinnage voisinnage = new Voisinnage();

		this.listPrint.addAll(voisinnage.generate(initialPrint));

		System.out.println("Voisin cree");

		long beginMs = Calendar.getInstance().getTimeInMillis();
		while (Calendar.getInstance().getTimeInMillis() < beginMs + 7200000) {
			System.out.println("Boucle");
			List<Print> reproduction = this.getReproduction();
			listPrint = reproduction;
			System.out.println(bestPrint(reproduction));

		}
	}

	public Print bestPrint(List<Print> tableau) {

		Print best = tableau.get(0);
		for (Print print : tableau) {
			if (print.simplexSolution() < best.simplexSolution()) {
				best = print;
			}
		}
		return best;
	}

}
