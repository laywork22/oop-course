/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.persone;

import it.unisa.diem.oop.persone.PersonaUnisa;

/**
 *
 * @author Flytr
 */
public class Docente extends PersonaUnisa{
    public String insegnamento;
    
    public Docente(String nome, String cognome, String codiceFiscale, String Matricola, String insegnamento){
        super(nome,cognome,codiceFiscale,Matricola);
        this.insegnamento = insegnamento;
    }

    public String getInsegnamento() {
        return insegnamento;
    }
    
    @Override
    public String getRuolo(){
        return "Docente";
    }
    
    @Override
    public String toString(){
        return super.toString() + "\nInsegnamento: " + insegnamento;
    }
}
