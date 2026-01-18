package librarymanager.persistence;

public interface ArchivioWriter<T> {
    void scriviArchivio(T data, String filename);
}
