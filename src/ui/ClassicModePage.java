package ui;

import arena.Arena;
import arena.ClassicMode;
import controller.Timer;
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

/**
 * The ClassicModePage class represents the UI for the Classic Mode of the Snake game.
 * It integrates the Arena for rendering and delegates game logic to the ClassicMode class.
 */
public class ClassicModePage implements GameListener {
    private final BorderPane layout;            // The main layout pane
    private final Arena arena;                  // The game arena for rendering
    private final ClassicMode classicMode;      // The game logic handler
    private final Scene scene;                  // The JavaFX scene for handling user input

    // UI Components for Timer, Apple Counter, and Start Button
    private Label timerLabel;                   // Label to display the timer
    private Label appleCounterLabel;            // Label to display the apple count
    private Button startButton;                 // Single instance of start button

    // New Timer Instance
    private Timer gameTimer;

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

        // Initialize the Timer for a 60-second countdown
        this.gameTimer = new Timer(60, 0); // Count down from 60 to 0
        this.gameTimer.addTimeUpdateListener(this::updateTimerLabel);
        this.gameTimer.addTimerCompleteListener(this::onTimerComplete);

        // Initialize and set up the UI components
        setupUI();
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

        // Initialize the single instance of the "Start Game" button
        startButton = new Button("Start Game");
        applyButtonStyles(startButton, 200);

        // Define action for the "Start Game" button
        startButton.setOnAction(e -> {
            startCountdown("Start Game");
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
            if (gameTimer != null) gameTimer.stop();
            if (classicMode.isRunning()) classicMode.stopGame("Game Stopped by User.");
            Window.changePage("playpage");
        });

        // Position the "Back" button at the bottom left
        VBox backButtonBox = new VBox(backButton);
        backButtonBox.setAlignment(Pos.BOTTOM_LEFT);
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
        timerLabel = new Label("01:00"); // Initialize with 60 seconds
        timerLabel.setStyle("-fx-font-size: 16px; -fx-background-color: white; -fx-border-color: black; -fx-padding: 5px;");
        timerLabel.setMinWidth(60);
        timerLabel.setAlignment(Pos.CENTER);

        Rectangle appleIcon = new Rectangle(15, 15, Color.RED);
        appleIcon.setStroke(Color.BLACK);
        appleCounterLabel = new Label("0");
        appleCounterLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        HBox appleCounterBox = new HBox(5, appleIcon, appleCounterLabel);
        appleCounterBox.setAlignment(Pos.CENTER_LEFT);

        HBox topBox = new HBox(20, timerLabel, appleCounterBox);
        topBox.setAlignment(Pos.CENTER_LEFT);
        topBox.setPadding(new Insets(10, 10, 10, 10));

        return topBox;
    }

    /**
     * Starts the countdown for the game start.
     *
     * @param buttonText the initial text of the button ("Start Game" or "Play Again")
     */
    private void startCountdown(String buttonText) {
        startButton.setDisable(true);
        startButton.setVisible(true);
        startButton.setText(buttonText);
        appleCounterLabel.setText("0");

        Timer countdownTimer = new Timer(5, 0); // 5-second countdown
        countdownTimer.addTimeUpdateListener(seconds -> {
            Platform.runLater(() -> startButton.setText(String.valueOf(seconds)));
        });
        countdownTimer.addTimerCompleteListener(() -> {
            Platform.runLater(() -> {
                startButton.setVisible(false);  // Hide button after countdown
                gameTimer.reset();
                gameTimer.start();
                classicMode.startGame();
            });
        });
        countdownTimer.start();
    }

    /**
     * Formats the elapsed time in "MM:SS" format.
     *
     * @param seconds the total elapsed seconds
     * @return a formatted time string
     */
    private String formatTimeForCountdown(int seconds) {
        return String.format("%d", seconds);
    }

    /**
     * Applies consistent styles to buttons.
     *
     * @param button the Button to style
     * @param width  the preferred width of the button
     */
    private void applyButtonStyles(Button button, int width) {
        button.setPrefWidth(width);  
        button.setStyle(StyleConfig.getBaseButtonStyle());
        button.setOnMouseEntered(e -> button.setStyle(StyleConfig.getHoverButtonStyle()));
        button.setOnMouseExited(e -> button.setStyle(StyleConfig.getBaseButtonStyle()));
        button.setOnMousePressed(e -> button.setStyle(StyleConfig.getClickButtonStyle()));
        button.setOnMouseReleased(e -> button.setStyle(StyleConfig.getHoverButtonStyle()));
    }

    /**
     * Called when the game is over. Updates the UI to show the "Play Again" button.
     *
     * @param message the message associated with the game end
     */
    @Override
    public void onGameOver(String message) {
        Platform.runLater(() -> {
            gameTimer.stop();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();

            startButton.setText("Play Again");
            startButton.setDisable(false);
            startButton.setVisible(true);
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

    /**
     * Updates the timer label based on the Timer's formatted time.
     *
     * @param formattedTime the formatted time string from the Timer
     */
    private void updateTimerLabel(String formattedTime) {
        Platform.runLater(() -> timerLabel.setText(formattedTime));
    }
    
    /**
     * In the event the timer is complete.
     */
    private void onTimerComplete() {
        Platform.runLater(() -> {
            classicMode.stopGame("Time's up! Game Over.");
        });
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
