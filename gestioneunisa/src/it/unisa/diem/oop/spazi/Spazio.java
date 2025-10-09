/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.spazi;

/**
 *
 * @author Flytr
 */

//a differenza di un'interfaccia, una classe astratta ha uno stato (attributi)

public abstract class Spazio implements Accessibile {
    private String nome;
    private int maxPosti;
    
    public Spazio (String nome, int maxPosti){
        this.nome = nome;
        this.maxPosti = maxPosti;
    }
    
    public int getMaxPosti(){
        return maxPosti;
    }
    
    public String getNome(){
        return nome;
    }
    
    public abstract boolean isPieno();
    
    public abstract boolean isVuoto();
    
    public abstract String getTipo();
    
    @Override
    public String toString(){
        return getTipo() + ":" + nome + " Capienza: " + maxPosti;
    }
}
