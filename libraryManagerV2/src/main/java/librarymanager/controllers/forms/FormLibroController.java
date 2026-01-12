package librarymanager.controllers.forms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import librarymanager.alert.DialogService;
import librarymanager.controllers.uialert.DialogServiceJavaFX;
import librarymanager.managers.GestoreLibro;
import librarymanager.models.Libro;
import librarymanager.validators.formvalidators.FormValidator;

public class FormLibroController {
    private GestoreLibro gestoreLibro;
    private FormValidator formValidator;
    private DialogService ds;
    private Callback<Libro, Boolean> onSaveAction;

    @javafx.fxml.FXML
    private TextField annoFld;
    @javafx.fxml.FXML
    private TextField titoloFld;
    @javafx.fxml.FXML
    private TextField autoreFld;
    @javafx.fxml.FXML
    private Button salvaLibroBtn;
    @javafx.fxml.FXML
    private TextField isbnFld;
    @javafx.fxml.FXML
    private AnchorPane insertBookContent;
    @javafx.fxml.FXML
    private Label insModLbl;
    @javafx.fxml.FXML
    private TextField copieTotaliFld;
    @javafx.fxml.FXML
    private Button annullaBtn;

    @FXML
    public void initialize() {
        this.formValidator = () -> {

            boolean formSpaziVuoti = titoloFld.getText().isBlank() ||
                    autoreFld.getText().isBlank() ||
                    annoFld.getText().isBlank() ||
                    isbnFld.getText().isBlank() ||
                    copieTotaliFld.getText().isBlank();

            return formSpaziVuoti;
        };

        this.ds = new DialogServiceJavaFX();

    }

    @javafx.fxml.FXML
    public void handleSalva(ActionEvent actionEvent) {
        if (formValidator.isInvalid()) {
            ds.mostraErrore("Compila tutti i campi obbligatori.");
            return;
        }

        try {
            int anno = Integer.parseInt(annoFld.getText());
            int copie = Integer.parseInt(copieTotaliFld.getText());

            Libro libroDalForm = new Libro(
                    titoloFld.getText(),
                    autoreFld.getText(),
                    anno,
                    isbnFld.getText(),
                    copie
            );

            if (onSaveAction != null) {
                boolean esitoPositivo = onSaveAction.call(libroDalForm);

                if (esitoPositivo) {
                    chiudiFinestra();
                }
            }

        } catch (NumberFormatException e) {
            ds.mostraErrore("I campi Anno e Copie Totali devono essere numeri interi.");
        }

    }


    @javafx.fxml.FXML
    public void handleAnnulla(ActionEvent actionEvent) {
        chiudiFinestra();
    }

    private void chiudiFinestra() {
        Stage stage = (Stage) salvaLibroBtn.getScene().getWindow();
        stage.close();
    }

    public void setFormOnAggiungi() {
        titoloFld.clear();
        autoreFld.clear();
        isbnFld.clear();
        copieTotaliFld.clear();


        insModLbl.setText("Nuovo Libro");
        salvaLibroBtn.setText("Aggiungi");

        isbnFld.setDisable(false);
    }

    public void setFormOnModifica(Libro libro) {
        titoloFld.setText(libro.getTitolo());
        autoreFld.setText(libro.getAutori());
        isbnFld.setText(libro.getIsbn());
        isbnFld.setText(String.valueOf(libro.getCopieTotali()));

        isbnFld.setDisable(true);

        salvaLibroBtn.setText("Modifica");

        insModLbl.setText("Modifica Libro");
    }

    public void setOnSaveAction(Callback<Libro, Boolean> onSaveAction) {
        this.onSaveAction = onSaveAction;
    }
}
