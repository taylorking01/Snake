package arena;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Window extends Application{
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Snake Game"); //Set title of window
		
		Pane root = new Pane();

		Scene scene = new Scene(root, 400, 400); //Set width: 400, height: 400
		primaryStage.setScene(scene); //Set scene to the stage (Window)
		primaryStage.show(); //Show the window
	}

	public static void main(String[] args) {
		launch(args); //Launch the JavaFX application
	}

}
