package it.unisa.diem.oop.threadgestioneristorante;

import java.util.Random;

public class Cameriere implements Runnable {
    private String nome;
    private Comande comande;

    public Cameriere(String nome, Comande comande) {
        this.nome = nome;
        this.comande = comande;
    }

    @Override
    public void run() {
        Random tavolo = new Random(10432);
        Random quantita = new Random(54366);

        Menu menu = new Menu();

        while(!Thread.currentThread().isInterrupted()) {
            Ordinazione o = new Ordinazione(menu.getPiatto(), (tavolo.nextInt(5) + 1), (quantita.nextInt(4) + 1));

            try {
                Thread.sleep(1000 * (new Random().nextInt(6) + 5));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            comande.aggiungiOrdinazione(o);

            System.out.println("Ordinazione presa da " + nome + ": " + o.toString());

        }
    }
}
