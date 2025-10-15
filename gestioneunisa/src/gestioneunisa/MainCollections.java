/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

Studente 
-nome,cognome,codiceFiscale, matricola, votoMedio
Docente
-nome, cognome, codiceFiscale, matricola, insegnamento
Tecnico
-nome, cognome, codiceFiscale, matricola, dipartimento
 */
package gestioneunisa;

import it.unisa.diem.oop.persone.*;

import java.util.*;
//import it.unisa.diem.oop.persone.*; --> se voglio importare tutto il package

/**
 *
 * @author Flytr
 */
public class MainCollections {

    /**non l hai visto negro   godo se muore luffy   ichigo neg diff luffy
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Persona p = new Persona("Maria", "Rossi","MRS0001");

        //Object p3 = p.clona();
        // si introducono i tipi generics
        //Persona p3 = p.clona();

        Persona p2 = new Persona("Mario","Rossi", "MRS0002");

        Persona p3 = new Persona("Maria", "Rossi", "MRS0003");

        Studente s = new Studente("Ernesto", "Grigi", "ERG0001","061270002",28.5F);

        StudenteErasmus se = new StudenteErasmus("Andrew","James","ANJ0001","061270005",31.0F);

        Docente d = new Docente("Enrico", "Bianchi", "EB0001", "325600", "ASD");
        
        boolean test = p.equals(p3);

        System.out.println(test);

        //Collection<String> c = new ArrayList<String>();
        //ai fini dell'intelligibilità è meglio usare un tipo interfaccia
        System.out.println("\n***Set***\n");

        Set<String> c = new HashSet<>();
        //List<String> c = new ArrayList<String>();

        c.add("Mario");
        c.add("Ernesto");
        c.add("Luigi");

        //String n2 = c.get(2); //valido per List

        //le liste mantengono il comportamento di una sequenza
        //si stampano in ordine di inserimento
        for(String nome : c) {
            System.out.println(nome);
        }

        System.out.println("\n*********\n");

        /*utilizziamo l'iteratore
        deve essere dello stesso tipo della stringa
        è un one time object, se ne deve istanziare un altro
        se si vuole eliminare un elemento della collezione si deve usare il metodo remove
        dell'iteratore altrimenti se si usa remove della collezione, l'iteratore perde il riferimento dell'elemento rimosso e
        si blocca*/

        Iterator<String> i = c.iterator();

        while(i.hasNext()){
            String corrente = i.next();

            System.out.println(corrente);
        }

        Set<Persona> persone = new HashSet<>();

        persone.add(p);
        persone.add(p2);
        persone.add(p3);
        persone.add(s);

        System.out.println(persone);

        System.out.println("\n***TreeSet***\n");

        Set<String> s1 = new TreeSet<>();

        s1.add("Mario");
        s1.add("Adele");
        s1.add("Luca");
        s1.add("Mario");

        System.out.println(s1);

        //è necessario implementare comparable per classi diverse che implementano una collezione
        Set<Persona> sp = new TreeSet<>(new CustomComparator());

        //il treeset utilizza la compareTo e non l'equals
        //Bisogna definirlo in un modo coerente
        //sia tra equals che tra compareTo

        sp.add(p);
        sp.add(p3);
        sp.add(p2);
        sp.add(s);
        sp.add(se);
        sp.add(d);

        System.out.println(sp);
    }
}
