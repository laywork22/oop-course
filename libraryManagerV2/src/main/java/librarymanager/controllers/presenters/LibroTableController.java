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
import librarymanager.controllers.forms.FormLibroController;
import librarymanager.managers.GestoreLibro;
import librarymanager.models.Libro;
import librarymanager.models.StatoLibro;

import java.io.IOException;
import java.util.*;

/**
 * @class LibroTableController
 * @brief Controller per la gestione della vista tabellare dei libri.
 * @details Implementa la logica di presentazione per l'area libri, gestendo la sincronizzazione
 * tra il GestoreLibro e la TableView JavaFX.
 * * @invariant La TableView 'tabellaLibri' mostra sempre il contenuto di 'listaOrdinata'.
 * @invariant 'listaOrdinata' deve riflettere i cambiamenti di 'listaFiltrata', a sua volta legata a 'lista'.
 * @invariant 'lista' deve contenere tutti e soli i libri restituiti da gestoreLibro.getLista() dopo ogni operazione di aggiornamento.
 */
public class LibroTableController implements AreaEditablePresenter {

    private final GestoreLibro gestoreLibro;
    private final Map<String, Comparator<Libro>> mappaOrdinamento;
    private final DialogService ds; // Ora è final e inizializzato

    // Dati per la tabella
    private ObservableList<Libro> lista;
    private FilteredList<Libro> listaFiltrata;
    private SortedList<Libro> listaOrdinata;

    @FXML private TableView<Libro> tabellaLibri;
    @FXML private AnchorPane mainContentLibri;

    // Colonne
    @FXML private TableColumn<Libro, String> isbnClm;
    @FXML private TableColumn<Libro, String> TitoloClm;
    @FXML private TableColumn<Libro, String> autoriClm;
    @FXML private TableColumn<Libro, Integer> annoClm;
    @FXML private TableColumn<Libro, Integer> copieTotaliClm;
    @FXML private TableColumn<Libro, Integer> copieDisponibiliClm;
    @FXML private TableColumn<Libro, StatoLibro> statoClm;


    public LibroTableController(GestoreLibro gestoreLibro) {
        this.gestoreLibro = gestoreLibro;
        this.ds = new DialogServiceJavaFX(); // Inizializzazione necessaria!

        this.mappaOrdinamento = new HashMap<>();
        mappaOrdinamento.put("Titolo (A-Z)", Comparator.comparing(Libro::getTitolo, String.CASE_INSENSITIVE_ORDER));
        mappaOrdinamento.put("Autore (A-Z)", Comparator.comparing(Libro::getAutori, String.CASE_INSENSITIVE_ORDER));
        mappaOrdinamento.put("Anno (Recenti)", Comparator.comparing(Libro::getAnno).reversed());
    }

