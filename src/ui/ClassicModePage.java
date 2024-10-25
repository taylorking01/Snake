package ui;

import arena.Arena;
import controller.GameController;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ClassicModePage {

    private BorderPane layout;
    private Arena arena;

    public ClassicModePage(Scene scene) {
        layout = new BorderPane();

        // Initialize the game arena
        arena = new Arena(scene);
        
        GameController controller = new GameController(arena);

        
     // Center the arena grid with dynamic resizing
        StackPane centerPane = new StackPane();
        centerPane.setPadding(new Insets(10));
        centerPane.getChildren().add(arena.getGrid());
        centerPane.setAlignment(Pos.CENTER);

        // Bind arena grid size to 60% of the scene's size for dynamic resizing
        centerPane.maxWidthProperty().bind(Bindings.min(scene.widthProperty().multiply(0.6), scene.heightProperty().multiply(0.6)));
        centerPane.maxHeightProperty().bind(centerPane.maxWidthProperty());

        // "Play" button setup
        Button playButton = new Button("Play");
        applyButtonStyles(playButton, 150);

        // Define action for the "Play" button
        playButton.setOnAction(e -> {
            toggleButtonEffect(playButton);
            controller.startGame();
            // Add logic here to start the game
        });

        // Combine the arena and "Play" button in a VBox to center them together
        VBox centerBox = new VBox(20, centerPane, playButton); // 20 spacing between arena and button
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(20, 0, 0, 0));

        // Set the combined VBox as the center of the layout
        layout.setCenter(centerBox);

        //Back button
        // Create the back button and apply the base style from StyleConfig
        Button backButton = new Button("Back");
        applyButtonStyles(backButton, 100);

        // Define action for the back button to navigate to the main menu
        backButton.setOnAction(e -> {
            toggleButtonEffect(backButton);
            Window.changePage("playpage");  // Navigate back to main menu
        });

        HBox backButtonBox = new HBox(backButton);
        backButtonBox.setAlignment(Pos.BOTTOM_LEFT);  // Align the back button at bottom left
        layout.setBottom(backButtonBox);
        
        // Add event filter to handle key presses for controlling the snake
        scene.addEventFilter(KeyEvent.KEY_PRESSED, controller::handleKeyPress);
    }

    // Helper method to apply styles and effects to buttons
    private void applyButtonStyles(Button button, int width) {
        button.setPrefWidth(width);  // Set a preferred width for back button
        button.setStyle(StyleConfig.getBaseButtonStyle());

        button.setOnMouseEntered(e -> button.setStyle(StyleConfig.getHoverButtonStyle()));
        button.setOnMouseExited(e -> button.setStyle(StyleConfig.getBaseButtonStyle()));
        button.setOnMousePressed(e -> button.setStyle(StyleConfig.getClickButtonStyle()));
        button.setOnMouseReleased(e -> button.setStyle(StyleConfig.getHoverButtonStyle()));
    }

    // Method to create a toggle effect for visual feedback
    private void toggleButtonEffect(Button button) {
        button.setStyle(StyleConfig.getClickButtonStyle());
        // Reset style to hover style shortly after click
        button.setOnMouseReleased(e -> button.setStyle(StyleConfig.getHoverButtonStyle()));
    }

    public BorderPane getLayout() {
        return layout;
    }
}
