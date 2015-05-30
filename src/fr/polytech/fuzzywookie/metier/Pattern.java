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
    	
    	freeSpace.add(pattern);
    }
    
    public ArrayList<Pattern> getFreeSpace(){
    	
    	return (ArrayList<Pattern>) freeSpace;
    }
    
    public Pattern changeImage(Pattern p, Image imgold,Image imgnew){
    	Pattern newp = p;
    	newp.getImageList().remove(imgold);
    	newp.addImage(imgnew);
    	if(patternTest(newp)){
    		return newp;
    	}
		return p;
    }

}
