package fr.polytech.fuzzywookie.metier;

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

public class Project {
	private List<Print> listPrint;
	private List<Image> listImage;
	private int patternX = 0, patternY = 0;
	private int pricePattern;
	private String file;
	private Print initialPrint;
	private Logger logger;
	private int moyenneFitness;

	public Project(String file, boolean logging) {
		this.file = file;
		listPrint = new ArrayList<Print>();
		listImage = new ArrayList<Image>();

		String[] tmp = file.split("/");
		String name = tmp[1].substring(0, tmp[1].length() - 4);

		if (logging)
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
	
	public int getMoyenne(){
		
		return this.moyenneFitness;
	}

	public List<Print> launchReproduction() {

		List<Print> solutions = getBestSolution();
		Reproduction repro = new Reproduction();
		List<Print> toReturn = new ArrayList<Print>();

		toReturn.addAll(solutions);
		while (toReturn.size() < this.listPrint.size()) {
				int rng = (int) (Math.random() * this.listPrint.size());
				int rng2 = (int) (Math.random() * solutions.size());
				Print child = repro.ReproductionPattern(solutions.get(rng2),
						this.listPrint.get(rng));
				if (child.isValid())
					toReturn.add(child);
		}
		System.out.println("Reproduction terminé");
		return toReturn;
	}

	public List<Print> getBestSolution() {

		int max = (int) Math.round(this.getListPrint().size() * 20 / 100);
		List<Print> toReturn = new ArrayList<Print>();
		List<Print> listPrintClone = new ArrayList<Print>();
		for (Print p : this.listPrint) {
			listPrintClone.add(p.clone());
		}

		while (toReturn.size() < max) {

			int min = Integer.MAX_VALUE;
			int save = 0;
			Print minimum = null;
			for (int i = 0; i < listPrintClone.size(); i++) {
				if (listPrintClone.get(i).getFitness() < min
						&& listPrintClone.get(i).getFitness() > 0) {
					min = listPrintClone.get(i).getFitness();
					minimum = listPrintClone.get(i);
					save = i;
				}
			}
			if (minimum != null) {
				toReturn.add(minimum);
				listPrintClone.remove(save);
			}
		}
		return toReturn;
	}

	public void launch() {

		parseFileAndSortImages();

		if (logger != null)
			logger.logConfiguration();

		initialPrint = new Print(this);
		Packing packing = new Packing();
		packing.packing(initialPrint);

		generateNeighborhood();

		long beginMs = Calendar.getInstance().getTimeInMillis();

		while (Calendar.getInstance().getTimeInMillis() < beginMs
				+ Configuration.timesInMs) {

			calculSimplex();
			Print best = bestPrint(listPrint);
			if (logger != null)
				logger.log(best.toString() + "\n____________\n");
			System.out.println(best);
			listPrint = this.launchReproduction();

			System.out.println(bestPrint(listPrint));

		}

		if (logger != null)
			logger.close();
	}

	private void calculSimplex() {
		int i = 0;
		moyenneFitness = 0;
		List<Print> toRemove = new ArrayList<Print>();
		for (Print print : listPrint) {
			try {
				print.simplexSolution();
				moyenneFitness += print.getFitness();
				i++;
			} catch (ArithmeticException e) 
			{
				e.printStackTrace();
				toRemove.add(print);
			}

		}
		System.out.println("sortiesimplex");
		
		for(Print print : toRemove)
			listPrint.remove(print);
		
		moyenneFitness = moyenneFitness/listPrint.size();
	}

	public Image getImageByName(String name) {
		for (Image i : listImage) {
			if (i.getName().equals(name))
				return i.clone();
		}
		return null;
	}

	private void generateNeighborhood() {

		Voisinnage voisinnage = new Voisinnage();
		this.listPrint.addAll(voisinnage.generate(initialPrint));
	}

	public Print bestPrint(List<Print> tableau) {

		Print best = tableau.get(0);
		for (Print print : tableau) {
			if (print.getFitness() < best.getFitness() && print.getFitness()>0) {
				best = print;
			}
		}
		System.out.println("Best solution : " + best + "Moyenne : " + moyenneFitness);
		return best;
	}

}
