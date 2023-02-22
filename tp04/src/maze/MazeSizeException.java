package maze;

public class MazeSizeException extends Exception {
    public MazeSizeException() {
        super("The maze cannot have only one box !");
    }
}