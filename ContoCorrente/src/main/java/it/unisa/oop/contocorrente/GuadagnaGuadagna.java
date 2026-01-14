package it.unisa.oop.contocorrente;

import java.util.Random;

public class GuadagnaGuadagna implements Runnable {
    private final ContoCorrente cr;
    private int periodo;

    public GuadagnaGuadagna(ContoCorrente cr, int periodo) {
        this.cr = cr;
        this.periodo = periodo;
    }

    @Override
    public void run() {
        // Spostiamo la creazione del Random fuori dal loop (opzionale ma consigliato)
        Random n = new Random(40000);

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(periodo);
            } catch (InterruptedException e) {
                return;
            }

            synchronized (cr) {
                int importo = (n.nextInt(19) + 1) * 50;
                cr.versa(importo);

                System.out.println(Thread.currentThread().getName() +
                        " ha versato: " + importo +
                        " ----> Nuovo saldo: " + cr.getSaldo());
            }
        }
    }
}