package librarymanager.controllers.presenters;

import java.util.List;

/**
 * @interface AreaPresenter
 * @brief Interfaccia per il controllo della visualizzazione delle tabelle.
 * @details Agisce come mediatore tra il PrimaryController e i gestori di business.
 * @invariant La vista deve essere sempre sincronizzata con lo stato dei manager dopo ogni operazione.
 */
public interface AreaPresenter {
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

    /**
     * @brief Forza il rinfresco dei dati nella tabella.
     * @post Gli Items della TableView corrispondono esattamente alla lista corrente del manager.
     */
    void ricarica();

    /**
     * @brief Restituisce i nomi dei criteri di ordinamento disponibili.
     * @return Una lista di stringhe rappresentanti i criteri (es. "Titolo (A-Z)").
     */
    List<String> getCriteriOrdinamento();

    /**
     * @brief Applica un ordinamento alla tabella.
     * @param criterio Stringa corrispondente a una chiave della mappaOrdinamento.
     * @pre criterio != null e presente tra quelli restituiti da getCriteriOrdinamento().
     * @post La tabella visualizza gli elementi ordinati secondo la logica specificata.
     */
    void ordina(String criterio);

    /**
     * @brief Filtra gli elementi visualizzati.
     * @param filtro Stringa di ricerca (case-insensitive).
     * @post La tabella mostra solo gli elementi che contengono la stringa in uno
     * dei campi definiti dalla logica di filtraggio specifica.
     */
    void filtra(String filtro);
}
