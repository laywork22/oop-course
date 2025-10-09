/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.veicoli;

/**
 *
 * @author Flytr
 */
public abstract class Veicolo {
    private final String numTelaio;
    private final String modello;
    private final String alimentazione;
    private String targa;
    
    public Veicolo(String numTelaio, String modello, String alimentazione, String targa){
        this.numTelaio = numTelaio;
        this.modello = modello;
        this.alimentazione = alimentazione;
        this.targa = targa;
    }

    public String getNumTelaio() {
        return numTelaio;
    }

    public String getModello() {
        return modello;
    }

    public String getAlimentazione() {
        return alimentazione;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }
    
    public abstract boolean controllaTarga(); //utilizzerò varie implementazioni per una revisione futura
    
    @Override
    public String toString(){
        return "--- Veicolo ---\n" + "Numero Telaio: " + numTelaio + "\nModello: " + modello + "\nAlimentazione: " + alimentazione + "\nTarga: " + targa + "\n";
    }
    
}
