package it.unisa.diem.oop.developed.groupxx;

import it.unisa.diem.oop.provided.DeviceFilter;
import it.unisa.diem.oop.provided.Filterable;

import java.util.*;

public class DeviceStore implements Filterable {
    private String name;
    private Set<Device> store;

    public DeviceStore(String name){
        this.name = name;
        this.store = new HashSet<>();
    }

    public DeviceStore(String name, Comparator<Device> c){
        this.name = name;
        this.store = new TreeSet<Device>(c); //si usa TreeSet perchè HashSet non ammette comparatori visto che
                                            //non garantisce alcun ordine degli elementi
    }

    public void addDevice(Device d){
        if (store.equals(d)) {
            throw new DeviceInsertionException("Device già presente\n");
        }

        store.add(d);
    }

    // da implementare
    @Override
    public DeviceStore filter(DeviceFilter d, Comparator<Device> c) {
        DeviceStore ds;

        if (c == null) {
            ds = new DeviceStore(name);
        }
        else {
             ds = new DeviceStore(name, c);
        }

        Iterator<Device> i = this.store.iterator();
        int j=0;

        while(i.hasNext()){
            Device dv = i.next();
            if (d.checkDevice(dv)) {
                ds.store.add(dv);
            }
        }

        return ds;
    }

    @Override
    public String toString(){
        StringBuffer bf = new StringBuffer("\n").append(name).append(" contains ").append(store.size()).append(" items\n");

        Iterator<Device> i = store.iterator();

        while(i.hasNext()){
            bf.append(i.next().toString());
            bf.append("\n*****\n");
        }

        return bf.toString();
    }
}
