package gestionemagazzino;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//3-11-25  Classi Annidate
public class Fattura {
    public String descrizione;
    private LocalDate dataEmissione;
    private List<Riga> righe;

    public Fattura(String descrizione , LocalDate dataEmissione){
        this.descrizione = descrizione;
        this.dataEmissione = dataEmissione;

        this.righe = new ArrayList<>();
    }


    public void aggiungi(Prodotto p, int quantita){
        this.righe.add(new Riga(p, quantita));
    }

    public float getTotale(){
        float totale = 0.0F;

        for(Riga r: righe){
            totale += r.quantita * r.p.getCost();
        }

        return totale;
    }

    public float getTotaleScontato(){
        float totale = 0.0F;

        class CalcolatoreSconto{
            float getPrezzoScontato(Riga r){
                int gruppitre = r.quantita%3;

                float costo;

                costo = r.p.getCost() * (r.quantita - gruppitre);

                return costo;
            }
        }

        CalcolatoreSconto calcolatore = new CalcolatoreSconto();

        for(Riga r: righe){
            totale += calcolatore.getPrezzoScontato(r);
        }

        return totale;
    }


    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Fattura: " + descrizione + " emessa il " + dataEmissione + "\n");

        for(Riga r: righe){
            sb.append(r);
        }
        //sb.append(this.righe.toString());

        return sb.toString();
    }


    //per le classi annidate è possibile dare una visibilità private
    //la classe riga non potrà in alcun modo essere referenziata al di fuori di Fattura

    //una classe annidata può essere statica
    //in questo caso è possibile non passare per l'istanza esterna
    public class Riga {
        private String descrizione;
        private Prodotto p;
        private int quantita;

        public Riga(Prodotto p, int quantita){
            this.p = p;
            this.quantita = quantita;

            String descrizione = "valore iniziale";

            this.descrizione = "valore descrizione riga"; //richiamo descrizione in Riga
            Fattura.this.descrizione = "valore descrizione fattura"; //richiamo descrizione in Fattura
        }

        Prodotto getProdotto(){
            return p;
        }

        int getQuantita(){
            return quantita;
        }


        @Override
        public String toString(){
            return p.toString() + " quantità:" + quantita;
        }
    }
}
