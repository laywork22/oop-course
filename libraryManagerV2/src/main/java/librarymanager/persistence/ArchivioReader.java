package librarymanager.persistence;

public interface ArchivioReader<T> {
    T leggiArchivio(String fileName);
}
