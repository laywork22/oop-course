package librarymanager.controllers;

import java.util.List;

public interface AreaPresenter {
    void onAggiungi();

    void onRimuovi();

    void onModifica();

    List<String> getCriteriOrdinamento();

    void ordina(String criterio);

    void filtra(String filtro);
}
