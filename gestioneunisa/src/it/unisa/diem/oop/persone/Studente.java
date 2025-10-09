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
/*Se metto final dopo public alla classe non vi potranno essere più specializzazioni, blocca la gerarchia*/
public class Studente extends PersonaUnisa{
    private final float votoMedio;
    
    public Studente(String nome, String cognome, String codiceFiscale, String Matricola, float votoMedio){
        super(nome,cognome,codiceFiscale,Matricola);
        
        if(votoMedio < 18 || votoMedio > 31){
            //non presuppone una gestione dell'oggetto
            throw new RuntimeException("Voto non consentito.");
        }
        
        this.votoMedio = votoMedio;
    }
    
    public float getVotoMedio(){
        return votoMedio;
    }
    
    @Override
    public String getRuolo(){
        return "Studente";
    }
    
    @Override //metodo già definito nella superclasse e ridefinito nella sottoclase1
    //usando la parola chiave final prima di public, il metodo della supermetodo non potrà essere sovrascritto
    public String toString(){
        return super.toString() + "\nVoto Medio: " + votoMedio;
    }
}
