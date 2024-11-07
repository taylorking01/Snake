package ui;

import arena.Arena;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The PRApage class represents the UI for training the Progressive Reinforcement Agent.
 * It integrates the Arena for rendering and includes a timer, apple counter, and a "Train Agent" button.
 */
public class PRAPage {
    private final BorderPane layout;            // The main layout pane
    private final Arena arena;                  // The game arena for rendering
    private final Scene scene;                  // The JavaFX scene for handling user input
    private Label timerLabel;                   // Label to display the timer
    private Label appleCounterLabel;            // Label to display the apple count
    private Button trainAgentButton;            // Button to start agent training

    /**
     * Constructs a PRApage object.
     *
     * @param scene the Scene instance for handling user input
     */
    public PRAPage(Scene scene) {
        this.layout = new BorderPane();
        this.scene = scene;

        // Initialize the Arena with the scene
        this.arena = new Arena(scene);

        // Set up the UI components
        setupUI();
    }

    /**
     * Sets up the UI components, including the timer, apple counter, and control buttons.
     */
    private void setupUI() {
        // Top section for Timer and Apple Counter
        HBox topBox = createTopBox();

        // Center the arena grid
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

        // Initialize the "Train Agent" button
        trainAgentButton = new Button("Train Agent");
        applyButtonStyles(trainAgentButton, 200);

        // Set action for the "Train Agent" button
        trainAgentButton.setOnAction(e -> {
            // Placeholder action for training
            startTraining();
        });

        // Combine arena and "Train Agent" button in a VBox for center alignment
        VBox centerBox = new VBox(20, centerPane, trainAgentButton); // 20px spacing
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(20, 0, 0, 0));

        layout.setCenter(centerBox);

        // "Back" button setup
        Button backButton = new Button("Back");
        applyButtonStyles(backButton, 100);

        backButton.setOnAction(e -> {
            // Navigate to the main menu
            Window.changePage("playpage");
        });

        VBox backButtonBox = new VBox(backButton);
        backButtonBox.setAlignment(Pos.BOTTOM_LEFT);
        layout.setBottom(backButtonBox);

        // Set topBox in the top region of the layout
        layout.setTop(topBox);
    }

    /**
     * Creates the top section of the UI containing the Timer and Apple Counter.
     *
     * @return an HBox containing the Timer and Apple Counter
     */
    private HBox createTopBox() {
        timerLabel = new Label("00:00");
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
     * Starts training for the Progressive Reinforcement Agent.
     */
    private void startTraining() {
        // Placeholder for agent training logic
        System.out.println("Training agent...");
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
