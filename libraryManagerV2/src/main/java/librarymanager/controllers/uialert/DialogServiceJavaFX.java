package librarymanager.controllers.uialert;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import librarymanager.alert.DialogService;

import java.util.Optional;

/**
 * @class DialogServiceJavaFX
 * @brief Implementazione concreta del servizio di dialogo basata sulla libreria JavaFX.
 * @details Questa classe funge da "Adapter" tra l'interfaccia agnostica DialogService e il sistema di
 * finestre di dialogo nativo di JavaFX (Alert).
 *
 * @invariant L'istanza deve essere eseguita all'interno del JavaFX Application Thread.
 * @invariant La risorsa CSS "/librarymanager/alert.css" deve essere presente nel classpath per garantire lo styling corretto.
 * @invariant Le chiamate ai metodi sono bloccanti (showAndWait) per il flusso del controller chiamante.
 */
public class DialogServiceJavaFX implements DialogService {

    /**
     * @brief Visualizza un alert di errore in stile JavaFX.
     * @param msg Il messaggio di errore da visualizzare.
     * @pre msg != null.
     * @pre Deve essere invocato dal JavaFX Application Thread.
     * @post Apre una finestra modale di tipo Alert.AlertType.ERROR.
     * @post Applica la classe CSS "error" al DialogPane.
     * @post L'esecuzione attende la chiusura della finestra da parte dell'utente.
     */
    @Override
    public void mostraErrore(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);

        applicaStile(alert, "error");

        alert.showAndWait();
    }

    /**
     * @brief Visualizza un alert informativo.
     * @param msg Il messaggio informativo.
     * @pre msg != null.
     * @post Apre una finestra modale di tipo Alert.AlertType.INFORMATION.
     * @post Applica lo stile base definito in "alert.css".
     */
    @Override
    public void mostraInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);

        applicaStile(alert, null); // Nessuna classe aggiuntiva specifica, solo base

        alert.showAndWait();
    }

    /**
     * @brief Visualizza un avviso (Warning).
     * @param msg Il messaggio di avviso.
     * @pre msg != null.
     * @post Apre una finestra modale di tipo Alert.AlertType.WARNING.
     * @post Applica la classe CSS "warning" al DialogPane.
     */
    @Override
    public void mostraAvviso(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(msg);

        applicaStile(alert, "warning");

        alert.showAndWait();
    }

    /**
     * @brief Richiede una conferma all'utente tramite dialog JavaFX.
     * @param msg La domanda da porre all'utente.
     * @return true se l'utente preme OK, false altrimenti (Cancel o chiusura).
     * @pre msg != null.
     * @post Apre una finestra modale di tipo Alert.AlertType.CONFIRMATION.
     * @post Applica la classe CSS "confirmation".
     * @post Restituisce il controllo solo dopo la scelta dell'utente.
     */
    @Override
    public boolean chiediConferma(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(msg);

        applicaStile(alert, "confirmation");

        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * @brief Metodo helper per applicare i fogli di stile.
     * @details Centralizza la logica di caricamento del CSS per evitare duplicazioni e garantire consistenza.
     * @param alert L'oggetto Alert a cui applicare lo stile.
     * @param cssClass La classe CSS specifica da aggiungere (opzionale).
     * @pre alert != null.
     * @pre Il file "/librarymanager/alert.css" deve esistere.
     * @post Il DialogPane dell'alert ha il foglio di stile caricato e le classi "custom-alert" + cssClass applicate.
     */
    private void applicaStile(Alert alert, String cssClass) {
        DialogPane dialogPane = alert.getDialogPane();
        String cssPath = getClass().getResource("/librarymanager/alert.css").toExternalForm();
        dialogPane.getStylesheets().add(cssPath);

        dialogPane.getStyleClass().add("custom-alert");
        if (cssClass != null && !cssClass.isEmpty()) {
            dialogPane.getStyleClass().add(cssClass);
        }
    }
}