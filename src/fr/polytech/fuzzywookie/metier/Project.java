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
import fr.polyteck.fuzzywookie.utils.QSortSimplex;
import fr.polyteck.fuzzywookie.utils.TriBulle;
import fr.polyteck.fuzzywookie.utils.TriFusion;

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

	public List<Print> launchReproduction() {

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
			System.out.println("nb d'efants" + toReturn.size());
		}
		System.out.println("Reproduction terminé");
		return toReturn;
	}

	public List<Print> getBestSolution() {
		
		int max = (int) Math.round(this.getListPrint().size() * 20 / 100);
		List<Print> toReturn = new ArrayList<Print>();
		List<Print> listPrintClone = new ArrayList<Print>();
		for(Print p : this.listPrint){
			listPrintClone.add(p.clone());
		}
		
		
		while(toReturn.size() < max){
			
			int min = Integer.MAX_VALUE;
			int save = 0;
			Print minimum = null;
			for(int i = 0; i < listPrintClone.size(); i++){
				if(listPrintClone.get(i).getFitness() < min && listPrintClone.get(i).getFitness()>0){
					System.out.println("find minimum");
					min = listPrintClone.get(i).getFitness();
					minimum = listPrintClone.get(i);
					save = i;
				}
			}
			if(minimum != null){
				toReturn.add(minimum);
				listPrintClone.remove(save);
			}
		}
		
		
		System.out.println("Minimum : " + bestPrint(toReturn).getFitness());
		
		return toReturn;
	}

	public void launch() {
		
		parseFileAndSortImages();
		
		initialPrint = new Print(this);
		Packing packing = new Packing();
		packing.packing(initialPrint);
		
		generateNeighborhood();

		long beginMs = Calendar.getInstance().getTimeInMillis();
		
		while (Calendar.getInstance().getTimeInMillis() < beginMs + 7200000) {
			
			System.out.println("Boucle");
			calculSimplex();
			listPrint = this.launchReproduction();
		}
	}

	private void calculSimplex() {
		System.out.println("calcule simplex");
		int i = 0;
		for(Print print : listPrint){
			print.simplexSolution();
			System.out.println("Simplex : " + print.getFitness());
			i++;
		}
			
		System.out.println("fin simplex");
	}

	private void generateNeighborhood() {
		Voisinnage voisinnage = new Voisinnage();

		this.listPrint.addAll(voisinnage.generate(initialPrint));

		System.out.println("Voisin cree");
	}

	public Print bestPrint(List<Print> tableau) {

		Print best = tableau.get(0);
		for (Print print : tableau) {
			if(print == null)
				System.out.println("lol");
			if(best == null)
				System.out.println("lel");
			if (print.getFitness() < best.getFitness()) {
				best = print;
			}
		}
		return best;
	}

}
