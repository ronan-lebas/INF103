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

public class Maze implements Graph {
	private int height;// = boxes[0].length;
	private int width;// = boxes.length;
	private final String defaultPath = "tp04/data/default.maze";
	private final String defaultDirectory = "./tp04/data/";
	private MazeBox[][] boxes;
	private ArrivalBox arrivalBox;
	private DepartureBox departureBox;

/**
 * Returns the default path used for loading/saving maze files.
 *
 * @return the default path
 */
public String getDefaultPath() {
    return defaultPath;
}

/**
 * Returns the default directory used for loading/saving maze files.
 *
 * @return the default directory
 */
public String getDefaultDirectory() {
    return defaultDirectory;
}

/**
 * Returns the height of the maze in number of cells.
 *
 * @return the height of the maze
 */
public int getHeight() {
    return height;
}

/**
 * Returns the width of the maze in number of cells.
 *
 * @return the width of the maze
 */
public int getWidth() {
    return width;
}

/**
 * Returns the 2D array of MazeBox objects representing the maze cells.
 *
 * @return the 2D array of MazeBox objects
 */
public MazeBox[][] getBoxes() {
    return boxes;
}

/**
 * Returns the MazeBox object located at the specified position in the maze.
 *
 * @param i the row index of the maze cell
 * @param j the column index of the maze cell
 * @return the MazeBox object at position (i,j)
 */
public MazeBox getBoxes(int i, int j) {
    return boxes[i][j];
}

/**
 * Returns the DepartureBox object representing the departure cell of the maze.
 *
 * @return the DepartureBox object representing the departure cell
 */
public DepartureBox getDepartureBox() {
    return departureBox;
}

/**
 * Returns the ArrivalBox object representing the arrival cell of the maze.
 *
 * @return the ArrivalBox object representing the arrival cell
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
	 * @return Hexagon[][] The list of hexagons in the maze.
	 */
	public Hexagon[][] getHexagonList() {
		return hexagonList;
	}

	/**
	 * Returns the hexagon at the specified position in the maze.
	 * 
	 * @param i The horizontal position of the hexagon.
	 * @param j The vertical position of the hexagon.
	 * @return Hexagon The hexagon at the specified position in the maze.
	 */
	public Hexagon getHexagon(int i, int j) {
		return hexagonList[i][j];
	}

	/**
	 * Returns the distance between the center of a hexagon and one of its sides.
	 * 
	 * @return int The distance between the center of a hexagon and one of its
	 *         sides.
	 */
	public int getD() {
		return d;
	}

	/**
	 * Returns the size of the border around the maze.
	 * 
	 * @return int The size of the border around the maze.
	 */
	public int getBorder() {
		return border;
	}

	/**
	 * Returns the origin of the maze (horizontal distance from the left edge of the
	 * screen).
	 * 
	 * @return int The origin of the maze (horizontal distance from the left edge of
	 *         the screen).
	 */
	public int getOrigin() {
		return origin;
	}

