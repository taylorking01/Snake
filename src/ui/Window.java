package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Window extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Set window title
        primaryStage.setTitle("Snake Game");

        // Create a root layout (BorderPane)
        BorderPane root = new BorderPane();

        // Create the main menu
        MainMenu mainMenu = new MainMenu();

        // Set the main menu layout at the center of the root
        root.setCenter(mainMenu.getLayout());

        // Create a scene with the root layout
        Scene scene = new Scene(root, 600, 400);

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
