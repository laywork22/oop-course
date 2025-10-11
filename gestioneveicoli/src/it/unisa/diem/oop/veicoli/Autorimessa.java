package it.unisa.diem.oop.veicoli;
import Eccezioni.*;

public class Autorimessa extends Box  {

    // COMPLETARE DEFINENDO GLI ATTRIBUTI NECESSARI

    private final Veicolo [] veicoli;
    private int testa;
    private int coda;
    private int riemp;


    public Autorimessa(int maxPosti, String nome) {
        super(maxPosti, nome);

        this.veicoli = new Veicolo[maxPosti];
        this.testa = 0;
        this.coda = 0;
        this.riemp = 0;
    }

    private boolean autorimessaPiena() {
        return riemp == super.maxPosti;
    }

    private boolean autorimessaVuota() {
        return riemp == 0;
    }

    @Override
    public void entra(Veicolo v) throws AutorimessaPienaException,TargaNonValidaException{
        if (autorimessaPiena()){
            throw new AutorimessaPienaException("Autorimessa Piena. Ingresso vietato\n");
        }
        if (!v.controllaTarga()){
            throw new TargaNonValidaException("Targa non valida. Ingresso vietato\n");
        }

        veicoli[coda] = v;

        coda = (coda + 1) % super.maxPosti;

        riemp++;
    }

    @Override
    public Veicolo esce() throws AutorimessaVuotaException {
        if (!autorimessaVuota()){
            Veicolo veicolo = veicoli[testa];

            veicoli[testa] = null;

            testa = (testa + 1) % super.maxPosti;
            riemp--;

            return veicolo;
        }
        else {
            throw new AutorimessaVuotaException("Autorimessa vuota. Impossibile rimuovere veicoli.\n");
        }
    }

    @Override
    public String toString() {

        StringBuffer b = new StringBuffer();

        b.append(super.toString()).append(" Capienza= ").append(super.maxPosti).append("\nVeicoli Presenti:\n");
        for(int i = 0; i < veicoli.length; i++){
            b.append(veicoli[testa+i % riemp].toString());
        }
        return b.toString();
    }

}

