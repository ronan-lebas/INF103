package ui;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import graph.*;
import maze.*;


public class GUI extends JFrame implements ChangeListener{
    private Maze maze;
    public Maze getMaze(){return maze;}
    private Panel panel;
    public Panel getPanel(){return panel;}
    public GUI() {
        super("Maze Application");
        this.maze = new Maze();
        load(maze.getDefaultPath());
        Panel panel = new Panel(this);
        this.panel = panel;
        setContentPane(panel);
        setLocationRelativeTo(null);
        
        JMenuBar menuBar = new JMenuBar();
        JMenuItem load = new JMenuItem("Load");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem newMaze = new JMenuItem("New");
        setJMenuBar(menuBar);
        menuBar.add(newMaze);
        menuBar.add(load);
        menuBar.add(save);

        newMaze.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {newMaze();}} );


        JFileChooser explorer = new JFileChooser();

        load.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
            String path;
            explorer.setCurrentDirectory(new File(maze.getDefaultDirectory()));
            explorer.setDialogTitle("Choose a maze :");
            explorer.setAcceptAllFileFilterUsed(false);
            explorer.addChoosableFileFilter(new FileNameExtensionFilter("Maze", "maze"));
            explorer.showOpenDialog(null);
            //try catch needed to avoid exception when the explorer is close without choosing a file
            try{
                path = explorer.getSelectedFile().getAbsolutePath();
                load(path);
            }
            catch(Exception e){}
        }} );
        save.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
            String path;
            explorer.setCurrentDirectory(new File(maze.getDefaultDirectory()));
            explorer.setDialogTitle("Save the maze :");
            explorer.setAcceptAllFileFilterUsed(false);
            explorer.addChoosableFileFilter(new FileNameExtensionFilter("Maze", "maze"));
            explorer.showSaveDialog(null);
            try{
                path = explorer.getSelectedFile().getAbsolutePath();
                save(path);
            }
            catch(Exception e){}
        }} );    



        maze.addObserver(this);





        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(maze.isEdited() == true){
                    int choice = JOptionPane.showConfirmDialog(null, "Do you want to save under the name \""+ maze.getDefaultPath() +"\" before exiting ?");
                if (choice == JOptionPane.YES_OPTION) {
                    save(maze.getDefaultPath());
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } 
                if (choice == JOptionPane.NO_OPTION) {
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
                if (choice == JOptionPane.CANCEL_OPTION) {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
            else {setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);}
                }
        });

        
        
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
    public void load(final String path){
		System.out.println("LOAD"); 
		try {
			maze.initFromTextFile(path);
            panel = new Panel(this);
            setContentPane(panel);
            pack();
        } 
		catch(NoSuchFileException e){JOptionPane.showMessageDialog(this, "This file doesn't exist", "Error", JOptionPane.ERROR_MESSAGE);}
        catch (MazeSizeException ex) {JOptionPane.showMessageDialog(this, "Please use a maze with more than 1 box", "Error", JOptionPane.ERROR_MESSAGE);} 
        catch (MazeReadingException ex) {JOptionPane.showMessageDialog(this, "An error occured", "Error", JOptionPane.ERROR_MESSAGE);} 
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public void save(final String path){
		System.out.println("SAVE"); 
		try {
			maze.setEdited(false);;
            maze.saveToTextFile(path);
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
