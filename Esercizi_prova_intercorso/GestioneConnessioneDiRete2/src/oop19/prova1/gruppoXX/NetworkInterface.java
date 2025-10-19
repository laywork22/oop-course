package oop19.prova1.gruppoXX;

public interface NetworkInterface {
    public void accept(NetworkInterface sourceInterface, int sourceAddress, int destAddress, String message);
    public void connect(NetworkInterface other);
}
