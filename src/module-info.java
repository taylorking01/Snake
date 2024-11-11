module Snake {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;

    exports arena; // Export the arena package so JavaFX can access it
    exports ui;
}
