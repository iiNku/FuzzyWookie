package fr.polytech.fuzzywookie.metier;

import java.util.ArrayList;
import java.util.List;

public class Pattern extends Rectangle {

	private List<Image> imageList;
	private int nbPrint;
	private String name;
	private int decoupX, decoupY;
	private int nbImage;
	private List<Pattern> freeSpace;

	public Pattern(int width, int height) {
		super(width, height);
		imageList = new ArrayList<Image>();
		freeSpace = new ArrayList<Pattern>();
		this.decoupX = 0;
		this.decoupY = 0;
		nbImage = 0;
	}

	public Pattern(int width, int height, int nbImage) {
		super(width, height);
		imageList = new ArrayList<Image>();
		freeSpace = new ArrayList<Pattern>();
		this.decoupX = 0;
		this.decoupY = 0;
		this.nbImage = nbImage;
	}

	public double[] getVecteur() {
		if (!(nbImage == 0)) {
			double[] vector = new double[nbImage];
			for(int i=0; i<vector.length;i++)
			{
				vector[i]=0;
			}
			for (Image i : imageList) {
				vector[Integer.valueOf(i.getName())] = vector[Integer.valueOf(i.getName())] +1;
			}
			return vector;
		} else
			return null;
	}

	public int getDecoupX() {
		return decoupX;
	}

	public void setDecoupX(int decoupX) {
		this.decoupX = decoupX;
	}

	public int getDecoupY() {
		return decoupY;
	}

	public void setDecoupY(int decoupY) {
		this.decoupY = decoupY;
	}

	public List<Image> getImageList() {
		return imageList;
	}

	public void setImageList(List<Image> imageList) {
		this.imageList = imageList;
	}

	public int getNbPrint() {
		return nbPrint;
	}

