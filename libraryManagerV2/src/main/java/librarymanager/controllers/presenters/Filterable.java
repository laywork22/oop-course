package librarymanager.controllers.presenters;

public interface Filterable {
    /**
     * @brief Filtra gli elementi visualizzati.
     * @param filtro Stringa di ricerca (case-insensitive).
     * @post La tabella mostra solo gli elementi che contengono la stringa in uno
     * dei campi definiti dalla logica di filtraggio specifica.
     */
    void filtra(String filtro);
}
