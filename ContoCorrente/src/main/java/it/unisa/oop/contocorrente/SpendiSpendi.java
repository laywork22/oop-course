package it.unisa.oop.contocorrente;

import java.util.Random;

public class SpendiSpendi implements Runnable {
    private final ContoCorrente cr;
    private int periodo;

    public SpendiSpendi(int periodo, ContoCorrente cr) {
        this.periodo = periodo;
        this.cr = cr;
    }

    public ContoCorrente getCr() {
        return cr;
    }

    @Override
    public void run() {
        Random n = new Random(2);

        while(!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(periodo);
            } catch (InterruptedException e) {
                return;
            }

            synchronized (cr) {
                int importo = (n.nextInt(19) + 1) * 50;
                cr.preleva(importo);

                System.out.println(Thread.currentThread().getName() + " ha prelevato " + importo + "----> Nuovo saldo: " + cr.getSaldo());
            }
        }

    }

}
