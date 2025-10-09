/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gestioneprodotti;

/**
 *
 * @author Flytr
 */
public class Prodotto {
    //SPAZIO ATTRIBUTI
    
    //proteggiamo le nostre variabili di istanza:
    private final int codice;
    private final String descrizione;    //N.B le stringhe sono classi 
    private final double cost;
    private final String dataProduzione;
        
    //SPAZIO METODI
    //prima lettera minuscola e seconda maiuscola->sintassi corretta
    
    public Prodotto(int codice, String descrizione, double costo, String dataProduzione){
        this.codice = codice;
        this.cost = costo;
        this.descrizione = descrizione;
        this.dataProduzione = dataProduzione;
    }
    
    //i metodi accedono allo stato dell'oggetto apertamente 
    public String stampaProdotto(){
        return "Codice: " + codice + " --- Costo: " + (float)cost + " --- Descrizione: " + descrizione + " --- Data di produzione: " + dataProduzione + "\n";
    }
    
    //metodo get
    public int getCodice() {
        return this.codice; //non c'è ambiguità ma male non fa mettere this invece che solo codice
    }

    public String getDescrizione() {
        return descrizione;
    }

    public double getCost() {
        return cost;
    }

    public String getDataProduzione() {
        return dataProduzione;
    }
}