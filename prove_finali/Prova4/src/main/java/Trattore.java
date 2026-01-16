// Importare ulteriori package, ove necessario

public class Trattore  {
    // Attributi
    private int idTelaio;
    private String modello;
    private int peso;
    private int annoImmatricolazione;


    // Costruttore
    
    public Trattore(int idTelaio, String modello, int peso, int annoImmatricolazione) {
        this.idTelaio = idTelaio;
        this.modello = modello;
        this.peso = peso;
        this.annoImmatricolazione = annoImmatricolazione;
    }

    // Getter

    public int getIdTelaio() {
        return idTelaio;
    }

    public String getModello() {
        return modello;
    }

    public int getPeso() {
        return peso;
    }

    public int getAnnoImmatricolazione() {
        return annoImmatricolazione;
    }

    // Comportamenti
    
    public void eseguiAzione() {
        System.out.println("Trattore ID: " + idTelaio);
        System.out.println("Trattore in azione...");
    }

    
    // Override dei metodi
    @Override
    public String toString() {
        return "ID Telaio: " + idTelaio +
                ", Modello: " + modello +
                ", Peso: " + peso +
                ", Anno di Immatricolazione: " + annoImmatricolazione +
                "\n";
    }
}