	/**
	 * Returns true if the specified coordinates are inside the boundaries of the
	 * maze.
	 * 
	 * @param i The horizontal coordinate.
	 * @param j The vertical coordinate.
	 * @return boolean True if the specified coordinates are inside the boundaries
	 *         of the maze.
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
	 * @return ArrayList<Vertex> The list of all walkable vertexes in the maze.
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
	 * @param vertex the vertex whose successors are to be returned
	 * @return an ArrayList of Vertex objects representing the successors of the
	 *         specified vertex
	 */
	public ArrayList<Vertex> getSuccessors(Vertex vertex) {
		MazeBox box = (MazeBox) vertex;
		int x = box.getX();
		int y = box.getY();
		ArrayList<Vertex> successors = new ArrayList<Vertex>();
		int e = 0;
		if (y % 2 == 0) {
			e = -1;
		}
		if (y % 2 == 1) {
			e = 1;
		}
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
			if (((MazeBox) vertex2).isWalkable()) {
				successors2.add(vertex2);
			}
		}
		return successors2;
	}

	/**
	 * Returns the distance between two vertices in the maze.
	 *
	 * @param src the source vertex
	 * @param dst the destination vertex
	 * @return an integer representing the distance between the source and
	 *         destination vertices: 0 if they are the same, 1 if they are
	 *         neighbors, -1 otherwise
	 */
	public int getDistance(Vertex src, Vertex dst) {
		// 0 si c'est les memes, 1 si ils sont voisins, -1 sinon
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
		setGUIValues();
		boxes = new MazeBox[width][height];
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
			throw new MazeReadingException(fileName, 0, "Il n'y a pas de case d'arrivée ou de départ.");
		// this line creates the list of polygons to save to then detect click
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
				line = line + this.boxes[i][j].getLabel();
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
				updateBox(arrivalBox.getX(), arrivalBox.getY(), arrivalBox.getFormerLabel());
				boxes[i][j] = new ArrivalBox(this, i, j, boxes[i][j].getLabel());
				arrivalBox = (ArrivalBox) boxes[i][j];
				break;
			case "D":
				updateBox(departureBox.getX(), departureBox.getY(), departureBox.getFormerLabel());
				boxes[i][j] = new DepartureBox(this, i, j, boxes[i][j].getLabel());
				departureBox = (DepartureBox) boxes[i][j];
				break;
			case "W":
				boxes[i][j] = new WallBox(this, i, j, boxes[i][j].getLabel());
				break;
		}
		fillHexagonsList();
		stateChanged();
	}

	// handle gui (the maze is the model)
	private String currentDragChange = "N";
	private boolean edited = false;
	private boolean showSolution = false;

	/**
	 * Returns whether or not to show the solution.
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
	 * Returns whether or not the game board has been edited.
	 *
	 * @return a boolean value indicating whether or not the game board has been
	 *         edited
	 */
	public boolean isEdited() {
		return edited;
	}

	/**
	 * Sets whether or not the game board has been edited.
	 *
	 * @param edited a boolean value indicating whether or not the game board has
	 *               been edited
	 */
	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	/**
	 * Returns the current drag change.
	 *
	 * @return the current drag change
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
	 * Fills the hexagon list with hexagons based on the game board's boxes and
	 * their labels.
	 */
	public void fillHexagonsList() {
		hexagonList = new Hexagon[width][height];
		for (int i = 0; i < this.getWidth(); i++) {
			for (int j = 0; j < this.getHeight(); j++) {
				switch ((this.getBoxes()[i][j]).getLabel()) {
					case "W":
						hexagonList[i][j] = new Hexagon(origin + (d + d / 20) * ((j % 2) + 2 * i),
								origin + (d - d / 10) * (2 * j), d, getBoxes()[i][j].getColor());
						break;
					case "E":
						hexagonList[i][j] = new Hexagon(origin + (d + d / 20) * ((j % 2) + 2 * i),
								origin + (d - d / 10) * (2 * j), d, getBoxes()[i][j].getColor());
						break;
					case "A":
						hexagonList[i][j] = new Hexagon(origin + (d + d / 20) * ((j % 2) + 2 * i),
								origin + (d - d / 10) * (2 * j), d, getBoxes()[i][j].getColor());
						break;
					case "D":
						hexagonList[i][j] = new Hexagon(origin + (d + d / 20) * ((j % 2) + 2 * i),
								origin + (d - d / 10) * (2 * j), d, getBoxes()[i][j].getColor());
						break;
				}
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
		setGUIValues();
		boxes = new MazeBox[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				boxes[i][j] = new EmptyBox(this, i, j);
			}
		}
		boxes[0][0] = new DepartureBox(this, 0, 0);
		boxes[width - 1][height - 1] = new ArrivalBox(this, width - 1, height - 1);
		this.departureBox = (DepartureBox) boxes[0][0];
		arrivalBox = (ArrivalBox) boxes[width - 1][height - 1];
		// this line creates the list of polygons to save to then detect click
		fillHexagonsList();
	}

	/**
	 * Creates an empty maze with default values for height and width.
	 */
	public Maze() {
	}

	/**
	 * Sets the GUI values for the maze.
	 */
	public void setGUIValues() {
		d = 200 / Math.max(width, height);
		border = 2 * d;
		origin = border + d / 5;
	}

}
