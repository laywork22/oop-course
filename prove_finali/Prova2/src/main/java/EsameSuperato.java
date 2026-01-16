import java.time.LocalDate;

public class EsameSuperato implements Comparable<EsameSuperato> // completare
{
    // completare
    private final String matricolaStudente;
    private final LocalDate dataSuperamento;
    private final String nomeInsegnamento;
    private final int voto;

    public EsameSuperato(String matricolaStudente, LocalDate dataSuperamento, String nomeInsegnamento, int voto) {
        this.matricolaStudente = matricolaStudente;
        this.dataSuperamento = dataSuperamento;
        this.nomeInsegnamento = nomeInsegnamento;
        this.voto = voto;
    }

    public String getMatricolaStudente() {
        return matricolaStudente;
    }

    public LocalDate getDataSuperamento() {
        return dataSuperamento;
    }

    public String getNomeInsegnamento() {
        return nomeInsegnamento;
    }

    public int getVoto() {
        return voto;
    }

    @Override
    public int hashCode() {
        int hash = 21;

        hash = hash*31 + ((matricolaStudente == null) ? 0 : matricolaStudente.hashCode());
        hash = hash*31 + ((nomeInsegnamento == null) ? 0 : nomeInsegnamento.hashCode());

        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !this.getClass().equals(other.getClass())) return false;

        if (this == other) return true;

        EsameSuperato s = (EsameSuperato) other;

        return this.matricolaStudente.equals(s.matricolaStudente) && this.nomeInsegnamento.equals(s.getNomeInsegnamento());
    }


    @Override
    public String toString() {
        return "Matricola Studente: " + matricolaStudente +
                ", Data Superamento: " + dataSuperamento +
                ", Nome Insegnamento: " + nomeInsegnamento +
                ", Voto: " + voto +
                "\n";
    }


    @Override
    public int compareTo(EsameSuperato o) {
        int comp = this.matricolaStudente.compareToIgnoreCase(o.getMatricolaStudente());

        if (comp == 0) {
            comp = this.nomeInsegnamento.compareToIgnoreCase(o.getNomeInsegnamento());
        }

        return comp;
    }
}