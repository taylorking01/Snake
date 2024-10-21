package arena;

import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Arena {
    private final int rows = 16;
    private final int cols = 16;

    private GridPane grid;

    public Arena(Scene scene) {
        grid = new GridPane();
        initializeGrid(scene);
    }

    private void initializeGrid(Scene scene) {
        // The grid will adjust dynamically based on the smaller dimension of the scene (either width or height)
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Rectangle tile = new Rectangle();

                // Bind the tile size to be equal based on the smaller dimension of the scene
                tile.widthProperty().bind(Bindings.min(scene.widthProperty().multiply(0.7).divide(cols), 
                                                       scene.heightProperty().multiply(0.7).divide(rows)));
                tile.heightProperty().bind(tile.widthProperty()); // Ensure the height matches the width (square tiles)

                tile.setFill(Color.DARKGRAY);
                grid.add(tile, c, r); // Add the tile to the grid at column c, row r
            }
        }
    }

    public GridPane getGrid() {
        return grid;
    }
}
