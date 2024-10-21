package arena;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



public class Arena {
	private final int rows = 16;
	private final int cols = 16;
	private final int tileSize = 35; //Size of each tile to fit into 600x600
	
	private GridPane grid;
	
	public Arena() {
		grid = new GridPane();
		grid.setHgap(2);
		grid.setVgap(2);
		initializeGrid();
	}

	private void initializeGrid() {
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				Rectangle tile = new Rectangle(tileSize, tileSize);
				tile.setFill(Color.DARKGRAY);
				grid.add(tile, r, c);
			}
		}
	}
	
	public GridPane getGrid() {
		return grid;
	}
	

}
