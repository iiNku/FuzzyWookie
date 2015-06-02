package fr.polytech.fuzzywookie.metier;

public class Image extends Rectangle implements Cloneable {

	private int x, y;
	private String name;
	int nbItem;

	public Image(int width, int height, String name) {
		super(width, height);
		this.name = name;
	}

	public int getNbItem() {
		return nbItem;
	}

	public void setNbItem(int nbItem) {
		this.nbItem = nbItem;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean intersect(Image i) {

		int tw = this.width;
		int th = this.height;
		int rw = i.width;
		int rh = i.height;
		if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
			return false;
		}
		int tx = this.x;
		int ty = this.y;
		int rx = i.x;
		int ry = i.y;
		rw += rx;
		rh += ry;
		tw += tx;
		th += ty;

		return ((rw < rx || rw > tx) && (rh < ry || rh > ty)
				&& (tw < tx || tw > rx) && (th < ty || th > ry));
	}

	public boolean isInside(Pattern pattern) {
		
		return this.x < pattern.getWidth()
				&& this.x + this.width <= pattern.getWidth()
				&& this.y < pattern.getHeight()
				&& this.y + this.height <= pattern.getHeight();
	}
	
	public String print(){
    	
    	String toReturn = "";
    	for(int i = 0; i < this.width; i++){
    		toReturn += "*";
    	}
    	toReturn += "\n";
    	for(int j = 0; j < this.height; j++){
    		toReturn += "*";
    		for(int i = 1; i < this.width -1; i++){
    			toReturn += " ";
    		}
    		toReturn += "*\n";
    	}
    	for(int i = 0; i < this.width; i++){
    		toReturn += "*";
    	}
    	
    	return toReturn;
    }
	
	@Override
	public String toString(){
		
		String toReturn = "";
		
		toReturn += "Image " + this.name + "\n";
		toReturn += "X=" + this.x + " et Y=" + this.y + "\n";
		toReturn += "Width=" + this.width + " et Height=" + this.height + "\n";
		
		return toReturn;
	}
	
	public Image clone(){
		
		Image image = null;
		
		try {
			image = (Image) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}
}
