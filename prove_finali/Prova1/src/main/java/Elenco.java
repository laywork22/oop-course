/* IMPORT DA COMPLETARE*/
import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.time.LocalDate;
public class Elenco {
    private final Map<String, Persona> elenco;
    /*CODICE DA COMPLETARE*/

    public Elenco() {
        this.elenco = new HashMap<>();
    }

    public void aggiungi(Persona p) {
        elenco.put(p.getCodiceFiscale(), p);
    }

    public Persona cerca(String codFiscale) {
        /*CODICE DA COMPLETARE*/
        return elenco.get(codFiscale);
    }

    public void stampaSuFile(String nomeFile) {
        /*CODICE DA COMPLETARE*/
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(nomeFile)))) {
            pw.println("nome;codFiscale;dataDiNascita");

            for(Persona p : elenco.values()) {
                pw.append(p.getNome()).append(";").append(p.getCodiceFiscale()).append(";");
                pw.println(p.getDataDiNascita());
            }
        } catch(FileNotFoundException ex) {
            System.out.println("File non trovato.");
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void leggiCSV(String nomeFile) {
        /*CODICE DA COMPLETARE*/
        try( BufferedReader br = new BufferedReader(new FileReader(nomeFile))) {
            if (br.readLine() == null) return;

            String line;

            while((line = br.readLine()) != null) {
                String campi[] = line.split("[;|\\n]");

                if (campi.length < 3) continue;

                Persona p = new Persona(campi[0], LocalDate.parse(campi[2]), campi[1]);

                System.out.println(p);
            }

        } catch(EOFException e) {
            System.out.println("Lettura completata.");
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }


    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        for (Persona p : elenco.values()) {
            sb.append(p.toString());
        }

        return sb.toString();
    }

}