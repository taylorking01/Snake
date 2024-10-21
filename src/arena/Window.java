package arena;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class Window extends Application{
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Snake Game"); //Set title of window
		
		BorderPane root = new BorderPane();

		Scene scene = new Scene(root, 600, 600); //Set width: 400, height: 400
		
		Arena arena = new Arena(scene); //Create arena (16x16 grid of tiles
		
		HBox topPane = new HBox();
		topPane.getChildren().add(new Button("Start Game"));
		HBox bottomPane = new HBox();
		bottomPane.getChildren().add(new Button("Pause Game"));
		
		VBox leftPane = new VBox();
		leftPane.getChildren().add(new Button("Settings"));
		VBox rightPane = new VBox();
		rightPane.getChildren().add(new Button("Stats"));
		
		StackPane arenaContainer = new StackPane();
		arenaContainer.getChildren().add(arena.getGrid());
		root.setCenter(arenaContainer);
		
		root.setTop(topPane);
		root.setBottom(bottomPane);
		root.setLeft(leftPane);
		root.setRight(rightPane);
				
		primaryStage.setScene(scene); //Set scene to the stage (Window)
		primaryStage.show(); //Show the window
	}

	public static void main(String[] args) {
		launch(args); //Launch the JavaFX application
	}

}
