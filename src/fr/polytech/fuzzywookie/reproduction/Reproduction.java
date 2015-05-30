package fr.polytech.fuzzywookie.reproduction;

import java.util.ArrayList;
import java.util.List;

import fr.polytech.fuzzywookie.metier.*;
import fr.polytech.fuzzywookie.pack.PackingTest;

public class Reproduction {
    
    public Print ReproductionPattern(Print p1,Print p2){
    	
    	Print fils = new Print(p1.getProject());
        List<Pattern> patterns1 = p1.getListPattern();
        List<Pattern> patterns2 = p2.getListPattern();
        int maxPattern1 = patterns1.size();
        int maxPattern2 = patterns2.size();
        int minPattern = Math.min(maxPattern1, maxPattern2) - 1;
        int meanPattern = Math.round((maxPattern1+maxPattern2)/2);
        
        int typeRepro = (int) Math.round(Math.random()*2);
        int i = 0;
        int getpat1;
        int getpat2;
        if(typeRepro ==  1){
            while(fils.getListPattern().size()<meanPattern/2 && (i+1)<maxPattern1){
                fils.getListPattern().add(patterns1.get(i));
                i++;
            }
            while(fils.getListPattern().size()<meanPattern && (i+1)<maxPattern2){
                fils.getListPattern().add(patterns2.get(i));
                i++;
            }
        }else{
            int change =(int) Math.random()*minPattern;
            while(fils.getListPattern().size()<meanPattern){
                getpat1 = 0;
                while(getpat1<change && patterns1.get(i)!=null){
                    fils.getListPattern().add(patterns1.get(i));
                    i++;
                    getpat1++;
                }
                getpat2 = 0;
                while(getpat2<change && patterns2.get(i)!=null){
                    fils.getListPattern().add(patterns2.get(i));
                    i++;
                    getpat2++;
                }
            }
        }
        // test fils
        return fils;
    }
    
}
