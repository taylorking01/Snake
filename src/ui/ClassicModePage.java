package ui;

import arena.Arena;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        
        // Center the arena grid with dynamic resizing
        StackPane centerPane = new StackPane();
        centerPane.setPadding(new Insets(10));
        centerPane.getChildren().add(arena.getGrid());
        centerPane.setAlignment(Pos.CENTER);

        // Bind arena grid size to 60% of the scene's size for dynamic resizing
        centerPane.maxWidthProperty().bind(Bindings.min(scene.widthProperty().multiply(0.6), scene.heightProperty().multiply(0.6)));
        centerPane.maxHeightProperty().bind(centerPane.maxWidthProperty());
        
        layout.setCenter(centerPane); // Set the arena grid in the center of the layout

        // "Play" button setup beneath the arena
        Button playButton = new Button("Play");
        applyButtonStyles(playButton, 150);

        // Define action for the "Play" button
        playButton.setOnAction(e -> {
            toggleButtonEffect(playButton);
            // Add logic here to start the game
        });

        // Place the "Play" button in a VBox beneath the arena
        VBox playButtonBox = new VBox(playButton);
        playButtonBox.setAlignment(Pos.CENTER);
        playButtonBox.setPadding(new Insets(20, 0, 0, 0)); // Padding above the button
        layout.setBottom(playButtonBox);

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
