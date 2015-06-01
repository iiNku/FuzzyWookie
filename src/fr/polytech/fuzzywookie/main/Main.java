package fr.polytech.fuzzywookie.main;

import fr.polytech.fuzzywookie.metier.Project;

public class Main {

	public static void main(String[] args) {

		Project project = new Project("test/data_50Salpha.txt");
		project.launch();

	}

}
