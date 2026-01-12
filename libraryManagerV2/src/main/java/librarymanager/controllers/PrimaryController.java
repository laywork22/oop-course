package librarymanager.controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.Duration;
import librarymanager.alert.DialogService;
import librarymanager.controllers.uialert.DialogServiceJavaFX;
import librarymanager.controllers.presenters.AreaPresenter;
import librarymanager.controllers.presenters.LibroTableController;
import librarymanager.controllers.presenters.PrestitoTableController;
import librarymanager.controllers.presenters.UtenteTableController;
import librarymanager.managers.*;
import librarymanager.models.ArchivioDati;
import librarymanager.models.Libro;
import librarymanager.models.Prestito;
import librarymanager.models.Utente;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class PrimaryController {
    private Map<Class<?>, Gestore<?, ?>> mappaGestori;
    private GestoreLibro gestoreLibro;
    private GestorePrestito gestorePrestito;
    private GestoreUtente gestoreUtente;

    private GestoreArchivio gestoreArchivio;
    private File directoryCorrente;

    private Gestore<?, ?>  gestoreCorrente;

    private AreaPresenter areaCorrente;

    private DialogService ds;

    private boolean menuVisible = false;

    @FXML
    private TextField barraRicerca;
    @FXML
    private MenuButton ordineFiltro;
    @FXML
    private Button areaLibriBtn;
    @FXML
    private Button apriFileBtn;
    @FXML
    private Button salvaConNomeBtn;
    @FXML
    private Label areaLbl;
    @FXML
    private Button salvaBtn;
    @FXML
    private Button addBtn;
    @FXML
    private Button modifyButton;
    @FXML
    private AnchorPane mainContent;
    @FXML
    private Button removeBtn;
    @FXML
    private Button menuButton;
    @FXML
    private VBox sideMenu;
    @FXML
    private Button areaPrestitiBtn;
    @FXML
    private Button areaUtentiBtn;
    @FXML
    private AnchorPane tabellaContent;

    public PrimaryController() {}

    public PrimaryController(Map<Class<?>, Gestore<?, ?>> mappaGestori, GestoreArchivio gestoreArchivio, DialogService ds) {
        this.ds = ds;
        this.mappaGestori = mappaGestori;

        this.gestoreArchivio = gestoreArchivio;

        this.gestoreLibro = (GestoreLibro) mappaGestori.get(GestoreLibro.class);
        this.gestorePrestito = (GestorePrestito) mappaGestori.get(GestorePrestito.class);
        this.gestoreUtente = (GestoreUtente) mappaGestori.get(GestoreUtente.class);

        if (gestoreLibro == null || gestorePrestito == null || gestoreUtente == null) {
            throw new IllegalStateException("Uno o più gestori non sono stati inizializzati correttamente nella mappa.");
        }
    }


    @FXML
    public void initialize() {
        gestoreCorrente = gestorePrestito;

        sideMenu.setTranslateX(-200);

        barraRicerca.textProperty().addListener((obs, oldVal, newVal) -> {
            if (areaCorrente != null) {
                areaCorrente.filtra(newVal);
            }
        });

        addBtn.setOnAction(e -> {
            if (areaCorrente != null) areaCorrente.onAggiungi();
        });

        modifyButton.setOnAction(e -> {
            if (areaCorrente != null) areaCorrente.onModifica();
        });

        removeBtn.setOnAction(e -> {
            if (areaCorrente != null) areaCorrente.onRimuovi();
        });

        gestorePrestito.aggiornaStati();

        setAreaPrestiti(null);

        toggleMenu(null);

        areaCorrente.ricarica();

    }




    @FXML
    public void salvaFile(ActionEvent actionEvent) {
        if (directoryCorrente != null) {

            ArchivioDati archivio = new ArchivioDati(gestoreLibro.getLista(), gestoreUtente.getLista(), gestorePrestito.getLista());

            gestoreArchivio.salvaArchivioCSV(archivio, directoryCorrente);

            ds.mostraInfo( "Salvato in " + directoryCorrente.getName());
        } else {
            salvaFileConNome(actionEvent);
        }

        toggleMenu(actionEvent);
    }

    @FXML
    public void salvaFileConNome(ActionEvent actionEvent) {
        Window window = mainContent.getScene().getWindow();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Salva archivio biblioteca");


        File directory = directoryChooser.showDialog(window);


        ArchivioDati ad = new ArchivioDati(gestoreLibro.getLista(), gestoreUtente.getLista(), gestorePrestito.getLista());

        try {
            if (directory != null) {
                gestoreArchivio.salvaArchivioCSV(ad, directory);
                directoryCorrente = directory;
                ds.mostraInfo( "Salvataggio completato: " + "Dati salvati nella cartella " + directory.getName());
            }

        } catch (Exception e) {
            ds.mostraErrore( "Errore di salvataggio");
        }

        toggleMenu(actionEvent);
    }

    @FXML
    public void setAreaPrestiti(ActionEvent actionEvent) {
        caricaVista("/librarymanager/TabellaPrestitoView.fxml", "Area Prestiti", classeRichiesta -> {
            if (classeRichiesta == PrestitoTableController.class) return new PrestitoTableController( gestorePrestito, gestoreLibro, gestoreUtente);

            throw new IllegalArgumentException("Controller non atteso " + classeRichiesta);
        });

        gestorePrestito.aggiornaStati();

        toggleMenu(actionEvent);
    }

    @FXML
    public void setAreaLibri(ActionEvent actionEvent) {
        caricaVista("/librarymanager/TabellaLibroView.fxml", "Area Libri", classeRichiesta -> {
            if (classeRichiesta == LibroTableController.class) return new LibroTableController(gestoreLibro);

            throw new IllegalArgumentException("Controller non atteso " + classeRichiesta);
        });

        areaCorrente.ricarica();
        toggleMenu(actionEvent);
    }

    @FXML
    public void setAreaUtenti(ActionEvent actionEvent) {
        caricaVista("/librarymanager/TabellaUtenteView.fxml", "Area Utente", classeRichiesta -> {
            if (classeRichiesta == UtenteTableController.class) return new UtenteTableController(gestoreUtente);

            throw new IllegalArgumentException("Controller non atteso " + classeRichiesta);
        });

        areaCorrente.ricarica();

        toggleMenu(actionEvent);
    }

    private void caricaVista(String fxmlPath, String titoloArea, Callback<Class<?>, Object> controllerFactory) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

            loader.setControllerFactory(controllerFactory);

            Parent view = loader.load();

            this.areaCorrente = loader.getController();

            areaLbl.setText(titoloArea);
            tabellaContent.getChildren().setAll(view);

            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);

            aggiornaMenuOrdinamento();

            barraRicerca.clear();

        } catch (IOException e) {
            ds.mostraErrore("Impossibile caricare la vista: " + fxmlPath + "\n" + e.getMessage());
        }
    }

    private void aggiornaMenuOrdinamento() {
        ordineFiltro.getItems().clear();
        ordineFiltro.setText("Ordina per...");

        if (areaCorrente != null && areaCorrente.getCriteriOrdinamento() != null) {
            for (String criterio : areaCorrente.getCriteriOrdinamento()) {
                MenuItem item = new MenuItem(criterio);
                item.setOnAction(e -> {
                    areaCorrente.ordina(criterio);
                    ordineFiltro.setText("Ordina: " + criterio);
                });
                ordineFiltro.getItems().add(item);
            }
        }
    }

    @FXML
    public void toggleMenu(ActionEvent actionEvent) {
        TranslateTransition slideMenu = new TranslateTransition(Duration.millis(300), sideMenu);
        TranslateTransition slideContent = new TranslateTransition(Duration.millis(300), mainContent);

        if (!menuVisible) {
            slideMenu.setToX(0);
            slideContent.setToX(200);
            menuVisible = true;
        } else {
            slideMenu.setToX(-200);
            slideContent.setToX(0);
            menuVisible = false;
        }
        slideMenu.play();
        slideContent.play();
    }

    @FXML
    public void apriFile(ActionEvent actionEvent) {
        javafx.stage.Window window = mainContent.getScene().getWindow();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Selezione cartella dati");

        File cartellaSelezionata = directoryChooser.showDialog(window);

        try {
            if (cartellaSelezionata != null) {
                ArchivioDati ad = gestoreArchivio.caricaArchivioCSV(cartellaSelezionata);

                gestoreLibro.setMappa(ad.getListaLibri().stream()
                        .collect(Collectors.toMap(Libro::getIsbn, libro -> libro)));

                gestoreUtente.setMappa(ad.getListaUtenti().stream()
                        .collect(Collectors.toMap(Utente::getMatricola, utente -> utente)));

                gestorePrestito.setMappa(ad.getListaPrestiti().stream()
                        .collect(Collectors.toMap(Prestito::getId, prestito -> prestito)));

                directoryCorrente = cartellaSelezionata;


            }
        } catch (Exception e) {
            ds.mostraErrore("Errore caricamento: "+ e.getMessage());
        }

        areaCorrente.ricarica();

    }


}
