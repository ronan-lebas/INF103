package maze;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import graph.*;

public class Maze implements Graph{
	private int height;// = boxes[0].length;
	private int width;// = boxes.length;
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
    private final int d = 50;
    private final int border = 2*d;
    private final int origin = border + d/5;
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
	
	public final void initFromTextFile(String fileName) throws MazeReadingException, IOException {
		
			ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(fileName));
			height = lines.size();
			width = lines.get(0).length();
			boxes = new MazeBox[width][height];
			boolean hasDeparture = false;
			boolean hasArrival = false;
			for (int j=0; j<height; j++) {
				if (lines.get(j).length() != width) throw new MazeReadingException(fileName,j+1,"Le nombre de cases par ligne n'est pas constant.");
				for (int i=0; i<width; i++) {
					//remplit boxes
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
			//this part creates the list of polygons to save to then detect click
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
				boxes[i][j] = new EmptyBox(this, i, j);
				break;
			case "A" :
				boxes[i][j] = new ArrivalBox(this, i, j);
				updateBox(arrivalBox.getX(),arrivalBox.getY(),"E");
				arrivalBox = (ArrivalBox) boxes[i][j];
				break;
			case "D" :
				boxes[i][j] = new DepartureBox(this, i, j);
				updateBox(departureBox.getX(),departureBox.getY(),"E");
				departureBox = (DepartureBox) boxes[i][j];
				break;
			case "W" :
				boxes[i][j] = new WallBox(this, i, j);
				break;
		}
	}
	






	
	
	
}













