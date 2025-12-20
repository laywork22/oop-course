package librarymanager.models;

import java.util.Comparator;
import java.util.Objects;

public class Libro implements Comparable<Libro> {
    private String titolo;
    private String autori;
    private int anno;
    private String isbn;
    private int copieTotali;
    private int copieDisponibili;
    private StatoLibro stato;

    public Libro(String titolo, String autori, int anno, String isbn, int copieTotali) {
        this.titolo = titolo;
        this.autori = autori;
        this.anno = anno;
        this.isbn = isbn;
        this.copieTotali = copieTotali;
        this.copieDisponibili = copieTotali;
        this.stato = StatoLibro.IN_ARCHIVIO;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutori() {
        return autori;
    }

    public void setAutori(String autori) {
        this.autori = autori;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public int getCopieTotali() {
        return copieTotali;
    }

    public void setCopieTotali(int copieTotali) {
        this.copieTotali = copieTotali;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getCopieDisponibili() {
        return copieDisponibili;
    }

    public void setCopieDisponibili(int copieDisponibili) {
        this.copieDisponibili = copieDisponibili;
    }

    public StatoLibro getStato() {
        return stato;
    }

    public void setStato(StatoLibro stato) {
        this.stato = stato;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return Objects.equals(isbn, libro.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(isbn);
    }


    @Override
    public String toString() {
        return "Libro{" +
                "titolo='" + titolo + '\'' +
                ", autori='" + autori + '\'' +
                ", anno=" + anno +
                ", isbn='" + isbn + '\'' +
                ", copieTotali=" + copieTotali +
                ", copieDisponibili=" + copieDisponibili +
                ", stato=" + stato +
                '}';
    }

    @Override
    public int compareTo(Libro o) {
        return this.isbn.compareToIgnoreCase(o.getIsbn());
    }
}
