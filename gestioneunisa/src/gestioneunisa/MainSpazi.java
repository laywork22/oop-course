/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestioneunisa;

import it.unisa.diem.oop.eccezioni.AccessibilePienoException;
import it.unisa.diem.oop.persone.*;
import it.unisa.diem.oop.spazi.*;


/**
 *
 * @author Flytr
 */
public class MainSpazi {

    /**
     * @param args the command line arguments
     */
    //se nessuno gestisce le eccezioni obbligatorie e si arriva alla fine della gerarchia allora l'eccezione controllata può essere assimilata
    //a una non controllata e si ferma il programma
    public static void main(String[] args) /*throws AccessibilePienoException*/ {
        // TODO code application logic here
        
        Persona p = new Persona("Mario", "Rossi","MRS0001");
        Studente s = new Studente("Ernesto", "Grigi", "ERG0001","061270002",28.5F);
        StudenteErasmus se = new StudenteErasmus("Andrew","James","ANJ0001","061270005",27.0F);
        Docente d = new Docente("Enrico", "Bianchi", "EB0001", "325600", "ASD");
         
        PersonaUnisa pu = s; //si può comunque usare una classe astratta come tipo

        //override presuppone che il tipo dei parametri della superclasse
        //in caso contrario si parla di overload

        
        Aula a = new Aula("A",3);

        //bisogna gestire obbligatoriamente l'eccezione controllata
        /*È possibile gestire tutti i metodi con un solo try ma non riesco a rilevare la riga che ha generato l'eccezione*/
        //nel blocco catch è possibile prendere contromisure in risposta ad un'anomalia ma la notazione try-catch è pesante per la leggibilità del programma
        // è possibile circondare l'intero main all'interno del try o una qualsiasi
        //tutto quello che accade dopo l'eccezione e la sua gestione non conta, si blocca il programma, quindi a seconda delle metodologie
        //potrebbe essere meglio incapsulare tutto in un try farne di più per ogni riga che potrebbe generare un'eccezione come ad esempio l'invocazione di metodi scorporati...


        try {
            a.entra(p);
        } catch (AccessibilePienoException e) {
            throw new RuntimeException(e);
        }
        try {
            a.entra(s);
        } catch (AccessibilePienoException e) {
            throw new RuntimeException(e);
        }

        try {
            a.entra(se);
        } catch (AccessibilePienoException e) {
            throw new RuntimeException(e);
        }
        //a.esce();

        try {
            a.entra(d);
        } catch (AccessibilePienoException e) {
            throw new RuntimeException(e);
        }


        /*try {

        * }catch(AccessibileException ex){
            Con un solo gestore catturo entrambe le eccezioni e copro tutte le casistiche con un solo handler
            Lo posso fare perchè le due eccezioni fanno parte della stessa gerarchia
          }

          oppure

          try {

        * }catch(AccessibilePienoException ex){
            posso concatenare le catch per gestire separatamente le varie eccezioni
          }
          catch (AccessibileVuotoException ex){

          } ...

          Più generico e funziona anche nei casi in cui le eccezioni appartengono a gerarchie diverse!
        */

        //le interfacce sono istanziabili
        Accessibile ae = a;
        
        System.out.println(a);

        //ciao
         
    }
    
}
