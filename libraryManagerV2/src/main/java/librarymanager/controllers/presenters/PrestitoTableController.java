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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import librarymanager.alert.DialogService;
import librarymanager.controllers.uialert.DialogServiceJavaFX;
import librarymanager.controllers.forms.FormPrestitoController;
import librarymanager.managers.GestoreLibro;
import librarymanager.managers.GestorePrestito;
import librarymanager.managers.GestoreUtente;
import librarymanager.models.Prestito;
import librarymanager.models.StatoPrestito;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * @class PrestitoTableController
 * @brief Controller per la gestione della vista tabellare dei prestiti.
 * @details Coordina la visualizzazione complessa dei prestiti, che coinvolge entità Utente e Libro.
 * Gestisce inoltre la logica visiva di notifica (colorazione righe) per scadenze e ritardi.
 * * @invariant Ogni riga della tabella rappresenta un Prestito univoco associato a un Utente e un Libro esistenti.
 * @invariant Le celle mostrano dati aggregati corretti (es. Nome Utente + Titolo Libro).
 * @invariant Il colore della riga deve riflettere lo stato del prestito rispetto alla data odierna (Verde=Chiuso, Rosso=Scaduto, Giallo=In Scadenza).
 */
public class PrestitoTableController implements AreaEditablePresenter {
    private final GestorePrestito gestorePrestito;
    private final GestoreUtente gestoreUtente;
    private final GestoreLibro gestoreLibro;

    private Map<String, Comparator<Prestito>> mappaOrdinamento;

    private ObservableList<Prestito> lista;
    private FilteredList<Prestito> listaFiltrata;
    private SortedList<Prestito> listaOrdinata;

    private DialogService ds;

    @javafx.fxml.FXML
    private AnchorPane mainContentPrestiti;
    @javafx.fxml.FXML
    private TableColumn<Prestito, String> cognomeClm;
    @javafx.fxml.FXML
    private TableColumn<Prestito, String> matricolaClm;
    @javafx.fxml.FXML
    private TableColumn<Prestito, LocalDate> dataConsegnaClm;
    @javafx.fxml.FXML
    private TableColumn<Prestito, String> autoreClm;
    @javafx.fxml.FXML
    private TableColumn<Prestito, LocalDate> dataScadenzaClm;
    @javafx.fxml.FXML
    private TableColumn<Prestito, String> isbnClm;
    @javafx.fxml.FXML
    private TableColumn<Prestito, String> nomeClm;
    @javafx.fxml.FXML
    private TableColumn<Prestito, String> titoloClm;
    @javafx.fxml.FXML
    private TableColumn<Prestito, LocalDate> dataInizioClm;
    @javafx.fxml.FXML
    private TableColumn<Prestito, Integer> idClm;
    @javafx.fxml.FXML
    private TableColumn<Prestito, StatoPrestito> statoClm;
    @FXML
    private TableView<Prestito> tabellaPrestiti;

    public PrestitoTableController(GestorePrestito gestorePrestito, GestoreLibro gestoreLibro, GestoreUtente gestoreUtente) {
        this.gestorePrestito = gestorePrestito;
        this.gestoreUtente = gestoreUtente;
        this.gestoreLibro = gestoreLibro;

        this.ds = new DialogServiceJavaFX();

        mappaOrdinamento = new HashMap<>();

        mappaOrdinamento.put("Utente (A-Z)", Comparator.comparing(p -> p.getUtente().getCognome(), String.CASE_INSENSITIVE_ORDER));
        mappaOrdinamento.put("Libro (A-Z)", Comparator.comparing(p -> p.getLibro().getTitolo(), String.CASE_INSENSITIVE_ORDER));
        mappaOrdinamento.put("Data Inizio (Recenti)", Comparator.comparing(Prestito::getDataInizio));
        mappaOrdinamento.put("Stato", Comparator.comparing(p -> p.getStato().toString()));

    }

