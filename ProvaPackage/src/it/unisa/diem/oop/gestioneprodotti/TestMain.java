/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gestioneprodotti;

import it.unisa.diem.oop.gestioneclienti.Clienti;

/**
 *
 * @author leonardorundo
 */
public class TestMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Creazione di due oggetti Cliente a cui destinare la fattura        
        Clienti cliente1 = new Clienti(
            "Luca", 
            "Bianchi", 
            "BNCGLC90C01F205Y", 
            "Via Milano 25, Torino"
        );
        
        Clienti cliente2 = new Clienti(
            "Matteo", 
            "Rossi", 
            "MTTRSS88C12H354W", 
            "Via Palermo 43, Napoli"
        );
        
        
        // Creazione e popolamento di tre oggetti Fattura
        Fattura fatt1 = new Fattura(5, cliente1, "20-09-2025", 0.10F); // fattura con max 5 prodotti
        fatt1.aggiungiProdotto(new Prodotto(35,"Pane", 1.20, "20-09-2025"));
        fatt1.aggiungiProdotto(new Prodotto(56,"Latte", 1.00, "03-09-2025"));
        fatt1.aggiungiProdotto(new Prodotto(22,"Pasta", 0.90, "30-07-2025"));
        System.out.println(fatt1.stampaFattura());
        
        Fattura fatt2 = new Fattura(6, cliente2, "23-09-2025", 0.12F); // fattura con max 6 prodotti
        fatt2.aggiungiProdotto(new Prodotto(13,"Uova", 1.80, "19-09-2025"));
        fatt2.aggiungiProdotto(new Prodotto(78,"Zucchero", 1.00, "11-06-2025"));
        fatt2.aggiungiProdotto(new Prodotto(55,"Farina", 0.90, "03-05-2025"));
        fatt2.aggiungiProdotto(new Prodotto(66,"Vaniglia", 2.10, "20-04-2025"));
        System.out.println(fatt2.stampaFattura());
        
        Fattura fatt3 = new Fattura(5, cliente1, "24-09-2025", 0.22F); // fattura con max 5 prodotti
        fatt3.aggiungiProdotto(new Prodotto(132,"Tastiera", 25.50, "08-11-2024"));
        fatt3.aggiungiProdotto(new Prodotto(145,"Mouse", 10.00, "30-09-2024"));
        fatt3.aggiungiProdotto(new Prodotto(188,"Monitor", 399.99, "05-02-2025"));
        System.out.println(fatt3.stampaFattura());
    }
    
}
