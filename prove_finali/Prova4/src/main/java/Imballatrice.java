/**
 *
 * @author leonardorundo
 */
public class Imballatrice extends Trattore {
    
    int numeroRulli;
    
    public Imballatrice(int idTelaio, String modello, int peso, int annoImmatricolazione, int numeroRulli) {
        super(idTelaio, modello, peso, annoImmatricolazione);
        this.numeroRulli = numeroRulli;
    }
    
    public int getNumeroRulli(){
        return numeroRulli;
    }
    
    @Override
    public void eseguiAzione() {
        System.out.println("Imballatrice ID: " + super.getIdTelaio());
        System.out.println("  + in azione...");
    }
    
}
