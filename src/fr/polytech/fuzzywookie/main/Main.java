package fr.polytech.fuzzywookie.main;

import fr.polytech.fuzzywookie.metier.Image;
import fr.polytech.fuzzywookie.metier.Project;

public class Main {

	public static void main(String[] args) {

		Project project = new Project("test/data_20Lalpha.txt");
		project.launch();
	}

}
