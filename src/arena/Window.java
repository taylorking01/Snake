package arena;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Window extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Snake Game");

        // Create a BorderPane as the root layout
        BorderPane root = new BorderPane();

        // Set the window size to 600x600
        Scene scene = new Scene(root, 600, 600, Color.web("#1a1a1a"));  // Dark background

        // Create the arena (16x16 grid of tiles)
        Arena arena = new Arena(scene);

        // Define colors
        String darkGrey = "#1a1a1a";  // Very dark grey
        String mediumGrey = "#2f2f2f";  // Slightly lighter grey for the tiles
        String whiteText = "-fx-text-fill: white;";  // White text for buttons

        // Button Style: Subtle hover effects and stylish look
        String buttonStyle = "-fx-background-color: #3a3a3a; " +
                             "-fx-text-fill: white; " +
                             "-fx-font-size: 14px; " +
                             "-fx-padding: 8px 15px; " +
                             "-fx-border-radius: 5px; " +
                             "-fx-background-radius: 5px; " +
                             "-fx-border-color: #5a5a5a; " +
                             "-fx-border-width: 2px;" +
                             "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 1, 1);";  // Add shadow effect
        
        // Subtle hover effect: only change the background shade slightly
        String buttonHoverStyle = "-fx-background-color: #4a4a4a; " +  // Slightly lighter grey
                                  "-fx-border-color: #6a6a6a;" +       // Subtle change to border color
                                  "-fx-border-radius: 10px; " +        // Keep rounded corners
                                  "-fx-background-radius: 10px;";      // Keep background rounded

        // Create top pane
        HBox topPane = new HBox();
        topPane.setPadding(new Insets(10));
        topPane.setAlignment(Pos.CENTER);  // Center-align the buttons
        Button startButton = new Button("Start Game");
        startButton.setStyle(buttonStyle);
        startButton.setOnMouseEntered(e -> startButton.setStyle(buttonHoverStyle)); // Hover effect
        startButton.setOnMouseExited(e -> startButton.setStyle(buttonStyle));
        topPane.getChildren().add(startButton);
        topPane.setPrefHeight(80);  // Give the top pane a higher priority height
        topPane.setStyle("-fx-background-color: " + darkGrey + ";");  // Dark grey background

        // Create bottom pane
        HBox bottomPane = new HBox();
        bottomPane.setPadding(new Insets(10));
        bottomPane.setAlignment(Pos.CENTER);  // Center-align the buttons
        Button pauseButton = new Button("Pause Game");
        pauseButton.setStyle(buttonStyle);
        pauseButton.setOnMouseEntered(e -> pauseButton.setStyle(buttonHoverStyle));
        pauseButton.setOnMouseExited(e -> pauseButton.setStyle(buttonStyle));
        bottomPane.getChildren().add(pauseButton);
        bottomPane.setPrefHeight(80);  // Give the bottom pane a higher priority height
        bottomPane.setStyle("-fx-background-color: " + darkGrey + ";");  // Dark grey background

        // Create left pane
        VBox leftPane = new VBox();
        leftPane.setPadding(new Insets(10));
        leftPane.setAlignment(Pos.CENTER);  // Center-align the buttons
        Button settingsButton = new Button("Settings");
        settingsButton.setStyle(buttonStyle);
        settingsButton.setOnMouseEntered(e -> settingsButton.setStyle(buttonHoverStyle));
        settingsButton.setOnMouseExited(e -> settingsButton.setStyle(buttonStyle));
        leftPane.getChildren().add(settingsButton);
        leftPane.setPrefWidth(120);  // Give the left pane a higher priority width
        leftPane.setStyle("-fx-background-color: " + darkGrey + ";");  // Dark grey background

        // Create right pane
        VBox rightPane = new VBox();
        rightPane.setPadding(new Insets(10));
        rightPane.setAlignment(Pos.CENTER);  // Center-align the buttons
        Button statsButton = new Button("Stats");
        statsButton.setStyle(buttonStyle);
        statsButton.setOnMouseEntered(e -> statsButton.setStyle(buttonHoverStyle));
        statsButton.setOnMouseExited(e -> statsButton.setStyle(buttonStyle));
        rightPane.getChildren().add(statsButton);
        rightPane.setPrefWidth(120);  // Give the right pane a higher priority width
        rightPane.setStyle("-fx-background-color: " + darkGrey + ";");  // Dark grey background

        // Create a StackPane to contain the arena grid and ensure it's centered
        StackPane centerPane = new StackPane();
        centerPane.setPadding(new Insets(10));  // Padding around the grid
        centerPane.getChildren().add(arena.getGrid());
        centerPane.setStyle("-fx-background-color: " + darkGrey + ";");  // Dark grey for the background

        // Bind the size of the grid to 70% of the available window space and ensure it's square
        centerPane.maxWidthProperty().bind(Bindings.min(scene.widthProperty().multiply(0.6), scene.heightProperty().multiply(0.6)));
        centerPane.maxHeightProperty().bind(centerPane.maxWidthProperty());  // Ensure the grid remains square

        // Set the panes in their respective places
        root.setTop(topPane);
        root.setBottom(bottomPane);
        root.setLeft(leftPane);
        root.setRight(rightPane);
        root.setCenter(centerPane);  // The grid goes in the center of the BorderPane

        // Fix: Style the BorderPane background directly
        root.setStyle("-fx-background-color: " + darkGrey + ";");

        // Set the scene to the stage
        primaryStage.setScene(scene);
        primaryStage.show();  // Show the window
    }

    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }
}

