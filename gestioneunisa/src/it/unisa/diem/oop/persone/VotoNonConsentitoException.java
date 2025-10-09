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
//Notifica di errore durante l'esecuzione del codice
//Il nome dell'eccezione deve riflettere il problema ed è non controllata
public class VotoNonConsentitoException extends RuntimeException{
    /**
     * Constructs an instance of <code>VotoNonConsentitoException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public VotoNonConsentitoException(){
    }

    public VotoNonConsentitoException(String msg) {
        super(msg);
    }
}

/*Nelle eccezioni controllate il programma si fermerà a un punto del codice e lascerà il controllo ad un handler che gestirà l'eccezione
* Non si è obbligati e risolvere l'eccezione è possibile rilanciare all'handler del chiamante fino a quando non si ritorna al livello precedente
* */

/*Bisognerà specificare esplicitamente  la classe dell'eccezione da lanciare try catch
* Un handler può gestire delle eccezioni e rilanciarne altre*/
