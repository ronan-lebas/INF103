package ui;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

import graph.*;
import maze.*;


public class GUI extends JFrame implements ChangeListener{
    private Maze maze;
    public Maze getMaze(){return maze;}
    private Panel panel;
    private boolean modeSelected;
    public Panel getPanel(){return panel;}
    public GUI() {
        super("Labyrinthe");
        this.maze = new Maze();
        load();
        this.modeSelected = false;
        Panel panel = new Panel(this);
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



        maze.addObserver(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);


    }


    public void load(){
		System.out.println("LOAD"); 
		try {
			maze.initFromTextFile("tp04/data/current_lab.maze");
		} 
		catch (MazeReadingException ex) {} 
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public void save(){
		System.out.println("SAVE"); 
		try {
			maze.saveToTextFile("tp04/data/saved_lab.maze");
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
            getMaze().getHexagon(((MazeBox) vertex).getX(), ((MazeBox) vertex).getY()).paint((Graphics2D) getPanel().getGraphics(),Color.CYAN);
        }
	}

    private void changeBox(int i, int j, String newType){
        maze.updateBox(i, j, newType);
    }

    //deselect all boxes if called by mouseReleased
    public void deselectAllBoxes(){
        maze.setCurrentDragChange("N");
        for(int i = 0; i < maze.getWidth(); i++) {
            for(int j = 0; j < maze.getHeight(); j++) {
                maze.getBoxes(i,j).setSelected(false);
            }
        }
    }
    public void click(int x, int y, boolean isLeftClick){
        //System.out.println("X : "+ selectedBox.getX() +", Y : "+ selectedBox.getY() + ", click gauche : "+isLeftClick +", selecte ? "+ selectedBox.isSelected());

        //this part finds the selected box
        MazeBox selectedBox = null;
        for(int i = 0; i < maze.getWidth(); i++) {
            for(int j = 0; j < maze.getHeight(); j++) {
                if(maze.getHexagon(i,j).contains(x,y)){
                    selectedBox = maze.getBoxes(i,j);
                }
                else{maze.getBoxes(i,j).setSelected(false);}
            }
        }
        //this part changes the type of the selected box
        if(selectedBox != null && selectedBox.isSelected() == false){
            int i = selectedBox.getX();
            int j = selectedBox.getY();
            if(isLeftClick){
                if(maze.getCurrentDragChange() != "N" && (selectedBox.getLabel() == "E" || selectedBox.getLabel() == "W")){changeBox(i,j,maze.getCurrentDragChange());}
                if(selectedBox.getLabel() == "E" && maze.getCurrentDragChange() == "N"){
                    changeBox(i, j, "W");
                    maze.setCurrentDragChange("W");
                }
                if(selectedBox.getLabel() == "W" && maze.getCurrentDragChange() == "N"){
                    changeBox(i, j, "E");
                    maze.setCurrentDragChange("E");
                }
            }
            maze.getBoxes(i,j).setSelected(true);
        }
        
    }



    public void stateChanged(ChangeEvent evt) {
        panel.notifyForUpdate();
    }
    public static void main(String[] args) {
	
		GUI gui = new GUI();
		
		
		
		

	}
}
