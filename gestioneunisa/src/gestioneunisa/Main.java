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
import it.unisa.diem.oop.persone.Docente;
import it.unisa.diem.oop.persone.Persona;// il nome della classe comprende l'intera path della classe stessa
import it.unisa.diem.oop.persone.Studente;
import it.unisa.diem.oop.persone.StudenteErasmus;
//import it.unisa.diem.oop.persone.*; --> se voglio importare tutto il package

/**
 *
 * @author Flytr
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Persona p = new Persona("Mario", "Rossi","MRS0001");
        
        //esiste la superclasse per eccellenza, Object
        //ogni classe nata da una superclasse
        
        //in java tutti gli oggetti devono avere una rappresentazione di tipo stringa affinchè possano essere fornite informazioni importanti
        System.out.println(p.toString());
        
        
        System.out.println("");
        
        //PersonaUnisa p1 = new PersonaUnisa("Luigi","Bianchi","LGB0001","061270001");
        //System.out.println(p1.toString());
        
        Studente s = new Studente("Ernesto", "Grigi", "ERG0001","061270002",28.5F);
        System.out.println(s.toString());
        
        //se nel modello un oggetto non è presente, può essere definita una classe astratte che racchiude alcune caratteristiche più geneerali.
        //le specializzazione di classi astratte prendono il nome di classi concretizzate
        
        StudenteErasmus se = new StudenteErasmus("Andrew","James","ANJ0001","061270005",31.0F);
        System.out.println(se);
        
        
        Docente d = new Docente("Enrico", "Bianchi", "EB0001", "325600", "ASD");
        System.out.println(d);
        
        
        System.out.println(d.getNome());
        
        /*upcast*/ 
        Persona p2 = s; 
        //il riferimento punta a un sottospazio dell'area di memoria in cui è stata allocata la variabile della sottoclasse
        
        /*downcast*/
        //Studente s2 = p2; -> incompatibile perchè lo spazio dei metodi e degli attributi è maggiore di Persona
        Studente s2 = (Studente) p2;//-> casting implicito
        System.out.println(s2.getVotoMedio());
        
        //posso accedere solamente ai campi della superclasse Persona
        System.out.println(p2.toString());
        
        System.out.println("\n");
        //una stessa firma produce risultati diversi del metodo invocato a seconda del tipo castato 
        System.out.println("Esempio di polimorfismo: \n");
        Persona persone[] = new Persona[3];
        
        persone[0] = p;
        persone[1] = s;
        persone[2] = d;
        
        for(int i = 0; i < persone.length; i++){
            System.out.println(persone[i].toString());
            
            //le due righe sotto tirano un'eccezione
           /* Studente sx = (Studente) persone[i];
            System.out.printf("%.2f\n",sx.getVotoMedio());*/
           
           if(persone[i] instanceof Studente){
                Studente sx = (Studente) persone[i];
                System.out.printf(" ---- STAMPA VOTO ---- : %.2f\n",sx.getVotoMedio());
           }
        }
        
        //classi wrapper per variabili di tipo primitivo
        
        Integer i = new Integer(3);
        
        int b = Integer.parseInt("10");
        
        
    }
}