    /**
     * @brief Inizializza il controller, le colonne della tabella e il binding dei dati.
     * @details Configura le PropertyValueFactory per le colonne, imposta la politica di ridimensionamento
     * e crea la catena di ObservableList (Base -> Filtered -> Sorted) per la gestione della vista.
     * @post La TableView è popolata con i dati attuali del GestoreLibro.
     * @post Le colonne Titolo, Autori, ISBN, Anno, Copie e Stato sono correttamente legate al modello Libro.
     */
    @FXML
    public void initialize() {
        TitoloClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getTitolo()));
        autoriClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getAutori()));
        isbnClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getIsbn()));
        annoClm.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getAnno()));
        copieTotaliClm.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getCopieTotali()));
        copieDisponibiliClm.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getCopieDisponibili()));
        statoClm.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getStato()));

        tabellaLibri.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        lista = FXCollections.observableArrayList(gestoreLibro.getLista());
        listaFiltrata = new FilteredList<>(lista, p->true);
        listaOrdinata = new SortedList<>(listaFiltrata);

        listaOrdinata.comparatorProperty().bind(tabellaLibri.comparatorProperty());

        tabellaLibri.setItems(listaOrdinata);

        aggiornaTabella();
    }

    private void aggiornaTabella() {
        List<Libro> nuovaLista = gestoreLibro.getLista();

        lista.setAll(nuovaLista);

        tabellaLibri.refresh();
    }

    /**
     * @brief Avvia la procedura di creazione di un nuovo libro.
     * @details Apre la finestra modale 'LibroView.fxml' in modalità di aggiunta.
     * @post Se l'utente conferma il salvataggio nel form, il nuovo libro viene aggiunto al GestoreLibro.
     * @post La tabella viene aggiornata per mostrare il nuovo inserimento.
     */
    @Override
    public void onAggiungi() {
        apriForm(null);
    }

    /**
     * @brief Avvia la procedura di modifica per il libro selezionato.
     * @details Apre la finestra modale 'LibroView.fxml' popolata con i dati del libro selezionato.
     * @pre Un libro deve essere attualmente selezionato nella tabella (tabellaLibri.getSelectionModel().getSelectedItem() != null).
     * @post Se l'utente conferma le modifiche, il libro nel GestoreLibro viene aggiornato.
     * @post La tabella riflette immediatamente le modifiche apportate (es. Cambio titolo o copie).
     * @note Se nessun libro è selezionato, viene mostrato un avviso all'utente.
     */
    @Override
    public void onModifica() {
        Libro selezione = tabellaLibri.getSelectionModel().getSelectedItem();
        if (selezione != null) {
            apriForm(selezione);
        } else {
            ds.mostraAvviso("Seleziona un libro da modificare.");
        }
    }


    /**
     * @brief Aggiorna i dati della tabella sincronizzandoli con il Gestore.
     * @post La 'lista' osservabile contiene esattamente gli elementi restituiti da gestoreLibro.getLista().
     * @post La TableView viene rinfrescata visivamente.
     */
    @Override
    public void ricarica() {
        aggiornaTabella();
        tabellaLibri.refresh();
    }

    private void apriForm(Libro libroDaModificare) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/librarymanager/LibroView.fxml"));
            Parent root = loader.load();
            FormLibroController controller = loader.getController();

            if (libroDaModificare == null) {
                controller.setFormOnAggiungi();
                controller.setOnSaveAction(nuovo -> {
                    try {
                        gestoreLibro.aggiungi(nuovo);
                        aggiornaTabella();
                        return true;
                    } catch (Exception e) {
                        ds.mostraErrore(e.getMessage());
                        return false;
                    }
                });
            } else {
                controller.setFormOnModifica(libroDaModificare);
                controller.setOnSaveAction(nuovo -> {
                    try {

                        gestoreLibro.modifica(libroDaModificare, nuovo);
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
            stage.setTitle(libroDaModificare == null ? "Nuovo Libro" : "Modifica Libro");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            ds.mostraErrore("Impossibile aprire il form: " + e.getMessage());
        }
    }

    /**
     * @brief Rimuove (archivia) il libro selezionato.
     * @details Chiede conferma all'utente prima di procedere con la rimozione logica.
     * @pre Un libro deve essere selezionato nella tabella.
     * @pre Il libro selezionato non deve avere copie attualmente in prestito (vincolo verificato dal GestoreLibro).
     * @post Se confermato e le pre-condizioni del Gestore sono soddisfatte, lo stato del libro diventa ARCHIVIATO.
     * @post Il libro rimosso non viene più visualizzato nella lista attiva (se il filtro lo esclude) o il suo stato visivo cambia.
     * @throws librarymanager.exceptions.LibroException Mostra un messaggio di errore se il GestoreLibro solleva eccezioni (es. copie in prestito).
     */
    @Override
    public void onRimuovi() {
        Libro selezione = tabellaLibri.getSelectionModel().getSelectedItem();
        if (selezione != null) {
            if (ds.chiediConferma("Eliminare " + selezione.getTitolo() + "?")) {
                try {
                    gestoreLibro.rimuovi(selezione.getIsbn());
                    aggiornaTabella();
                } catch (Exception e) {
                    ds.mostraErrore(e.getMessage());
                }
            }
        }
    }

    /**
     * @brief Restituisce l'elenco dei criteri di ordinamento disponibili.
     * @return Lista di stringhe contenente le chiavi della mappaOrdinamento (es. "Titolo (A-Z)", "Anno (Recenti)").
     */
    @Override
    public List<String> getCriteriOrdinamento() {
        return new ArrayList<>(mappaOrdinamento.keySet());
    }

    /**
     * @brief Applica un criterio di ordinamento alla tabella.
     * @param criterio Stringa che identifica il comparatore da utilizzare.
     * @pre criterio deve essere una chiave valida in 'mappaOrdinamento'.
     * @post La 'listaOrdinata' viene riordinata secondo il comparatore associato al criterio.
     * @post L'ordinamento visuale della TableView viene aggiornato.
     */
    @Override
    public void ordina(String criterio) {
        if (mappaOrdinamento.containsKey(criterio)) {
            listaOrdinata.comparatorProperty().unbind();

            tabellaLibri.getSortOrder().clear();

            listaOrdinata.setComparator(mappaOrdinamento.get(criterio));
        }
    }

    /**
     * @brief Filtra i libri visualizzati in base a una stringa di ricerca.
     * @param testo Stringa da cercare (case-insensitive).
     * @post La 'listaFiltrata' contiene solo i libri che hanno un match parziale con Titolo, Autori o ISBN.
     * @post Se 'testo' è null o vuoto, vengono mostrati tutti i libri.
     */
    @Override
    public void filtra(String testo) {
        listaFiltrata.setPredicate(libro -> {
            if (testo == null || testo.isEmpty()) return true;
            String lower = testo.toLowerCase();
            return libro.getTitolo().toLowerCase().contains(lower) ||
                    libro.getAutori().toLowerCase().contains(lower) ||
                    libro.getIsbn().toLowerCase().contains(lower);
        });
    }
}