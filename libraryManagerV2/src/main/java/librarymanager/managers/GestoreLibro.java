package librarymanager.managers;

import librarymanager.controllers.exceptions.LibroException;
import librarymanager.models.Libro;
import librarymanager.models.StatoLibro;
import librarymanager.validators.modelvalidator.LibroValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestoreLibro implements Gestore<String, Libro>{
    private Map<String, Libro> mappaLibri;

    public GestoreLibro() {
        this.mappaLibri = new HashMap<>();
    }

    public GestoreLibro(Map<String, Libro> listaLibri) {
        this.mappaLibri = listaLibri;
    }

    @Override
    public void aggiungi(Libro elem) throws LibroException {
        if (elem == null) return;

        if (!LibroValidator.isISBNValido(elem.getIsbn())) {
            throw new LibroException("LibroException: L'ISBN non è in un formato corretto");
        }
        else if (!LibroValidator.isCopieTotaliValido(elem.getCopieTotali())) {
            throw new LibroException("LibroException: Il numero di copie totali non può essere minore di zero");

        }

        Libro libroEsistente = cerca(elem.getIsbn());

        if (libroEsistente == null) {
            mappaLibri.put(elem.getIsbn(), elem);
        }
        else {
            if (libroEsistente.getStato() == StatoLibro.ARCHIVIATO) {
                libroEsistente.setStato(StatoLibro.IN_ARCHIVIO);
            }
            else {
                libroEsistente.setCopieTotali(libroEsistente.getCopieTotali() + elem.getCopieTotali());
            }
        }
    }

    @Override
    public void rimuovi(String chiave) throws LibroException {
        if (chiave == null) return;

        Libro libroDaRimuovere = cerca(chiave);

        if (libroDaRimuovere == null) {
            throw new LibroException("LibroException: Impossibile rimuovere un libro non esistente!");

        }

        if (libroDaRimuovere.getStato() == StatoLibro.ARCHIVIATO) {
            throw new LibroException("LibroException: Il libro è già stato archiviato. Impossibile rimuovere.");
        }

        if (libroDaRimuovere.getCopieDisponibili() != libroDaRimuovere.getCopieTotali()) {
            throw new LibroException("LibroException: Il libro ha ancora copie in prestito");
        }

        libroDaRimuovere.setStato(StatoLibro.ARCHIVIATO);
    }

    @Override
    public void modifica(Libro vecchio, Libro nuovo) throws LibroException {
        if (vecchio == null || nuovo == null) return;

        if (!LibroValidator.isLibroValido(nuovo.getIsbn(), nuovo.getCopieTotali())) {
            throw new LibroException("LibroException: Il libro non è in un formato corretto.");
        }

        Libro libroDaModificare = cerca(vecchio.getIsbn());
        if (libroDaModificare == null) {
            throw new LibroException("LibroException: Libro originale non trovato");
        }

        if (libroDaModificare.getStato() == StatoLibro.ARCHIVIATO) {
            throw new LibroException("LibroException: Il libro non è attivo. Impossibile modificare");

        }

        int copieInPrestito = vecchio.getCopieTotali() - vecchio.getCopieDisponibili();

        if (copieInPrestito > nuovo.getCopieTotali()) {
            throw new LibroException("LibroException: Impossibile ridurre le copie totali a " + nuovo.getCopieTotali() +
                    " perché ci sono " + copieInPrestito + " copie ancora in prestito");

        }

        int nuoveCopieDisponibili = nuovo.getCopieTotali() - copieInPrestito;

        if (!vecchio.getIsbn().equals(nuovo.getIsbn())) {
            if (mappaLibri.containsKey(nuovo.getIsbn())) {
                throw new LibroException("LibroException: Il nuovo ISBN inserito esiste già per un altro libro!");

            }
            mappaLibri.remove(vecchio.getIsbn());

            aggiornaCampiLibro(libroDaModificare, nuovo, nuoveCopieDisponibili);

            mappaLibri.put(nuovo.getIsbn(), libroDaModificare);
        } else {
            aggiornaCampiLibro(libroDaModificare, nuovo, nuoveCopieDisponibili);
        }
    }

    private void aggiornaCampiLibro(Libro destinazione, Libro fonte, int nuoveCopieDisponibili) {
        destinazione.setTitolo(fonte.getTitolo());
        destinazione.setAutori(fonte.getAutori());
        destinazione.setAnno(fonte.getAnno());
        destinazione.setIsbn(fonte.getIsbn());
        destinazione.setCopieTotali(fonte.getCopieTotali());
        destinazione.setCopieDisponibili(nuoveCopieDisponibili);
    }

    @Override
    public Libro cerca(String chiave) {
        Libro trovato = null;

        if (mappaLibri.containsKey(chiave)) {
            trovato = mappaLibri.get(chiave);
        }

        return trovato;
    }

    @Override
    public void setMappa(Map<String, Libro> m) {
        mappaLibri = m;
    }

    @Override
    public List<Libro> getLista() {
        return new ArrayList<>(mappaLibri.values());
    }
}
