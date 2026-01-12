package librarymanager.alert;

/**
 * @interface DialogService
 * @brief Interfaccia per la gestione della comunicazione visiva con l'utente tramite finestre di dialogo.
 * @details Questa interfaccia applica il pattern "Service Wrapper" o "Adapter" per disaccoppiare i controller
 * dalla specifica libreria grafica (es. JavaFX Alert), facilitando il testing e la manutenibilità.
 *
 * @invariant Il servizio deve essere sempre disponibile a ricevere richieste di visualizzazione, indipendentemente dallo stato dell'applicazione.
 * @invariant Le finestre di dialogo generate devono essere bloccanti (modali) rispetto al flusso dell'interfaccia per garantire che l'utente prenda visione del messaggio.
 */
public interface DialogService {

    /**
     * @brief Visualizza un messaggio di errore critico.
     * @param msg Il testo descrittivo dell'errore.
     * @pre msg != null && !msg.isEmpty() (Idealmente, anche se l'implementazione potrebbe gestire null).
     * @post Viene mostrata a schermo una finestra di dialogo di tipo ERRORE contenente il messaggio 'msg'.
     * @post L'esecuzione del flusso UI è sospesa finché l'utente non chiude la finestra.
     */
    void mostraErrore(String msg);

    /**
     * @brief Visualizza un messaggio informativo di successo o notifica.
     * @param msg Il testo del messaggio informativo.
     * @pre msg != null.
     * @post Viene mostrata a schermo una finestra di dialogo di tipo INFORMAZIONE contenente 'msg'.
     * @post L'utente viene notificato dell'avvenuto completamento di un'operazione.
     */
    void mostraInfo(String msg);

    /**
     * @brief Visualizza un messaggio di avvertimento (Warning).
     * @details Usato per situazioni non bloccanti ma che richiedono attenzione (es. "Seleziona un elemento").
     * @param msg Il testo dell'avviso.
     * @pre msg != null.
     * @post Viene mostrata a schermo una finestra di dialogo di tipo AVVISO/WARNING contenente 'msg'.
     */
    void mostraAvviso(String msg);

    /**
     * @brief Richiede una conferma esplicita all'utente.
     * @details Solitamente utilizzata per operazioni distruttive (es. Cancellazione).
     * @param msg La domanda o il messaggio da mostrare all'utente.
     * @return true se l'utente ha confermato (es. cliccando "Sì" o "OK"), false altrimenti (es. "No", "Annulla" o chiusura finestra).
     * @pre msg != null.
     * @post Viene mostrata una finestra di dialogo con opzioni di scelta (es. OK/ANNULLA).
     * @post Il sistema attende l'input dell'utente prima di ritornare il valore booleano corrispondente.
     */
    boolean chiediConferma(String msg);
}
