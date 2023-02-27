package maze;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import graph.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;

/**
 * A class representing a maze, and implementing the Graph interface.
 */
public class Maze implements Graph {
	private int height;
	private int width;
	// The default path and directory are used for loading/saving maze files.
	private final String defaultPath = "tp04/data/default.maze";
	private final String defaultDirectory = "./tp04/data/";
	private MazeBox[][] boxes;
	private ArrivalBox arrivalBox;
	private DepartureBox departureBox;

	/**
	 * Returns the default path used for loading/saving maze files.
	 *
	 * @return defaultPath
	 */
	public String getDefaultPath() {
		return defaultPath;
	}

	/**
	 * Returns the default directory used for loading/saving maze files.
	 *
	 * @return defaultDirectory
	 */
	public String getDefaultDirectory() {
		return defaultDirectory;
	}

	/**
	 * Returns the height of the maze in number of boxes.
	 *
	 * @return height of the maze
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns the width of the maze in number of boxes.
	 *
	 * @return width of the maze
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the 2D array of MazeBox objects representing the maze boxes.
	 *
	 * @return boxes the array of boxes
	 */
	public MazeBox[][] getBoxes() {
		return boxes;
	}

	/**
	 * Returns the MazeBox object located at the specified position in the maze.
	 *
	 * @param i the column index of the maze box
	 * @param j the row index of the maze box
	 * @return boxes[i][j] the box at position (i,j)
	 */
	public MazeBox getBoxes(int i, int j) {
		return boxes[i][j];
	}

	/**
	 * Returns the DepartureBox object representing the departure box of the maze.
	 *
	 * @return departureBox object representing the departure box
	 */
	public DepartureBox getDepartureBox() {
		return departureBox;
	}

	/**
	 * Returns the ArrivalBox object representing the arrival box of the maze.
	 *
	 * @return arrivalBox object representing the arrival box
	 */
	public ArrivalBox getArrivalBox() {
		return arrivalBox;
	}

	// this part is for the GUI part
	private Hexagon[][] hexagonList;
	private int d;
	private int border;
	private int origin;

	/**
	 * Returns the list of all hexagons in the maze.
	 * 
	 * @return hexagonList the list of hexagons in the maze.
	 */
	public Hexagon[][] getHexagonList() {
		return hexagonList;
	}

	/**
	 * Returns the hexagon at the specified position in the maze.
	 * 
	 * @param i the column of the hexagon.
	 * @param j the row of the hexagon.
	 * @return hexagonList[i][j] the hexagon at the specified position in the maze.
	 */
	public Hexagon getHexagon(int i, int j) {
		return hexagonList[i][j];
	}

	/**
	 * Returns the distance between the center of a hexagon and one of its sides.
	 * 
	 * @return d the distance between the center of a hexagon and one of its sides.
	 */
	public int getD() {
		return d;
	}

	/**
	 * Returns the size of the border around the maze.
	 * 
	 * @return border the size of the border around the maze.
	 */
	public int getBorder() {
		return border;
	}

	/**
	 * Returns the origin of the maze (horizontal distance from the left edge of the
	 * screen to the first box).
	 * 
	 * @return origin the origin of the maze
	 */
	public int getOrigin() {
		return origin;
	}

	/**
	 * Returns true if the given coordinates correspond to a box inside the maze, false otherwise.
	 * 
	 * @param i the column index.
	 * @param j the row index.
	 * @return boolean True if the given coordinates are inside the maze, false otherwise.
	 */
	private boolean isInMaze(int i, int j) {
		if (i > -1 && i < width && j > -1 && j < height) {
			return true;
		}
		return false;
	}

	/**
	 * Returns a list of all walkable vertexes in the maze.
	 * 
	 * @return allVertexes the list of all walkable vertexes in the maze.
	 */
	public ArrayList<Vertex> getAllVertexes() {
		ArrayList<Vertex> allVertexes = new ArrayList<Vertex>();
		for (MazeBox[] box1 : boxes) {
			for (MazeBox box : box1) {
				if (box.isWalkable()) {
					allVertexes.add(box);
				}
			}
		}
		return allVertexes;
	}

