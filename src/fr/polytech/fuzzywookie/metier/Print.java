package fr.polytech.fuzzywookie.metier;

import java.util.ArrayList;
import java.util.List;

import fr.polytech.fuzzywookie.pack.Packing;
import fr.polytech.fuzzywookie.simplex.Simplex;
import fr.polyteck.fuzzywookie.utils.Parser;
import fr.polyteck.fuzzywookie.utils.QSort;

public class Print {
	private List<Pattern> listPattern;
	private Project project;
	
	public Print(Project p)
	{
		listPattern = new ArrayList<Pattern>();
		project = p;
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
	
	public int simplexSolution()
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
		
		return value;
	}
	
	public boolean isValid(){
		
		ArrayList<Image> placed = new ArrayList<Image>();
		for(Pattern pattern : listPattern){
			for(Image image : pattern.getImageList()){
				if(! placed.contains(image)) placed.add(image);
			}
		}
		
		return (placed.size() == project.getListImage().size()) && Packing.isValid(listPattern);
	}

}
