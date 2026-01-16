/* IMPORT DA COMPLETARE*/
import java.util.Set;
import java.util.TreeSet;
import java.io.*;

public class Libretto {
    private final Set<EsameSuperato> libretto;
    // completare

    public Libretto() {
        this.libretto = new TreeSet<>();
    }

    public void aggiungi (EsameSuperato e) {
        libretto.add(e);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        for (EsameSuperato e : libretto) {
            sb.append(e);
        }

        return sb.toString();
    }

    public void salvaCSV(String nomefile) {
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(nomefile)))) {
            pw.println("matricolaStudente;dataSuperamento;nomeInsegnamento;voto");

            for (EsameSuperato e : libretto) {
                pw.append(e.getMatricolaStudente()).append(";");
                pw.print(e.getDataSuperamento());
                pw.append(";");
                pw.append(e.getNomeInsegnamento()).append(";");
                pw.println(e.getVoto());
            }
        } catch(FileNotFoundException e) {
           System.out.println("File inesistente.");
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}