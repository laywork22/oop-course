package librarymanager.controllers.presenters;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import librarymanager.alert.DialogService;
import librarymanager.controllers.uialert.DialogServiceJavaFX;
import librarymanager.controllers.forms.FormUtenteController;
import librarymanager.managers.GestoreUtente;
import librarymanager.models.StatoUtente;
import librarymanager.models.Utente;

import java.io.IOException;
import java.util.*;


/**
 * @class UtenteTableController
 * @brief Controller per la gestione della vista tabellare degli utenti.
 * @details Gestisce l'interazione tra la UI e il GestoreUtente, permettendo operazioni CRUD sugli utenti.
 * * @invariant 'tabellaUtenti' deve visualizzare in modo consistente gli utenti gestiti da GestoreUtente.
 * @invariant Il numero di prestiti attivi visualizzato per ogni utente deve corrispondere allo stato reale nel sistema.
 * @invariant 'listaOrdinata' è sempre sincronizzata con i filtri applicati su 'listaFiltrata'.
 */
public class UtenteTableController implements AreaPresenter{
    private GestoreUtente gestoreUtente;
    private Map<String, Comparator<Utente>> mappaOrdinamento;

    private ObservableList<Utente> lista;
    private FilteredList<Utente> listaFiltrata;
    private SortedList<Utente> listaOrdinata;

    private DialogService ds;

    @javafx.fxml.FXML
    private TableView<Utente> tabellaUtenti;
    @javafx.fxml.FXML
    private AnchorPane mainContentUtenti;
    @javafx.fxml.FXML
    private TableColumn<Utente, String> cognomeClm;
    @javafx.fxml.FXML
    private TableColumn<Utente, Integer> prestitAttiviClm;
    @javafx.fxml.FXML
    private TableColumn<Utente, String> matricolaClm;
    @javafx.fxml.FXML
    private TableColumn<Utente, String> emailClm;
    @javafx.fxml.FXML
    private TableColumn<Utente, String> nomeClm;
    @javafx.fxml.FXML
    private TableColumn<Utente, StatoUtente> statoClm;

    public UtenteTableController(GestoreUtente gestoreUtente) {
        this.gestoreUtente = gestoreUtente;

        this.ds = new DialogServiceJavaFX();

        mappaOrdinamento = new HashMap<>();

        mappaOrdinamento.put("Nome (A-Z)", Comparator.comparing(Utente::getNome, String.CASE_INSENSITIVE_ORDER));
        mappaOrdinamento.put("Cognome (A-Z)", Comparator.comparing(Utente::getCognome, String.CASE_INSENSITIVE_ORDER));
    }



