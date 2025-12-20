package librarymanager.managers;

import librarymanager.exceptions.PrestitoException;
import librarymanager.models.Prestito;
import librarymanager.models.StatoLibro;
import librarymanager.models.StatoPrestito;
import librarymanager.models.StatoUtente;
import librarymanager.validators.modelvalidator.PrestitoValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorePrestito implements Gestore<Integer, Prestito> {
    private Map<Integer, Prestito> mappaPrestiti;

    public GestorePrestito() {
        this.mappaPrestiti = new HashMap<>();
    }

    public GestorePrestito(Map<Integer, Prestito> mappaPrestiti) {
        this.mappaPrestiti = mappaPrestiti;
    }

    @Override
    public void aggiungi(Prestito elem) throws PrestitoException {
        if (elem == null) return;

        if (!PrestitoValidator.isPrestitAttiviMinTre(elem.getUtente().getPrestitiAttivi())) {
            throw new PrestitoException("L'utente ha raggiunto il limite dei 3 prestiti");
        }
        else if (!PrestitoValidator.isCopieDisponibiliNotZero(elem.getLibro().getCopieDisponibili())) {
            throw new PrestitoException("Non ci sono copie disponibili per il libro: " + elem.getLibro().getTitolo() + "(" + elem.getLibro().getIsbn() + ").");
        }


        if (elem.getLibro().getStato() != StatoLibro.IN_ARCHIVIO) {
            throw new PrestitoException("Il libro risulta non attivo");
        }

        if (elem.getUtente().getStato() != StatoUtente.IN_ARCHIVIO) {
            throw new PrestitoException("L'utente risulta non attivo");
        }

        elem.getUtente().setPrestitiAttivi(elem.getUtente().getPrestitiAttivi() + 1);
        elem.getLibro().setCopieDisponibili(elem.getLibro().getCopieDisponibili() - 1);

        mappaPrestiti.put(elem.getId(), elem);
    }

    @Override
    public void rimuovi(Integer id) {
        Prestito p = cerca(id);
        if (p == null) return;

        p.getUtente().setPrestitiAttivi(p.getUtente().getPrestitiAttivi() - 1);
        p.getLibro().setCopieDisponibili(p.getLibro().getCopieDisponibili() + 1);
        p.setDataFineEffettiva(LocalDate.now());


        if (p.getStato() == StatoPrestito.ATTIVO  || p.getStato() == StatoPrestito.IN_SCADENZA) {
            p.setStato(StatoPrestito.CHIUSO);
        }
        else if (LocalDate.now().isAfter(p.getDataFinePrestabilita())) {
            p.setStato(StatoPrestito.CHIUSO_IN_RITARDO);
        }


        aggiornaStati();
    }

    public void chiudiPrestito(Integer idPrestito)  {
        Prestito p = cerca(idPrestito);
        if (p == null) return;

        p.setDataFineEffettiva(LocalDate.now());
        p.getLibro().setCopieDisponibili(p.getLibro().getCopieDisponibili() + 1);
        p.getUtente().setPrestitiAttivi(p.getUtente().getPrestitiAttivi() - 1);

        aggiornaStati();
    }

    @Override
    public void modifica(Prestito vecchio, Prestito nuovo) throws PrestitoException {
        if (vecchio == null || nuovo == null) return;

        Prestito prestitoDaModificare = cerca(vecchio.getId());

        if (prestitoDaModificare == null) {
            return;
        }

        if (prestitoDaModificare.getStato() == StatoPrestito.CHIUSO ||
                prestitoDaModificare.getStato() == StatoPrestito.CHIUSO_IN_RITARDO) {
            throw new PrestitoException("Impossibile modificare un prestito già concluso.");
        }


        if (!prestitoDaModificare.getLibro().getIsbn().equals(nuovo.getLibro().getIsbn()) ||
                !prestitoDaModificare.getUtente().getMatricola().equals(nuovo.getUtente().getMatricola())) {
            throw new PrestitoException("Non è possibile cambiare il Libro o l'Utente di un prestito attivo. Chiudere il prestito e crearne uno nuovo.");
        }

        if (nuovo.getDataInizio().isAfter(nuovo.getDataFinePrestabilita())) {
            throw new PrestitoException("La data di inizio non può essere successiva alla data di scadenza.");
        }


        prestitoDaModificare.setDataInizio(nuovo.getDataInizio());
        prestitoDaModificare.setDataFinePrestabilita(nuovo.getDataFinePrestabilita());

        aggiornaStati();
    }

    @Override
    public Prestito cerca(Integer id) {
        Prestito trovato = null;

        if (mappaPrestiti.containsKey(id)) {
            trovato = mappaPrestiti.get(id);
        }

        return trovato;
    }

    @Override
    public void setMappa(Map<Integer, Prestito> m) {
        this.mappaPrestiti = m;
    }

    @Override
    public List<Prestito> getLista() {
        return new ArrayList<>(mappaPrestiti.values());
    }

    public void aggiornaStati() {
        LocalDate oggi = LocalDate.now();

        for (Prestito p : mappaPrestiti.values()) {
            if (p.getDataFineEffettiva() != null) {
                if (p.getDataFineEffettiva().isAfter(p.getDataFinePrestabilita())) {
                    p.setStato(StatoPrestito.CHIUSO_IN_RITARDO);
                } else {
                    p.setStato(StatoPrestito.CHIUSO);
                }
                continue;
            }

            if (p.getDataFinePrestabilita().isBefore(oggi)) {
                p.setStato(StatoPrestito.CHIUSO_IN_RITARDO);
            } else {
                p.setStato(StatoPrestito.ATTIVO);
            }
        }
    }
}
