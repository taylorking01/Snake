module Snake {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;

    exports arena; // Export the arena package so JavaFX can access it
}
