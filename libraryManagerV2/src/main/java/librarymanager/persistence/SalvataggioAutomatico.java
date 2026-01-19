package librarymanager.persistence;
import java.lang.String;
import java.util.List;
import java.util.Map;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import librarymanager.managers.Registro;
import librarymanager.managers.RegistroLibri;
import librarymanager.managers.RegistroPrestiti;
import librarymanager.managers.RegistroUtenti;
import librarymanager.models.Libro;
import librarymanager.models.Prestito;
import librarymanager.models.Utente;

public class SalvataggioAutomatico extends Service<String> {
    private Map<Class<?>, Registro<?, ?>> mappaRegistri;
    private ArchivioWriter<Archiviabile> writerUtility;
    private String fileArchivio;

    public SalvataggioAutomatico(Map<Class<?>, Registro<?,?>> mappaRegistri, ArchivioWriter<Archiviabile> writerUtility, String fileArchivio) {
        this.mappaRegistri = mappaRegistri;
        this.writerUtility = writerUtility;
        this.fileArchivio = fileArchivio;

    }

    public Map<Class<?>, Registro<?, ?>> getMappaRegistri() {
        return mappaRegistri;
    }

    public void setMappaRegistri(Map<Class<?>, Registro<?, ?>> mappaRegistri) {
        this.mappaRegistri = mappaRegistri;
    }

    public String getFileArchivio() {
        return fileArchivio;
    }

    public void setFileArchivio(String fileArchivio) {
        this.fileArchivio = fileArchivio;
    }

    public ArchivioWriter<Archiviabile> getWriterUtility() {
        return writerUtility;
    }

    public void setWriterUtility(ArchivioWriter<Archiviabile> writerUtility) {
        this.writerUtility = writerUtility;
    }

    @Override
    protected Task<String> createTask() {
        return new Task<>() {

            @Override @SuppressWarnings("unchecked")
            protected String call() throws Exception {
                while(!isCancelled()) {
                    updateProgress(0, 100);
                    updateMessage("Preparazione dati...");

                    List<Libro> libri =  (List<Libro>) mappaRegistri.get(RegistroLibri.class).getLista();
                    List<Utente> utenti = (List<Utente>) mappaRegistri.get(RegistroUtenti.class).getLista();
                    List<Prestito> prestiti = (List<Prestito>) mappaRegistri.get(RegistroPrestiti.class).getLista();

                    Archiviabile archivioAttuale = new ArchivioDati(libri, utenti, prestiti);

                    try {

                        updateMessage("Salvataggio in corso...");

                        writerUtility.scriviArchivio(archivioAttuale, fileArchivio);

                        updateProgress(100, 100);
                        updateMessage("Salvataggio completato.");
                    } catch (Exception e) {
                        updateMessage("Errore nel salvataggio");
                    }

                    for (int i = 0; i < 100; i++) {
                        Thread.sleep(300);

                        if (i == 30) updateMessage("In attesa...");
                        if (i == 30) updateProgress(0, 100);
                    }
                }

                return "Servizio interrotto";
            }
        };
    }


}
