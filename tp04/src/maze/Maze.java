package maze;
import java.util.ArrayList;
import graph.*;

public class Maze implements Graph{
	private MazeBox[][] boxes;
	private int height = boxes[0].length;
	private int width = boxes.length;
	private boolean isInMaze(int i, int j) {
		if (i>-1 && i < width && j > -1 && j < height) {return true;}
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
		return successors;
	}
	
	
	public int getWeight(Vertex src, Vertex dst) {
		
	}
}
