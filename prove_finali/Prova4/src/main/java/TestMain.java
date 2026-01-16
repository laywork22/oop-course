/**
 *
 * @author leonardorundo
 */
public class TestMain {

    public static void main(String[] args) {
        
        Trattore tr1 = new Trattore(13456,"TruX", 5000, 2010);
        Trattore tr2 = new Trattore(54321,"ElTractor", 5500, 2021);
        Trattore tr3 = new Trattore(786542,"Inveco", 4800, 2021);
        
        Irrigatore irr1 = new Irrigatore(773344,"Irrigo", 6000, 2015, 8);
                
        Imballatrice imb1 = new Imballatrice(223344,"Sballo", 6200, 2018, 10);
        Imballatrice imb2 = new Imballatrice(786542,"FarmPacker", 6500, 2021, 12);
        
        System.out.println("--- Inizio Simulazione ---");
        tr1.eseguiAzione();
        tr2.eseguiAzione();
        irr1.eseguiAzione();
        imb1.eseguiAzione();
        imb2.eseguiAzione();
        System.out.println("--- Fine Simulazione ---");
        
        GestioneTrattoriGuidaAutonoma myInventarioTrattori = new GestioneTrattoriGuidaAutonoma();
        
        myInventarioTrattori.aggiungiTrattore(tr1);
        myInventarioTrattori.aggiungiTrattore(tr2);
        myInventarioTrattori.aggiungiTrattore(tr3);
        myInventarioTrattori.aggiungiTrattore(irr1);
        myInventarioTrattori.aggiungiTrattore(imb1);
        myInventarioTrattori.aggiungiTrattore(imb2);
        
        myInventarioTrattori.rimuoviTrattore(223344);
        
        System.out.println("\n O||==||O Visita Inventario Trattori a Guida Autonoma O||==||O \n");
        System.out.println(myInventarioTrattori.toString());
        
        // Scrittura su file
        myInventarioTrattori.esportaInventarioTrattori("export.csv");
        // Lettura da file (mediante metodo statico fornito dalla classe Utility)
        System.out.println("--- Stampa file CSV ---");
        Utility.leggiCSV("export.csv");
        
        
        System.out.println("\n--- Import Back-up ---");
        GestioneTrattoriGuidaAutonoma myInventarioTrattori_backup = myInventarioTrattori.importaInventarioTrattori("export.csv");
        System.out.println("\n O||==||O Visita Inventario Trattori a Guida Autonoma (back-up) O||==||O \n");
        System.out.println(myInventarioTrattori_backup.toString());
        
    }
    
}
