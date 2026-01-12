package librarymanager.controllers.presenters;

import java.util.List;

public interface Sortable {
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
}
