package librarymanager.controllers.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

import java.util.Optional;

public class DialogServiceJavaFX implements DialogService {
    @Override
    public void mostraErrore(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/librarymanager/alert.css").toExternalForm());

        String cssAlertType = "error";

        dialogPane.getStyleClass().addAll("custom-alert",cssAlertType);

        alert.showAndWait();
    }

    @Override
    public void mostraInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/librarymanager/alert.css").toExternalForm());



        dialogPane.getStyleClass().addAll("custom-alert");

        alert.showAndWait();
    }

    @Override
    public void mostraAvviso(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(msg);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/librarymanager/alert.css").toExternalForm());

        String cssAlertType = "warning";

        dialogPane.getStyleClass().addAll("custom-alert", cssAlertType);

        alert.showAndWait();
    }

    @Override
    public boolean chiediConferma(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(msg);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/librarymanager/alert.css").toExternalForm());

        String cssAlertType = "confirmation";

        dialogPane.getStyleClass().addAll("custom-alert", cssAlertType);

        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
