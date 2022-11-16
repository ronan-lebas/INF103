package maze;
import java.util.ArrayList;
import graph.*;

public class Maze implements Graph{
	private MazeBox[][] boxes;
		
	public ArrayList<Vertex> getAllVertexes(){
		ArrayList<Vertex> allVertexes = new ArrayList<Vertex>();
		for (MazeBox[] box1 : boxes) {
			for (MazeBox box : box1) {
				allVertexes.add(box);
			}
		}
		return allVertexes;
	}
	
	
	
	//public List<Vertex> getSuccessors(Vertex vertex);
	//public int getWeight(Vertex src, Vertex dst);
}
