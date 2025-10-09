/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.persone;

/**
 *
 * @author Flytr
 */
public class StudenteErasmus extends Studente{
    public StudenteErasmus(String nome, String cognome, String codiceFiscale, String Matricola, float votoMedio){
        super(nome,cognome,codiceFiscale,Matricola,votoMedio);
        
    }
    
    @Override
    public String getRuolo(){
        return "Studente Erasmus";
    }
    
    @Override
    public float getVotoMedio(){
        return super.getVotoMedio()/3; //ritorna il voto in decimi
    }
    
    @Override
    public String toString(){
        return super.toString() + "\nVoto medio (decimi): " + getVotoMedio();
    }
}
