package librarymanager.controllers.presenters;


public interface AreaPresenter extends Sortable, Filterable{
    /**
     * @brief Forza il rinfresco dei dati nella tabella.
     * @post Gli Items della TableView corrispondono esattamente alla lista corrente del manager.
     */
    void ricarica();
}
