package fr.polyteck.fuzzywookie.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Logger {

	private SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy-HH'h'mm'm'ss's'");

	private File file;
	private FileWriter fw;
	private BufferedWriter output;

	public Logger(String dataName) {

		File dir = new File("logs");
		if(!dir.exists())
			dir.mkdir();
		
		File file = new File("logs/" + dataName + "-" + sdf.format(new Date()) + ".txt");
		try {
			file.createNewFile();
			fw = new FileWriter(file, false);
			output = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void log(String message) {
		try {
			output.write(message);
		} catch (IOException ioe) {
			System.out.print("Erreur : ");
			ioe.printStackTrace();
		}
	}

	public void close() {

		try {
			output.flush();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void logConfiguration() {

		log("Configuration : \n");
		log("\tNombre de voisins initial : " + Configuration.nbNeighbors + "\n");
		log("\tTemps d'éxécution : " + Configuration.timesInMs + "\n");
		log("\n");
		
	}
}
