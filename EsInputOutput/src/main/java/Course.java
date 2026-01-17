import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Course implements Serializable {
    private Map<String, Student> studenti;

    public Course() {
        this.studenti = new HashMap<>();
    }

    public Student get(String matricola) {
        return studenti.get(matricola);
    }

    public Student put(String matricola, Student studente) {
        return studenti.put(matricola, studente);
    }

    public Set<String> matricole() {
        return studenti.keySet();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        for (Student s : studenti.values()) {
            sb.append(s).append("\n");
        }

        return sb.toString();
    }
}
