package main;
import java.io.IOException;
import java.util.ArrayList;

import graph.*;
import maze.*;
import ui.*;

public class Main {
	private Maze maze;
	private UI ui;
	public Main(){
		this.maze = new Maze();
		load();
		this.ui = new UI(this,this.maze);
	}

	public void load(){
		System.out.println("LOAD"); 
		try {
			maze.initFromTextFile("C:/Users/ronan/Documents/Telecom/1A/INF/INF103/projet/tp04/data/labyrinthe1.maze");
		} 
		catch (MazeReadingException ex) {} 
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public void save(){
		System.out.println("SAVE"); 
		try {
			maze.saveToTextFile("C:/Users/ronan/Documents/Telecom/1A/INF/INF103/projet/tp04/data/labyrinthe2.maze");
		} 
		catch (MazeReadingException ex) {} 
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
	
		Main main = new Main();
		
		
		
		
		/*
		ShortestPaths shortestPaths = Dijkstra.dijkstra(maze, maze.getDepartureBox(), maze.getArrivalBox());
		ArrayList<Vertex> shortestPath = shortestPaths.getShortestPath(maze.getArrivalBox());
		for (Vertex vertex : shortestPath) {System.out.println("X : "+((MazeBox) vertex).getX()+", Y : "+((MazeBox) vertex).getY());}
	*/
	}
}
