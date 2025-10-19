package oop19.prova1.gruppoXX;

public abstract class NetworkDevice implements NetworkInterface {
    private static int nextAddress = 1;
    private final int address;
    protected NetworkInterface connection;

    public NetworkDevice() {
        this.connection = null;
        this.address = nextAddress++;
    }

    public void accept(NetworkInterface sourceInterface, int sourceAddress, int destAddress, String message){
        if (this.getAddress() != destAddress) return;

        this.process(sourceAddress, message);
    }

    public void connect(NetworkInterface other){
        this.connection = other;
    }

    @Override
    public boolean equals(Object other){
        if (other == null || !this.getClass().equals(other.getClass())) return false;

        if (this == other) return true;

        NetworkDevice d = (NetworkDevice) other;
        return this.getAddress() == d.getAddress();

    }

    public int getAddress(){
        return address;
    }

    public NetworkInterface getConnection(){
        return connection;
    }

    @Override
    public int hashCode(){
        return (address < 0) ? -1 : address % 10;
    }

    protected abstract void process(int sourceAddress, String message);

    @Override
    public String toString(){
        return "Device " + address;
    }

}
