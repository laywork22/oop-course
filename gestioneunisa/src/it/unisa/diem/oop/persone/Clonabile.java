package it.unisa.diem.oop.persone;

/*interfaccia generica T di tipo generics
introdotto per consentire allo sviluppatore di non fare errori
a tempo di compilazione T sarà sostituito con il tipo opportuno*/
public interface Clonabile<T>{
    T clona();
}
