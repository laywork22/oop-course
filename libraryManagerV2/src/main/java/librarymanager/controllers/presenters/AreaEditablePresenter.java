package librarymanager.controllers.presenters;

/**
 * @interface AreaPresenter
 * @brief Interfaccia per il controllo della visualizzazione delle tabelle.
 * @details Agisce come mediatore tra il PrimaryController e i gestori di business.
 * @invariant La vista deve essere sempre sincronizzata con lo stato dei manager dopo ogni operazione.
 */
public interface AreaEditablePresenter extends AreaPresenter, Editable {

}
