// Importare ulteriori package, ove necessario


import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GestioneTrattoriGuidaAutonoma  {
    
    //Attributi
    private Map<Integer, Trattore> trattori;
    
    //Costruttore
    public GestioneTrattoriGuidaAutonoma() {
        trattori = new HashMap<>();
    }
    
    //Metodi
    
    public void aggiungiTrattore(Trattore t){
        if (t == null) return;
        // Completare

        trattori.put(t.getIdTelaio(), t);
        
    }
    
    public void rimuoviTrattore(int idTelaio){
        trattori.remove(idTelaio);
    }
    
    public Trattore ricercaTrattore(int idTelaio){
        return trattori.get(idTelaio);
    }
    
     public void esportaInventarioTrattori(String nomeFile) {
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(nomeFile)))) {
            pw.println("tipologiaTrattore;idTelaio;peso;annoImmatricolazione;attributoSpecifico");

            for (Trattore t : trattori.values()) {
                pw.append(t.getClass().getSimpleName()).append(";");
                pw.print(t.getIdTelaio());
                pw.append(";");
                pw.print(t.getPeso());
                pw.append(";");
                pw.print(t.getAnnoImmatricolazione() + ";");
                if (t instanceof Imballatrice) {
                    Imballatrice i = (Imballatrice) t;
                    pw.println(i.getNumeroRulli());
                }
                else if (t instanceof Irrigatore) {
                    Irrigatore ir = (Irrigatore) t;
                    pw.println(ir.getNumeroTestine());
                }
                else {
                    pw.println(1);
                }
            }

        } catch (IOException ignored) {}

    }
    
    public GestioneTrattoriGuidaAutonoma importaInventarioTrattori(String nomeFile) {
        GestioneTrattoriGuidaAutonoma gtga = new GestioneTrattoriGuidaAutonoma();

        try(BufferedReader br = new BufferedReader(new FileReader(nomeFile))) {
            if (br.readLine() == null) return null;

            String line;

            while((line = br.readLine()) != null) {
                String campi[] = line.split("[;|\\n]");

                if (campi.length < 5) continue;

                final Trattore t = getTrattore(campi);

                gtga.aggiungiTrattore(t);
            }
        } catch (EOFException e) {
            System.out.println("Lettura completata");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return gtga;
    }

    private Trattore getTrattore(String[] campi) {
        int attrSpec = Integer.parseInt(campi[4]);
        Trattore t = null;

        if (attrSpec == 1) {
            t = new Trattore(Integer.parseInt(campi[1]), campi[0], Integer.parseInt(campi[2]), Integer.parseInt(campi[3]));
        }
        else if (campi[0].equals(Imballatrice.class.getSimpleName())) {
            t = new Imballatrice(Integer.parseInt(campi[1]), campi[0], Integer.parseInt(campi[2]), Integer.parseInt(campi[3]), attrSpec);
        }
        else if (campi[0].equals(Irrigatore.class.getSimpleName())) {
            t = new Irrigatore(Integer.parseInt(campi[1]), campi[0], Integer.parseInt(campi[2]), Integer.parseInt(campi[3]), attrSpec);
        }

        return t;
    }


    // Override dei metodi
    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        
        for(Trattore t : trattori.values()) {
            sb.append(t);
        }
        
        return sb.toString();
    }
    
}
