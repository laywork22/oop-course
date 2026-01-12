package librarymanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import librarymanager.controllers.PrimaryController;
import librarymanager.controllers.uialert.DialogServiceJavaFX;
import librarymanager.managers.*;

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
                return new PrimaryController(initMappaGestori(), new GestoreArchivio(), new DialogServiceJavaFX());
            }

            throw new IllegalArgumentException("Controller non atteso " + classeRichiesta);
        });

        return fxmlLoader.load();
    }

    private Map<Class<?>, Gestore<?, ?>> initMappaGestori() {
        Map<Class<?>, Gestore<?, ?>> mappaGestori = new HashMap<>();

        mappaGestori.put(GestoreLibro.class, new GestoreLibro());
        mappaGestori.put(GestoreUtente.class, new GestoreUtente());
        mappaGestori.put(GestorePrestito.class, new GestorePrestito());

        return mappaGestori;
    }

    public static void main(String[] args) {
        launch();
    }

}