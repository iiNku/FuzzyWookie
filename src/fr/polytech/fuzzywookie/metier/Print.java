package fr.polytech.fuzzywookie.metier;

import java.util.ArrayList;
import java.util.List;

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
			Pattern pattern = new Pattern(project.getPatternX(), project.getPatternY());
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

}
