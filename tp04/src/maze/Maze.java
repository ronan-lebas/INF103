package maze;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import graph.*;

public class Maze implements Graph{
	private MazeBox[][] boxes;
	private int height;// = boxes[0].length;
	private int[] width;// = boxes.length;
	private boolean isInMaze(int i, int j) {
		if (i>-1 && i < width[j] && j > -1 && j < height) {return true;}
		return false;
	}
		
	public ArrayList<Vertex> getAllVertexes(){
		ArrayList<Vertex> allVertexes = new ArrayList<Vertex>();
		for (MazeBox[] box1 : boxes) {
			for (MazeBox box : box1) {
				allVertexes.add(box);
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
	
	public final void initFromTextFile(String fileName) {
		try {
			ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(fileName));
			height = lines.size();
			for (String line : lines) {width[width.length] = line.length();}
			for (int j=0; j<height; j++) {
				for (int i=0; i<width[j]; i++) {
					//remplit boxes
					boxes[i][j] = new MazeBox();
				}
			}

		}
		catch(IOException ex) {}
		finally {}
	}
	
	
	
	
	
	
	
}













