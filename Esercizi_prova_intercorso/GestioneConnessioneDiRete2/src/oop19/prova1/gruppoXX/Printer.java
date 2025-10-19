package oop19.prova1.gruppoXX;

public class Printer extends NetworkDevice {
    public Printer() {
        super();
    }

    @Override
    protected void process(int sourceAddress, String message) {
        System.out.printf("%s: Su richiesta di %d: %s", this.toString(), sourceAddress, message);
    }

    @Override
    public String toString(){
        return "Device " + this.getAddress() + ": Printer ";
    }
}
