package it.unisa.diem.oop.developed.groupxx;

import java.time.LocalDate;

public abstract class Device implements Comparable<Device> {
    private final String serialNumber;
    private final LocalDate releaseDate;
    private final int ramSize;
    private final int storageCapacity;

    public Device (String serialNumber, int year, int month, int dayOfMonth, int ramSize, int storageCapacity){
        this.serialNumber = serialNumber;
        this.releaseDate = LocalDate.of(year, month, dayOfMonth);
        this.ramSize = ramSize;
        this.storageCapacity = storageCapacity;
    }

    public String getSerialNumber(){
        return serialNumber;
    }

    public LocalDate getReleaseDate(){
        return releaseDate;
    }

    public int getRamSize(){
        return ramSize;
    }

    public int getStorageCapacity(){
        return storageCapacity;
    }

    public int hashCode(){
        return (this.getSerialNumber() == null) ? 0 : this.getSerialNumber().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (this == obj) return true;

        if (this.getClass() != obj.getClass()) return false;

        Device d = (Device) obj;

        return this.getSerialNumber().equals(d.getSerialNumber());
    }

    @Override
    public String toString(){
        StringBuffer bf = new StringBuffer();
        bf.append("\nserialNumber= ").append(serialNumber);
        bf.append("\nreleaseDate= ").append(releaseDate.toString());
        bf.append("\nRAMsize= ").append(ramSize).append(" GB");
        bf.append("\nstorageCapacity= ").append(storageCapacity).append(" GB");

        return bf.toString();

    }

    public abstract boolean hasTouchScreen();

    @Override
    public final int compareTo(Device o){
        return this.serialNumber.compareToIgnoreCase(o.serialNumber);
    }
}
