import java.time.LocalDate;

public class Persona {
    /*Dichiarazione Attributi*/
    private final String nome;
    private final String codiceFiscale;
    private final LocalDate dataDiNascita;
    /*CODICE DA COMPLETARE*/

    public Persona(String nome, LocalDate dataDiNascita , String codiceFiscale ) {
        this.nome = nome;
        this.codiceFiscale = codiceFiscale;
        this.dataDiNascita = dataDiNascita;
    }

    /*Metodi Getter*/
    /*CODICE DA COMPLETARE*/

    public String getNome() {
        return nome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    @Override
    public int hashCode() {
        int hash = 21;

        hash = hash*31 + ((codiceFiscale == null) ? 0 : codiceFiscale.hashCode());

        return hash;
    }

    @Override
    public boolean equals(Object other) {
        /*CODICE DA COMPLETARE*/
        if (other == null || !this.getClass().equals(other.getClass())) return false;

        if (this == other) return true;

        Persona p = (Persona) other;

        return this.codiceFiscale.equals(p.getCodiceFiscale());
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", Codice Fiscale: " + codiceFiscale + ", Data di Nascita: " + dataDiNascita + "\n";
    }

}