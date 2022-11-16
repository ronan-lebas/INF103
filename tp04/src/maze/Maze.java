package maze;
import java.util.ArrayList;
import graph.*;

public class Maze implements Graph{
	private MazeBox[][] boxes;
	private int height = boxes[0].length;
	private int width = boxes.length;
		
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
		if (x>0) {successors.add(boxes[x-1][y]);}
		if (x<width-1) {successors.add(boxes[x+1][y]);}
		int e = 0;
		if (y%2 == 0) {e = -1;}
		if (y%2 == 1) {e = 1;}
		try {
			successors.add(boxes[x][y+1]);
		}
		catch (Exception ex) {}
		try {
			successors.add(boxes[x][y-1]);
		}
		catch (Exception ex) {}
		try {
			successors.add(boxes[x+e][y+1]);
		}
		catch (Exception ex) {}
		try {
			successors.add(boxes[x+e][y-1]);
		}
		catch (Exception ex) {}
		return successors;
	}
	
	
	//public int getWeight(Vertex src, Vertex dst);
}
