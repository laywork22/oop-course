package librarymanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import librarymanager.controllers.PrimaryController;
import librarymanager.controllers.uialert.DialogServiceJavaFX;
import librarymanager.managers.*;
import librarymanager.models.Libro;
import librarymanager.models.Prestito;
import librarymanager.models.Utente;
import librarymanager.persistence.ArchivioCsvIO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * JavaFX LibraryManager
 */
public class LibraryManager extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 1400, 700);
        stage.setScene(scene);
        stage.show();
    }

     void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private  Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryManager.class.getResource(fxml + ".fxml"));

        fxmlLoader.setControllerFactory(classeRichiesta ->  {
            if (classeRichiesta == PrimaryController.class) {
                return new PrimaryController(initMappaGestori(), new ArchivioCsvIO(), new DialogServiceJavaFX());
            }

            throw new IllegalArgumentException("Controller non atteso " + classeRichiesta);
        });

        return fxmlLoader.load();
    }

    private Map<Class<?>, Registro<?, ?>> initMappaGestori() {
        Map<Class<?>, Registro<?, ?>> mappaGestori = new HashMap<>();

        mappaGestori.put(RegistroLibri.class, (Registro<String, Libro>) new RegistroLibri());
        mappaGestori.put(RegistroUtenti.class, (Registro<String, Utente>) new RegistroUtenti());
        mappaGestori.put(RegistroPrestiti.class,(Registro<Integer, Prestito>) new RegistroPrestiti());

        return mappaGestori;
    }

    public static void main(String[] args) {
        launch();
    }

}