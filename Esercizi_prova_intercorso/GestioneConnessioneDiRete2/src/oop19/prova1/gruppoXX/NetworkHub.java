package oop19.prova1.gruppoXX;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class NetworkHub implements NetworkInterface{
    private final String id;
    private final Set<NetworkInterface> interfaceSet;

    public NetworkHub(String id){
        super();
        this.id = id;
        this.interfaceSet = new HashSet<NetworkInterface>();
    }

    public String getId(){
        return id;
    }
    //tutti da implementare
    @Override
    public void accept(NetworkInterface sourceInterface, int sourceAddress, int destAddress, String message) {
        Iterator<NetworkInterface> i = interfaceSet.iterator();

        System.out.printf("Hub %s: da %d via %s per %d: %s\n", this.toString(), sourceAddress, sourceInterface.toString(),destAddress,message);
        //System.out.printf("Hub %s: da %d via %s per %d: %s\n", id, destAddress, sourceInterface.toString(),sourceAddress,message);
        while(i.hasNext()){
            NetworkInterface n = i.next();
            if(!sourceInterface.equals(n)){
                n.accept(this, sourceAddress, destAddress,message); //non deve modificare i campi sourceAddress e destAddress, è un hub
            }
        }
    }

    @Override
    public void connect(NetworkInterface other) {
        if (other != null && !this.equals(other)) interfaceSet.add(other);
    }

    @Override
    public int hashCode(){
        return (id == null) ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(Object other){
        if (other == null || !this.getClass().equals(other.getClass())) return false;

        if (this == other) return true;

        return this.getId().equals(((NetworkHub) other).getId());
    }

    public void printConnections(){
        Iterator <NetworkInterface> i = interfaceSet.iterator();
        System.out.printf("Connessioni di %s:\n", id);

        while(i.hasNext()){
            NetworkInterface n = i.next();
            System.out.println(n.toString());
        }
    }

    @Override
    public String toString(){
        return "Hub " + id;
    }
}
