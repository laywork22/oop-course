package librarymanager.models;

import java.util.Objects;

public class Utente implements Comparable<Utente> {
    private String nome;
    private String cognome;
    private String email;
    private String matricola;
    private int prestitiAttivi;
    private StatoUtente stato;

    public static enum StatoUtente {
        IN_ARCHIVIO,
        ARCHIVIATO
    }

    public Utente(String nome, String cognome, String email, String matricola) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.matricola = matricola;
        this.prestitiAttivi = 0;
        this.stato = StatoUtente.IN_ARCHIVIO;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public int getPrestitiAttivi() {
        return prestitiAttivi;
    }

    public void setPrestitiAttivi(int prestitiAttivi) {
        this.prestitiAttivi = prestitiAttivi;
    }

    public StatoUtente getStato() {
        return stato;
    }

    public void setStato(StatoUtente stato) {
        this.stato = stato;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Utente utente = (Utente) o;
        return Objects.equals(matricola, utente.matricola);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(matricola);
    }

    @Override
    public String toString() {
        return "Utente{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", matricola='" + matricola + '\'' +
                ", stato=" + stato +
                '}';
    }

    @Override
    public int compareTo(Utente o) {
        return this.getMatricola().compareToIgnoreCase(o.getMatricola());
    }
}
