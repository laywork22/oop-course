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
import librarymanager.controllers.alert.DialogService;
import librarymanager.controllers.alert.DialogServiceJavaFX;
import librarymanager.controllers.forms.FormPrestitoController;
import librarymanager.managers.GestoreLibro;
import librarymanager.managers.GestorePrestito;
import librarymanager.managers.GestoreUtente;
import librarymanager.models.Prestito;
import librarymanager.models.StatoPrestito;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class PrestitoTableController implements AreaPresenter {
    private final GestorePrestito gestorePrestito;
    private final GestoreUtente gestoreUtente;
    private final GestoreLibro gestoreLibro;

    private Map<String, Comparator<Prestito>> mappaOrdinamento;

    private ObservableList<Prestito> lista;
    private FilteredList<Prestito> listaFiltrata;
    private SortedList<Prestito> listaOrdinata;

    private DialogService ds;

    @javafx.fxml.FXML
    private TableView<Prestito> prestitoTable;
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

        prestitoTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        aggiornaTabella();

    }

    private void aggiornaTabella() {
        lista = FXCollections.observableArrayList(gestorePrestito.getLista());
        listaFiltrata = new FilteredList<>(lista, p->true);
        listaOrdinata = new SortedList<>(listaFiltrata);

        listaOrdinata.comparatorProperty().bind(prestitoTable.comparatorProperty());

        prestitoTable.setItems(listaOrdinata);
        prestitoTable.refresh();
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
            e.printStackTrace();
            ds.mostraErrore("Impossibile aprire il form: " + e.getMessage());
        }
    }
    
    @Override
    public void onAggiungi() {
        apriForm(null);
    }

    @Override
    public void onRimuovi() {
        Prestito selezione = prestitoTable.getSelectionModel().getSelectedItem();

        if (selezione != null) {
            if (ds.chiediConferma("Eliminare " + selezione.getId() + " - " + selezione.getLibro().getTitolo() + " (" + selezione.getLibro().getIsbn() + ")")) {
                try {
                    gestorePrestito.rimuovi(selezione.getId());
                    aggiornaTabella();
                } catch (Exception e) {
                    ds.mostraErrore(e.getMessage());
                }
            }
        }
    }

    @Override
    public void onModifica() {
        Prestito selezione = prestitoTable.getSelectionModel().getSelectedItem();

        if (selezione != null) {
            apriForm(selezione);
        }
        else {
            ds.mostraErrore("Seleziona un prestito da modificare");
        }
    }

    @Override
    public void ricarica() {
        aggiornaTabella();
        prestitoTable.refresh();
    }

    @Override
    public List<String> getCriteriOrdinamento() {
        return new ArrayList<>(mappaOrdinamento.keySet());
    }

    @Override
    public void ordina(String criterio) {
        if (mappaOrdinamento.containsKey(criterio)) {
            listaOrdinata.comparatorProperty().unbind();

            listaOrdinata.setComparator(mappaOrdinamento.get(criterio));

            prestitoTable.getSortOrder().clear();
        }
        aggiornaTabella();
    }

    @Override
    public void filtra(String filtro) {
        listaFiltrata.setPredicate(prestito -> {
            if (filtro == null || filtro.isEmpty()) return true;
            String filtro_lc = filtro.toLowerCase();

            // Cerca per Cognome Utente o Titolo Libro prestato
            return prestito.getUtente().getCognome().toLowerCase().contains(filtro_lc) ||
                    prestito.getLibro().getTitolo().toLowerCase().contains(filtro_lc) ||
                    prestito.getUtente().getNome().toLowerCase().contains(filtro_lc);
        });
    }
}
