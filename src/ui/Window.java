package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Window extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Set window title
        primaryStage.setTitle("Blank Window");

        // Create a blank scene with no content
        Scene scene = new Scene(new javafx.scene.layout.Pane(), 400, 300); // Empty pane, width: 400, height: 300

        // Set the scene to the stage
        primaryStage.setScene(scene);

        // Show the window
        primaryStage.show();
    }

    // Main entry point
    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }
}
