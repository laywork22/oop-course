package librarymanager.models;

import java.time.LocalDate;
import java.util.Objects;

public class Prestito implements Comparable<Prestito>{
    private static int nextId = 1;

    private int id;
    private Utente utente;
    private Libro libro;
    private LocalDate dataInizio;
    private LocalDate dataFinePrestabilita;
    private LocalDate dataFineEffettiva;
    private StatoPrestito stato;

    public static enum StatoPrestito {
        IN_SCADENZA,
        ATTIVO,
        CHIUSO_IN_RITARDO,
        CHIUSO
    }


    public Prestito(Utente utente, Libro libro, LocalDate dataFinePrestabilita) {
        this.id = nextId;
        this.utente = utente;
        this.libro = libro;
        this.dataInizio = LocalDate.now();
        this.dataFinePrestabilita = dataFinePrestabilita;
        this.stato = StatoPrestito.ATTIVO;

        nextId++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFinePrestabilita() {
        return dataFinePrestabilita;
    }

    public void setDataFinePrestabilita(LocalDate dataFinePrestabilita) {
        this.dataFinePrestabilita = dataFinePrestabilita;
    }

    public LocalDate getDataFineEffettiva() {
        return dataFineEffettiva;
    }

    public void setDataFineEffettiva(LocalDate dataFineEffettiva) {
        this.dataFineEffettiva = dataFineEffettiva;
    }

    public StatoPrestito getStato() {
        return stato;
    }

    public void setStato(StatoPrestito stato) {
        this.stato = stato;
    }

    public static void setNextId(int nextId){
        Prestito.nextId = nextId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Prestito prestito = (Prestito) o;
        return id == prestito.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Prestito{" +
                "id=" + id +
                ", utente=" + utente +
                ", libro=" + libro +
                ", dataInizio=" + dataInizio +
                ", dataFinePrestabilita=" + dataFinePrestabilita +
                ", dataFineEffettiva=" + dataFineEffettiva +
                '}';
    }

    @Override
    public int compareTo(Prestito o) {
        return Integer.compare(this.id, o.getId());
    }
}
