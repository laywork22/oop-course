/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionemagazzino;

/**
 *
 * @author Flytr
 */
//se il nome della classe è costituito da  più parole allora solo le prime lettere maiuscole di ciascuna parole (camel case)

//dallo spazio dinamico posso accedere a quello statico ma il contrario non è possibile
public class Prodotto {
    //SPAZIO ATTRIBUTI
    
    //proteggiamo le nostre variabili di istanza:
    private int codice;
    //N.B le stringhe sono classi 
    private String descrizione;
    private float cost;
    private String dataProduzione;
    static int numProdotti; //anche gli attributi statici possono essere privati e incapsulati
    //non conviene inizializzare nella definizione della classe l'attributo statico in quanto noi vogliamo che 
    //il valore non si annulli a ogni definizione di un oggetto di tale classe
    
    //ATTRIBUTI COSTANTI->inizializzato una sola volta e non più modificato
    //final int n; //è il costruttore che lo inizializza quindi il set è inutile
    
    
    
    //SPAZIO METODI
    //prima lettera minuscola e seconda maiuscola->sintassi corretta
    
    //inizializzatore statico
    static {
        //numProdotti = 0;
    }
    
    /*inizializzatore di istanza -> si mette del codice che dovrà essere 
    inizializzato da tutti i costruttori alternativi */
    /*Il costruttore chiamerà sempre l'inizializzatore per primo. Possiamo usarlo per definire
    le azioni che tutti i costruttori dovranno eseguire*/
    {
        numProdotti++;
    }
    
    //METODO COSTRUTTORE -> non ha un tipo restituito (restituisce un riferimento) ed è un metodo pubblico
    //è possibile definire più costruttori! -> overloading, diverse impleemntazioni di un metodo con lo stesso nome ma parametri diversi(numero e tipo) che li distinguono l'uno dall'altro
    //un costruttore vuoto inizializza ai valori di default
    
    //una volta definito un costruttore senza parametri, quello di default non è più disponibile
    public Prodotto(){
        /*this.codice = 1;*/
        
        this.codice = numProdotti;
        this.cost = 0.0F;
        this.descrizione = "Non disponibile";
        this.dataProduzione = "2025-01-01";
        //this(numProdotti,"Non disponibile",0.0F,"2025-01-01");
    }
    
    //si può invocare un costruttore dentro un altro costruttore detto 
    //costruttore generale. È consigliabile che questo sia capace di passare come parametri tutti gli attributi 
    //dell'oggetto...scegliamo questo sotto!
    
    public Prodotto(int codice, String descrizione, float costo, String dataProduzione){
        this.codice = codice;
        this.cost = costo;
        this.descrizione = descrizione;
        this.dataProduzione = dataProduzione;
    }
    
   /* public Prodotto(String descrizione, float costo, String dataProduzione){
        this.codice = numProdotti;
        this.cost = costo;
        this.descrizione = descrizione;
        this.dataProduzione = dataProduzione;
    }*/
    
    public Prodotto(String descrizione, float costo, String dataProduzione){
        //con questa magia evito ridondanze e il dover usare l'inizializzatore di istanza
        this(numProdotti,descrizione,costo,dataProduzione);
    }
    
    
    //i metodi accedono allo stato dell'oggetto apertamente 
    public void stampaInfo(){
        System.out.println("**** Prodotto ****");
        
        System.out.print("Codice: ");
        System.out.println(codice);
        
        System.out.print("Descrizione: ");
        System.out.println(descrizione);
        
        System.out.print("Costo: ");
        System.out.println(cost);
        
        System.out.print("Data di produzione: ");
        System.out.println(dataProduzione);
    }
    
    public int stampaInfo(int c){
        return 1; 
    }
    //metodi setter e getter->metodi di accesso e seguono una convenzione per i nomi
    
    //impostare il codice 
    //parametro formale con stesso nome della variabile d'istanza -> si usa "this" ed equivale a "questa istanza"
    
    //il passaggio dei parametri avviene sempre per copia
    public void setCodice(int codice){
        this.codice = codice;
    }
    
    //metodo get
    public int getCodice() {
        return this.codice; //non c'è ambiguità ma male non fa mettere this invece che solo codice
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getDataProduzione() {
        return dataProduzione;
    }

    public void setDataProduzione(String dataProduzione) {
        this.dataProduzione = dataProduzione;
    }
    
    public static int getNumProdotti(){
        //è sempre possibile accedere al contesto statico da qualunque oggetto della classe, anche con un metodo non statico
        // se aggiungo static adesso il metodo è utilizzabile anche esternamente alla singola istanza
        return numProdotti; 
    }
    
    /*public static int getCode(){
        //return codice 
        //-> codice appartiene allo spazio dinamico e di conseguenza nel contesto statico non esiste
                
    }*/    
}