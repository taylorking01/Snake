package arena;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;



public class Arena {
	private final int rows = 16;
	private final int cols = 16;
	
	private GridPane grid;
	
	public Arena(Scene scene) {
		grid = new GridPane();
		initializeGrid(scene);
	}

	private void initializeGrid(Scene scene) {
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				Rectangle tile = new Rectangle();
				
				tile.widthProperty().bind(Bindings.divide(scene.widthProperty(), cols));
				tile.heightProperty().bind(Bindings.divide(scene.heightProperty(), rows));
				
				tile.setFill(Color.DARKGRAY);
				grid.add(tile, r, c);
			}
		}
	}
	
	public GridPane getGrid() {
		return grid;
	}
	

}
