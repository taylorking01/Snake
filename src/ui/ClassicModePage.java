package ui;

import arena.Arena;
import arena.ClassicMode;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * The ClassicModePage class represents the UI for the Classic Mode of the Snake game.
 * It integrates the Arena for rendering and delegates game logic to the ClassicMode class.
 */
public class ClassicModePage implements GameListener { // Implement GameListener
    private final BorderPane layout;            // The main layout pane
    private final Arena arena;                  // The game arena for rendering
    private final ClassicMode classicMode;      // The game logic handler
    private final Scene scene;                  // The JavaFX scene for handling user input
    private Timeline countdownTimeline;         // Timeline for countdown

    // UI Components for Timer and Apple Counter
    private Label timerLabel;                   // Label to display the timer
    private Timeline timerTimeline;             // Timeline to update the timer
    private int elapsedSeconds;                 // Counter for elapsed seconds

    private Label appleCounterLabel;            // Label to display the apple count

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

        // Initialize ClassicMode with the arena, scene, game speed, and GameListener
        this.classicMode = new ClassicMode(arena, scene, gameSpeedMillis, this);

        // Initialize timer variables
        this.elapsedSeconds = 0;

        // Set up the UI components
        setupUI();

        // Ensure the game does not start automatically
        // classicMode.startGame(); // Removed to prevent automatic start
    }

    /**
     * Sets up the UI components, including the game arena and control buttons.
     */
    private void setupUI() {
        // Top section for Timer and Apple Counter
        HBox topBox = createTopBox();

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
            startCountdown(startButton, "Start Game");
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

            // Stop the timer if it's running
            if (timerTimeline != null) {
                timerTimeline.stop();
            }

            // Stop the game if it's running
            if (classicMode.isRunning()) {
                classicMode.stopGame("Game Stopped by User.");
            }

            // Navigate back to main menu
            Window.changePage("playpage");
        });

        // Position the "Back" button at the bottom left
        VBox backButtonBox = new VBox(backButton);
        backButtonBox.setAlignment(Pos.BOTTOM_LEFT);  // Align at bottom left
        layout.setBottom(backButtonBox);

        // Set the topBox in the top region of the layout
        layout.setTop(topBox);
    }

    /**
     * Creates the top section of the UI containing the Timer and Apple Counter.
     *
     * @return an HBox containing the Timer and Apple Counter
     */
    private HBox createTopBox() {
        // Timer Display
        timerLabel = new Label("00:00");
        timerLabel.setStyle("-fx-font-size: 16px; -fx-background-color: white; -fx-border-color: black; -fx-padding: 5px;");
        timerLabel.setMinWidth(60);
        timerLabel.setAlignment(Pos.CENTER);

        // Apple Counter Display
        Rectangle appleIcon = new Rectangle(15, 15, Color.RED);
        appleIcon.setStroke(Color.BLACK);
        appleCounterLabel = new Label("0");
        appleCounterLabel.setStyle("-fx-font-size: 16px;");

        HBox appleCounterBox = new HBox(5, appleIcon, appleCounterLabel);
        appleCounterBox.setAlignment(Pos.CENTER_LEFT);

        // Combine Timer and Apple Counter in an HBox
        HBox topBox = new HBox(20, timerLabel, appleCounterBox);
        topBox.setAlignment(Pos.CENTER_LEFT);
        topBox.setPadding(new Insets(10, 10, 10, 10));

        return topBox;
    }

    /**
     * Starts the countdown for the game start.
     *
     * @param startButton the button to update during the countdown
     * @param buttonText  the initial text of the button ("Start Game" or "Play Again")
     */
    private void startCountdown(Button startButton, String buttonText) {
        // Disable the button to prevent multiple clicks
        startButton.setDisable(true);

        // Reset button text
        startButton.setText(buttonText);

        // Create a Timeline with specific KeyFrames for each countdown step
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> startButton.setText("3")),
            new KeyFrame(Duration.seconds(2), e -> startButton.setText("2")),
            new KeyFrame(Duration.seconds(3), e -> startButton.setText("1")),
            new KeyFrame(Duration.seconds(4), e -> startButton.setText("Go!")),
            new KeyFrame(Duration.seconds(5), e -> {
                // Remove the start button and start the game
                VBox centerBox = (VBox) layout.getCenter();
                centerBox.getChildren().remove(startButton);

                // Start the timer
                startTimer();

                // Start the game
                classicMode.startGame();
            })
        );

        timeline.play();
        countdownTimeline = timeline; // Assign to the instance variable for control
    }

    /**
     * Starts the game timer.
     */
    private void startTimer() {
        elapsedSeconds = 0;
        timerLabel.setText(formatTime(elapsedSeconds));

        timerTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            elapsedSeconds++;
            timerLabel.setText(formatTime(elapsedSeconds));
        }));
        timerTimeline.setCycleCount(Timeline.INDEFINITE);
        timerTimeline.play();
    }

    /**
     * Stops the game timer.
     */
    private void stopTimer() {
        if (timerTimeline != null) {
            timerTimeline.stop();
        }
    }

    /**
     * Formats the elapsed time in "MM:SS" format.
     *
     * @param totalSeconds the total elapsed seconds
     * @return a formatted time string
     */
    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
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

    /**
     * Called when the game is over. Updates the UI to show the "Play Again" button.
     *
     * @param message the message associated with the game end
     */
    @Override
    public void onGameOver(String message) {
        Platform.runLater(() -> {
            // Stop the timer
            stopTimer();

            // Show an alert dialog with the game over message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();

            // Recreate the "Play Again" button
            Button playAgainButton = new Button("Play Again");
            applyButtonStyles(playAgainButton, 200);

            // Define action for the "Play Again" button
            playAgainButton.setOnAction(e -> {
                startCountdown(playAgainButton, "Play Again");
            });

            // Add the "Play Again" button back to the center VBox
            VBox centerBox = (VBox) layout.getCenter();
            centerBox.getChildren().add(playAgainButton);

            // Reset timer and apple counter labels
            timerLabel.setText("00:00");
            appleCounterLabel.setText("0");
        });
    }

    /**
     * Called when an apple is eaten by the snake. Updates the apple counter.
     *
     * @param appleCount the total number of apples eaten so far
     */
    @Override
    public void onAppleEaten(int appleCount) {
        Platform.runLater(() -> {
            appleCounterLabel.setText(String.valueOf(appleCount));
        });
    }
}