    /**
     * @brief Inizializza la tabella prestiti e la logica di colorazione delle righe.
     * @details Imposta un RowFactory personalizzato per cambiare lo stile della riga (CSS) in base
     * a dataScadenza e dataConsegnaEffettiva.
     * @post Le colonne sono legate alle proprietà nidificate (Prestito -> Utente -> Nome, ecc.).
     * @post La tabella visualizza i prestiti con il color-coding appropriato.
     */
    @FXML
    public void initialize() {
        idClm.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getId()));
        idClm.setMaxWidth(100);
        nomeClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getUtente().getNome()));
        cognomeClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getUtente().getCognome()));
        matricolaClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getUtente().getMatricola()));
        isbnClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getLibro().getIsbn()));
        titoloClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getLibro().getTitolo()));
        autoreClm.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getLibro().getAutori()));
        dataInizioClm.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getDataInizio()));
        dataScadenzaClm.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getDataFinePrestabilita()));
        dataConsegnaClm.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getDataFineEffettiva()));
        statoClm.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getStato()));

        tabellaPrestiti.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        tabellaPrestiti.setRowFactory(tv -> new TableRow<>() {
            @Override
            public void updateItem(Prestito prestito, boolean empty) {
                super.updateItem(prestito, empty);

                if (empty) {
                    setStyle("");
                    return;
                }

                if (isSelected()) {
                    setStyle("");
                }
                else {
                    try {
                        LocalDate now = LocalDate.now();
                        LocalDate scadenza = prestito.getDataFinePrestabilita();
                        LocalDate scadenzaEffettiva = prestito.getDataFineEffettiva();

                        if (prestito.getStato() == StatoPrestito.CHIUSO) {
                            setStyle("-fx-background-color: lightgreen;");
                        } else if (scadenza.isBefore(now)) {
                            if (scadenzaEffettiva != null) {
                                setStyle("-fx-background-color: orange;");
                            }
                            else {
                                setStyle("-fx-background-color: red;");
                            }
                        } else if (!scadenza.isAfter(now.plusDays(5))) {
                            setStyle("-fx-background-color: yellow;");
                        } else {
                            setStyle("");
                        }
                    } catch (Exception e) { setStyle(""); }
                }
            }
        });


        lista = FXCollections.observableArrayList();
        listaFiltrata = new FilteredList<>(lista, p->true);
        listaOrdinata = new SortedList<>(listaFiltrata);

        listaOrdinata.comparatorProperty().bind(tabellaPrestiti.comparatorProperty());

        tabellaPrestiti.setItems(listaOrdinata);

        aggiornaTabella();

    }

    private void aggiornaTabella() {
        List<Prestito> nuovaLista = gestorePrestito.getLista();

        lista.setAll(nuovaLista);

        tabellaPrestiti.refresh();
    }

    private void apriForm(Prestito prestitoDaModificare) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/librarymanager/PrestitoView.fxml"));
            Parent root = loader.load();
            FormPrestitoController controller = loader.getController();

            controller.init(gestoreLibro, gestoreUtente, gestorePrestito);

            if (prestitoDaModificare == null) {
                controller.setFormOnAggiungi();
                controller.setOnSaveAction(nuovo -> {
                    try {
                        gestorePrestito.aggiungi(nuovo);
                        aggiornaTabella();
                        return true;
                    } catch (Exception e) {
                        ds.mostraErrore(e.getMessage());
                        return false;
                    }
                });
            }
            else {
                controller.setFormOnModifica(prestitoDaModificare);
                controller.setOnSaveAction(nuovo -> {
                    try {
                        gestorePrestito.modifica(prestitoDaModificare, nuovo);
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
            stage.setTitle(prestitoDaModificare == null ? "Nuovo Prestito" : "Modifica Prestito");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            ds.mostraErrore("Impossibile aprire il form: " + e.getMessage());
        }
    }

    /**
     * @brief Avvia la creazione di un nuovo prestito.
     * @details Apre il form iniettando tutti i gestori necessari (Libro, Utente, Prestito) per permettere la selezione.
     * @post Se il prestito è creato con successo, viene aggiunto al GestorePrestito.
     * @post Le disponibilità del libro e i contatori dell'utente vengono aggiornati indirettamente.
     */
    @Override
    public void onAggiungi() {
        apriForm(null);
    }

    /**
     * @brief Chiude il prestito selezionato (Restituzione).
     * @details Non elimina fisicamente il record, ma lo segna come concluso (rimozione logica dai prestiti attivi).
     * @pre Un prestito deve essere selezionato.
     * @post Il prestito viene marcato come CHIUSO o CHIUSO_IN_RITARDO in base alla data odierna.
     * @post La data di fine effettiva viene impostata a oggi.
     * @post Il libro torna disponibile e il contatore dell'utente viene decrementato.
     */
    @Override
    public void onRimuovi() {
        Prestito selezione = tabellaPrestiti.getSelectionModel().getSelectedItem();

        if (selezione != null) {
            if (ds.chiediConferma("Eliminare Prestito n.  " + selezione.getId() + " - " + selezione.getLibro().getTitolo() + " (" + selezione.getLibro().getIsbn() + ")")) {
                try {
                    gestorePrestito.rimuovi(selezione.getId());
                    aggiornaTabella();
                } catch (Exception e) {
                    ds.mostraErrore(e.getMessage());
                }
            }
        }
    }

    /**
     * @brief Modifica un prestito esistente (es. proroga data).
     * @pre Un prestito deve essere selezionato.
     * @pre Il prestito non deve essere in uno stato concluso (CHIUSO o CHIUSO_IN_RITARDO).
     * @post Se modificato, le nuove date vengono salvate e lo stato ricalcolato.
     */
    @Override
    public void onModifica() {
        Prestito selezione = tabellaPrestiti.getSelectionModel().getSelectedItem();

        if (selezione != null) {
            apriForm(selezione);
        }
        else {
            ds.mostraAvviso("Seleziona un prestito da modificare");
        }
    }

    /**
     * @brief Ricarica i dati dal gestore.
     * @post La vista è allineata con i dati presenti in GestorePrestito.
     */
    @Override
    public void ricarica() {
        aggiornaTabella();
    }

    /**
     * @brief Restituisce i criteri di ordinamento (Utente, Libro, Data, Stato).
     */
    @Override
    public List<String> getCriteriOrdinamento() {
        return new ArrayList<>(mappaOrdinamento.keySet());
    }

    /**
     * @brief Ordina i prestiti.
     * @param criterio Criterio scelto (es. Per Data Inizio o per Cognome Utente).
     * @post La tabella viene riordinata.
     */
    @Override
    public void ordina(String criterio) {
        if (mappaOrdinamento.containsKey(criterio)) {
            listaOrdinata.comparatorProperty().unbind();

            tabellaPrestiti.getSortOrder().clear();

            listaOrdinata.setComparator(mappaOrdinamento.get(criterio));
        }
    }

    /**
     * @brief Filtra i prestiti visualizzati.
     * @details Permette la ricerca incrociata su più campi.
     * @param filtro Stringa di ricerca.
     * @post Visualizza i prestiti che corrispondono per: Nome/Cognome/Matricola Utente OPPURE Titolo Libro.
     */
    @Override
    public void filtra(String filtro) {
        listaFiltrata.setPredicate(prestito -> {
            if (filtro == null || filtro.isEmpty()) return true;
            String filtro_lc = filtro.toLowerCase();

            // Cerca per Cognome Utente o Titolo Libro prestato
            return prestito.getUtente().getCognome().toLowerCase().contains(filtro_lc) ||
                    prestito.getLibro().getTitolo().toLowerCase().contains(filtro_lc) ||
                    prestito.getUtente().getNome().toLowerCase().contains(filtro_lc)  ||
                    prestito.getUtente().getMatricola().toLowerCase().contains(filtro_lc);
        });
    }
}
