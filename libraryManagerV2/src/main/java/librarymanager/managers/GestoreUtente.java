package librarymanager.managers;

import librarymanager.exceptions.UtenteException;
import librarymanager.models.StatoUtente;
import librarymanager.models.Utente;
import librarymanager.validators.modelvalidator.UtenteValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestoreUtente implements Gestore<String, Utente> {
    private Map<String, Utente> mappaUtenti;

    public GestoreUtente() {
        this.mappaUtenti = new HashMap<>();
    }

    public GestoreUtente(Map<String, Utente> mappaUtenti) {
        this.mappaUtenti = mappaUtenti;
    }


    @Override
    public void aggiungi(Utente elem) throws UtenteException {
        if (elem == null) return;

        if (!UtenteValidator.isMatricolaValida(elem.getMatricola())) {
            throw new UtenteException("UtenteException: La matricola è in un formato non valido");
        }
        else if (!UtenteValidator.isEmailValida(elem.getEmail())) {
            throw new UtenteException("UtenteException: L'email è in un formato non valido");
        }

        Utente utenteEsistente = cerca(elem.getMatricola());

        if (utenteEsistente == null) {
            mappaUtenti.put(elem.getMatricola(),elem);
        }
        else {
            if (utenteEsistente.getStato() == StatoUtente.ARCHIVIATO) {
                utenteEsistente.setStato(StatoUtente.IN_ARCHIVIO);

                utenteEsistente.setNome(elem.getNome());
                utenteEsistente.setCognome(elem.getCognome());
                utenteEsistente.setEmail(elem.getEmail());
            }
            else {
                throw new UtenteException("Esiste già un utente attivo con questa matricola");
            }
        }
    }


    @Override
    public void rimuovi(String chiave) throws UtenteException {
        if (chiave == null) return;

        Utente utenteDaRimuovere = cerca(chiave);

        if (utenteDaRimuovere == null) {
            throw new UtenteException("UtenteException: impossibile rimuovere l'utente, non è presente nell'archivio");
        }

        if (utenteDaRimuovere.getStato() == StatoUtente.ARCHIVIATO) {
            throw new UtenteException("UtenteException: L'utente è già stato archiviato. Impossibile rimuovere.");
        }

        if (utenteDaRimuovere.getPrestitiAttivi() == 0) {
            utenteDaRimuovere.setStato(StatoUtente.ARCHIVIATO);
        }
        else {
            throw new UtenteException("UtenteException: L'utente ha ancora copie in prestito");
        }
    }

    /**
     * @param vecchio l'utente da modificare
     * @param nuovo   l'utente con i nuovi dati
     * @brief Permette di modificare i dati anagrafici di un utente esistente.
     *
     * @pre utentiList.contains(vecchio) == true
     * @pre vecchio.isAttivo() == true
     * @pre nuovo != null
     *
     * @post vecchio aggiornato con i valori di nuovo (Nome, Cognome, Email)
     * @post utentiList.size() invariata
     *
     * @throws
     */
    @Override
    public void modifica(Utente vecchio, Utente nuovo) throws UtenteException {
        if (vecchio == null || nuovo == null) return;

        if (!UtenteValidator.isUtenteValido(nuovo.getEmail(), nuovo.getMatricola())) {
            throw new UtenteException("UtenteException: L'utente è in un formato non valido");
        }

        Utente utenteDaModificare = cerca(vecchio.getMatricola());
        if (utenteDaModificare == null) {
            throw new UtenteException("UtenteException: Utente originale non esistente");
        }

        if (utenteDaModificare.getStato() == StatoUtente.ARCHIVIATO) {
            throw new UtenteException("UtenteException: l'utente non è in archivio");
        }

        if (!vecchio.getMatricola().equals(nuovo.getMatricola())) {
            // Se la matricola cambia, devo controllare che la NUOVA non esista già
            if (mappaUtenti.containsKey(nuovo.getMatricola())) {
                throw new UtenteException("La nuova matricola inserita appartiene già a un altro utente!");
            }

            mappaUtenti.remove(vecchio.getMatricola());

            aggiornaCampiUtente(utenteDaModificare, nuovo);

            mappaUtenti.put(nuovo.getMatricola(), utenteDaModificare);

        } else {
            aggiornaCampiUtente(utenteDaModificare, nuovo);
        }
    }

    private void aggiornaCampiUtente(Utente dest, Utente src) {
        dest.setNome(src.getNome());
        dest.setCognome(src.getCognome());
        dest.setEmail(src.getEmail());
        dest.setMatricola(src.getMatricola());
    }

    @Override
    public Utente cerca(String chiave) {
        Utente trovato = null;

        if (mappaUtenti.containsKey(chiave)) {
            trovato = mappaUtenti.get(chiave);
        }

        return trovato;
    }

    @Override
    public void setMappa(Map<String, Utente> m) {
        mappaUtenti = m;
    }

    @Override
    public List<Utente> getLista() {
        return new ArrayList<>(mappaUtenti.values());
    }
}
