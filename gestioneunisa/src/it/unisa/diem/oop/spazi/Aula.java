/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.spazi;

import it.unisa.diem.oop.eccezioni.AccessibilePienoException;
import it.unisa.diem.oop.eccezioni.AccessibileVuotoException;
import it.unisa.diem.oop.persone.Persona;

/**
 *
 * @author Flytr
 */
public class Aula extends Spazio{
    private Persona persone[];
    private int riemp;

    public Aula(String nome, int maxPosti){
        super(nome, maxPosti);
        
        persone = new Persona[maxPosti];
        
        this.riemp = 0;
    }
    
    @Override
    public boolean isPieno() {
        return riemp == persone.length;
    }

    @Override
    public boolean isVuoto() {
        return riemp == 0;
    }

    @Override
    public String getTipo() {
        return "AULA";
    }

    //all'interno del metodo che abbiamo definito accessibile non abbiamo detto che può lanciare eccezioni
    //se si vuole che un metodo di una sottoclassa vuole usare un'eccezione, ciò deve essere notificato al top della gerarchia

    @Override
    //le sottoclassi non possono estendere lo spazio delle eccezioni gestite dalla superclasse. Si ha un errore che può essere risolto estendendo la classe di eccezioni nella superclasse
    //le sottoclassi non estendono le eccezioni ma possono ridurle.
    //ciò è dovuto all'upcast -> se non vado a specificare a livello top potrei ad esempio far si che Aula gestisca l'eccezione ma Accessibile no
    //non si può sapere se l'oggetto è Aula o altro...
    public void entra(Persona p) throws AccessibilePienoException {
        if (isPieno()){
            //System.out.println("Aula piena");
            //return;

            //throw new AccessibilePienoException("Aula piena.");
        }
        
        persone[riemp++] = p;        
    }

    @Override
    public Persona esce() throws AccessibileVuotoException {
        if (isVuoto()){
            //System.out.println("Aula vuota");
            //return null;
            throw new AccessibileVuotoException("Aula vuota.");
        }
        
        Persona p = persone[riemp-1];
        persone[--riemp] = null;
        
        return p; 
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(super.toString() + "\n");
        
        for(int i = 0; i < riemp; i++){
            sb.append(persone[i].toString());
        }
        
        return sb.toString();
    }
}
