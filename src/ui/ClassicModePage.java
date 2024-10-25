package ui;

import arena.Arena;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ClassicModePage {

    private BorderPane layout;
    private Arena arena;

    public ClassicModePage(Scene scene) {
        layout = new BorderPane();

        // Initialize the game arena
        arena = new Arena(scene);
        layout.setCenter(arena.getGrid());  // Set the arena grid in the center of the layout

        // Back button setup
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
