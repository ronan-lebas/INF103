package maze;

public class WallBox extends MazeBox {
	public String getLabel() {
		return "WallBox";
	}
	public boolean isWalkable() {
		return false;
	}
}
