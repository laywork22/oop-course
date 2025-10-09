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

//se è stato effettuato un overload del costruttore della superclasse, la sottoclasse ci obbligherà a utilizzarlo per inizializzare i parametri
public abstract class PersonaUnisa extends Persona {
    String matricola;
    
    //super -> costruttore della superclasse
    //super.--  -> metodi della superclasse
    
    //PersonaUnisa è una metarappresentazione, non rappresenta un oggetto concreto del contesto del problema
    //(sono troppo generiche) ma magari si preferisce utilizzare
    //le sottoclassi derivate che sono più semplici da definire a partire da questa classe
    //PersonaUnisa è definita una classe astratta e serve per il modello. Non ha una traduzione nello spazio del problema
    //non si possono istanziare classi astratte
    public PersonaUnisa(String nome, String cognome, String codiceFiscale, String matricola){
       
        super(nome,cognome,codiceFiscale); 
        
        this.matricola = matricola;
    }
    
    public String getMatricola(){
        return matricola;
    }
    
    public void setMatricola(String matricola){
        this.matricola = matricola;
    }
    
    @Override
    public String toString(){
       return "\n-->" + getRuolo() + "\n" + super.toString() + "\nMatricola: " + matricola; 
    }
    
    //metodo astratto -> c'è il prototipo ma non l'implementazione del metodo
    public abstract String getRuolo(); //una classe specializzata potrà ridefinire questo metodo
    //una classe astratta non deve necessariamente avere metodi e attributi astratti
    //posso utilizzare un metodo astratto in una classe astratta perchè la sottoclasse 
    //che la estende sarà obbligata a fornire un'implementazione
}
