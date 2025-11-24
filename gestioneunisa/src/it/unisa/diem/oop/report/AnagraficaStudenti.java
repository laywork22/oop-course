/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.report;

import it.unisa.diem.oop.persone.Studente;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author laywork
 */
public class AnagraficaStudenti implements Serializable{
    private final String descrizione;
    private final Map<String, Studente> studenti;
    
    public AnagraficaStudenti(String descrizione){
        this.descrizione = descrizione;
        
        this.studenti = new HashMap<>();
    }
    
    public void aggiungi(Studente s){
        this.studenti.putIfAbsent(s.getMatricola(), s);
    }
    
    public Studente cerca(String matricola){
        return this.studenti.get(matricola);
    }
    
    public void salvaDOS(String nomeFile) throws IOException{
        FileOutputStream  fos = new FileOutputStream(nomeFile);
        
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        
        DataOutputStream dos = new DataOutputStream(bos);
        
        dos.writeUTF(descrizione);
        
        for (Studente s : studenti.values()){
            dos.writeUTF(s.getNome());
            dos.writeUTF(s.getCognome());
            dos.writeUTF(s.getCodiceFiscale());
            dos.writeUTF(s.getMatricola());
            dos.writeUTF(s.getRuolo());
            dos.writeFloat(s.getVotoMedio());
        }
        
        dos.close();
    }
    
    public static AnagraficaStudenti leggiDIS(String nomeFile) throws IOException{
        FileInputStream fis = new FileInputStream(nomeFile);
        
        BufferedInputStream bis = new BufferedInputStream(fis);
        
        //leggere o scrivere tipi primitivi o sequenze di caratteri UTF
        DataInputStream dis = new DataInputStream(bis);
        
        AnagraficaStudenti a = new AnagraficaStudenti(dis.readUTF());
        
        try {
            while(true){
                //readUTF legge un carattere alla volta
                String nome = dis.readUTF();
                String cognome = dis.readUTF();
                String codiceFiscale = dis.readUTF();
                String matricola = dis.readUTF();
                String ruolo = dis.readUTF();
                float votoMedio = dis.readFloat();
                
                Studente s = new Studente(nome, cognome, codiceFiscale, matricola, votoMedio);
                
                a.aggiungi(s);
            }

        } catch (EOFException e){
            System.out.println("Lettura completata");
        }
        
        dis.close(); // la chiusura vca effettuata in un  blocco finally
        
        return a;
    }
    
    public void salvaOBJ(String nomeFile){
        
        //le risorse saranno automaticamente rilasciate alla fine dell'esecuzione del blocco try()-catch()
        try(ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(nomeFile)))){
            os.writeObject(this);
        } catch(IOException ignored){
            
        }
        
    }
    
    public static AnagraficaStudenti leggiOBJ(String nomeFile) {
        AnagraficaStudenti a = null;
        
        try( ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(nomeFile))) ){
            a = (AnagraficaStudenti) ois.readObject();
        } catch(IOException e){
            System.out.println("No");
        } catch(ClassNotFoundException ex){
            System.out.println("Bastardo");
        }
        
        return a;
    }

    public void salvaCSV(String nomeFile) {
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(nomeFile)))) {
            pw.println("NOME;COGNOME;CODICEFISCALE;MATRICOLA;VOTOMEDIO");

            for (Studente s : this.studenti.values()) {
                pw.append(s.getNome());
                pw.append(";");
                pw.append(s.getCognome());
                pw.append(";");
                pw.append(s.getCodiceFiscale()).append(";").append(s.getMatricola()).append(";");
                pw.println(s.getVotoMedio());
                /*pw.print(s.getVotoMedio()); //.append(Float.toString(s.getVotoMedio()));
                pw.append("\n");*/
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static AnagraficaStudenti leggiCSV(String nomeFile) {
        String descrizione = nomeFile.split("\\.")[0].toUpperCase(); //recuperiamo la descrizione dal nome del file

        AnagraficaStudenti a = new AnagraficaStudenti(descrizione);

        try(BufferedReader br = new BufferedReader(new FileReader(nomeFile))) {
            String line;

            if (br.readLine() == null) return null; //lettura a vuoto per ignorare l'intestazione

            while((line = br.readLine()) != null) {

                //array di stringhe dove ogni elemento è un attributo di uno studente
                String campi[] = line.split(";");

                a.aggiungi(new Studente(campi[0],campi[1], campi[2], campi[3], Float.parseFloat(campi[4]) ));

            }
        } catch (IOException e) {
            System.out.println("CSV exception");
        }

        return a;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("Anagrafica: ").append(descrizione);
        
        for(Studente s: this.studenti.values()){
            sb.append(s);
        }
                
        return sb.toString();
    }
}
