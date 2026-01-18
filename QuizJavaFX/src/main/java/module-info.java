module gestionesami {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    opens gestionesami to javafx.fxml;
    exports gestionesami;
}