import java.io.Serializable;
import java.time.LocalDate;

public class EsameSuperato implements Comparable<EsameSuperato>, Serializable {
    private static int nextId = 1;

    private final int id;
    private final String matricola;
    private final LocalDate data;
    private final String insegnamento;
    private final int voto;


    public EsameSuperato(String matricola, LocalDate data, String insegnamento, int voto) {
        this.id = nextId;
        this.insegnamento = insegnamento;
        this.data = data;
        this.matricola = matricola;
        this.voto = voto;

        nextId++;
    }

    public String getMatricola() {
        return matricola;
    }

    public int getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public String getInsegnamento() {
        return insegnamento;
    }

    public int getVoto() {
        return voto;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !this.getClass().equals(obj.getClass())) return false;

        if (this == obj) return true;

        EsameSuperato es = (EsameSuperato) obj;

        return this.getId() == es.getId();
    }

    @Override
    public int hashCode(){
        int hash = 21;

        hash = hash*31 + Integer.hashCode(this.id);

        return hash;
    }

    @Override
    public String toString() {
        return "EsameSuperato:\n " +
                "matricola=" + matricola + "\n" +
                "dataSuperamento=" + data + "\n" +
                "insegnamento=" + insegnamento + '\n' +
                "voto=" + voto +
                "\n";
    }

    @Override
    public int compareTo(EsameSuperato o) {
        return Integer.compare(this.getId(), o.getId());
    }
}
