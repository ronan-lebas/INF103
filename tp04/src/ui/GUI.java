package ui;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

import graph.*;
import maze.*;


public class GUI extends JFrame {
    private Maze maze;
    private Panel panel;
    private boolean modeSelected;
    public Panel getPanel(){return panel;}
    public GUI() {
        super("Labyrinthe");
        this.maze = new Maze();
        load();
        this.modeSelected = false;
        Panel panel = new Panel(this,maze);
        this.panel = panel;
        setContentPane(panel);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu mode = new JMenu("Mode");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem solve = new JMenuItem("Solve");
        JMenuItem edit = new JMenuItem("Edit");
        setJMenuBar(menuBar);
        menuBar.add(file);
        menuBar.add(mode);
        file.add(load);
        file.add(save);
        mode.add(solve);
        mode.add(edit);

        load.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {load();}} );
        save.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {save();}} );
        solve.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {modeSelected = false;}} );
        edit.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {modeSelected = true;}} );        




        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);


    }


    public void load(){
		System.out.println("LOAD"); 
		try {
			maze.initFromTextFile("C:/Users/ronan/Documents/Telecom/1A/INF/INF103/projet/tp04/data/current_lab.maze");
		} 
		catch (MazeReadingException ex) {} 
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public void save(){
		System.out.println("SAVE"); 
		try {
			maze.saveToTextFile("C:/Users/ronan/Documents/Telecom/1A/INF/INF103/projet/tp04/data/saved_lab.maze");
		} 
		catch (MazeReadingException ex) {} 
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public void solve(){
		ShortestPaths shortestPaths = Dijkstra.dijkstra(maze, maze.getDepartureBox(), maze.getArrivalBox());
		ArrayList<Vertex> shortestPath = shortestPaths.getShortestPath(maze.getArrivalBox());
		for (Vertex vertex : shortestPath) {
            System.out.println("X : "+((MazeBox) vertex).getX()+", Y : "+((MazeBox) vertex).getY());
            getPanel().repaintHexagon(getPanel().getGraphics(), ((MazeBox) vertex).getX(),((MazeBox) vertex).getY(), Color.CYAN);
        }
	}

    private void changeBox(int i, int j, String newType){
        maze.updateBox(i, j, newType);
    }

    public void click(int x, int y, boolean isLeftClick){
        MazeBox selectedBox = null;
        System.out.println("X : "+ x +", Y : "+ y + ", click gauche : "+isLeftClick);
        for(int i = 0; i < maze.getWidth(); i++) {
            for(int j = 0; j < maze.getHeight(); j++) {
                if(getPanel().getHexagonList()[i][j].contains(x,y)){
                    selectedBox = maze.getBoxes(i,j);
                }
            }
        }
        if(isLeftClick){
            if(selectedBox.getLabel() == "E"){
                changeBox(selectedBox.getX(), selectedBox.getY(), "W");
                System.out.println("SELECTED BOX : "+selectedBox.getLabel());
            }
            else{changeBox(selectedBox.getX(), selectedBox.getY(), "W");}
        }
        repaint();
    }

    public static void main(String[] args) {
	
		GUI gui = new GUI();
		
		
		
		

	}
}
