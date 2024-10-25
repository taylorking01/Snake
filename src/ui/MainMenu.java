package ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainMenu {

    private VBox layout;

    public MainMenu() {
        // Create the large centered ASCII text for "SNAKE"
        Label title = new Label(
            "  ██████  ███▄    █  ▄▄▄       ██ ▄█▀▓█████  \n" +
            "▒██    ▒  ██ ▀█   █ ▒████▄     ██▄█▒ ▓█   ▀  \n" +
            "░ ▓██▄   ▓██  ▀█ ██▒▒██  ▀█▄  ▓███▄░ ▒███    \n" +
            "  ▒   ██▒▓██▒  ▐▌██▒░██▄▄▄▄██ ▓██ █▄ ▒▓█  ▄  \n" +
            "▒██████▒▒▒██░   ▓██░ ▓█   ▓██▒▒██▒ █▄░▒████▒ \n" +
            "▒ ▒▓▒ ▒ ░░ ▒░   ▒ ▒  ▒▒   ▓▒█░▒ ▒▒ ▓▒░░ ▒░ ░\n" +
            "░ ░▒  ░ ░░ ░░   ░ ▒░  ▒   ▒▒ ░░ ░▒ ▒░ ░ ░  ░\n" +
            "░  ░  ░     ░   ░ ░   ░   ▒   ░ ░░ ░    ░   \n" +
            "      ░           ░       ░  ░░  ░      ░  ░\n"
        );

        // Style the ASCII text (centered and green using StyleConfig)
        title.setFont(Font.font("Courier New", FontWeight.BOLD, 20));  // ASCII text font size
        title.setTextFill(StyleConfig.TEXT_COLOR);  // Set the text color to the style config's green
        title.setAlignment(Pos.CENTER);

        // Create the Play and Train buttons
        Button playButton = new Button("Play");
        Button trainButton = new Button("Train");

        // Style the buttons (set text and background colors from StyleConfig)
        playButton.setStyle("-fx-background-color: " + Window.toHex(StyleConfig.BUTTON_BACKGROUND_COLOR) +
                            "; -fx-text-fill: " + Window.toHex(StyleConfig.BUTTON_TEXT_COLOR) + ";");
        trainButton.setStyle("-fx-background-color: " + Window.toHex(StyleConfig.BUTTON_BACKGROUND_COLOR) +
                            "; -fx-text-fill: " + Window.toHex(StyleConfig.BUTTON_TEXT_COLOR) + ";");

        // Set button sizes
        playButton.setPrefWidth(150);
        trainButton.setPrefWidth(150);

        // Attach action listeners for buttons
        playButton.setOnAction(event -> Window.changePage("play"));
        trainButton.setOnAction(event -> {
            // Add relevant action for the train button later
        });

        // Align the buttons and title in a vertical box layout (VBox)
        layout = new VBox(20);  // Spacing of 20 between elements
        layout.setAlignment(Pos.CENTER);  // Center the elements
        layout.setStyle("-fx-background-color: " + Window.toHex(StyleConfig.BACKGROUND_COLOR) + ";");  // Set background color

        // Add all elements to the VBox
        layout.getChildren().addAll(title, playButton, trainButton);
    }

    // Return the layout to be used in the Window scene
    public VBox getLayout() {
        return layout;
    }
}
