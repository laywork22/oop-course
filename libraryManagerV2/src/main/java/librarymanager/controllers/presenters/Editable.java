package librarymanager.controllers.presenters;

public interface Editable {
    /**
     * @brief Innesca la procedura di creazione di un nuovo elemento.
     * @details Solitamente apre una finestra modale (Form).
     * @post Se l'operazione nel form ha successo, la tabella viene aggiornata
     * riflettendo il nuovo elemento inserito nel manager.
     */
    void onAggiungi();

    /**
     * @brief Innesca la procedura di rimozione dell'elemento selezionato.
     * @pre Un elemento deve essere selezionato nella TableView.
     * @post Se confermato, l'elemento viene rimosso dal manager e la tabella rinfrescata.
     */
    void onRimuovi();

    /**
     * @brief Innesca la procedura di modifica dell'elemento selezionato.
     * @pre Un elemento deve essere selezionato nella TableView.
     * @post I dati dell'elemento nel manager vengono aggiornati e la vista sincronizzata.
     */
    void onModifica();


}
