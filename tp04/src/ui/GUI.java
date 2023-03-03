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

/**
 * A JFrame class for building the GUI and managing the maze and its solution.
 * Contains a menu bar with buttons for loading, saving, and creating a new
 * maze.
 */
public class GUI extends JFrame implements ChangeListener {
    private Maze maze;
    private Panel panel;

    /**
     * Returns the maze object
     * 
     * @return maze the maze object
     */
    public Maze getMaze() {
        return maze;
    }

    /**
     * Returns the panel object
     * 
     * @return panel the panel object
     */
    public Panel getPanel() {
        return panel;
    }

    /**
     * Builds the GUI, adds the menu bar, and set the functions of the menu bar
     * buttons.
     */
    public GUI() {
        super("Maze Application");
        this.maze = new Maze();
        load(maze.getDefaultPath());
        Panel panel = new Panel(this);
        this.panel = panel;
        setContentPane(panel);
        // sets the window at the center of the screen
        setLocationRelativeTo(null);
        //changes the icon of the application
        setIconImage(new ImageIcon(maze.getDefaultDirectory()+"icon.png").getImage());

        JMenuBar menuBar = new JMenuBar();
        JMenuItem load = new JMenuItem("Load");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem newMaze = new JMenuItem("New");
        setJMenuBar(menuBar);
        menuBar.add(newMaze);
        menuBar.add(load);
        menuBar.add(save);

        newMaze.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                newMaze();
            }
        });
        // sets up the file explorer
        JFileChooser explorer = new JFileChooser();

        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String path;
                explorer.setCurrentDirectory(new File(maze.getDefaultDirectory()));
                explorer.setDialogTitle("Choose a maze :");
                // filters the files to only show .maze files
                explorer.setAcceptAllFileFilterUsed(false);
                explorer.addChoosableFileFilter(new FileNameExtensionFilter("Maze", "maze"));
                explorer.showOpenDialog(null);
                // try catch needed to avoid exception when the explorer is close without
                // choosing a file
                try {
                    // if a file is chosen, loads it
                    path = explorer.getSelectedFile().getAbsolutePath();
                    load(path);
                } catch (Exception e) {
                }
            }
        });
        save.addActionListener(new ActionListener() {
            // similar to load
            public void actionPerformed(ActionEvent event) {
                String path;
                explorer.setCurrentDirectory(new File(maze.getDefaultDirectory()));
                explorer.setDialogTitle("Save the maze :");
                explorer.setAcceptAllFileFilterUsed(false);
                explorer.addChoosableFileFilter(new FileNameExtensionFilter("Maze", "maze"));
                explorer.showSaveDialog(null);
                try {
                    path = explorer.getSelectedFile().getAbsolutePath();
                    save(path);
                } catch (Exception e) {
                }
            }
        });
        // implements the observer observable pattern
        maze.addObserver(this);

        addWindowListener(new WindowAdapter() {
            /**
             * This method is called when the user clicks on the close button of the window.
             * If the current maze has been edited, the user is prompted to save before
             * exiting.
             * 
             * @param e the window event
             */
            @Override
            public void windowClosing(WindowEvent e) {
                if (maze.isEdited() == true) {
                    int choice = JOptionPane.showConfirmDialog(null, "Do you want to save before exiting ?");
                    if (choice == JOptionPane.YES_OPTION) {
                        String name = JOptionPane.showInputDialog(null, "Enter the name of the maze", "default");
                        // save the maze with the new name to the "data" directory
                        save(maze.getDefaultDirectory() + name + ".maze");
                        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    }
                    if (choice == JOptionPane.NO_OPTION) {
                        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    }
                    if (choice == JOptionPane.CANCEL_OPTION) {
                        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    }
                } else {
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        });

        pack();
        setVisible(true);
        setResizable(false);

    }

    /**
     * This method creates a new maze with the user-defined height and width, or
     * displays an error message if the input values are not valid.
     * If the current maze has been edited, the user is prompted to save before
     * creating a new maze.
     */
    public void newMaze() {
        // similar to the windowClosing method
        if (maze.isEdited() == true) {
            int choice = JOptionPane.showConfirmDialog(null, "Do you want to save before creating a new maze ?");
            if (choice == JOptionPane.YES_OPTION) {
                String name = JOptionPane.showInputDialog(null, "Enter the name of the maze", "default");
                save(maze.getDefaultDirectory() + name + ".maze");
            }
            if (choice == JOptionPane.NO_OPTION) {
            }
            if (choice == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }

        // creates a new maze with the user-defined height and width
        try {
            String newHeight = JOptionPane.showInputDialog(this, "New height ?", 2);
            String newWidth = JOptionPane.showInputDialog(this, "New width ?", 2);
            int height = Integer.parseInt(newHeight);
            int width = Integer.parseInt(newWidth);
            if (height * width < 2) {
                // the maze has one box, so it is not valid
                throw new MazeSizeException();
            }
            maze = new Maze(height, width);
            maze.addObserver(this);
            panel = new Panel(this);
            setContentPane(panel);
            pack();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter two numbers", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (MazeSizeException e) {
            JOptionPane.showMessageDialog(this, "Please enter numbers greater than 1", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method loads a maze from a file with the given path, or displays an
     * error message if the file is invalid.
     * If the current maze has been edited, the user is prompted to save before
     * loading a new maze.
     * 
     * @param path the path of the file to load the maze from.
     */
    public void load(final String path) {
        // similar to the windowClosing method
        if (maze.isEdited() == true) {
            int choice = JOptionPane.showConfirmDialog(null, "Do you want to save before loading a new maze ?");
            if (choice == JOptionPane.YES_OPTION) {
                String name = JOptionPane.showInputDialog(null, "Enter the name of the maze", "default");
                save(maze.getDefaultDirectory() + name + ".maze");
            }
            if (choice == JOptionPane.NO_OPTION) {
            }
            if (choice == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }

        try {
            maze.initFromTextFile(path);
            panel = new Panel(this);
            setContentPane(panel);
            pack();
        } catch (NoSuchFileException ex) {
            JOptionPane.showMessageDialog(this, "This file doesn't exist", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (MazeSizeException ex) {
            JOptionPane.showMessageDialog(this, "Please use a maze with more than 1 box", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (MazeReadingException ex) {
            JOptionPane.showMessageDialog(this, "An error occured : " + ex.getErrorText(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Saves the current maze to a text file at the specified path.
     *
     * @param path the path where the maze will be saved
     */
    public void save(final String path) {
        try {
            maze.setEdited(false);
            maze.saveToTextFile(path);
        } catch (MazeReadingException ex) {
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Solves the current maze and displays the shortest path.
     */
    public void solve() {
        ShortestPaths shortestPaths = Dijkstra.dijkstra(maze, maze.getDepartureBox(), maze.getArrivalBox());
        ArrayList<Vertex> shortestPath = shortestPaths.getShortestPath(maze.getArrivalBox());
        if (!(shortestPath.contains((Vertex) maze.getArrivalBox())
                && shortestPath.contains((Vertex) maze.getDepartureBox()))) {
            // error message if there is no solution
            panel.eraseSolution();
            JOptionPane.showMessageDialog(this, "There is no solution", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            for (int i = 1; i < shortestPath.size() - 1; i++) {
                // asks the maze to paint the shortest path
                getMaze().getHexagon(((MazeBox) shortestPath.get(i)).getX(), ((MazeBox) shortestPath.get(i)).getY())
                        .paint((Graphics2D) getPanel().getGraphics(), Color.CYAN);
            }
        }
    }

    /**
     * Solves the current maze and displays the shortest path using the specified
     * graphics object.
     *
     * @param g the graphics object to be used for drawing the solution
     */
    public void solve(Graphics g) {
        // same as before, but with a specified graphics object, so this method can be
        // used by the panel
        ShortestPaths shortestPaths = Dijkstra.dijkstra(maze, maze.getDepartureBox(), maze.getArrivalBox());
        ArrayList<Vertex> shortestPath = shortestPaths.getShortestPath(maze.getArrivalBox());
        if (!(shortestPath.contains((Vertex) maze.getArrivalBox())
                && shortestPath.contains((Vertex) maze.getDepartureBox()))) {
            panel.eraseSolution();
            JOptionPane.showMessageDialog(this, "There is no solution", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            for (int i = 1; i < shortestPath.size() - 1; i++) {
                getMaze().getHexagon(((MazeBox) shortestPath.get(i)).getX(), ((MazeBox) shortestPath.get(i)).getY())
                        .paint(g, Color.CYAN);
            }
        }
    }

    /**
     * Updates the type of the box at the given coordinates (i, j) to the given
     * newType.
     *
     * @param i       the x coordinate of the box to be updated
     * @param j       the y coordinate of the box to be updated
     * @param newType the new type of the box
     */
    private void changeBox(int i, int j, String newType) {
        maze.updateBox(i, j, newType);
    }

    /**
     * Deselects all boxes if called by the mouseDetector.
     */
    public void mouseReleased() {
        for (int i = 0; i < maze.getWidth(); i++) {
            for (int j = 0; j < maze.getHeight(); j++) {
                maze.getBoxes(i, j).setSelected(false);
            }
        }
        //sets the current drag change to "N" for null
        maze.setCurrentDragChange("N");
    }

    /**
     * Handles a mouse click event at the given coordinates (x, y).
     * Changes the type of the selected box and computes new placement of arrival or
     * departure boxes if necessary.
     *
     * @param x           the x coordinate of the mouse click
     * @param y           the y coordinate of the mouse click
     * @param isLeftClick true if the click is a left-click, false if it is a
     *                    right-click
     */
    public void click(int x, int y, boolean isLeftClick) {
        // this part finds the selected box
        MazeBox selectedBox = null;
        for (int i = 0; i < maze.getWidth(); i++) {
            for (int j = 0; j < maze.getHeight(); j++) {
                //manages the isSelected boolean of the boxes, indicating if the box is selected for the first time or not
                if (maze.getHexagon(i, j).contains(x, y)) {
                    selectedBox = maze.getBoxes(i, j);
                } else {
                    maze.getBoxes(i, j).setSelected(false);
                }
            }
        }
        // this part changes the type of the selected box
        if (selectedBox != null && selectedBox.isSelected() == false) { // if the box is selected for the first time
            int i = selectedBox.getX();
            int j = selectedBox.getY();
            if (isLeftClick) { // if the click is a left-click
                if (maze.getCurrentDragChange() != "N"
                        && (selectedBox.getLabel() == "E" || selectedBox.getLabel() == "W")) {
                    //the drag changes boxes to empty or wall
                    changeBox(i, j, maze.getCurrentDragChange());
                }

                if (selectedBox.getLabel() == "E" && maze.getCurrentDragChange() == "N") {
                    //starts a drag change
                    changeBox(i, j, "W");
                    maze.setCurrentDragChange("W");
                }
                if (selectedBox.getLabel() == "W" && maze.getCurrentDragChange() == "N") {
                    //starts a drag change
                    changeBox(i, j, "E");
                    maze.setCurrentDragChange("E");
                }
                if (selectedBox.getLabel() == "D" && maze.getCurrentDragChange() == "N") {
                    //starts a drag change, but doesn't change the type of the box because this is already a departure box, we only want to move it
                    maze.setCurrentDragChange("D");
                }
                if (selectedBox.getLabel() == "A" && maze.getCurrentDragChange() == "N") {
                    //starts a drag change, but doesn't change the type of the box because this is already an arrival box, we only want to move it
                    maze.setCurrentDragChange("A");
                }
            }
            //sets the isSelected boolean of the box to true for the next computation, so that the box is not selected for the first time anymore
            maze.getBoxes(i, j).setSelected(true);
        }

    }

    /**
     * This method is called whenever the state changes. It notifies
     * the panel to update its view.
     * 
     * @param evt the ChangeEvent that occurred
     */
    public void stateChanged(ChangeEvent evt) {
        panel.notifyForUpdate();
    }

    /**
     * The main method of the program. It creates a new GUI and runs the program.
     * 
     * @param args an array of command-line arguments for the program, useless here
     */
    public static void main(String[] args) {
        GUI gui = new GUI();
    }

}
