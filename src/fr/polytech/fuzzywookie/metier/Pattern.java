package fr.polytech.fuzzywookie.metier;

import java.util.ArrayList;
import java.util.List;

import fr.polytech.fuzzywookie.pack.Packing;

public class Pattern extends Rectangle implements Cloneable {

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
	
	public Pattern(Pattern p) {
		super(p.width, p.height);
		this.imageList = new ArrayList<Image>();
		this.imageList.addAll(p.getImageList());
		this.nbPrint = p.nbPrint;
		this.name = p.name;
		this.nbImage = p.nbImage;
		this.decoupX = p.decoupX;
		this.decoupY = p.decoupY;
		this.freeSpace = new ArrayList<Pattern>();
		this.freeSpace.addAll(p.getFreeSpace());
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
    
	public void setPattern(Pattern p)
	{
		this.decoupX = p.decoupX;
		this.decoupY = p.decoupY;
		this.freeSpace.addAll(p.getFreeSpace());
		this.height = p.getHeight();
		this.imageList.addAll(p.getImageList());
		this.name = p.name;
		this.nbImage = p.nbImage;
		this.nbPrint = p.nbPrint;
		this.width = p.getWidth();
		
	}
    
    public void addFreeSpace(Pattern pattern){
    	boolean fusionSuccess = false;
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
	    					//TODO : DEBUG HERE AVEC JOCE MAUVAIS DECOUP X Y
	    					fusion.setDecoupY(space.decoupY);
	    				}
	    			}	
	    		}
	    		if(fusion != null && fusion.getArea()>pattern.getArea() && fusion.getArea() > space.getArea())
	    		{
	    			freeSpace.remove(space);
	    			freeSpace.add(fusion);
	    			fusionSuccess = true;
	    			break;
	    		}	
    		}
    		if(!fusionSuccess)
    			freeSpace.add(pattern);
    	}
    }
    
    public ArrayList<Pattern> getFreeSpace(){
    	
    	return (ArrayList<Pattern>) freeSpace;
    }
   
    public boolean isValid(){
    	
    	List<Image> images = this.getImageList();
		for(int i = 0; i < images.size(); i++){
			for(int j = i + 1; j < images.size(); j++){
				if(images.get(i).intersect(images.get(j)) || !images.get(i).isInside(this)) 
					{
						return false;
					}
			}
		}
		return true;
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
    
    public int getNbImage(Image img)
    {
    	int counter = 0;
    	for(Image i : imageList)
    	{
    		if(img.getName() == i.getName())
    		{
    			counter++;
    		}
    	}
    	return counter;
    }
    
    public boolean addImageInFreeSpace(Image img)
    {
    	Packing packing = new Packing();
    	
    	for(Pattern space : this.getFreeSpace())
		{
			if(img.getArea() < space.getArea() && img.getWidth() < space.getWidth() && img.getHeight() < space.getHeight())
			{
				this.addImage(img.clone());
				List<Pattern> splitpatterns = packing.splitPattern(space, img);
				img.setX(space.getDecoupX());
				img.setY(space.getDecoupY());
				this.getFreeSpace().remove(space);
				this.addFreeSpace(splitpatterns.get(0));
				this.addFreeSpace(splitpatterns.get(1));
				return true;
			}
		}
    	return false;
    }
    
    @Override
    public String toString(){
    	
    	String toReturn = "";
    	
    	toReturn += "Nombre d'image : " + imageList.size() + "\n";
    	toReturn += "Nombre d'impression :" + "" + "\n";
    	for(Image image : imageList){
    		toReturn += image;
    	}
    	
    	return toReturn;
    	
    }

	public void remove(String name2) {

		for(int i = 0; i < imageList.size(); i++){
			if(imageList.get(i).getName().equals(name2)){
				Image image = imageList.get(i);
				Pattern freeSpace = new Pattern(image.getWidth(), image.getHeight());
				freeSpace.setDecoupX(image.getX());
				freeSpace.setDecoupY(image.getY());
				addFreeSpace(freeSpace);
				imageList.remove(i);
			}
		}
	}
	
	public Pattern clone(){
		
		Pattern pattern = null;
		
		try {
			pattern = (Pattern) super.clone();
			List<Pattern> freeSpace = new ArrayList<Pattern>();
			for(Pattern p : this.freeSpace){
				freeSpace.add(p.clone());
			}
			pattern.freeSpace = freeSpace;
			
			List<Image> imageList = new ArrayList<Image>();
			for(Image i : this.imageList){
				imageList.add(i.clone());
			}
			pattern.imageList = imageList;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pattern;
	}
}
