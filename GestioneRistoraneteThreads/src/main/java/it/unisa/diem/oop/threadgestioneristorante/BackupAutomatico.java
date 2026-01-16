package it.unisa.diem.oop.threadgestioneristorante;

public class BackupAutomatico implements Runnable {
    private Comande comande;

    public BackupAutomatico(Comande comande) {
        this.comande = comande;
    }


    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            comande.salvaOrdinazione();

            System.out.println("BACKUP EFFETTUATO");

            try {
                Thread.sleep(1000 * 20);
            } catch (InterruptedException e) {
                System.out.println("Errore nel backup");
            }
        }
    }
}
