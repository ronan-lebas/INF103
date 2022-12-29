

import java.io.IOException;

import graph.Dijkstra;
import maze.Maze;
import maze.MazeReadingException;

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
		//Dijkstra.dijkstra(maze, maze.departureBox, maze.arrivalBox)
	
	
	}

}
