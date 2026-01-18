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
import librarymanager.controllers.presenters.*;
import librarymanager.managers.*;
import librarymanager.models.ArchivioDati;
import librarymanager.models.Libro;
import librarymanager.models.Prestito;
import librarymanager.models.Utente;
import librarymanager.persistence.Archiviabile;
import librarymanager.persistence.ArchivioIO;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PrimaryController {
    private Map<Class<?>, Registro<?, ?>> mappaRegistri;

    private ArchivioIO<Archiviabile> archivioCsvIO;
    private File directoryCorrente;

    private Registro<?, ?>  gestoreCorrente;

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

    public PrimaryController(Map<Class<?>, Registro<?, ?>> mappaRegistri, ArchivioIO<Archiviabile> archivio, DialogService ds) {
        this.ds = ds;
        this.mappaRegistri = mappaRegistri;

        this.archivioCsvIO = archivio;

        if (mappaRegistri.get(RegistroLibri.class) == null || mappaRegistri.get(RegistroPrestiti.class) == null || mappaRegistri.get(RegistroUtenti.class) == null) {
            throw new IllegalStateException("Uno o più gestori non sono stati inizializzati correttamente nella mappa.");
        }
    }


    @FXML
    public void initialize() {
        gestoreCorrente = mappaRegistri.get(RegistroPrestiti.class);

        sideMenu.setTranslateX(-200);

        barraRicerca.textProperty().addListener((obs, oldVal, newVal) -> {
            if (areaCorrente != null) {
                areaCorrente.filtra(newVal);
            }
        });

        addBtn.setOnAction(e -> {
            if (areaCorrente != null && areaCorrente instanceof Editable) ((Editable) areaCorrente).onAggiungi();
        });

        modifyButton.setOnAction(e -> {
            if (areaCorrente != null  && areaCorrente instanceof Editable) ((Editable) areaCorrente).onModifica();
        });

        removeBtn.setOnAction(e -> {
            if (areaCorrente != null  && areaCorrente instanceof Editable) ((Editable)areaCorrente).onRimuovi();
        });

        if (areaCorrente instanceof RefreshableState)
            ((RefreshableState) areaCorrente).aggiornaStati();

        setAreaPrestiti(null);

        toggleMenu(null);

        areaCorrente.ricarica();

    }

    @FXML
    public void salvaFile(ActionEvent actionEvent) {
        if (directoryCorrente != null) {

            ArchivioDati archivio = new ArchivioDati((List<Libro>) mappaRegistri.get(RegistroLibri.class).getLista(), (List<Utente>) mappaRegistri.get(RegistroUtenti.class).getLista(), (List<Prestito>) mappaRegistri.get(RegistroPrestiti.class).getLista());

            archivioCsvIO.scriviArchivio(archivio, directoryCorrente.getPath());

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


        Archiviabile ad = new ArchivioDati((List<Libro>) mappaRegistri.get(RegistroLibri.class).getLista(), (List<Utente>) mappaRegistri.get(RegistroUtenti.class).getLista(), (List<Prestito>) mappaRegistri.get(RegistroPrestiti.class).getLista());

        try {
            if (directory != null) {
                archivioCsvIO.scriviArchivio(ad, directory.getPath());
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
        this.gestoreCorrente = getRegistro(RegistroPrestiti.class);

        caricaVista("/librarymanager/TabellaPrestitoView.fxml", "Area Prestiti", classeRichiesta -> {
            if (classeRichiesta == PrestitoTableController.class) return new PrestitoTableController((RegistroPrestiti) mappaRegistri.get(RegistroPrestiti.class),
                    (RegistroLibri) mappaRegistri.get(RegistroLibri.class),
                    (RegistroUtenti) mappaRegistri.get(RegistroUtenti.class));

            throw new IllegalArgumentException("Controller non atteso " + classeRichiesta);
        });

        if (gestoreCorrente instanceof RefreshableState)
            ((RefreshableState) gestoreCorrente).aggiornaStati();

        areaCorrente.ricarica();
        toggleMenu(actionEvent);
    }

    @FXML
    public void setAreaLibri(ActionEvent actionEvent) {
        this.gestoreCorrente = getRegistro(RegistroLibri.class);

        caricaVista("/librarymanager/TabellaLibroView.fxml", "Area Libri", classeRichiesta -> {
            if (classeRichiesta == LibroTableController.class) return new LibroTableController((RegistroLibri) mappaRegistri.get(RegistroLibri.class));

            throw new IllegalArgumentException("Controller non atteso " + classeRichiesta);
        });

        if (gestoreCorrente instanceof RefreshableState)
            ((RefreshableState) gestoreCorrente).aggiornaStati();

        areaCorrente.ricarica();
        toggleMenu(actionEvent);
    }

    @FXML
    public void setAreaUtenti(ActionEvent actionEvent) {
        this.gestoreCorrente = getRegistro(RegistroUtenti.class);

        caricaVista("/librarymanager/TabellaUtenteView.fxml", "Area Utente", classeRichiesta -> {
            if (classeRichiesta == UtenteTableController.class) return new UtenteTableController((RegistroUtenti) mappaRegistri.get(RegistroUtenti.class));

            throw new IllegalArgumentException("Controller non atteso " + classeRichiesta);
        });

        if (gestoreCorrente instanceof RefreshableState)
            ((RefreshableState) gestoreCorrente).aggiornaStati();

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
                ArchivioDati ad = (ArchivioDati) archivioCsvIO.leggiArchivio(cartellaSelezionata.getPath());

                Registro<String, Libro> regLibri = getRegistro(RegistroLibri.class);
                regLibri.setMappa(ad.getListaLibri().stream()
                        .collect(Collectors.toMap(Libro::getIsbn, libro -> libro)));

                Registro<String, Utente> regUtenti = getRegistro(RegistroUtenti.class);
                regUtenti.setMappa(ad.getListaUtenti().stream()
                        .collect(Collectors.toMap(Utente::getMatricola, utente -> utente)));

                Registro<Integer, Prestito> regPrestiti = getRegistro(RegistroPrestiti.class);
                regPrestiti.setMappa(ad.getListaPrestiti().stream()
                        .collect(Collectors.toMap(Prestito::getId, prestito -> prestito)));

                directoryCorrente = cartellaSelezionata;


            }
        } catch (Exception e) {
            ds.mostraErrore("Errore caricamento: "+ e.getMessage());
        }

        areaCorrente.ricarica();

    }

    @SuppressWarnings("unchecked")
    private <K, T> Registro<K, T> getRegistro(Class<?> key) {
        return (Registro<K, T>) mappaRegistri.get(key);
    }

}
