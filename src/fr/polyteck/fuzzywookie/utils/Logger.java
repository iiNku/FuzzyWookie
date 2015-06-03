package fr.polyteck.fuzzywookie.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
		
		file = new File("logs/" + dataName + "-" + sdf.format(new Date()) + ".txt");
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
	
	public String recupLog()
	{
		String log = "";
		try {
			InputStream ips = new FileInputStream(file);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				log+=ligne+"\n";
			}
			br.close(); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return log;
	}

	public void logConfiguration() {

		log("Configuration : \n");
		log("\tNombre de voisins initial : " + Configuration.nbNeighbors + "\n");
		log("\tOccurence : " + Configuration.occurence + "\n");
		log("\n");
		
	}
}
