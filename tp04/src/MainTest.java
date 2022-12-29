import java.io.IOException;
import java.util.ArrayList;

import graph.*;
import maze.*;
import ui.*;

public class MainTest {

	public static void main(String[] args) {
		Maze maze = new Maze();
		try {
			maze.initFromTextFile("C:/Users/ronan/Documents/Télécom/1A/INF/INF103/projet/tp04/data/labyrinthe1.maze");
			maze.saveToTextFile("C:/Users/ronan/Documents/Télécom/1A/INF/INF103/projet/tp04/data/labyrinthe2.maze");
		} 
		catch (MazeReadingException ex) {} 
		catch (IOException ex) {
			ex.printStackTrace();
		}
		ShortestPaths shortestPaths = Dijkstra.dijkstra(maze, maze.getDepartureBox(), maze.getArrivalBox());
		ArrayList<Vertex> shortestPath = shortestPaths.getShortestPath(maze.getArrivalBox());
		for (Vertex vertex : shortestPath) {System.out.println("X : "+((MazeBox) vertex).getX()+", Y : "+((MazeBox) vertex).getY());}
		UI ui = new UI();
	}

}
