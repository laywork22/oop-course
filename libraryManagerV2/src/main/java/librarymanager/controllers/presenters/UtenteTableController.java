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
import librarymanager.controllers.forms.FormUtenteController;
import librarymanager.managers.GestoreUtente;
import librarymanager.models.StatoUtente;
import librarymanager.models.Utente;

import java.io.IOException;
import java.util.*;

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

    @Override
    public void onAggiungi() {
        apriForm(null);
    }


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


    @Override
    public void onModifica() {
        Utente selezione = tabellaUtenti.getSelectionModel().getSelectedItem();

        if (selezione != null) {
            apriForm(selezione);
        }
        else {
            ds.mostraErrore("Seleziona un utente da modificare");
        }

        aggiornaTabella();
    }

    @Override
    public void ricarica() {
        aggiornaTabella();
        tabellaUtenti.refresh();
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

            tabellaUtenti.getSortOrder().clear();
        }
        aggiornaTabella();
    }

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
