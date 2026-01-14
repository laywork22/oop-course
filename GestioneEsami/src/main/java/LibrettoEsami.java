import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class LibrettoEsami implements Serializable {
    private final Map<String, List<EsameSuperato>> librettoEsami;

    public LibrettoEsami() {
        this.librettoEsami = new TreeMap<>();
    }

    public Map<String, List<EsameSuperato>> getLibrettoEsami() {
        return librettoEsami;
    }

    public void aggiungi(EsameSuperato es) {
        if (es == null) {
            System.out.println("L'esame non esiste!");
            return;
        }

        if (librettoEsami.containsKey(es.getInsegnamento())) {
            List<EsameSuperato> s = librettoEsami.get(es.getInsegnamento());
            s.add(es);
        }
        else {
            List<EsameSuperato> nuovaLista = new LinkedList<>();

            nuovaLista.add(es);

            librettoEsami.put(es.getInsegnamento(), nuovaLista);
        }
    }

    public void stampaPerInsegnamento() {
        System.out.println("Esami stampati per matricola: ");
        for (Map.Entry<String, List<EsameSuperato>> l : librettoEsami.entrySet()) {
            for (EsameSuperato es : l.getValue()) {
                System.out.println(es);
            }
        }
    }

    public void stampaInsegnamento(String insegnamento) {
        System.out.println("Esami visualizzati dell'insegnamento " + insegnamento + ": ");
        for (Map.Entry<String, List<EsameSuperato>> l : librettoEsami.entrySet()) {
            if (l.getKey().equals(insegnamento)) {
                System.out.println(l.getValue());
            }
        }
    }

    public void stampaMatricola(String matricola) {
        System.out.println("Esami visualizzati della matricola " + matricola + ": ");
        for (Map.Entry<String, List<EsameSuperato>> l : librettoEsami.entrySet()) {
            for (EsameSuperato m : l.getValue()) {
                if (m.getMatricola().equals(matricola)) {
                    System.out.println(m);
                }
            }
        }
    }

    public void salvaBinSer(String nomeFile) {
        try(ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(nomeFile)))) {
            os.writeObject(this);
        } catch (FileNotFoundException e) {
            System.out.println("File inesistente: impossibile salvare.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public LibrettoEsami caricaBinSer(String nomeFile) {
        LibrettoEsami le = new LibrettoEsami();

        try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(nomeFile)))) {

            le = (LibrettoEsami) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("File inesistente: impossibile caricare.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Classe inesistente. Impossibile caricare.");
        }

        return le;
    }

    public void salvaSuFile(String nomeFile) {
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(nomeFile)))) {

            for (List<EsameSuperato> les : librettoEsami.values()) {
                for (EsameSuperato es : les) {
                    pw.append(es.getMatricola()).append(",").append(es.getData().toString()).append(",").
                            append(es.getInsegnamento()).append(",");
                    pw.println(Integer.toString(es.getVoto()));
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public LibrettoEsami caricaDaFile(String nomeFile) {
        LibrettoEsami le = new LibrettoEsami();

        try(BufferedReader br = new BufferedReader(new FileReader(nomeFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                String campi[] = line.split(",");

                if (campi.length < 4) continue;

                le.aggiungi(new EsameSuperato(campi[0], LocalDate.parse(campi[1]), campi[2], Integer.parseInt(campi[3])));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato. Impossibile leggere da file di testo.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return le;
    }

    public LibrettoEsami caricaDaFileScanner(String nomeFile) {
        LibrettoEsami le = new LibrettoEsami();
        EsameSuperato es;

        try(Scanner s = new Scanner(new BufferedReader(new FileReader(nomeFile)))) {
            s.useDelimiter("[,\\n]");

            while(s.hasNext()) {
                es = new EsameSuperato(s.next(), LocalDate.parse(s.next()), s.next(), Integer.parseInt(s.next().trim()));
                le.aggiungi(es);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Impossibile utilizzare lo scanner: file inesistente.");
        }

        return le;
    }


}
