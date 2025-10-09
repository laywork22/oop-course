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
public class Autovettura extends Veicolo{
    private final int numeroPosti;
    
    public Autovettura (String numTelaio, String modello, String alimentazione, String targa, int numeroPosti){
        super(numTelaio,modello,alimentazione,targa);
        this.numeroPosti = numeroPosti;
    }
    
    public int getNumeroPosti(){
        return numeroPosti;
    }
    
    @Override
    public boolean controllaTarga(){
        if (getTarga().length() != 7){
            return false;
        }
        
        char c[] = getTarga().toCharArray();
        
        return Character.isLetter(c[0]) && Character.isLetter(c[1]) && 
                Character.isDigit(c[2]) && Character.isDigit(c[3]) && 
                Character.isDigit(c[4]) && Character.isLetter(c[5]) && Character.isLetter(c[6]);  
    }
    
    @Override
    public String toString(){
        return super.toString() + "Numero Posti: " + numeroPosti + "\n";
    }
}
