package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
        root.setStyle("-fx-background-color: " + toHex(StyleConfig.BACKGROUND_COLOR) + ";");

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Static method to change between different pages
    public static void changePage(String pageName) {
        BorderPane root = new BorderPane();  // New root layout

        switch (pageName.toLowerCase()) {
            case "play":
                PlayPage playPage = new PlayPage();
                root.setCenter(playPage.getLayout());  // Load the play page layout
                break;
            case "mainmenu":
            default:
                MainMenu mainMenu = new MainMenu();
                root.setCenter(mainMenu.getLayout());  // Load the main menu layout
                break;
        }

        // Style the root background color consistently
        root.setStyle("-fx-background-color: " + toHex(StyleConfig.BACKGROUND_COLOR) + ";");

        // Update the scene in the primaryStage
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
    }

    // Static helper method to convert Color to hex string for CSS styling
    public static String toHex(javafx.scene.paint.Color color) {
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
