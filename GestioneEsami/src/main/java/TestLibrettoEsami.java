public class TestLibrettoEsami {
    public static void main(String args[]) {
        /*LibrettoEsami libretto = new LibrettoEsami();

        libretto = libretto.caricaDaFile("esami.txt");

        System.out.println("Stampa del LibrettoEsami utilizzando lo stream di caratteri: \n");

        libretto.stampaInsegnamento("Analisi 1");

        System.out.println("\n");
        libretto.stampaMatricola("0612706111");
        System.out.println("\n");

        //libretto.stampaPerInsegnamento();

        //libretto.salvaBinSer("esami.bin");

        //libretto.salvaSuFile("esami_test.txt");*/

        LibrettoEsami libretto2 = new LibrettoEsami();

        libretto2 = libretto2.caricaDaFileScanner("esami.txt");

        libretto2.stampaPerInsegnamento();


    }
}
