package ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TrainPage {

    private BorderPane layout;

    public TrainPage() {
        layout = new BorderPane();
        
        //PRA Mode button
        Button PRAButton = new Button("Progressive Reinforcement Agent");
        applyButtonStyles(PRAButton, 250);
        
        // Define action for the button to navigate to the PRApage
        PRAButton.setOnAction(e -> {
            toggleButtonEffect(PRAButton);
            Window.changePage("PRApage");  // Navigate to PRApage
        });
        
        //Center PRA button
        VBox centerBox = new VBox(PRAButton);
        centerBox.setAlignment(Pos.CENTER);
        layout.setCenter(centerBox);

        //Back button
        // Create the back button and apply the base style from StyleConfig
        Button backButton = new Button("Back");
        applyButtonStyles(backButton, 100);

        // Define action for the back button to navigate to the main menu
        backButton.setOnAction(e -> {
            toggleButtonEffect(backButton);
            Window.changePage("mainmenu");  // Navigate back to main menu
        });

        HBox backButtonBox = new HBox(backButton);
        backButtonBox.setAlignment(Pos.BOTTOM_LEFT);  // Align the back button at bottom left
        layout.setBottom(backButtonBox);
    }

    // Helper method to apply styles and effects to buttons
    private void applyButtonStyles(Button button, int width) {
        button.setPrefWidth(width);  // Set a preferred width for back button
        button.setStyle(StyleConfig.getBaseButtonStyle());

        button.setOnMouseEntered(e -> button.setStyle(StyleConfig.getHoverButtonStyle()));
        button.setOnMouseExited(e -> button.setStyle(StyleConfig.getBaseButtonStyle()));
        button.setOnMousePressed(e -> button.setStyle(StyleConfig.getClickButtonStyle()));
        button.setOnMouseReleased(e -> button.setStyle(StyleConfig.getHoverButtonStyle()));
    }

    // Method to create a toggle effect for visual feedback
    private void toggleButtonEffect(Button button) {
        button.setStyle(StyleConfig.getClickButtonStyle());
        // Reset style to hover style shortly after click
        button.setOnMouseReleased(e -> button.setStyle(StyleConfig.getHoverButtonStyle()));
    }

    public BorderPane getLayout() {
        return layout;
    }
}
