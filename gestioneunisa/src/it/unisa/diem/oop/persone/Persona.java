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
public class Persona implements  Clonabile<Persona>, Comparable<Persona>{
    private String nome;
    private String cognome;
    private String codiceFiscale;
    
    public Persona(String nome, String cognome, String codiceFiscale){
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
    }
    
    //utilizzando protected solo le sottoclassi della classe che la estendono posso utilizzare il metodo getNome
    //esempio il Main non può accedere al metodo ma Studente si
    //fuori dal package non sono visibili
    /*protected*/ public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }
    
    //quando si ridefinisce un metodo si dice che si fa un override
    @Override /*annotazione=strumento che serve al compilatore per verificare che 
    il nome del metodo da ridefinire già previsto nella superclasse sia scritto bene*/
    public String toString(){
        return "Nome: " + nome + "\nCognome: " + cognome + "\nCodice Fiscale: " + codiceFiscale;
    }

    @Override
    public boolean equals(Object obj) {
        // si verificano prima i casi degeneri
        if (obj == null) return false;

        if (this == obj) return true;

        //si verifica che siano della stessa classe
        //alternativa Object.equals(this,obj)
        if (this.getClass() != obj.getClass()) return false;

        Persona p = (Persona) obj;

        /* siamo all'interno della classe quindi è possibile accedere agli attributi privati di p */
        return this.codiceFiscale == p.codiceFiscale;
    }

    @Override
    public Persona clona(){
        return new Persona(nome,cognome,codiceFiscale);
    }

    @Override
    public int hashCode(){
        int code = (codiceFiscale == null) ? 0 : codiceFiscale.hashCode();

        return code;
    }

    @Override
    public int compareTo(Persona p) { return this.codiceFiscale.compareTo(p.codiceFiscale); }
}
