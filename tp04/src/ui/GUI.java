package ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.NoSuchFileException;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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
     * Asks the maze to solve itself
     * 
     * @param g the Graphics object to draw on
     */
    public void solve(Graphics g){
        maze.solve(g);
    }

    /**
     * Asks the maze to deselect all boxes.
     */
    public void mouseReleased(){
        maze.mouseReleased();
    }

    /**
     * Sends the coordinates of the box that was clicked to the maze.
     */
    public void click(int i, int j, boolean isLeftClick){
        maze.click(i, j, isLeftClick);
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
        new GUI();
    }

}
