module Snake {
    requires javafx.controls;
    requires javafx.fxml;

    exports arena; // Export the arena package so JavaFX can access it
}
