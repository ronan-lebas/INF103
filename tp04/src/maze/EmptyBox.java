package maze;

public class EmptyBox extends MazeBox {
	public String getLabel() {
		return "EmptyBox";
	}
	public boolean isWalkable() {
		return true;
	}
}
