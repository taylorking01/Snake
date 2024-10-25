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

        title = new Label(baseText);
        title.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        title.setTextFill(Color.valueOf(StyleConfig.TEXT_COLOR));
        title.setAlignment(Pos.CENTER);

        // Initialize buttons
        Button playButton = new Button("Play");
        Button trainButton = new Button("Train");

        // Set base styles from StyleConfig
        applyButtonStyles(playButton);
        applyButtonStyles(trainButton);

        // Define actions for the play button click
        playButton.setOnAction(e -> {
        	toggleButtonEffect(playButton);
            Window.changePage("playpage");
        });

        // Arrange elements in layout
        layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: " + Window.toHex(Color.valueOf(StyleConfig.BACKGROUND_COLOR)) + ";");
        layout.getChildren().addAll(title, playButton, trainButton);

        // Start ASCII animation
        startTextAnimation();
    }

    // Helper method to apply styles and effects to buttons
    private void applyButtonStyles(Button button) {
        button.setPrefWidth(150);
        button.setStyle(StyleConfig.getBaseButtonStyle());

        button.setOnMouseEntered(e -> button.setStyle(StyleConfig.getHoverButtonStyle()));
        button.setOnMouseExited(e -> button.setStyle(StyleConfig.getBaseButtonStyle()));
        button.setOnMousePressed(e -> button.setStyle(StyleConfig.getClickButtonStyle()));
        button.setOnMouseReleased(e -> button.setStyle(StyleConfig.getHoverButtonStyle()));
    }

    // Method to create a toggle effect
    private void toggleButtonEffect(Button button) {
        button.setStyle(StyleConfig.getClickButtonStyle());
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> button.setStyle(StyleConfig.getHoverButtonStyle())));
        timeline.play();
    }

    private void startTextAnimation() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> updateText()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateText() {
        String newText = title.getText()
            .replace('░', '\u0001')
            .replace('▒', '░')
            .replace('▓', '▒')
            .replace('\u0001', '▓');
        title.setText(newText);
    }

    public VBox getLayout() {
        return layout;
    }
}
