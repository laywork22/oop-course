package it.unisa.diem.oop.threadgestioneristorante;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class Comande implements Serializable {
    private Queue<Ordinazione> codaOrdinazioni;
    private final String filename;

    public Comande(String filename, boolean leggiBackup) {
        this.filename = filename;

        if (leggiBackup) {
            codaOrdinazioni = leggiOrdinazione();
        } else {
            codaOrdinazioni = new LinkedList<>();
        }
    }

    public synchronized void aggiungiOrdinazione(Ordinazione ordinazione) {
        codaOrdinazioni.add(ordinazione);

        notifyAll();
    }

    public synchronized Ordinazione consegnaOrdinazione() throws InterruptedException {
        while(codaOrdinazioni.isEmpty()) {
            wait();
        }

        return codaOrdinazioni.remove();
    }

    private Queue<Ordinazione> leggiOrdinazione() {
        Queue<Ordinazione> codaOrdinazioniBackup = new LinkedList<>();

        try(ObjectInputStream oos = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
            codaOrdinazioniBackup = (Queue<Ordinazione>) oos.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato");
        } catch (EOFException e) {
            System.out.println("Lettura effettuata");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return codaOrdinazioniBackup;
    }

    public synchronized void salvaOrdinazione() {
        while(codaOrdinazioni.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        try(ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
            oos.writeObject(codaOrdinazioni);
        } catch (IOException e) {
            System.out.println("Errore nel salvataggio delle ordinazioni: " + e.getMessage());
        }
    }
}
