/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestioneunisa;

import it.unisa.diem.oop.persone.Studente;
import it.unisa.diem.oop.report.AnagraficaStudenti;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author laywork
 */
public class MainReport  {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        AnagraficaStudenti as = new AnagraficaStudenti("OOP");
        Studente s1 = new Studente("Ernesto", "Grigi", "ERG0001","061270001",28.5F);
        Studente s2 = new Studente("Ernesto", "Grigi", "ERG0001","061270002",28.5F);
        Studente s3 = new Studente("Ernesto", "Grigi", "ERG0001","061270003",28.5F);
        Studente s4 = new Studente("Ernesto", "Grigi", "ERG0001","061270004",28.5F);
        
        as.aggiungi(s1);
        as.aggiungi(s2);
        as.aggiungi(s3);
        as.aggiungi(s4);
        
        //System.out.println(as.toString());
        
        /*try {
            as.salvaDOS("anagrafica_bos.bin");
        } catch (IOException ex) {
            Logger.getLogger(MainReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        AnagraficaStudenti a2 = AnagraficaStudenti.leggiDIS("anagrafica_bos.bin");*/
        
        //as.salvaOBJ("anagrafica.obj");
        
        AnagraficaStudenti a3 = AnagraficaStudenti.leggiOBJ("anagrafica.obj");
        
        System.out.println(a3);
    }
    
}
