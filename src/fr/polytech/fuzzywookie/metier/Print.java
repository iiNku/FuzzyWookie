package fr.polytech.fuzzywookie.metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.polytech.fuzzywookie.pack.Packing;
import fr.polytech.fuzzywookie.simplex.Simplex;
import fr.polytech.fuzzywookie.simplex.StdOut;
import fr.polyteck.fuzzywookie.utils.Parser;
import fr.polyteck.fuzzywookie.utils.QSort;

public class Print implements Cloneable {
	private List<Pattern> listPattern;
	private Project project;
	
	private int fitness;
	
	public Print(Project p)
	{
		listPattern = new ArrayList<Pattern>();
		project = p;
	}
	
	public Print(Print initialPrint) {
		project = new Project();
		project = initialPrint.getProject();
		listPattern = new ArrayList<Pattern>();
		listPattern.addAll(initialPrint.getListPattern());
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Pattern createPattern()
	{
		if(!(project.getPatternX() == 0) && !(project.getPatternY() ==0))
		{
			Pattern pattern = new Pattern(project.getPatternX(), project.getPatternY(), project.getListImage().size());
			listPattern.add(pattern);
			return pattern;
		}
		return null;	
	}

	public List<Pattern> getListPattern() {
		return listPattern;
	}

	public void setListPattern(List<Pattern> listPattern) {
		this.listPattern = listPattern;
	}
	
	public void simplexSolution()
	{
		int value = 0;
		int i = 0;
		double d = listPattern.size();
		double[][] matrice = new double[project.getListImage().size()][listPattern.size()];
		double[] contrainte = new double[project.getListImage().size()];
		double[] minimisation = new double[listPattern.size()];
		for(Pattern p : listPattern)
		{
			double[] vecteur = p.getVecteur();
			int j = 0;
			for(double vecteurValue : vecteur)
			{
				matrice[j][i] = vecteurValue;
				j++;
			}
			i++;
		}
		for(int indiceContrainte =0 ; indiceContrainte<contrainte.length; indiceContrainte++)
		{
			contrainte[indiceContrainte] = -project.getListImage().get(indiceContrainte).getNbItem();
		}
		for(int indiceMin = 0 ; indiceMin<minimisation.length; indiceMin++)
		{
			minimisation[indiceMin] = 1;
		}
		
		Simplex fitness = new Simplex(matrice, contrainte, minimisation);
		value = (int) fitness.value();
		double[] x = fitness.primal();
        for (int k = 0; k < x.length; k++)
        {
        	listPattern.get(k).setNbPrint(Math.abs((int)Math.round(x[k])+1));
        }
        
		this.fitness = Math.abs(value) + listPattern.size()*20;
	}
	
	public boolean isValid(){
		
		Map<String, Boolean> placed = new HashMap<String, Boolean>();
		for(Pattern pattern : listPattern){
			if(! pattern.isValid()) return false;
			for(Image image : pattern.getImageList()){
				placed.put(image.getName(), true);
			}
		}
		return (placed.size() == project.getListImage().size());
	}
	
	public int getNbImage(Image img)
	{
		int counterImg = 0;
		for(Pattern p : listPattern)
		{
			counterImg += p.getNbImage(img);
		}
		return counterImg;
	}
	
	public void setPrint(Print p)
	{
		this.listPattern.addAll(p.listPattern);
		this.project = p.project;
	}
	
	@Override
	public String toString(){
		
		String toReturn = "";
		toReturn += "Print : \n";
		
		toReturn += "\tNombre pattern : " + listPattern.size() + "\n";
		toReturn += "\tFitness = " + this.fitness + "\n";
		int i = 0;
		for(Pattern pattern : listPattern){
			toReturn += "\tPattern " + i + " : \n" + pattern;
			i++;
		}
		return toReturn;
	}
	
	public int getFitness(){
		
		return fitness;
	}
	
	public Print clone(){
		
		Print print = null;
		
		try {
			print = (Print) super.clone();
			List<Pattern> pattern = new ArrayList<Pattern>();
			for(Pattern p : this.listPattern)
				pattern.add(p.clone());
			print.listPattern = pattern;
			print.project = this.project;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return print;
	}

}
