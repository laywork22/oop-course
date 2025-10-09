package gestionemagazzino;

public class MainTwo {

    public static void main(String[] args) {
        // TODO code application logic here
        
        /*ARRAY*/
        
        int v[]; //v è un riferimento non inizializzato        
        v = new int[3]; //array di 3 elementi di tipo intero
        
        //System.out.println(v[0]);
        
        v[1] = 3;
        
        //non si può accedere ad un elemento fuori dal range->compilatore mi tira una bella eccezione
        //length è un final, non si può modificare
        for(int i = 0; i < v.length/*4*/; i++){
            System.out.println(v[i]);
        }
        
        //Tipi riferimento
        
        Prodotto prodotti[];
        prodotti = new Prodotto[3]; // i 3 oggetti non sono ancora istanziati, vanno generati
        
        prodotti[0] = new Prodotto("Tavolo",30.0F,"2024-06-01");
        prodotti[1] = new Prodotto("Penna",3.0F,"2024-05-01");
        prodotti[2] = new Prodotto("Microfono",30.0F,"2023-06-01");
        
        //è molto più utile sapere la dimensione logica per evitare NullPointerException
        for(int i = 0; i < prodotti.length; i++){
            prodotti[i].stampaInfo();
        }   
        
        System.out.println();
        
        /*for each->iterazione su tutti gli elementi dell'array
        sconsigliato perchè scorre tutti gli elementi degli array*/
        for(Prodotto p : prodotti){
            p.stampaInfo();
        }
        
        /*STRINGHE*/
        
        //le stringhe sono immutabili
        String s = "Tavolo";
        String s2 = "Tavolo";
        String s1 = new String("Penna");
        //String s1 = new String("Tavolo"); //se si utilizza il metodo costruttore i riferimenti sono diversi
        
        boolean test = s == s1;
        
        System.out.println(test); //la jvm elimina le stringhe duplicate
        //esiste uno spazio chiamato string pull in cui ogni qualvolta si alloca una stringa e quando deve essere verificato se il
        //contenuto è uguale, il riferimento si duplica
        
        boolean test1 = s.equals(s2); //metodo per confrontare contenuto di due stringhe
        System.out.println(test1);
        
        //CONCATENAZIONE
        String nomiProdotto = "";
        
        for(int i = 0; i < prodotti.length; i++){
            //a ogni concatenazione si crea un nuovo oggetto in quanto le stringhe sono immutabili
            //si usa per array di poche stringhe
            nomiProdotto+=prodotti[i].getDescrizione();
        }
        
        System.out.println(nomiProdotto);
        
        /*Esiste una classe StringBuffer che non è immutabile*/
        StringBuffer nomiProdottoBuffer = new StringBuffer();
        
        for(int i = 0; i < prodotti.length; i++){
            nomiProdottoBuffer.append(prodotti[i].getDescrizione());
            nomiProdottoBuffer.append("\n");
        }
        
        //durante l'operazione l'oggetto sarà UNICO
        
        System.out.println(nomiProdottoBuffer);
        
    }
    
}
