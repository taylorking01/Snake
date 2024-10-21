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
import javafx.stage.Stage;

public class Window extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Snake Game");

        // Create a BorderPane as the root layout
        BorderPane root = new BorderPane();

        // Set the window size to 600x600
        Scene scene = new Scene(root, 600, 600);

        // Create the arena (16x16 grid of tiles)
        Arena arena = new Arena(scene);

        // Create top pane
        HBox topPane = new HBox();
        topPane.setPadding(new Insets(10));
        topPane.setAlignment(Pos.CENTER);  // Center-align the buttons
        Button startButton = new Button("Start Game");
        topPane.getChildren().add(startButton);
        topPane.setPrefHeight(80);  // Give the top pane a higher priority height

        // Create bottom pane
        HBox bottomPane = new HBox();
        bottomPane.setPadding(new Insets(10));
        bottomPane.setAlignment(Pos.CENTER);  // Center-align the buttons
        Button pauseButton = new Button("Pause Game");
        bottomPane.getChildren().add(pauseButton);
        bottomPane.setPrefHeight(80);  // Give the bottom pane a higher priority height

        // Create left pane
        VBox leftPane = new VBox();
        leftPane.setPadding(new Insets(10));
        leftPane.setAlignment(Pos.CENTER);  // Center-align the buttons
        Button settingsButton = new Button("Settings");
        leftPane.getChildren().add(settingsButton);
        leftPane.setPrefWidth(120);  // Give the left pane a higher priority width

        // Create right pane
        VBox rightPane = new VBox();
        rightPane.setPadding(new Insets(10));
        rightPane.setAlignment(Pos.CENTER);  // Center-align the buttons
        Button statsButton = new Button("Stats");
        rightPane.getChildren().add(statsButton);
        rightPane.setPrefWidth(120);  // Give the right pane a higher priority width

        // Create a StackPane to contain the arena grid and ensure it's centered
        StackPane centerPane = new StackPane();
        centerPane.setPadding(new Insets(10));  // Padding around the grid
        centerPane.getChildren().add(arena.getGrid());

        // Bind the size of the grid to 70% of the available window space and ensure it's square
        centerPane.maxWidthProperty().bind(Bindings.min(scene.widthProperty().multiply(0.6), scene.heightProperty().multiply(0.6)));
        centerPane.maxHeightProperty().bind(centerPane.maxWidthProperty());  // Ensure the grid remains square

        // Set the panes in their respective places
        root.setTop(topPane);
        root.setBottom(bottomPane);
        root.setLeft(leftPane);
        root.setRight(rightPane);
        root.setCenter(centerPane);  // The grid goes in the center of the BorderPane

        // Set the scene to the stage
        primaryStage.setScene(scene);
        primaryStage.show();  // Show the window
    }

    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }
}
