package it.unisa.diem.oop.threadgestioneristorante;

import java.util.Random;

public class Cuoco implements Runnable {
    private Comande comande;

    public Cuoco(Comande comande) {
        this.comande = comande;
    }


    @Override
    public void run() {
        Random r = new Random();

        while(!Thread.currentThread().isInterrupted()) {
            try {
                Ordinazione o = comande.consegnaOrdinazione();

                Thread.sleep(1000*r.nextInt(6) + 5);

                System.out.println("Piatto pronto: " + o.toString());

            } catch (InterruptedException ignored) {

            }
        }
    }
}
