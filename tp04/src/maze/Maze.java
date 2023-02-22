package maze;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import graph.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;

public class Maze implements Graph{
	private int height;// = boxes[0].length;
	private int width;// = boxes.length;
	private final String defaultPath = "tp04/data/default.maze";
	public String getDefaultPath(){return defaultPath;}
	private final String defaultDirectory = "./tp04/data";
	public String getDefaultDirectory(){return defaultDirectory;}
	public int getHeight(){return height;}
	public int getWidth(){return width;}
	private MazeBox[][] boxes;
	private ArrivalBox arrivalBox;
	private DepartureBox departureBox;

	public MazeBox[][] getBoxes() {return boxes;}
	public MazeBox getBoxes(int i, int j) {return boxes[i][j];}

	public DepartureBox getDepartureBox() {return departureBox;}
	public ArrivalBox getArrivalBox() {return arrivalBox;}


	//this part is for the GUI part
	private Hexagon[][] hexagonList;
    public Hexagon[][] getHexagonList(){return hexagonList;}
	public Hexagon getHexagon(int i, int j){return hexagonList[i][j];}
    private int d;
	private int border;
	private int origin;
	public int getD(){return d;}
	public int getBorder(){return border;}
	public int getOrigin(){return origin;}
	//end of GUI part

	private boolean isInMaze(int i, int j) {
		if (i>-1 && i < width && j > -1 && j < height) {return true;}
		return false;
	}
		
	public ArrayList<Vertex> getAllVertexes(){
		ArrayList<Vertex> allVertexes = new ArrayList<Vertex>();
		for (MazeBox[] box1 : boxes) {
			for (MazeBox box : box1) {
				if(box.isWalkable()) {allVertexes.add(box);}
			}
		}
		return allVertexes;
	}
	
	public ArrayList<Vertex> getSuccessors(Vertex vertex) {
		MazeBox box = (MazeBox) vertex;
		int x = box.getX();
		int y = box.getY();
		ArrayList<Vertex> successors = new ArrayList<Vertex>();
		int e = 0;
		if (y%2 == 0) {e = -1;}
		if (y%2 == 1) {e = 1;}
		if (isInMaze(x-1,y)) {successors.add(boxes[x-1][y]);}
		if (isInMaze(x+1,y)) {successors.add(boxes[x+1][y]);}
		if (isInMaze(x,y+1)) {successors.add(boxes[x][y+1]);}
		if (isInMaze(x,y-1)) {successors.add(boxes[x][y-1]);}
		if (isInMaze(x+e,y+1)) {successors.add(boxes[x+e][y+1]);}
		if (isInMaze(x+e,y-1)) {successors.add(boxes[x+e][y-1]);}
		ArrayList<Vertex> successors2 = new ArrayList<Vertex>();
		for (Vertex vertex2 : successors) {if (((MazeBox) vertex2).isWalkable()) {successors2.add(vertex2);}}
		return successors2;
	}
	
	
	public int getDistance(Vertex src, Vertex dst) {
		//0 si c'est les memes, 1 si ils sont voisins, -1 sinon
		if (src == dst) {return 0;}
		ArrayList<Vertex> successors = getSuccessors(src);
		if (successors.contains(dst)) {return 1;}
		return -1;		
	}
	
