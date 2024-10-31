package ui;

import arena.Arena;
import arena.ClassicMode;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * The ClassicModePage class represents the UI for the Classic Mode of the Snake game.
 * It integrates the Arena for rendering and delegates game logic to the ClassicMode class.
 */
public class ClassicModePage {
    private final BorderPane layout;            // The main layout pane
    private final Arena arena;                  // The game arena for rendering
    private final ClassicMode classicMode;      // The game logic handler
    private final Scene scene;                  // The JavaFX scene for handling user input

    /**
     * Constructs a ClassicModePage object.
     *
     * @param scene the Scene instance for handling user input
     * @param gameSpeedMillis the speed of the game in milliseconds per tick
     */
    public ClassicModePage(Scene scene, int gameSpeedMillis) {
        this.layout = new BorderPane();
        this.scene = scene;

        // Initialize the Arena with the scene
        this.arena = new Arena(scene);

        // Initialize ClassicMode with the arena, scene, and game speed
        this.classicMode = new ClassicMode(arena, scene, gameSpeedMillis);

        // Set up the UI components
        setupUI();

        // Start the game
        classicMode.startGame();
    }

    /**
     * Sets up the UI components, including the game arena and control buttons.
     */
    private void setupUI() {
        // Center the arena grid with dynamic resizing
        StackPane centerPane = new StackPane();
        centerPane.setPadding(new Insets(10));
        centerPane.getChildren().add(arena.getGrid());
        centerPane.setAlignment(Pos.CENTER);

        // Bind arena grid size to 60% of the scene's size for dynamic resizing
        centerPane.maxWidthProperty().bind(Bindings.min(
                scene.widthProperty().multiply(0.6), 
                scene.heightProperty().multiply(0.6)
        ));
        centerPane.maxHeightProperty().bind(centerPane.maxWidthProperty());

        // "Pause" button setup
        Button pauseButton = new Button("Pause");
        applyButtonStyles(pauseButton, 100);

        // Define action for the "Pause" button
        pauseButton.setOnAction(e -> {
            toggleButtonEffect(pauseButton);
            if (classicMode.isRunning()) {
                classicMode.pauseGame();
                pauseButton.setText("Resume");
            } else {
                classicMode.resumeGame();
                pauseButton.setText("Pause");
            }
        });

        // Combine the arena and "Pause" button in a VBox to center them together
        VBox centerBox = new VBox(20, centerPane, pauseButton); // 20px spacing
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(20, 0, 0, 0));

        // Set the combined VBox as the center of the layout
        layout.setCenter(centerBox);

        // "Back" button setup
        Button backButton = new Button("Back");
        applyButtonStyles(backButton, 100);

        // Define action for the "Back" button to navigate to the main menu
        backButton.setOnAction(e -> {
            toggleButtonEffect(backButton);
            classicMode.stopGame("Game Stopped by User.");
            Window.changePage("mainmenu");  // Navigate back to main menu
        });

        // Position the "Back" button at the bottom left
        VBox backButtonBox = new VBox(backButton);
        backButtonBox.setAlignment(Pos.BOTTOM_LEFT);  // Align at bottom left
        backButtonBox.setPadding(new Insets(10));
        layout.setBottom(backButtonBox);
    }

    /**
     * Applies consistent styles to buttons.
     *
     * @param button the Button to style
     * @param width the preferred width of the button
     */
    private void applyButtonStyles(Button button, int width) {
        button.setPrefWidth(width);  // Set preferred width
        button.setStyle(StyleConfig.getBaseButtonStyle());

        // Add hover and click effects
        button.setOnMouseEntered(e -> button.setStyle(StyleConfig.getHoverButtonStyle()));
        button.setOnMouseExited(e -> button.setStyle(StyleConfig.getBaseButtonStyle()));
        button.setOnMousePressed(e -> button.setStyle(StyleConfig.getClickButtonStyle()));
        button.setOnMouseReleased(e -> button.setStyle(StyleConfig.getHoverButtonStyle()));
    }

    /**
     * Creates a toggle effect for visual feedback when buttons are clicked.
     *
     * @param button the Button to apply the effect to
     */
    private void toggleButtonEffect(Button button) {
        button.setStyle(StyleConfig.getClickButtonStyle());
        // Reset style to hover style shortly after click
        button.setOnMouseReleased(e -> button.setStyle(StyleConfig.getHoverButtonStyle()));
    }

    /**
     * Retrieves the layout pane for this page.
     *
     * @return the BorderPane layout
     */
    public BorderPane getLayout() {
        return layout;
    }
}
