package arena;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Window extends Application{
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Snake Game"); //Set title of window
		
		StackPane root = new StackPane();

		Scene scene = new Scene(root, 600, 600); //Set width: 400, height: 400
		Arena arena = new Arena(); //Create arena (16x16 grid of tiles
		
		root.getChildren().add(arena.getGrid());
		
		primaryStage.setScene(scene); //Set scene to the stage (Window)
		primaryStage.show(); //Show the window
	}

	public static void main(String[] args) {
		launch(args); //Launch the JavaFX application
	}

}
