package librarymanager.models;

import java.util.List;

public class ArchivioDati {
    private List<Libro> listaLibri;
    private List<Utente> listaUtenti;
    private List<Prestito> listaPrestiti;

    public ArchivioDati(List<Libro> listaLibri, List<Utente> listaUtenti, List<Prestito> listaPrestiti) {
        this.listaLibri = listaLibri;
        this.listaUtenti = listaUtenti;
        this.listaPrestiti = listaPrestiti;
    }

    public List<Libro> getListaLibri() {
        return listaLibri;
    }

    public void setListaLibri(List<Libro> listaLibri) {
        this.listaLibri = listaLibri;
    }

    public List<Prestito> getListaPrestiti() {
        return listaPrestiti;
    }

    public void setListaPrestiti(List<Prestito> listaPrestiti) {
        this.listaPrestiti = listaPrestiti;
    }

    public List<Utente> getListaUtenti() {
        return listaUtenti;
    }

    public void setListaUtenti(List<Utente> listaUtenti) {
        this.listaUtenti = listaUtenti;
    }
}
