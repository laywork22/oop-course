package librarymanager.managers;

import librarymanager.exceptions.LibroException;
import librarymanager.exceptions.PrestitoException;
import librarymanager.exceptions.UtenteException;

import java.util.List;
import java.util.Map;

public interface Gestore<K, T> {
    void aggiungi(T elem) throws UtenteException, PrestitoException, LibroException;
    void rimuovi(K chiave) throws UtenteException, LibroException;
    void modifica(T vecchio, T nuovo) throws UtenteException, PrestitoException, LibroException;
    T cerca(K chiave);
    void setMappa(Map<K, T> m);
    List<T> getLista();
}
