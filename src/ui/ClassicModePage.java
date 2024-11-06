package ui;

import arena.Arena;
import arena.ClassicMode;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * The ClassicModePage class represents the UI for the Classic Mode of the Snake game.
 * It integrates the Arena for rendering and delegates game logic to the ClassicMode class.
 */
public class ClassicModePage {
    private final BorderPane layout;            // The main layout pane
    private final Arena arena;                  // The game arena for rendering
    private final ClassicMode classicMode;      // The game logic handler
    private final Scene scene;                  // The JavaFX scene for handling user input
    private Timeline countdownTimeline;         // Timeline for countdown

    /**
     * Constructs a ClassicModePage object.
     *
     * @param scene           the Scene instance for handling user input
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

        // Ensure the game does not start automatically
        // classicMode.startGame(); // Removed to prevent automatic start
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

        // "Start Game" button setup
        Button startButton = new Button("Start Game");
        applyButtonStyles(startButton, 200);

        // Define action for the "Start Game" button
        startButton.setOnAction(e -> {
            startCountdown(startButton);
        });

        // Combine the arena and "Start Game" button in a VBox to center them together
        VBox centerBox = new VBox(20, centerPane, startButton); // 20px spacing
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(20, 0, 0, 0));

        // Set the combined VBox as the center of the layout
        layout.setCenter(centerBox);

        // "Back" button setup
        Button backButton = new Button("Back");
        applyButtonStyles(backButton, 100);

        // Define action for the "Back" button to navigate to the main menu
        backButton.setOnAction(e -> {
            // Stop the countdown if it's running
            if (countdownTimeline != null) {
                countdownTimeline.stop();
            }

            // Stop the game if it's running
            if (classicMode.isRunning()) {
                classicMode.stopGame("Game Stopped by User.");
            }

            // Navigate back to main menu
            Window.changePage("mainmenu");
        });

        // Position the "Back" button at the bottom left
        VBox backButtonBox = new VBox(backButton);
        backButtonBox.setAlignment(Pos.BOTTOM_LEFT);  // Align at bottom left
        backButtonBox.setPadding(new Insets(10));
        layout.setBottom(backButtonBox);
    }

    /**
     * Starts the countdown for the game start.
     *
     * @param startButton the "Start Game" button to update during the countdown
     */
    private void startCountdown(Button startButton) {
        // Disable the button to prevent multiple clicks
        startButton.setDisable(true);

        // Create a Timeline with specific KeyFrames for each countdown step
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> startButton.setText("3")),
            new KeyFrame(Duration.seconds(2), e -> startButton.setText("2")),
            new KeyFrame(Duration.seconds(3), e -> startButton.setText("1")),
            new KeyFrame(Duration.seconds(4), e -> {
                startButton.setText("Go!");
                // Remove the start button and start the game
                VBox centerBox = (VBox) layout.getCenter();
                centerBox.getChildren().remove(startButton);

                // Start the game
                classicMode.startGame();
            })
        );

        // Optionally, add an onFinished handler if you need to perform actions after the timeline ends
        timeline.setOnFinished(e -> {
            // Cleanup or additional actions can be performed here
        });

        timeline.play();
        countdownTimeline = timeline; // Assign to the instance variable for control
    }

    /**
     * Applies consistent styles to buttons.
     *
     * @param button the Button to style
     * @param width  the preferred width of the button
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
     * Retrieves the layout pane for this page.
     *
     * @return the BorderPane layout
     */
    public BorderPane getLayout() {
        return layout;
    }
}