	public final void initFromTextFile(String fileName) throws MazeReadingException, IOException, NoSuchFileException, MazeSizeException {
		
			ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(fileName));
			height = lines.size();
			width = lines.get(0).length();
			if(height*width < 2) throw new MazeSizeException();
			setGUIValues();
			boxes = new MazeBox[width][height];
			boolean hasDeparture = false;
			boolean hasArrival = false;
			for (int j=0; j<height; j++) {
				if (lines.get(j).length() != width) throw new MazeReadingException(fileName,j+1,"Le nombre de cases par ligne n'est pas constant.");
				for (int i=0; i<width; i++) {
					//fill boxes
					switch(""+lines.get(j).charAt(i)){
						case "E": 
							boxes[i][j] = new EmptyBox(this,i,j);
							break;
						case "W": 
							boxes[i][j] = new WallBox(this,i,j);
							break;
						case "A": 
							if (! hasArrival) {boxes[i][j] = new ArrivalBox(this,i,j); hasArrival = ! hasArrival; this.arrivalBox = (ArrivalBox) boxes[i][j];}
							else {throw new MazeReadingException(fileName, j+1, "Il y a deux cases d'arrivée.");}
							break;
						case "D": 
							if (! hasDeparture) {boxes[i][j] = new DepartureBox(this,i,j); hasDeparture = ! hasDeparture; this.departureBox = (DepartureBox) boxes[i][j];}
							else {throw new MazeReadingException(fileName, j+1, "Il y a deux cases de départ.");}
							break;
						default : 
							throw new MazeReadingException(fileName, j+1, "Un caractère n'est pas valide.");
					}		
				}
			}
			//this line creates the list of polygons to save to then detect click
			fillHexagonsList();
			
	}
	
	public final void saveToTextFile(String fileName) throws MazeReadingException,IOException{
			ArrayList<String> lines = new ArrayList<String>();
			for (int j=0;j<height;j++) {
				String line = new String();
				for (int i=0;i<width;i++) {
					line = line + this.boxes[i][j].getLabel();
				}
				lines.add(line);
			}
			Files.write(Paths.get(fileName),lines);
		}
	
	public final void updateBox(int i, int j, String newType){
		switch(newType){
			case "E" :
				boxes[i][j] = new EmptyBox(this, i, j,boxes[i][j].getLabel());
				break;
			case "A" :
				updateBox(arrivalBox.getX(),arrivalBox.getY(), arrivalBox.getFormerLabel());	
				boxes[i][j] = new ArrivalBox(this, i, j ,boxes[i][j].getLabel());
				arrivalBox = (ArrivalBox) boxes[i][j];
				break;
			case "D" :
				updateBox(departureBox.getX(),departureBox.getY(), departureBox.getFormerLabel());
				boxes[i][j] = new DepartureBox(this, i, j,boxes[i][j].getLabel());
				departureBox = (DepartureBox) boxes[i][j];
				break;
			case "W" :
				boxes[i][j] = new WallBox(this, i, j,boxes[i][j].getLabel());
				break;
		}
		fillHexagonsList();
		stateChanged();
	}
	//handle gui (the maze is the model)
	private String currentDragChange = "N";
	private boolean edited = false;
	public boolean isEdited() {
		return edited;
	}
	public void setEdited(boolean edited) {
		this.edited = edited;
	}
	public String getCurrentDragChange() {
		return currentDragChange;
	}
	public void setCurrentDragChange(String currentDragChange) {
		this.currentDragChange = currentDragChange;
	}
	public void fillHexagonsList(){
		hexagonList = new Hexagon[width][height];
		for(int i = 0; i < this.getWidth(); i++) {
			for(int j = 0; j < this.getHeight(); j++) {
				switch((this.getBoxes()[i][j]).getLabel()) {
					case "W": hexagonList[i][j] = new Hexagon(origin + (d+d/20)*((j%2)+2*i), origin + (d-d/10)*(2*j), d, getBoxes()[i][j].getColor()); break;
					case "E": hexagonList[i][j] = new Hexagon(origin + (d+d/20)*((j%2)+2*i), origin + (d-d/10)*(2*j), d, getBoxes()[i][j].getColor()); break;
					case "A": hexagonList[i][j] = new Hexagon(origin + (d+d/20)*((j%2)+2*i), origin + (d-d/10)*(2*j), d, getBoxes()[i][j].getColor()); break;
					case "D": hexagonList[i][j] = new Hexagon(origin + (d+d/20)*((j%2)+2*i), origin + (d-d/10)*(2*j), d, getBoxes()[i][j].getColor()); break;
				}
			}
		}
	}

	public void paintHexagons(Graphics2D g) {
		for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
				hexagonList[i][j].paint(g);
            }
        }
	}
	private final ArrayList<ChangeListener> listeners = new ArrayList<ChangeListener>() ;

	public void addObserver(ChangeListener listener) {
   	listeners.add(listener) ;
}
	public void stateChanged() {
		edited = true;
		ChangeEvent evt = new ChangeEvent(this) ;
	
		for (ChangeListener listener : listeners) {
	   	listener.stateChanged(evt);
		}
 	}

	public Maze(int height, int width) {
		this.height = height;
		this.width = width;
		setGUIValues();
		boxes = new MazeBox[width][height];
		for (int i=0; i<width; i++) {
			for (int j=0; j<height; j++) {
				boxes[i][j] = new EmptyBox(this,i,j);
			}
		}
		boxes[0][0] = new DepartureBox(this,0,0);
		boxes[width-1][height-1] = new ArrivalBox(this,width-1,height-1);
		this.departureBox = (DepartureBox) boxes[0][0];
		arrivalBox = (ArrivalBox) boxes[width-1][height-1];
		//this line creates the list of polygons to save to then detect click
		fillHexagonsList();
	}
	public Maze(){}
	public void setGUIValues(){
		//int n = 500;
		//int m = 500;
		//double r2 = Math.min(n / (2 * width * Math.cos(Math.PI / 6)), m / ((height + 0.5) * Math.sin(Math.PI / 6)));
		//d = (int) Math.round(r2);
		/*if(Math.max(width,height) >= 7){
			d = 300/Math.max(width,height);
		}
		else{d = 50;}*/
		

		d = 200/Math.max(width,height);
		border = 2*d;
    	origin = border + d/5;
	}

	
	
	
}













