package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class Window extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        Window.primaryStage = primaryStage;  // Store the reference to primaryStage

        primaryStage.setTitle("Snake Game");

        BorderPane root = new BorderPane();

        // Load the main menu
        MainMenu mainMenu = new MainMenu();
        root.setCenter(mainMenu.getLayout());

        // Use Color.valueOf() to convert string color to Color object
        Color myCol = Color.valueOf(StyleConfig.BACKGROUND_COLOR);
        root.setStyle("-fx-background-color: " + toHex(myCol) + ";");

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Static method to change between different pages
    public static void changePage(String pageName) {
        BorderPane root = new BorderPane();  // New root layout

        Scene scene = primaryStage.getScene(); // Access current scene
        
        switch (pageName.toLowerCase()) {
            case "mainmenu":
            default:
                MainMenu mainMenu = new MainMenu();
                root.setCenter(mainMenu.getLayout());  // Load the main menu layout
                break;
            case "playpage":
                PlayPage playPage = new PlayPage();
                root.setCenter(playPage.getLayout());  // Load the play page layout
                break;
            case "classicmodepage":
                ClassicModePage classicModePage = new ClassicModePage(scene); // Pass scene to ClassicModePage
                root.setCenter(classicModePage.getLayout());
                break;
        }

        // Set background color
        Color myCol = Color.valueOf(StyleConfig.BACKGROUND_COLOR);
        root.setStyle("-fx-background-color: " + toHex(myCol) + ";");

        // Update the root layout in the current scene
        scene.setRoot(root);
    }


    // Static helper method to convert Color to hex string for CSS styling
    public static String toHex(Color color) {
        return String.format("#%02X%02X%02X",
            (int)(color.getRed() * 255),
            (int)(color.getGreen() * 255),
            (int)(color.getBlue() * 255));
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }
}
