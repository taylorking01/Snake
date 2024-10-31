package arena;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.binding.Bindings;

/**
 * Represents the game arena grid for the Snake game.
 */
public class Arena {
    private final int rows = 16;
    private final int cols = 16;
    private final Map<String, Rectangle> tileMap = new HashMap<>();
    private GridPane grid;

    /**
     * Constructs an Arena object and initializes the grid.
     *
     * @param scene the scene of the game, used to bind grid elements to window size
     */
    public Arena(Scene scene) {
        grid = new GridPane();
        initializeGrid(scene);
    }

    /**
     * Initializes the grid with tiles bound to the scene's size.
     *
     * @param scene the scene of the game
     */
    private void initializeGrid(Scene scene) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Rectangle tile = new Rectangle();
                tile.widthProperty().bind(Bindings.min(
                        scene.widthProperty().multiply(0.6).divide(cols),
                        scene.heightProperty().multiply(0.6).divide(rows)
                ));
                tile.heightProperty().bind(tile.widthProperty());
                tile.setFill(Color.DARKGRAY);
                tileMap.put(r + "," + c, tile);
                grid.add(tile, c, r);
            }
        }
    }

    /**
     * Clears the grid by resetting all tiles to the default color.
     */
    public void clearGrid() {
        tileMap.values().forEach(tile -> tile.setFill(Color.DARKGRAY));
    }

    /**
     * Retrieves the GridPane representing the arena.
     *
     * @return the GridPane of the arena
     */
    public GridPane getGrid() {
        return grid;
    }

    /**
     * Retrieves the tile at a specific position.
     *
     * @param x the x-coordinate (column)
     * @param y the y-coordinate (row)
     * @return the Rectangle tile at the specified position, or null if out of bounds
     */
    public Rectangle getTile(int x, int y) {
        return tileMap.get(y + "," + x);
    }

    /**
     * Gets the number of rows in the arena.
     *
     * @return the number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Gets the number of columns in the arena.
     *
     * @return the number of columns
     */
    public int getCols() {
        return cols;
    }
}
