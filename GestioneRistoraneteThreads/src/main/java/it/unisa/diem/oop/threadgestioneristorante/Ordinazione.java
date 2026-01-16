package it.unisa.diem.oop.threadgestioneristorante;

import java.io.Serializable;

public class Ordinazione implements Serializable {
    private String piatto;
    private int tavolo;
    private int quantita;

    public Ordinazione(String piatto, int tavolo, int quantita) {
        this.piatto = piatto;
        this.tavolo = tavolo;
        this.quantita = quantita;
    }

    public void setPiatto(String piatto) {
        this.piatto = piatto;
    }

    public String getPiatto() {
        return piatto;
    }

    public void setTavolo(int tavolo) {
        this.tavolo = tavolo;
    }

    public int getTavolo() {
        return tavolo;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public int getQuantita() {
        return quantita;
    }

    @Override
    public String toString() {
        return "Piatto: " + piatto + ", Tavolo: " + tavolo + ", Quantità: " + quantita + "\n";
    }
}
