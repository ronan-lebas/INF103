package maze;
/**
 * MazeReadingException represents an exception that is thrown when there is an error in reading a maze file.
 */


public class MazeReadingException extends Exception {
	private final String fileName;
	private final int line;
	private final String errorText;

	/**
	 * Constructs a new MazeReadingException object with the given parameters.
	 * 
	 * @param fileName  the name of the file that caused the exception
	 * @param line      the line number that caused the exception
	 * @param errorText the error text
	 */
	public MazeReadingException(String fileName, int line, String errorText) {
		this.fileName = fileName;
		this.line = line;
		this.errorText = errorText;
		//prints the error message in the console containing the file name, the line number and the error text
		System.out.println("File : " + fileName + ", line : " + line + ", " + errorText);
	}

	/**
	 * Returns the error text.
	 * 
	 * @return the error text
	 */
	public String getErrorText() {
		return errorText;
	}
}
