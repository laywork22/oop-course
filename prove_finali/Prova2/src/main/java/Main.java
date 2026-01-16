// QUESTO FILE NON VA MODIFICATO
import java.time.LocalDate;


public class Main {
    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {

        EsameSuperato e1 = new EsameSuperato("06127001", LocalDate.of(2023, 2, 28), "OOP", 30);
        EsameSuperato e1bis = new EsameSuperato("06127001", LocalDate.of(2023, 2, 28), "OOP", 18);
        EsameSuperato e2 = new EsameSuperato("06127001", LocalDate.of(2023, 2, 10), "Antenne", 31);
        EsameSuperato e3 = new EsameSuperato("06127001", LocalDate.of(2022, 2, 10), "Lingua inglese", 22);
        EsameSuperato e4 = new EsameSuperato("06127002", LocalDate.of(2023, 2, 28), "OOP", 27);
        EsameSuperato e5 = new EsameSuperato("06127003", LocalDate.of(2023, 2, 28), "OOP", 18);
        EsameSuperato e6 = new EsameSuperato("06127002", LocalDate.of(2023, 2, 10), "Antenne", 27);
        EsameSuperato e7 = new EsameSuperato("06127002", LocalDate.of(2022, 2, 10), "Lingua inglese", 28);
        EsameSuperato e7bis = new EsameSuperato("06127002", LocalDate.of(2022, 2, 10), "Lingua inglese", 18);
        EsameSuperato e8 = new EsameSuperato("06127003", LocalDate.of(2023, 2, 10), "Antenne", 24);
        EsameSuperato e9 = new EsameSuperato("06127003", LocalDate.of(2022, 2, 10), "Lingua inglese", 30);
        Libretto l = new Libretto();
        l.aggiungi(e1);
        l.aggiungi(e1bis);
        l.aggiungi(e2);
        l.aggiungi(e3);
        l.aggiungi(e4);
        l.aggiungi(e5);
        l.aggiungi(e6);
        l.aggiungi(e7);
        l.aggiungi(e7bis);
        l.aggiungi(e8);
        l.aggiungi(e9);
        l.salvaCSV("test_esami_superati.csv");

    }
}