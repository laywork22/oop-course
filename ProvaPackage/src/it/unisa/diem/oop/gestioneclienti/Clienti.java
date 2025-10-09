/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gestioneclienti;

/**
 *
 * @author Flytr
 */
public class Clienti {
    //Spazio attributi
    private final String nome;
    private final String cognome;
    private final String codiceFiscale;
    private final String indirizzo;
    
    //Spazio dei metodi
    public Clienti(String nome, String cognome, String codiceFiscale, String indirizzo){
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.indirizzo = indirizzo;
    }
    
    public String stampaCliente(){
        return "Nome: " + this.nome + "\nCognome: " + this.cognome + "\nCodice Fiscale: " + this.codiceFiscale+"\nIndirizzo: " + this.indirizzo;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public String getCognome(){
        return this.cognome;
    }
    
    public String getCodiceFiscale(){
        return this.codiceFiscale;
    }
    
    public String getIndirizzo(){
        return this.indirizzo;
    }
}
