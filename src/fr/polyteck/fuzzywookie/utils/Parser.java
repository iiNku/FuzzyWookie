package fr.polyteck.fuzzywookie.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import fr.polytech.fuzzywookie.metier.Image;
import fr.polytech.fuzzywookie.metier.Print;
import fr.polytech.fuzzywookie.metier.Project;

public class Parser {
	
	public static void parserFile(String file, Project project)
	{
		String chaine="";
		InputStream ips;
		try {
			ips = new FileInputStream(file);
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			int counter = 0;
			while ((ligne=br.readLine())!=null){
				if(counter == 0)
				{
					project.setPatternX(Integer.valueOf(ligne.substring(ligne.indexOf("=")+1, ligne.length())));
				}
				else if(counter == 1)
				{
					project.setPatternY(Integer.valueOf(ligne.substring(ligne.indexOf("=")+1, ligne.length())));
				}
				else if(counter == 2)
				{
					project.setPricePattern(Integer.valueOf(ligne.substring(ligne.indexOf("=")+1, ligne.length())));
				}
				else
				{
					String[] imageValue = ligne.split("\t");
					Image img = new Image(Integer.valueOf(imageValue[0].substring(0,imageValue[0].indexOf("."))), 
							Integer.valueOf(imageValue[1].substring(0,imageValue[1].indexOf("."))),
							"Image"+(counter-2));
					project.getListImage().add(img);
					img.setNbItem(Integer.valueOf(imageValue[2]));
				}
				
				chaine+=ligne+"\n";
				counter++;
			}
			br.close(); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
	}

}
