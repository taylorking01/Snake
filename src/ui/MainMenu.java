package ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;

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

        // Style the ASCII text (centered and green)
        title.setFont(Font.font("Courier New", FontWeight.BOLD, 20));  // ASCII text font size
        title.setTextFill(Color.LIMEGREEN);  // Set the text color to green
        title.setAlignment(Pos.CENTER);

        // Create the Play and Train buttons
        Button playButton = new Button("Play");
        Button trainButton = new Button("Train");

        // Set button sizes and styles
        playButton.setPrefWidth(150);
        trainButton.setPrefWidth(150);

        // Align the buttons and title in a vertical box layout (VBox)
        layout = new VBox(20);  // Spacing of 20 between elements
        layout.setAlignment(Pos.CENTER);  // Center the elements
        layout.getChildren().addAll(title, playButton, trainButton);  // Add all elements to the VBox
    }

    // Return the layout to be used in the Window scene
    public VBox getLayout() {
        return layout;
    }
}
