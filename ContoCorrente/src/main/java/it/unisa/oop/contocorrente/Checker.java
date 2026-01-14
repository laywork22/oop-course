package it.unisa.oop.contocorrente;

import java.util.HashSet;
import java.util.Set;

public class Checker implements Runnable {
    private Set<Thread> threads;
    private int period;

    public Checker(int period) {
        this.period = period;
        this.threads = new HashSet<>();
    }

    public void add(Thread t) {
        threads.add(t);
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(period);
            } catch (InterruptedException e) {
                return;
            }

            for (Thread t : threads) {
                System.out.println(t.getName() + ": " + t.getState());
            }
        }
    }
}