	/**
	 * Returns the successors of the specified vertex in the maze.
	 *
	 * @param vertex the vertex that we want the successors of
	 * @return successors2 an ArrayList of Vertex objects representing the
	 *         successors of the
	 *         specified vertex
	 */
	public ArrayList<Vertex> getSuccessors(Vertex vertex) {
		MazeBox box = (MazeBox) vertex;
		int x = box.getX();
		int y = box.getY();
		ArrayList<Vertex> successors = new ArrayList<Vertex>();
		int e = 0;
		//e is used to determine the successors according to the parity of the row
		if (y % 2 == 0) {
			e = -1;
		}
		if (y % 2 == 1) {
			e = 1;
		}
		//the isInMaze method is used to avoid IndexOutOfBoundsException
		if (isInMaze(x - 1, y)) {
			successors.add(boxes[x - 1][y]);
		}
		if (isInMaze(x + 1, y)) {
			successors.add(boxes[x + 1][y]);
		}
		if (isInMaze(x, y + 1)) {
			successors.add(boxes[x][y + 1]);
		}
		if (isInMaze(x, y - 1)) {
			successors.add(boxes[x][y - 1]);
		}
		if (isInMaze(x + e, y + 1)) {
			successors.add(boxes[x + e][y + 1]);
		}
		if (isInMaze(x + e, y - 1)) {
			successors.add(boxes[x + e][y - 1]);
		}
		ArrayList<Vertex> successors2 = new ArrayList<Vertex>();
		for (Vertex vertex2 : successors) {
			//only the walkable boxes are added to the successors list
			if (((MazeBox) vertex2).isWalkable()) {
				successors2.add(vertex2);
			}
		}
		return successors2;
	}

	/**
	 * Returns the distance between two vertexes in the maze.
	 *
	 * @param src the source vertex
	 * @param dst the destination vertex
	 * @return an integer representing the distance between the source and
	 *         destination vertexes: 0 if they are the same, 1 if they are
	 *         neighbors, -1 otherwise
	 */
	public int getDistance(Vertex src, Vertex dst) {
		if (src == dst) {
			return 0;
		}
		ArrayList<Vertex> successors = getSuccessors(src);
		if (successors.contains(dst)) {
			return 1;
		}
		return -1;
	}