    /**
     * @brief Inizializza la tabella utenti.
     * @details Collega le colonne (Nome, Cognome, Matricola, Email, Prestiti, Stato) alle proprietà dell'oggetto Utente.
     * @post La tabella è pronta e popolata con la lista degli utenti fornita dal GestoreUtente.
     */
    @FXML
    public void initialize() {
        nomeClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getNome()));
        cognomeClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getCognome()));
        matricolaClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getMatricola()));
        emailClm.setCellValueFactory(r-> new SimpleStringProperty(r.getValue().getEmail()));
        prestitAttiviClm.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getPrestitiAttivi()));
        statoClm.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getStato()));

        tabellaUtenti.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        lista = FXCollections.observableArrayList(gestoreUtente.getLista());
        listaFiltrata = new FilteredList<>(lista, p->true);
        listaOrdinata = new SortedList<>(listaFiltrata);

        listaOrdinata.comparatorProperty().bind(tabellaUtenti.comparatorProperty());

        tabellaUtenti.setItems(listaOrdinata);

        aggiornaTabella();
    }

    private void aggiornaTabella() {
        List<Utente> nuovaLista = gestoreUtente.getLista();

        lista.setAll(nuovaLista);

        tabellaUtenti.refresh();
    }

    /**
     * @brief Apre il form per la registrazione di un nuovo utente.
     * @post Se l'operazione ha successo, il nuovo utente è aggiunto al sistema e visibile in tabella.
     */
    @Override
    public void onAggiungi() {
        apriForm(null);
    }

    /**
     * @brief Rimuove (archivia) l'utente selezionato.
     * @pre Un utente deve essere selezionato nella tabella.
     * @pre L'utente selezionato deve avere 0 prestiti attivi (vincolo di business).
     * @post Se confermato e valido, l'utente viene posto in stato ARCHIVIATO.
     * @post La tabella viene aggiornata per riflettere il nuovo stato.
     * @note Corretto rispetto al codice originale: ora documenta la rimozione di un Utente, non di un Prestito.
     */
    @Override
    public void onRimuovi() {
        Utente selezione = tabellaUtenti.getSelectionModel().getSelectedItem();

        if (selezione != null) {
            if (ds.chiediConferma("Eliminare " + selezione.getNome() + " " + selezione.getCognome() + " (" + selezione.getMatricola() + ")")) {
                try {
                    gestoreUtente.rimuovi(selezione.getMatricola());
                    aggiornaTabella();
                } catch (Exception e) {
                    ds.mostraErrore(e.getMessage());
                }
            }
        }
    }

    private void apriForm(Utente utenteDaModificare) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/librarymanager/UtenteView.fxml"));
            Parent root = loader.load();
            FormUtenteController controller = loader.getController();

            if (utenteDaModificare == null) {
                controller.setFormOnAggiungi();
                controller.setOnSaveAction(nuovo -> {
                    try {
                        gestoreUtente.aggiungi(nuovo);
                        aggiornaTabella();
                        return true;
                    } catch (Exception e) {
                        ds.mostraErrore(e.getMessage());
                        return false;
                    }
                });
            }
            else {
                controller.setFormOnModifica(utenteDaModificare);
                controller.setOnSaveAction(nuovo -> {
                    try {
                        gestoreUtente.modifica(utenteDaModificare, nuovo);
                        aggiornaTabella();
                        return true;
                    } catch (Exception e) {
                        ds.mostraErrore(e.getMessage());
                        return false;
                    }
                });
            }

            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            stage.setTitle(utenteDaModificare == null ? "Nuovo Utente" : "Modifica Utente");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            ds.mostraErrore("Impossibile aprire il form: " + e.getMessage());
        }
    }


    /**
     * @brief Modifica i dati anagrafici dell'utente selezionato.
     * @pre Un utente deve essere selezionato nella tabella.
     * @post Apre il form di modifica pre-caricato con i dati dell'utente.
     * @post Se salvato, i dati (Nome, Cognome, Email) vengono aggiornati nel Gestore e nella vista.
     */
    @Override
    public void onModifica() {
        Utente selezione = tabellaUtenti.getSelectionModel().getSelectedItem();

        if (selezione != null) {
            apriForm(selezione);
        }
        else {
            ds.mostraAvviso("Seleziona un utente da modificare");
        }

        aggiornaTabella();
    }

    /**
     * @brief Sincronizza la vista con il modello dati.
     * @post I dati visualizzati corrispondono esattamente allo stato attuale del GestoreUtente.
     */
    @Override
    public void ricarica() {
        aggiornaTabella();
        tabellaUtenti.refresh();
    }

    /**
     * @brief Fornisce i criteri di ordinamento per gli utenti.
     * @return Lista contenente "Nome (A-Z)" e "Cognome (A-Z)".
     */
    @Override
    public List<String> getCriteriOrdinamento() {
        return new ArrayList<>(mappaOrdinamento.keySet());
    }

    /**
     * @brief Ordina la lista degli utenti.
     * @param criterio Chiave del criterio di ordinamento desiderato.
     * @pre criterio valido presente nella mappa.
     * @post La tabella mostra gli utenti ordinati alfabeticamente per Nome o Cognome.
     */
    @Override
    public void ordina(String criterio) {
        if (mappaOrdinamento.containsKey(criterio)) {
            listaOrdinata.comparatorProperty().unbind();

            listaOrdinata.setComparator(mappaOrdinamento.get(criterio));

            tabellaUtenti.getSortOrder().clear();
        }
        aggiornaTabella();
    }

    /**
     * @brief Filtra i prestiti in base ai dati dell'utente o del libro.
     * @param filtro Testo da ricercare.
     * @post La tabella mostra solo i prestiti dove Cognome/Nome utente, Matricola o Titolo libro corrispondono al filtro.
     */
    @Override
    public void filtra(String filtro) {
        listaFiltrata.setPredicate(utente -> {
            if (filtro == null || filtro.isEmpty()) return true;
            String lower = filtro.toLowerCase();

            return utente.getMatricola().toLowerCase().contains(lower) ||
                    utente.getNome().toLowerCase().contains(lower) ||
                    utente.getCognome().toLowerCase().contains(lower);
        });
    }
}
