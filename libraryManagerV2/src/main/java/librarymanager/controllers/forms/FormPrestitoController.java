package librarymanager.controllers.forms;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import librarymanager.alert.DialogService;
import librarymanager.controllers.uialert.DialogServiceJavaFX;
import librarymanager.managers.GestoreLibro;
import librarymanager.managers.GestorePrestito;
import librarymanager.managers.GestoreUtente;
import librarymanager.models.*;
import librarymanager.validators.formvalidators.FormValidator;

import java.time.LocalDate;

public class FormPrestitoController {
    private GestorePrestito gestorePrestito;
    private GestoreUtente gestoreUtente;
    private GestoreLibro gestoreLibro;

    private FormValidator formValidator;
    private DialogService ds;
    private Callback<Prestito, Boolean> onSaveAction;

    @javafx.fxml.FXML
    private Label insModFld;
    @javafx.fxml.FXML
    private ComboBox<Utente> utentiCb;
    @javafx.fxml.FXML
    private DatePicker dataInizioDp;
    @javafx.fxml.FXML
    private Button salvaBtn;
    @javafx.fxml.FXML
    private ComboBox<Libro> libroCb;
    @javafx.fxml.FXML
    private DatePicker dataScadenzaDp;
    @javafx.fxml.FXML
    private Button annullaBtn;

    @FXML
    public void initialize() {
        this.formValidator = () -> {
            boolean utenteMancante = utentiCb.getValue() == null;
            boolean libroMancante = libroCb.getValue() == null;

            boolean dataInizioMancante = dataInizioDp.getValue() == null;
            boolean dataScadenzaMancante = dataScadenzaDp.getValue() == null;

            return utenteMancante || libroMancante || dataInizioMancante || dataScadenzaMancante;
        };

        this.ds = new DialogServiceJavaFX();

    }

    @javafx.fxml.FXML
    public void annullaNuovoPrestito(ActionEvent actionEvent) {
        chiudiFinestra();
    }

    @javafx.fxml.FXML
    public void salvaNuovoPrestito(ActionEvent actionEvent) {

        if (formValidator.isInvalid()) {
            ds.mostraErrore("Compila tutti i campi obbligatori.");
            return;
        }

        try {
            Prestito prestitoDalForm = new Prestito (
                    utentiCb.getSelectionModel().getSelectedItem(),
                    libroCb.getSelectionModel().getSelectedItem(),
                    dataScadenzaDp.getValue()
            );

            if (onSaveAction != null) {
                boolean esitoPositivo = onSaveAction.call(prestitoDalForm);

                if (esitoPositivo) {
                    chiudiFinestra();
                }
            }

        } catch (NumberFormatException e) {
            ds.mostraErrore("I campi Anno e Copie Totali devono essere numeri interi.");
        }
    }

    private void chiudiFinestra() {
        Stage stage = (Stage) salvaBtn.getScene().getWindow();
        stage.close();
    }

    public void setFormOnAggiungi() {
        utentiCb.setValue(null);
        libroCb.setValue(null);
        dataInizioDp.setValue(LocalDate.now());
        dataInizioDp.setEditable(false);
        dataScadenzaDp.getEditor().clear();


        insModFld.setText("Nuovo Prestito");
        salvaBtn.setText("Aggiungi");
    }

    public void setFormOnModifica(Prestito prestito) {
        utentiCb.setValue(prestito.getUtente());
        libroCb.setValue(prestito.getLibro());

        dataInizioDp.setValue(prestito.getDataInizio());
        dataInizioDp.setEditable(false);
        dataInizioDp.setDisable(true);

        dataScadenzaDp.setValue(prestito.getDataFinePrestabilita());

        salvaBtn.setText("Modifica");

        insModFld.setText("Modifica Libro");
    }

    public void setOnSaveAction(Callback<Prestito, Boolean> onSaveAction) {
        this.onSaveAction = onSaveAction;
    }

    public void init(GestoreLibro gestoreLibro, GestoreUtente gestoreUtente, GestorePrestito gestorePrestito) {
        this.gestoreLibro = gestoreLibro;
        this.gestoreUtente = gestoreUtente;
        this.gestorePrestito = gestorePrestito;

        setComboBox();
    }

    private void setComboBox() {
        FilteredList<Utente> utentiFiltrati = new FilteredList<>(FXCollections.observableArrayList(gestoreUtente.getLista()),
                 utente -> utente != null && (utente.getStato() != StatoUtente.ARCHIVIATO));

        FilteredList<Libro> libriFiltrati = new FilteredList<>(FXCollections.observableArrayList(gestoreLibro.getLista()),
                libro -> libro != null && libro.getStato() != StatoLibro.ARCHIVIATO);

        utentiCb.setItems(utentiFiltrati);
        libroCb.setItems(libriFiltrati);

        utentiCb.setPromptText("Selezionare  Utente");
        libroCb.setPromptText("Selezionare  Libro");

        //definire il modo in cui libri e utenti sono visualizzati nei combobox
        utentiCb.setConverter(new StringConverter<>() {
            @Override
            public String toString(Utente u) {
                if (u == null) return null;
                return u.getCognome() + " " + u.getNome() + " (" + u.getMatricola() + ")";
            }

            @Override
            public Utente fromString(String s) {
                return null;
            }

        });
        libroCb.setConverter(new StringConverter<>() {
            @Override
            public String toString(Libro l) {
                if (l == null) return null;
                return l.getTitolo() + " - " + l.getAutori();
            }

            @Override
            public Libro fromString(String string) {
                return null;
            }
        });
    }
}
