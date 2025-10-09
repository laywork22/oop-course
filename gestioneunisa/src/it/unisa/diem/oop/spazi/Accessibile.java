/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.spazi;

import it.unisa.diem.oop.eccezioni.AccessibileException;
import it.unisa.diem.oop.eccezioni.AccessibilePienoException;
import it.unisa.diem.oop.eccezioni.AccessibileVuotoException;
import it.unisa.diem.oop.persone.Persona;

/**
 *
 * @author Flytr
 */
//i nomi delle interfacce sono spesso aggettivi che rimandano ad alcune caratteristiche
//nel meccanismo degli spazi vogliamo modellare uno spazio 
//spazio accessibile se implementa un comportamento
//i metodi all'interno di un'interfaccia sono astratti e pubblici
//da java 8 in poi si hanno alternativi con metodi statici concreti 

//le interfacce racchiudono i metodi che descrivono 
public interface Accessibile {
    void entra(Persona p) throws AccessibilePienoException;
    
    Persona esce() throws AccessibileVuotoException;
}
