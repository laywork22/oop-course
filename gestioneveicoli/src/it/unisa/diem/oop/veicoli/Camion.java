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
public class Camion extends Veicolo{
    private final int numeroAssi;
    
    public Camion(String numTelaio, String modello, String alimentazione, String targa, int numeroAssi){
        super(numTelaio, modello, alimentazione, targa);
        this.numeroAssi = numeroAssi;
    }
    
    public int getNumeroAssi(){
        return numeroAssi;
    }
    
    @Override
    public boolean controllaTarga() {
        String str = super.getTarga();
        
        if (str.length() != 8){
            return false;
        }
        
        for (int i = 0; i < 2; i++){
            Character c = str.charAt(i);
            
            if(!c.isLetter(c)){
                return false;
            }
        }
        
        for (int i = 2; i < 8; i++){
            Character c = str.charAt(i);
            
            if(!c.isDigit(c)){
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public String toString(){
        return super.toString() + "Numero assi :" + numeroAssi + "\n";
    }
}