	public void setNbPrint(int nbPrint) {
		this.nbPrint = nbPrint;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addImage(Image image) {

		imageList.add(image);
	}
	
	public int getHigherSide(){
		
		return width > height ? width : height;
	}
    
    public boolean patternTest(Pattern p){
        p.getImageList();
        int i=0;
        int total=0;
        while(p.getImageList().get(i)!=null){
            Image img = p.getImageList().get(i);
            int width = img.getWidth();
            int height = img.getHeight();
            if(img.getX()+img.getWidth()>p.getWidth()){
                return false;
            }
            if(img.getY()+img.getHeight()>p.getHeight()){
                return false;
            }
            total += width*height;
        }
        if(p.getArea()<=total){
            return true;
        }else{
            return false;
        }
    }
    
    public void addFreeSpace(Pattern pattern){
    	if(freeSpace.isEmpty())
    		freeSpace.add(pattern);
    	else
    	{ 		
    		for(Pattern space : freeSpace)
    		{
    			Pattern fusion = null;
	    		if(space.getDecoupX() <= pattern.getDecoupX() &&
	    				pattern.getDecoupX() <= space.getDecoupX() + space.getWidth())
	    		{
	    			if(space.getDecoupY()<= pattern.getDecoupY() &&
	    					pattern.getDecoupY() <= space.getDecoupY() + space.getHeight())
	    			{
	    				if(space.getDecoupX() + space.getWidth() - pattern.getDecoupX() > space.getDecoupY()+ space.getHeight() - pattern.decoupY)
	    				{
	    					fusion = new Pattern(space.getDecoupX() + space.getWidth() - pattern.getDecoupX(), space.getHeight() + pattern.getHeight());
	    					fusion.setDecoupX(pattern.decoupX);
	    					fusion.setDecoupY(space.decoupY);
	    				}
	    				else
	    				{
		    				fusion = new Pattern(pattern.width+space.width, space.height + space.decoupY - pattern.getDecoupY());
		    				fusion.setDecoupX(space.decoupX);
		    				fusion.setDecoupY(pattern.decoupY);
	    				}
	    			}
	    			else if(space.getDecoupY() <= pattern.getDecoupY() + pattern.getHeight() &&
	    					pattern.getDecoupY() + pattern.getHeight() <= space.getDecoupY() + space.getHeight())
	    			{
	    				if(space.getDecoupX() + space.getWidth() - pattern.getDecoupX() > pattern.getDecoupY()+ pattern.getHeight() - space.decoupY)
	    				{
	    					fusion = new Pattern(space.getDecoupX() + space.getWidth() - pattern.decoupX, space.getHeight() + pattern.getHeight());
	    					fusion.setDecoupX(pattern.decoupX);
	    					fusion.setDecoupY(pattern.decoupY);
	    				}
	    				else
	    				{
	    					fusion = new Pattern(space.getWidth() + pattern.getWidth(), pattern.getDecoupY() + pattern.getHeight() - space.getDecoupY());
	    					fusion.setDecoupX(space.decoupX);
	    					fusion.setDecoupY(space.decoupY);
	    				}
	    			}
	    		}
	    		else if(space.getDecoupX() <= pattern.getDecoupX() + pattern.width &&
	    				pattern.getDecoupX()+pattern.width <= space.getDecoupX() + pattern.width)
	    		{
	    			if(space.getDecoupY()<= pattern.getDecoupY() &&
	    					pattern.getDecoupY() <= space.getDecoupY() + space.getHeight())
	    			{
	    				if(pattern.getDecoupX() + pattern.width - space.getDecoupX() > space.decoupY + space.height - pattern.decoupY)
	    				{
	    					fusion = new Pattern( pattern.decoupX +pattern.width - space.decoupX , space.height + pattern.height);
	    					fusion.setDecoupX(space.decoupX);
	    					fusion.setDecoupY(space.decoupY);
	    				}
	    				else
	    				{
	    					fusion = new Pattern(pattern.width + space.width, space.decoupY + space.height - pattern.decoupY);
	    					fusion.setDecoupX(pattern.decoupX);
	    					fusion.setDecoupY(pattern.decoupY);
	    				}
	    			}
	    			else if(space.getDecoupY() <= pattern.getDecoupY() + pattern.getHeight() &&
	    					pattern.getDecoupY() + pattern.getHeight() <= space.getDecoupY() + space.getHeight())
	    			{
	    				if(pattern.getDecoupX() + pattern.width - space.getDecoupX() > pattern.getDecoupY() + pattern.getHeight() - space.getDecoupY())
	    				{
	    					fusion = new Pattern(pattern.getDecoupX() + pattern.width - space.getDecoupX(), pattern.getHeight() + space.getHeight());
	    					fusion.setDecoupX(space.decoupX);
	    					fusion.setDecoupY(pattern.decoupY);
	    				}
	    				else
	    				{
	    					fusion = new Pattern(pattern.width + space.width, pattern.getDecoupY()+pattern.height - space.decoupY);
	    					fusion.setDecoupX(pattern.decoupX);
	    					fusion.setDecoupY(pattern.decoupY);
	    				}
	    			}	
	    		}
	    		if(fusion != null && fusion.getArea()>pattern.getArea() && fusion.getArea() > space.getArea())
	    		{
	    			freeSpace.remove(space);
	    			freeSpace.add(pattern);
	    			break;
	    		}	
    		}
    	}
    }
    
    public ArrayList<Pattern> getFreeSpace(){
    	
    	return (ArrayList<Pattern>) freeSpace;
    }
   
    public boolean isValid(){
    	
    	List<Image> images = this.getImageList();
		for(int i = 0; i < images.size(); i++){
			for(int j = i + 1; j < images.size(); j++){
				if(images.get(i).intersect(images.get(j)) || !images.get(i).isInside(this)) return false;
			}
		}
		return true;
    }
    
    public String print(){
    	
    	String toReturn = "";
    	for(int i = 0; i < this.width; i++){
    		toReturn += "_";
    	}
    	toReturn += "\n";
    	for(int j = 0; j < this.height; j++){
    		toReturn += "|";
    		for(int i = 1; i < this.width -1; i++){
    			toReturn += " ";
    		}
    		toReturn += "|\n";
    	}
    	for(int i = 0; i < this.width; i++){
    		toReturn += "_";
    	}
    	
    	return toReturn;
    }
 
    public Pattern changeImage(Pattern p, Image imgold,Image imgnew){
    	Pattern newp = p;
    	newp.getImageList().remove(imgold);
    	newp.addImage(imgnew);
    	if(newp.isValid()){
    		return newp;
    	}
		return p;
    }
}
