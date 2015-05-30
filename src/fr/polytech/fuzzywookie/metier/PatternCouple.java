package fr.polytech.fuzzywookie.metier;

public class PatternCouple {

	private Pattern p1;
	private Pattern p2;
	
	public PatternCouple(Pattern p1, Pattern p2){
		
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public Pattern getPattern1(){
		
		return p1;
	}
	
	public Pattern getPattern2(){
		
		return p2;
	}
	
	public Pattern getLarger(){
		
		return p1.getArea() > p2.getArea() ? p1 : p2;
	}
}
