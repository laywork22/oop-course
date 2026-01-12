package librarymanager.controllers.presenters;

import javafx.util.Callback;

public interface FormController<T> {
    /** Definisce l'azione da eseguire alla pressione del tasto 'Salva' */
    void setOnSaveAction(Callback<T, Boolean> onSaveAction);

    /** Configura il form per l'inserimento di un nuovo elemento */
    void setFormOnAggiungi();

    /** Configura il form per la modifica di un elemento esistente */
    void setFormOnModifica(T item);

}
