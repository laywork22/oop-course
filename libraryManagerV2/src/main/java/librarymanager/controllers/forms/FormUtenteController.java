package librarymanager.controllers.forms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import librarymanager.alert.DialogService;
import librarymanager.controllers.presenters.FormController;
import librarymanager.controllers.uialert.DialogServiceJavaFX;
import librarymanager.managers.RegistroUtenti;
import librarymanager.models.Utente;
import librarymanager.validators.formvalidators.FormValidator;

public class FormUtenteController implements FormController<Utente> {
    private RegistroUtenti gestoreUtente;
    private FormValidator formValidator;
    private DialogService ds;
    private Callback<Utente, Boolean> onSaveAction;
    
    @javafx.fxml.FXML
    private TextField nomeFld;
    @javafx.fxml.FXML
    private TextField cognomeFld;
    @javafx.fxml.FXML
    private Button salvaBtn;
    @javafx.fxml.FXML
    private Label insModLbl;
    @javafx.fxml.FXML
    private Button annullaBtn;
    @javafx.fxml.FXML
    private TextField matricolaFld;
    @javafx.fxml.FXML
    private TextField emailFld;
    @FXML
    private Label pAttiviLbl;
    @FXML
    private TextField pAttiviFld;

    @FXML
    public void initialize() {
        this.formValidator = () -> {
            boolean formSpaziVuoti = nomeFld.getText().isBlank() ||
                    cognomeFld.getText().isBlank() ||
                    matricolaFld.getText().isBlank() ||
                    emailFld.getText().isBlank();
            
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
        Utente utenteDalForm = new Utente(nomeFld.getText(), cognomeFld.getText(), emailFld.getText(), matricolaFld.getText());

        if (onSaveAction != null) {
            boolean esitoPositivo = onSaveAction.call(utenteDalForm);

            if (esitoPositivo) {
                chiudiFinestra();
            }
        }
    }

    @javafx.fxml.FXML
    public void handleAnnulla(ActionEvent actionEvent) {
        chiudiFinestra();
    }


    private void chiudiFinestra() {
        Stage stage = (Stage) salvaBtn.getScene().getWindow();
        stage.close();
    }

    @Override
    public void setFormOnAggiungi() {
        nomeFld.clear();
        cognomeFld.clear();
        emailFld.clear();
        matricolaFld.clear();

        matricolaFld.setDisable(false);

        pAttiviFld.setVisible(false);
        pAttiviLbl.setVisible(false);

        insModLbl.setText("Nuovo Utente");
        salvaBtn.setText("Aggiungi");
    }

    @Override
    public void setFormOnModifica(Utente utente) {
        nomeFld.setText(utente.getNome());
        cognomeFld.setText(utente.getCognome());
        matricolaFld.setText(utente.getMatricola());
        emailFld.setText(utente.getEmail());

        pAttiviLbl.setVisible(true);
        pAttiviFld.setVisible(true);

        salvaBtn.setText("Modifica");

        matricolaFld.setDisable(true);

        insModLbl.setText("Modifica Utente");
    }

    @Override
    public void setOnSaveAction(Callback<Utente, Boolean> onSaveAction) {
        this.onSaveAction = onSaveAction;
    }

}
