package maze;

public class MazeReadingException extends Exception{
	private final String fileName;
	private final int line;
	private final String errorText;
	public MazeReadingException(String fileName, int line, String errorText) {
		this.fileName = fileName;
		this.line = line;
		this.errorText = errorText;
		System.out.println("File : "+fileName+", line : "+line+", "+errorText);
	}
	public String getErrorText(){
		return errorText;
	}
}
