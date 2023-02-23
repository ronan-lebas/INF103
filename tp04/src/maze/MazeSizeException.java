package maze;
/**
 * This exception is thrown when the size of the maze is invalid.
 */
public class MazeSizeException extends Exception {

    /**
     * Constructs a new MazeSizeException with a default error message.
     */
    public MazeSizeException() {
        super("The maze cannot have only one box !");
    }
}
