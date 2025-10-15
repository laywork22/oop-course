package it.unisa.diem.oop.persone;

import java.util.Comparator;

public class CustomComparator implements Comparator<Persona> {
    //si riusano i metodi di confronto che si hanno sui tipi
    @Override
    public int compare(Persona o1, Persona o2){
        if (o1.getCognome().equals(o2.getCognome())) return o1.compareTo(o2);

        return o1.getCognome().compareToIgnoreCase(o2.getCognome());
    }
}
