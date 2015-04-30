package fr.polyteck.fuzzywookie.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import fr.polytech.fuzzywookie.metier.Image;
import fr.polytech.fuzzywookie.metier.Print;

public class Parser {
	
	public static void parserFile(String file, Print print)
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
					print.setPatternX(Integer.valueOf(ligne.substring(ligne.indexOf("=")+1, ligne.length())));
				}
				else if(counter == 1)
				{
					print.setPatternY(Integer.valueOf(ligne.substring(ligne.indexOf("=")+1, ligne.length())));
				}
				else if(counter == 2)
				{
					print.setPricePattern(Integer.valueOf(ligne.substring(ligne.indexOf("=")+1, ligne.length())));
				}
				else
				{
					String[] imageValue = ligne.split(" ");
					Image img = new Image(Integer.valueOf(imageValue[0]), Integer.valueOf(imageValue[1]), "Image"+(counter-2));
					print.getListImage().add(img);
					img.setNbItem(Integer.valueOf(imageValue[2]));
				}
				System.out.println(ligne);
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
