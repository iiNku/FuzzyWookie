package fr.polytech.fuzzywookie.main;

import fr.polytech.fuzzywookie.metier.Project;

public class Main {

	public static void main(String[] args) {

		Project project = new Project("/Users/iiNku/Documents/workspace/FuzzyWookie/test/data_50Lalpha.txt");
		project.launch();

	}

}
