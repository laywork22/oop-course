package librarymanager.controllers.alert;

public interface DialogService {
    void mostraErrore(String msg);
    void mostraInfo(String msg);
    void mostraAvviso(String msg);
    boolean chiediConferma(String msg);
}
