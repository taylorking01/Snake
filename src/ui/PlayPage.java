package ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayPage {

    private BorderPane layout;

    public PlayPage() {
        layout = new BorderPane();

        // Create a Back button in the bottom left corner
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> Window.changePage("mainmenu"));  // Go back to main menu

        HBox backButtonBox = new HBox(backButton);
        backButtonBox.setAlignment(Pos.BOTTOM_LEFT);  // Align the button at bottom left

        // Add the back button to the layout (positioned at the bottom)
        layout.setBottom(backButtonBox);

        // Create any other UI elements specific to the PlayPage here
        VBox contentBox = new VBox();  // Placeholder for game content
        contentBox.setAlignment(Pos.CENTER);  // Center the content

        // Add content to the layout
        layout.setCenter(contentBox);
    }

    // Return the layout to be used in Window class
    public BorderPane getLayout() {
        return layout;
    }
}
