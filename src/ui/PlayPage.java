package ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class PlayPage {

    private BorderPane layout;

    public PlayPage() {
        layout = new BorderPane();

        // Create the back button
        Button backButton = new Button("Back");
        backButton.setStyle(StyleConfig.getBackButtonStyle());  // Apply back button style

        // Apply hover and click effects
        backButton.setOnMouseEntered(e -> backButton.setStyle(StyleConfig.getHoverButtonStyle()));
        backButton.setOnMouseExited(e -> backButton.setStyle(StyleConfig.getBackButtonStyle()));
        backButton.setOnMousePressed(e -> backButton.setStyle(StyleConfig.getClickButtonStyle()));
        backButton.setOnMouseReleased(e -> backButton.setStyle(StyleConfig.getHoverButtonStyle()));

        HBox backButtonBox = new HBox(backButton);
        backButtonBox.setAlignment(Pos.BOTTOM_LEFT);  // Align the back button at bottom left
        layout.setBottom(backButtonBox);
    }

    public BorderPane getLayout() {
        return layout;
    }
}
