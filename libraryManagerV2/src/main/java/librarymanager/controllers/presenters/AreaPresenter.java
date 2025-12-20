package librarymanager.controllers.presenters;

import java.util.List;

public interface AreaPresenter {
    void onAggiungi();

    void onRimuovi();

    void onModifica();

    void ricarica();

    List<String> getCriteriOrdinamento();

    void ordina(String criterio);

    void filtra(String filtro);
}
