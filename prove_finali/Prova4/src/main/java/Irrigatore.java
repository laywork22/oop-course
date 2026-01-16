/**
 *
 * @author leonardorundo
 */
public class Irrigatore extends Trattore {
    
    int numeroTestine;
    
    public Irrigatore(int idTelaio, String modello, int peso, int annoImmatricolazione, int numeroTestine) {
        super(idTelaio, modello, peso, annoImmatricolazione);
        this.numeroTestine = numeroTestine;
    }
    
    public int getNumeroTestine(){
        return numeroTestine;
    }
    
    public void eseguiAzione() {
        System.out.println("Irrigatore ID: " + super.getIdTelaio());
        System.out.println("  + in azione...");
    }
    
}
