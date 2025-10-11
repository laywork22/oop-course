package it.unisa.diem.oop.veicoli;
import Eccezioni.AutorimessaPienaException;
import Eccezioni.AutorimessaVuotaException;
import Eccezioni.BoxException;
import Eccezioni.TargaNonValidaException;

public abstract class  Box  {

    // DA COMPLETARE
    public int maxPosti;
    private String nome;


    public Box(int maxPosti, String nome) {
        // DA COMPLETARE
        this.maxPosti = maxPosti;
        this.nome = nome;

    }

    public int getMaxPosti() {
        // DA COMPLETARE
        return maxPosti;
    }

    public void setMaxPosti(int maxPosti) {
        this.maxPosti = maxPosti;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public abstract void entra(Veicolo v) throws AutorimessaPienaException, TargaNonValidaException;

    public abstract Veicolo esce() throws AutorimessaVuotaException;

    @Override
    public String toString() {
        return "Nome box = " + nome + ", Numero posti: " + maxPosti;
    }
}

