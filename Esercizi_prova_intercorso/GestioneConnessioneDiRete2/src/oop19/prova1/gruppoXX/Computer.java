package oop19.prova1.gruppoXX;

public class Computer extends NetworkDevice{
    String name;

    public Computer(String name) {
        super();
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    protected void process(int sourceAddress, String message) {
        System.out.printf("%s ha ricevuto da %d: %s", this.toString(), sourceAddress, message);
    }

    public void remoteFileAccessRequest(int serverAddress, String fileName){
        NetworkInterface connection = this.getConnection();
        if (connection != null){
            int sourceAddress = this.getAddress();

            connection.accept(this, sourceAddress, serverAddress, "Accesso a" + fileName );
        }
        else{
            throw new NetworkException("Il computer " + this.getName() + " non è connesso per inviare la richiesta di accesso al file\n");
        }
    }

    public void remotePrintRequest(int printerAddress, String fileName){
        NetworkInterface connection = this.getConnection();

        if (connection != null){
            int sourceAddress = this.getAddress();

            connection.accept(this, sourceAddress, printerAddress, "Richiesta di stampa del file " + fileName);
        }
        else {
            throw new NetworkException("Il computer " + this.getName() + " non è connesso per inviare la richiesta di stampa del file\n");
        }
    }

    @Override
    public String toString(){
        StringBuffer bf = new StringBuffer(super.toString());
        bf.append(": ").append(name);

        return bf.toString();
    }


}
