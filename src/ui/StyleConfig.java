package ui;

public class StyleConfig {

    public static final String BACKGROUND_COLOR = "black";
    public static final String TEXT_COLOR = "limegreen";
    public static final String BUTTON_TEXT_COLOR = "limegreen";
    public static final String BUTTON_BACKGROUND_COLOR = "black";
    public static final String BUTTON_BORDER_COLOR = "limegreen";

    public static String getBaseButtonStyle() {
        return "-fx-background-color: " + BUTTON_BACKGROUND_COLOR + ";" +
               "-fx-text-fill: " + BUTTON_TEXT_COLOR + ";" +
               "-fx-font-family: 'Courier New';" +
               "-fx-font-size: 16px;" +
               "-fx-padding: 10px 20px;" +
               "-fx-border-color: " + BUTTON_BORDER_COLOR + ";" +
               "-fx-border-width: 3px;" +
               "-fx-border-radius: 0;" +
               "-fx-background-radius: 0;";
    }

    public static String getHoverButtonStyle() {
        return getBaseButtonStyle() + "-fx-border-width: 2px;";
    }

    public static String getClickButtonStyle() {
        return getBaseButtonStyle() + "-fx-border-width: 1px;";
    }

    public static String getBackButtonStyle() {
        return getBaseButtonStyle();
    }
}
