package oop19.prova1.gruppoXX;

public class FileServer extends NetworkDevice{
    public FileServer() {
        super();
    }

    @Override
    protected void process(int sourceAddress, String message) {
        NetworkInterface connection = this.getConnection();

        System.out.printf("%s: Su richiesta di %d: %s",this.toString(), sourceAddress, message);

        if (connection != null){
            connection.accept(this, this.getAddress(), sourceAddress, "Dati risposta: "+ message);
        }
        else{
            throw new NetworkException("Il server non è collegato alla rete\n");
        }

    }

    @Override
    public String toString(){
        return "Device " + this.getAddress() + ": File Server";
    }
}
