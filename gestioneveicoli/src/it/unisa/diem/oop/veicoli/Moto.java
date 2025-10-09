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
public class Moto extends Veicolo{
    private final boolean guidaLibera;
    
    public Moto(String numTelaio, String modello, String alimentazione, String targa, boolean guidaLibera){
        super(numTelaio,modello,alimentazione,targa);
        this.guidaLibera = guidaLibera;
    }
    
    boolean getGuidaLibera(){
        return guidaLibera;
    }
    
    @Override 
     /*Si possono usare le espressioni regolari:
    return getTarga().matches("^[A-Za-z]{2}\d{6}")
    Controlla che il pattern della stringa matchi quello della targa */
    public boolean controllaTarga(){
        /*if (getTarga().length() != 7){
            return false;
        }
        
        char c[] = getTarga().toCharArray();
        
        return Character.isLetter(c[0]) && Character.isLetter(c[1]) && 
                Character.isDigit(c[2]) && Character.isDigit(c[3]) && 
                Character.isDigit(c[4]) && Character.isDigit(c[5]) && Character.isDigit(c[6]); */
        
        return getTarga().matches("^[A-Za-z]{2}\\d{5}");
    }
    
    @Override
    public String toString(){
        return super.toString() + "Guida Libera: " + guidaLibera + "\n";
    }
}
