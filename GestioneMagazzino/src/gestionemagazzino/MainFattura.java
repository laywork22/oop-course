package gestionemagazzino;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import gestionemagazzino.Fattura.Riga;

public class MainFattura {
    public static void main(String[] args) {
        Fattura f = new Fattura("Spese cancelleria", LocalDate.now());

        f.aggiungi(new Prodotto("Penna", 3.0F, "2024-05-01"), 3);
        f.aggiungi(new Prodotto("Matita", 1.5F, "2024-05-01"), 2);
        f.aggiungi(new Prodotto("Gomma", 2.0F, "2024-05-01"), 1);

        System.out.println("Totale fattura: " + f.getTotale());

        //è possibile utilizzare la classe riga all'esterno con questa sintassi (a patto che sia pubblica)
        //la classe Riga è subordinata all'esistenza di Fattura
        // Fattura.Riga r = f.new Riga(new Prodotto("Gomma", 2.0F, "2024-05-01"), 1); //inner class pubblica


        //classe statica -> non è necessaria un'istanza della classe
        Prodotto p = new Prodotto("Gomma", 2.0F, "2024-05-01");
        //Fattura.Riga r = new Fattura.Riga(p, 0);

        /*dipendenze immediate -> per l'organizzazione è necessaria la classe annidata
        collocazione semantica generale -> necessità non grande ma è un modo per gestire l'organizzazione
        usiamo un import statico per estrarre solo la classe annidata*/
        //Riga r1 = new Riga(p, 0);


        //classi anonime -> istanziare interfacce senza implementarle in una classe
        Set<Prodotto> prodotti = new TreeSet<>(new Comparator<Prodotto>() {
            @Override
            public int compare(Prodotto o1, Prodotto o2) {
                return Integer.compare(o1.getCodice(), o2.getCodice());
            }
        });

        //solo per un'interfaccia funzionale
        Set<Prodotto> prod = new TreeSet<>((o1, o2) -> Integer.compare(o2.getCodice(), o1.getCodice()));
        //oppure (o1, o2) -> {
        //  return Integer.compare(o2.getCodice(), o2.getCodice());
        //}

        prodotti.add(new Prodotto("Penna", 3.0F, "2024-05-01"));
        prodotti.add(new Prodotto("Matita", 1.5F, "2024-05-01"));
        prodotti.add(new Prodotto("Gomma", 2.0F, "2024-05-01"));

        System.out.println(prodotti);

    }
}
