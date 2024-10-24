package arena;

import controller.GameController;

import javafx.application.Application;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * The Window class is responsible for setting up the main game window for the Snake game.
 * It extends JavaFX's Application class and arranges various panes for the game's user interface.
 */
public class Window extends Application {

    /**
    /* Private methods follow
    /**/

    /**
     * Initializes and starts the primary stage (the main window) for the Snake game.
     *
     * @param primaryStage the main stage for this application, provided by JavaFX
     *
     * This method sets up the layout of the game window, creates an instance of the game controller,
     * and binds the grid's visual appearance to the window's size. It also configures buttons and event handling.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Snake Game");

        // Create a BorderPane as the root layout
        BorderPane root = new BorderPane();

        // Set the window size to 700x600
        Scene scene = new Scene(root, 700, 600, Color.web("#1a1a1a"));  // Dark background

        // Create the arena (16x16 grid of tiles)
        Arena arena = new Arena(scene);
        
        // Create a game controller
        GameController controller = new GameController(arena);

        // Define colors
        String darkGrey = "#1a1a1a";  // Very dark grey
        String mediumGrey = "#2f2f2f";  // Slightly lighter grey for the tiles
        String whiteText = "-fx-text-fill: white;";  // White text for buttons

        // Set up the style for buttons
        String buttonStyle = "-fx-background-color: #3a3a3a; " +
                             "-fx-text-fill: white; " +
                             "-fx-font-size: 14px; " +
                             "-fx-padding: 8px 15px; " +
                             "-fx-border-radius: 10px; " +  // Rounded corners
                             "-fx-background-radius: 10px; " +
                             "-fx-border-color: #5a5a5a; " +
                             "-fx-border-width: 2px;";

        // Subtle hover effect for buttons
        String buttonHoverStyle = "-fx-background-color: #4a4a4a; " +  // Slightly lighter grey
                                  "-fx-border-color: #6a6a6a;" +       // Subtle change to border color
                                  "-fx-border-radius: 10px; " +        // Keep rounded corners
                                  "-fx-background-radius: 10px;";      // Keep background rounded
        
        // Click effect for buttons
        String buttonClickStyle = "-fx-background-color: #2a2a2a; " +  // Darker grey
                                  "-fx-border-color: #7a7a7a;" +       // Slightly lighter border
                                  "-fx-border-radius: 10px; " +
                                  "-fx-background-radius: 10px;";

        // Create and configure the top pane with the "Start Game" button
        HBox topPane = new HBox();
        topPane.setPadding(new Insets(10));
        topPane.setAlignment(Pos.CENTER);  // Center-align the button
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> controller.startGame());
        startButton.setOnMouseEntered(e -> startButton.setStyle(buttonHoverStyle));
        startButton.setOnMouseExited(e -> startButton.setStyle(buttonStyle));
        startButton.setOnMousePressed(e -> startButton.setStyle(buttonClickStyle));
        startButton.setOnMouseReleased(e -> startButton.setStyle(buttonHoverStyle));  // Return to hover effect when released
        topPane.getChildren().add(startButton);
        topPane.setPrefHeight(80);  // Give the top pane a higher priority height
        topPane.setStyle("-fx-background-color: " + darkGrey + ";");  // Dark grey background

        // Create and configure the bottom pane with the "Pause Game" button
        HBox bottomPane = new HBox();
        bottomPane.setPadding(new Insets(10));
        bottomPane.setAlignment(Pos.CENTER);  // Center-align the button
        Button pauseButton = new Button("Pause Game");
        pauseButton.setOnMouseEntered(e -> pauseButton.setStyle(buttonHoverStyle));
        pauseButton.setOnMouseExited(e -> pauseButton.setStyle(buttonStyle));
        pauseButton.setOnMousePressed(e -> pauseButton.setStyle(buttonClickStyle));
        pauseButton.setOnMouseReleased(e -> pauseButton.setStyle(buttonHoverStyle));
        bottomPane.getChildren().add(pauseButton);
        bottomPane.setPrefHeight(80);  // Give the bottom pane a higher priority height
        bottomPane.setStyle("-fx-background-color: " + darkGrey + ";");  // Dark grey background

        // Create and configure the left pane with the "Settings" button
        VBox leftPane = new VBox();
        leftPane.setPadding(new Insets(10));
        leftPane.setAlignment(Pos.CENTER);  // Center-align the button
        Button settingsButton = new Button("Settings");
        settingsButton.setOnMouseEntered(e -> settingsButton.setStyle(buttonHoverStyle));
        settingsButton.setOnMouseExited(e -> settingsButton.setStyle(buttonStyle));
        settingsButton.setOnMousePressed(e -> settingsButton.setStyle(buttonClickStyle));
        settingsButton.setOnMouseReleased(e -> settingsButton.setStyle(buttonHoverStyle));
        leftPane.getChildren().add(settingsButton);
        leftPane.setPrefWidth(120);  // Give the left pane a higher priority width
        leftPane.setStyle("-fx-background-color: " + darkGrey + ";");  // Dark grey background

        // Create right pane
        VBox rightPane = new VBox();
        rightPane.setPadding(new Insets(10));
        rightPane.setAlignment(Pos.CENTER);  // Center-align the buttons
        Button trainButton = new Button("Train AI");
        //trainButton.setOnAction(e -> controller.startTraining());
        trainButton.setOnMouseEntered(e -> trainButton.setStyle(buttonHoverStyle));
        trainButton.setOnMouseExited(e -> trainButton.setStyle(buttonStyle));
        trainButton.setOnMousePressed(e -> trainButton.setStyle(buttonClickStyle));
        trainButton.setOnMouseReleased(e -> trainButton.setStyle(buttonHoverStyle));
        rightPane.getChildren().add(trainButton);
        rightPane.setPrefWidth(120);  // Give the right pane a higher priority width
        rightPane.setStyle("-fx-background-color: " + darkGrey + ";");  // Dark grey background


        // Create a StackPane to contain the arena grid and ensure it's centered
        StackPane centerPane = new StackPane();
        centerPane.setPadding(new Insets(10));  // Padding around the grid
        centerPane.getChildren().add(arena.getGrid());
        centerPane.setStyle("-fx-background-color: " + darkGrey + ";");  // Dark grey background

        // Bind the size of the grid to 70% of the available window space and ensure it's square
        centerPane.maxWidthProperty().bind(Bindings.min(scene.widthProperty().multiply(0.6), scene.heightProperty().multiply(0.6)));
        centerPane.maxHeightProperty().bind(centerPane.maxWidthProperty());  // Ensure the grid remains square

        // Set the panes in their respective places within the BorderPane
        root.setTop(topPane);
        root.setBottom(bottomPane);
        root.setLeft(leftPane);
        root.setRight(rightPane);
        root.setCenter(centerPane);  // The grid goes in the center of the BorderPane

        // Fix: Style the BorderPane background directly
        root.setStyle("-fx-background-color: " + darkGrey + ";");

        // Add event filter to handle key presses for controlling the snake
        scene.addEventFilter(KeyEvent.KEY_PRESSED, controller::handleKeyPress);
        
        // Set the scene to the stage and display the window
        primaryStage.setScene(scene);
        primaryStage.show();  // Show the window
    }

    /**
    /* Public methods follow
    /**/

    /**
     * The main entry point for the Snake game, launching the JavaFX application.
     *
     * @param args command line arguments passed to the application
     */
    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }
}
