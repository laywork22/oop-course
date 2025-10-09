/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gestioneprodotti;
import it.unisa.diem.oop.gestioneclienti.Clienti;

/**
 *
 * @author Flytr
 */

public class Fattura {
    private final int codice;
    private final Prodotto prodotti[];
    private int dimLogica = 0;
    private final Clienti clienteDestinatario;
    private final String dataEmissione;
    private final float percentualeTassa;
    
    public Fattura(int codice, Clienti clienteDestinatario, String dataEmissione, float percentualeTassa){
        this.codice = codice;
        this.prodotti = new Prodotto[10];
        this.dimLogica = 0;
        this.clienteDestinatario = clienteDestinatario;
        this.dataEmissione = dataEmissione;
        this.percentualeTassa = percentualeTassa;
    }
    
    
    public int getCodice() {
        return codice;
    }

    public Prodotto[] getProdotti() {
        return prodotti;
    }

    public int getDimLogica() {
        return dimLogica;
    }

    public Clienti getClienteDestinatario() {
        return clienteDestinatario;
    }

    public String getDataEmissione() {
        return dataEmissione;
    }

    public float getPercentualeTassa() {
        return percentualeTassa;
    }   
    
    public void aggiungiProdotto(Prodotto p){
        if(dimLogica < prodotti.length){
            prodotti[dimLogica] = new Prodotto(p.getCodice(),p.getDescrizione(),(float)p.getCost(),p.getDataProduzione());
            dimLogica++;
        }
        else {
            System.out.println("È stata raggiunto il massimo numero di elementi della fattura. Impossibile aggiungere altri prodotti.");
        }      
    }
    
    public double calcolaImponibile(){
        double imponibile=0;
        
        for(int i = 0; i < dimLogica; i++){
            imponibile+=prodotti[i].getCost();
        }
        
        return imponibile;
    }
    
    public double calcolaTotale(){
        double Totale = this.calcolaImponibile();
        
        return Totale+Totale*this.percentualeTassa;
    }
    
    public String stampaFattura(){
        StringBuffer str = new StringBuffer();
        
        str.append("===== FATTURA ID: ").append(codice).append(" ").append(dataEmissione).append(" =====\n");
            
        str.append("Cliente: ").append(clienteDestinatario.getNome()).
                append(" ").append(clienteDestinatario.getCognome()).append(" | CF: ").
                append(clienteDestinatario.getCodiceFiscale()).append(" | Indirizzo: ").
                append(clienteDestinatario.getIndirizzo()).append("\n");
        
        for (int i = 0; i < dimLogica; i++){
            str.append(prodotti[i].stampaProdotto());
        }
        
        str.append("Da pagare: ").append((float)calcolaTotale()).append(" euro\n");
        
        return str.toString();
    }
}
