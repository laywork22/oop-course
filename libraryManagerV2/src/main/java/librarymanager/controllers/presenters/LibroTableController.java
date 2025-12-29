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
import librarymanager.controllers.forms.FormLibroController;
import librarymanager.managers.GestoreLibro;
import librarymanager.models.Libro;
import librarymanager.models.StatoLibro;

import java.io.IOException;
import java.util.*;

// Nota: Aggiunto <Libro> all'interfaccia AreaPresenter per type safety
public class LibroTableController implements AreaPresenter {

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

    @Override
    public void onAggiungi() {
        apriForm(null);
    }

    @Override
    public void onModifica() {
        Libro selezione = tabellaLibri.getSelectionModel().getSelectedItem();
        if (selezione != null) {
            apriForm(selezione);
        } else {
            ds.mostraErrore("Seleziona un libro da modificare.");
        }
    }

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



    @Override
    public List<String> getCriteriOrdinamento() {
        return new ArrayList<>(mappaOrdinamento.keySet());
    }

    @Override
    public void ordina(String criterio) {
        if (mappaOrdinamento.containsKey(criterio)) {
            listaOrdinata.comparatorProperty().unbind();

            tabellaLibri.getSortOrder().clear();

            listaOrdinata.setComparator(mappaOrdinamento.get(criterio));
        }
    }

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