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
public class Main {

    /**
     * @param args the command line arguments
     */
    /*È il primo metodo che viene eseguito e di conseguenza deve essere statico*/
    int x; //è visibile dal main? NO perchè non è stata istanziato l'oggetto du classe Main
    static int y;
    
    public static void main(String[] args) {
        //una variabile locale non inizializzata non è inizializzata dal compilatore e darà un errore
        
        //Main m = new Main();
        //m.x = 3;
        //y = 4;
        
        int a=5;
        
        System.out.println(a);
        
        Prodotto p; //dichiarazione di una variabile locale di tipo Prodotto
        //ogni volta che creo un oggetto viene allocato nell'heap -> la variabile dichiarata conterrà il riferimento ad un'istanza della classe
        p = new Prodotto(10,"Tavolo",13.5F,"2025-06-01"); //inizializzazione dell'oggetto(riferimento) e degli attributi ai valori predefiniti del tipo -> metodo costruttore PREDEFINITO dell'oggetto->bisogna specificare che si tratta di una nuova istanza
        
        //tipo predefinito per oggetti: NULL
        
        //ricordiamo che sta roba non va bene(stiamo andando direttamente a modificare i campi  dell'oggetto:
        //p.codice = 1; //Inaccessibili perchè adesso privati
        //p.descrizione = "Tavolo"; 
        
        //System.out.println(p.codice);
        //System.out.println(p.descrizione);
        
        // l'oggetto è la parte dinamica della struttura
        //esiste un'altra regione chiamata metaspazio->ospita la struttura delle classi, eventuali costanti, istruzioni che servono ad eseguire i metodi...è anche lo spazio
        //che contiene il contesto statico 
        
        //contesto dinamico -> oggetti istanziati a partire dalle classi
        
        //variabili statiche o di classe: attributi che sono comuni a tutti gli oggetti della stessa classe->sono caricate all'avvio del programma-> esistono prima di istnaziare un oggetto
        //ciclo di vita coincidente con quello del programma
        
        
        Prodotto p1 = new Prodotto();
        p1.setCodice(2);
        p1.setCost(2.0F);
        p1.setDescrizione("Penna");
        p1.setDataProduzione("2024-01-01");
        
        p1.stampaInfo();
        
        int b = Prodotto.numProdotti;
        System.out.println(b);
        
        System.out.println(" ");
        Prodotto p2 = new Prodotto();
        
        //p2 = p;
        
        //p.stampaInfo();
        p2.stampaInfo();
       
        System.out.println(Prodotto.getNumProdotti());
        
    }
}
