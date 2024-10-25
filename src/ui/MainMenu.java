package ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class MainMenu {

    private VBox layout;
    private Label title;
    private String baseText;

    public MainMenu() {
        // Define the base ASCII art with initial characters
        baseText = 
            "  ██████  ███▄    █  ▄▄▄       ██ ▄█▀▓█████  \n" +
            "▒██    ▒  ██ ▀█   █ ▒████▄     ██▄█▒ ▓█   ▀  \n" +
            "░ ▓██▄   ▓██  ▀█ ██▒▒██  ▀█▄  ▓███▄░ ▒███    \n" +
            "  ▒   ██▒▓██▒  ▐▌██▒░██▄▄▄▄██ ▓██ █▄ ▒▓█  ▄  \n" +
            "▒██████▒▒▒██░   ▓██░ ▓█   ▓██▒▒██▒ █▄░▒████▒ \n" +
            "▒ ▒▓▒ ▒ ░░ ▒░   ▒ ▒  ▒▒   ▓▒█░▒ ▒▒ ▓▒░░ ▒░ ░\n" +
            "░ ░▒  ░ ░░ ░░   ░ ▒░  ▒   ▒▒ ░░ ░▒ ▒░ ░ ░  ░\n" +
            "░  ░  ░     ░   ░ ░   ░   ▒   ░ ░░ ░    ░   \n" +
            "      ░           ░       ░  ░░  ░      ░  ░\n";

        // Create the Label for ASCII art and set the initial text
        title = new Label(baseText);
        title.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        title.setTextFill(StyleConfig.TEXT_COLOR);
        title.setAlignment(Pos.CENTER);

        // Initialize the play and train buttons
        Button playButton = new Button("Play");
        Button trainButton = new Button("Train");
        playButton.setPrefWidth(150);
        trainButton.setPrefWidth(150);

        // Attach actions to buttons
        playButton.setOnAction(event -> Window.changePage("play"));
        trainButton.setOnAction(event -> {
            // Future action for the train button
        });

     // Apply the base styling for square, beveled buttons
        String baseButtonStyle = "-fx-background-color: black;" +
                                 "-fx-text-fill: limegreen;" +
                                 "-fx-font-family: 'Courier New';" +  // Vintage font
                                 "-fx-font-size: 16px;" +
                                 "-fx-padding: 10px 20px;" +
                                 "-fx-border-color: limegreen;" +
                                 "-fx-border-width: 3px;" +  // Thicker border for strong bevel effect
                                 "-fx-border-radius: 0;" +
                                 "-fx-background-radius: 0;" +
                                 "-fx-effect: dropshadow(gaussian, limegreen, 5, 0, 3, 3);" +  // Outer green bevel
                                 "-fx-effect: innershadow(two-pass-box, limegreen, 7, 0.5, -3, -3);";  // Inner shadow

        // Apply the hover styling for when the mouse is over the button (reduce bevel size)
        String hoverButtonStyle = "-fx-background-color: black;" +
                                  "-fx-text-fill: limegreen;" +
                                  "-fx-font-family: 'Courier New';" +  // Vintage font
                                  "-fx-font-size: 16px;" +
                                  "-fx-padding: 10px 20px;" +
                                  "-fx-border-color: limegreen;" +
                                  "-fx-border-width: 2px;" +  // Reduce border width for subtle bevel effect
                                  "-fx-border-radius: 0;" +
                                  "-fx-background-radius: 0;" +
                                  "-fx-effect: dropshadow(gaussian, limegreen, 3, 0, 1, 1);" +  // Slightly smaller bevel shadow
                                  "-fx-effect: innershadow(two-pass-box, limegreen, 5, 0.5, -2, -2);";  // Subtle inner shadow

     // Apply the click effect style (makes the button look pressed)
        String clickButtonStyle = "-fx-background-color: black;" +
                                  "-fx-text-fill: limegreen;" +
                                  "-fx-font-family: 'Courier New';" +  // Vintage font
                                  "-fx-font-size: 16px;" +
                                  "-fx-padding: 10px 20px;" +
                                  "-fx-border-color: limegreen;" +
                                  "-fx-border-width: 1px;" +  // Even thinner bevel when clicked
                                  "-fx-border-radius: 0;" +
                                  "-fx-background-radius: 0;" +
                                  "-fx-effect: dropshadow(gaussian, limegreen, 2, 0, 1, 1);" +  // Subtle pressed shadow
                                  "-fx-effect: innershadow(two-pass-box, limegreen, 3, 0.5, -1, -1);";  // Reduced inner shadow
        
        // Set the default style to both buttons
        playButton.setStyle(baseButtonStyle);
        trainButton.setStyle(baseButtonStyle);

        // Set the hover effect for play button
        playButton.setOnMouseEntered(e -> playButton.setStyle(hoverButtonStyle));
        playButton.setOnMouseExited(e -> playButton.setStyle(baseButtonStyle));

        // Set the hover effect for train button
        trainButton.setOnMouseEntered(e -> trainButton.setStyle(hoverButtonStyle));
        trainButton.setOnMouseExited(e -> trainButton.setStyle(baseButtonStyle));
        
     // Set the click effect for play button
        playButton.setOnMousePressed(e -> playButton.setStyle(clickButtonStyle));
        playButton.setOnMouseReleased(e -> playButton.setStyle(hoverButtonStyle));  // Go back to hover style

        // Set the click effect for train button
        trainButton.setOnMousePressed(e -> trainButton.setStyle(clickButtonStyle));
        trainButton.setOnMouseReleased(e -> trainButton.setStyle(hoverButtonStyle));  // Go back to hover style

        // Arrange elements in layout
        layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: " + Window.toHex(StyleConfig.BACKGROUND_COLOR) + ";");
        layout.getChildren().addAll(title, playButton, trainButton);

        // Start the animation to alternate characters in the ASCII text
        startTextAnimation();
    }

    private void startTextAnimation() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.4), event -> updateText()));
        timeline.setCycleCount(Timeline.INDEFINITE);  // Loop indefinitely
        timeline.play();
    }

    private void updateText() {
        // Replace characters ░, ▒, ▓ with each other in a cyclic manner
        String newText = title.getText()
            .replace('░', '\u0001')  // Temporary placeholder
            .replace('▒', '░')
            .replace('▓', '▒')
            .replace('\u0001', '▓');  // Replace the placeholder with '▓'

        title.setText(newText);
    }

    public VBox getLayout() {
        return layout;
    }
}
