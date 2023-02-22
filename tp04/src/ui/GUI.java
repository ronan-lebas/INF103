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
    public Panel getPanel(){return panel;}
    public GUI() {
        super("Labyrinthe");
        this.maze = new Maze();
        load();
        Panel panel = new Panel(this);
        this.panel = panel;
        setContentPane(panel);
        
        JMenuBar menuBar = new JMenuBar();
        JMenuItem load = new JMenuItem("Load");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem newMaze = new JMenuItem("New");
        setJMenuBar(menuBar);
        menuBar.add(newMaze);
        menuBar.add(load);
        menuBar.add(save);

        newMaze.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {newMaze();}} );
        load.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {load();}} );
        save.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {save();}} );    



        maze.addObserver(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setResizable(false);


    }


    public void newMaze(){
        try{
            String newHeight = JOptionPane.showInputDialog(this,"New height ?", 2);
            String newWidth = JOptionPane.showInputDialog(this,"New width ?", 2);
            int height = Integer.parseInt(newHeight);
            int width = Integer.parseInt(newWidth);
            if(height*width < 2) throw new MazeSizeException();
            maze = new Maze(height, width);
            maze.addObserver(this);
            maze.stateChanged();
            panel = new Panel(this);
            setContentPane(panel);
            pack();
        }
        catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Please enter two numbers", "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch (MazeSizeException e){
            JOptionPane.showMessageDialog(this, "Please enter numbers greater than 1", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void load(){
		System.out.println("LOAD"); 
		try {
			maze.initFromTextFile("tp04/data/current_lab.maze");
            panel = new Panel(this);
            setContentPane(panel);
            pack();
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
		for (int i = 1; i < shortestPath.size() - 1; i++) {
            System.out.println("X : "+((MazeBox) shortestPath.get(i)).getX()+", Y : "+((MazeBox) shortestPath.get(i)).getY());
            getMaze().getHexagon(((MazeBox) shortestPath.get(i)).getX(), ((MazeBox) shortestPath.get(i)).getY()).paint((Graphics2D) getPanel().getGraphics(),Color.CYAN);
        }
	}

    private void changeBox(int i, int j, String newType){
        maze.updateBox(i, j, newType);
    }

    //deselect all boxes if called by the mouseDetector and compute the new placement of arrival or departure boxes if necessary
    public void mouseReleased(int x, int y){
        for(int i = 0; i < maze.getWidth(); i++) {
            for(int j = 0; j < maze.getHeight(); j++) {
                maze.getBoxes(i,j).setSelected(false);
            }
        }
        maze.setCurrentDragChange("N");
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
                if(selectedBox.getLabel() == "D" && maze.getCurrentDragChange() == "N"){
                    //changeBox(i, j, "D");
                    maze.setCurrentDragChange("D");
                }
                if(selectedBox.getLabel() == "A" && maze.getCurrentDragChange() == "N"){
                    //changeBox(i, j, "A");
                    maze.setCurrentDragChange("A");
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
