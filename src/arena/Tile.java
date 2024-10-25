package arena;

import javafx.scene.paint.Color;

public class Tile {
	private int x;
	private int y;
	private Color currentColor;
	private final Color defaultColor = Color.DARKGRAY;
	
	public Tile(int x, int y) {
	    this.x = x;
	    this.y = y;
	    this.currentColor = defaultColor;  // Set a default color
	}

	
	public void setFill(Color color) {
		currentColor = color;
	}
	
	public void resetColor() {
	    this.currentColor = defaultColor;  // Reset to default color
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
