package librarymanager.managers;

import librarymanager.exceptions.BibliotecaException;
import librarymanager.exceptions.LibroException;
import librarymanager.exceptions.PrestitoException;
import librarymanager.exceptions.UtenteException;

import java.util.List;
import java.util.Map;

public interface Registro<K, T> {
    void aggiungi(T elem) throws BibliotecaException;
    void rimuovi(K chiave) throws BibliotecaException;
    void modifica(T vecchio, T nuovo) throws BibliotecaException;
    T cerca(K chiave);
    void setMappa(Map<K, T> m);
    List<T> getLista();
}
