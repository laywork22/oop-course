import java.io.Serializable;

public class Student implements Serializable {
    private String nome;
    private String matricola;
    private int esamiSostenuti;
    private double votoMedio;

    public Student(String nome, String matricola, int esamiSostenuti, double votoMedio) {
        this.nome = nome;
        this.matricola = matricola;
        this.esamiSostenuti = esamiSostenuti;
        this.votoMedio = votoMedio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getEsamiSostenuti() {
        return esamiSostenuti;
    }

    public void setEsamiSostenuti(int esamiSostenuti) {
        this.esamiSostenuti = esamiSostenuti;
    }

    public double getVotoMedio() {
        return votoMedio;
    }

    public void setVotoMedio(double votoMedio) {
        this.votoMedio = votoMedio;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    @Override
    public String toString() {
        return "Nome: " + nome +
                "\nMatricola: " + matricola +
                "\nEsami Sostenuti: " + esamiSostenuti +
                "\nVoto Medio: " + votoMedio + "\n";
    }
}
