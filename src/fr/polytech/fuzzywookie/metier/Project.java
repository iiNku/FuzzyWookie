package fr.polytech.fuzzywookie.metier;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.polytech.fuzzywookie.pack.Packing;
import fr.polytech.fuzzywookie.reproduction.Reproduction;
import fr.polytech.fuzzywookie.voisinage.Voisinnage;
import fr.polyteck.fuzzywookie.utils.Configuration;
import fr.polyteck.fuzzywookie.utils.Logger;
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
	private Logger logger;

	public Project(String file) {
		this.file = file;
		listPrint = new ArrayList<Print>();
		listImage = new ArrayList<Image>();
		
		String[] tmp = file.split("/");
		String name = tmp[1].substring(0, tmp[1].length() - 4);
		
		logger = new Logger(name);
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
		}
		System.out.println("Reproduction terminé");
		return toReturn;
	}

	public List<Print> getBestSolution() {
		
		int max = (int) Math.round(this.getListPrint().size() * 20 / 100);
		List<Print> toReturn = new ArrayList<Print>();
		
		while(toReturn.size() < max){
			
			int min = Integer.MAX_VALUE;
			Print minimum = null;
			for(int i = 0; i < listPrint.size(); i++){
				if(listPrint.get(i).getFitness() < min){
					System.out.println("find minimum");
					min = listPrint.get(i).getFitness();
					minimum = listPrint.get(i);
					listPrint.remove(i);
				}
			}
			toReturn.add(minimum);
		}
		return toReturn;
	}

	public void launch() {
		
		parseFileAndSortImages();
		
		logger.log("Configuration : \n");
		logger.log("\tNombre de voisins initial : " + Configuration.nbNeighbors + "\n");
		logger.log("\tTemps d'éxécution : " + Configuration.timesInMs + "\n");
		logger.log("\n");
		
		initialPrint = new Print(this);
		Packing packing = new Packing();
		packing.packing(initialPrint);
		
		generateNeighborhood();

		long beginMs = Calendar.getInstance().getTimeInMillis();
		
		while (Calendar.getInstance().getTimeInMillis() < beginMs + Configuration.timesInMs) {
			
			calculSimplex();
			Print best = bestPrint(listPrint);
			logger.log(best.toString() + "\n____________\n");
			System.out.println(best);
			listPrint = this.launchReproduction();

			System.out.println(bestPrint(listPrint));

		}
		
		logger.close();
	}

	private void calculSimplex() {
		int i = 0;
		for(Print print : listPrint){
			print.simplexSolution();
			i++;
		}
	}

	private void generateNeighborhood() {
		
		Voisinnage voisinnage = new Voisinnage();
		this.listPrint.addAll(voisinnage.generate(initialPrint));
	}

	public Print bestPrint(List<Print> tableau) {

		Print best = tableau.get(0);
		for (Print print : tableau) {
			if (print.getFitness() < best.getFitness()) {
				best = print;
			}
		}
		System.out.println("Best solution : " + best);
		return best;
	}

}
