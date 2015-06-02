package fr.polytech.fuzzywookie.main;

import fr.polytech.fuzzywookie.metier.Project;
import fr.polyteck.fuzzywookie.utils.Configuration;

public class Main {

	public static void main(String[] args) {

		Configuration.timesInMs = 200;

		Project project = new Project("test/data_20Lalpha.txt");
		project.launch();
	}

}