	/**
	 * Initializes the maze from a text file.
	 *
	 * @param fileName the name of the text file to read the maze from
	 * @throws MazeReadingException if there is an error reading the maze file
	 * @throws IOException          if there is an error reading the maze file
	 * @throws NoSuchFileException  if the specified file does not exist
	 * @throws MazeSizeException    if the size of the maze is too small
	 */
	public final void initFromTextFile(String fileName)
			throws MazeReadingException, IOException, NoSuchFileException, MazeSizeException {

		ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(fileName));
		height = lines.size();
		width = lines.get(0).length();
		if (height * width < 2)
			throw new MazeSizeException();
		//calls the method setGUIValues() to set the values of the attributes according to the size of the maze
		setGUIValues();
		boxes = new MazeBox[width][height];
		//the two followings booleans are used to check if the maze has exactly one arrival and one departure
		boolean hasDeparture = false;
		boolean hasArrival = false;
		for (int j = 0; j < height; j++) {
			if (lines.get(j).length() != width)
				throw new MazeReadingException(fileName, j + 1, "The number of boxes per line is not constant.");
			for (int i = 0; i < width; i++) {
				// fill boxes
				switch ("" + lines.get(j).charAt(i)) {
					case "E":
						boxes[i][j] = new EmptyBox(this, i, j);
						break;
					case "W":
						boxes[i][j] = new WallBox(this, i, j);
						break;
					case "A":
						if (!hasArrival) {
							boxes[i][j] = new ArrivalBox(this, i, j);
							hasArrival = !hasArrival;
							this.arrivalBox = (ArrivalBox) boxes[i][j];
						} else {
							throw new MazeReadingException(fileName, j + 1, "There are two arrival boxes.");
						}
						break;
					case "D":
						if (!hasDeparture) {
							boxes[i][j] = new DepartureBox(this, i, j);
							hasDeparture = !hasDeparture;
							this.departureBox = (DepartureBox) boxes[i][j];
						} else {
							throw new MazeReadingException(fileName, j + 1, "There are two departure boxes.");
						}
						break;
					default:
						throw new MazeReadingException(fileName, j + 1, "A character is not valid.");
				}
			}
		}
		if (!(hasArrival && hasDeparture))
			throw new MazeReadingException(fileName, 0, "The maze misses a departure or arrival box.");
		//creates the list of polygons to save and then detect clicks
		fillHexagonsList();
		edited = false;

	}

	/**
	 * Saves the current state of the maze to a text file with the given file name.
	 *
	 * @param fileName the name of the file to save the maze to
	 * @throws MazeReadingException if an error occurs while reading the maze
	 * @throws IOException          if an I/O error occurs while writing to the file
	 */
	public final void saveToTextFile(String fileName) throws MazeReadingException, IOException {
		ArrayList<String> lines = new ArrayList<String>();
		for (int j = 0; j < height; j++) {
			String line = new String();
			for (int i = 0; i < width; i++) {
				line = line + boxes[i][j].getLabel();
			}
			lines.add(line);
		}
		Files.write(Paths.get(fileName), lines);
	}

	/**
	 * Updates the box at the given location with the new type of box.
	 *
	 * @param i       the x-coordinate of the box to update
	 * @param j       the y-coordinate of the box to update
	 * @param newType the new type of box to set at the given location
	 */
	public final void updateBox(int i, int j, String newType) {
		switch (newType) {
			case "E":
				boxes[i][j] = new EmptyBox(this, i, j, boxes[i][j].getLabel());
				break;
			case "A":
				//update the former arrival box with its previous state	
				updateBox(arrivalBox.getX(), arrivalBox.getY(), arrivalBox.getFormerLabel());
				boxes[i][j] = new ArrivalBox(this, i, j, boxes[i][j].getLabel());
				arrivalBox = (ArrivalBox) boxes[i][j];
				break;
			case "D":
				//update the former departure box with its previous state
				updateBox(departureBox.getX(), departureBox.getY(), departureBox.getFormerLabel());
				boxes[i][j] = new DepartureBox(this, i, j, boxes[i][j].getLabel());
				departureBox = (DepartureBox) boxes[i][j];
				break;
			case "W":
				boxes[i][j] = new WallBox(this, i, j, boxes[i][j].getLabel());
				break;
		}
		//update the list of polygons to save and then detect clicks
		fillHexagonsList();
		stateChanged();
	}

	// handle gui (the maze is the model)
	private String currentDragChange = "N";
	private boolean edited = false;
	private boolean showSolution = false;

	/**
	 * Returns whether or not to show the solution.
	 * @return showSolution a boolean value indicating whether or not to show the solution
	 */
	public boolean getShowSolution() {
		return showSolution;
	}

	/**
	 * Sets whether or not to show the solution.
	 *
	 * @param showSolution a boolean value indicating whether or not to show the
	 *                     solution
	 */
	public void setShowSolution(boolean showSolution) {
		this.showSolution = showSolution;
	}

	/**
	 * Returns whether or not the maze has been edited.
	 *
	 * @return a boolean value indicating whether or not the maze has been edited
	 */
	public boolean isEdited() {
		return edited;
	}

	/**
	 * Sets whether or not the maze has been edited.
	 *
	 * @param edited a boolean value indicating whether or not the maze has
	 *               been edited
	 */
	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	/**
	 * Returns the current drag change.
	 *
	 * @return currentDragChange the current drag change
	 */
	public String getCurrentDragChange() {
		return currentDragChange;
	}

	/**
	 * Sets the current drag change.
	 *
	 * @param currentDragChange the current drag change
	 */
	public void setCurrentDragChange(String currentDragChange) {
		this.currentDragChange = currentDragChange;
	}

	/**
	 * Fills the hexagon list with hexagons based on the maze's boxes.
	 */
	public void fillHexagonsList() {
		hexagonList = new Hexagon[width][height];
		for (int i = 0; i < this.getWidth(); i++) {
			for (int j = 0; j < this.getHeight(); j++) {
				//creates hexagons by computing the coordinates of the center of the hexagon and copying the color of the box
				hexagonList[i][j] = new Hexagon(origin + (d + d / 20) * ((j % 2) + 2 * i),
				origin + (d - d / 10) * (2 * j), d, getBoxes()[i][j].getColor());
			}
		}
	}

	/**
	 * Paints all the hexagons in the maze.
	 *
	 * @param g the Graphics2D object to paint on
	 */
	public void paintHexagons(Graphics2D g) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				//asks each hexagon to paint itself
				hexagonList[i][j].paint(g);
			}
		}
	}

	private final ArrayList<ChangeListener> listeners = new ArrayList<ChangeListener>();

	/**
	 * Adds a change listener to the maze.
	 *
	 * @param listener the ChangeListener to add
	 */
	public void addObserver(ChangeListener listener) {
		listeners.add(listener);
	}

	/**
	 * Notifies all the change listeners that the state has changed.
	 */
	public void stateChanged() {
		edited = true;
		ChangeEvent evt = new ChangeEvent(this);

		for (ChangeListener listener : listeners) {
			listener.stateChanged(evt);
		}
	}

	/**
	 * Creates a new maze of the specified height and width.
	 *
	 * @param height the height of the maze
	 * @param width  the width of the maze
	 */
	public Maze(int height, int width) {
		this.height = height;
		this.width = width;
		//computes the gui values based on the height and width
		setGUIValues();
		boxes = new MazeBox[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				//creates an empty maze
				boxes[i][j] = new EmptyBox(this, i, j);
			}
		}
		//the maze needs to have exactly one departure box and one arrival box
		boxes[0][0] = new DepartureBox(this, 0, 0);
		boxes[width - 1][height - 1] = new ArrivalBox(this, width - 1, height - 1);
		this.departureBox = (DepartureBox) boxes[0][0];
		arrivalBox = (ArrivalBox) boxes[width - 1][height - 1];
		//creates the list of polygons to save and then detect clicks
		fillHexagonsList();
	}

	/**
	 * Creates an empty maze.
	 */
	public Maze() {
	}

	/**
	 * Sets the GUI values for the maze.
	 */
	public void setGUIValues() {
		//arbitrary values to make the maze looking good
		d = 200 / Math.max(width, height);
		border = 2 * d;
		origin = border + d / 5;
	}

}
