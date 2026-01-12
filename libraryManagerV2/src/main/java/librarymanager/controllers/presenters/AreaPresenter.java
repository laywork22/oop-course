package librarymanager.controllers.presenters;

import java.util.List;

/**
 * @interface AreaPresenter
 * @brief Interfaccia per il controllo della visualizzazione delle tabelle.
 * @details Agisce come mediatore tra il PrimaryController e i gestori di business.
 * @invariant La vista deve essere sempre sincronizzata con lo stato dei manager dopo ogni operazione.
 */
public interface AreaPresenter extends Sortable, Filterable, Order, Editor{
    /**
     * @brief Forza il rinfresco dei dati nella tabella.
     * @post Gli Items della TableView corrispondono esattamente alla lista corrente del manager.
     */
    void ricarica();
}
